package com.wolf.framework.lucene;

import com.wolf.framework.config.FrameworkLoggerEnum;
import com.wolf.framework.logger.LogFactory;
import com.wolf.framework.task.Task;
import com.wolf.framework.task.TaskExecutor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexFileNames;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.MultiReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;

/**
 *
 * @author zoe
 */
public class HdfsLuceneImpl implements HdfsLucene {

    //提交锁
    private volatile boolean commitLock = false;
    //合并锁
    private volatile boolean mergeLock = false;
    //轮转锁
    private volatile boolean rotateLock = false;
    //写入配置对象
    private final IndexWriterConfig iwc;
    //任务处理对象
    private final TaskExecutor taskExecutor;
    //hdfs文件管理对象
    private final FileSystem fileSystem;
    //ip
    private final String ip;
    //根路径
    private final Path rootPath;
    //主索引
    private final Path mainPath;
    private volatile IndexReader mainIndexReader;
    //当前内存索引
    private final Analyzer analyzer;
    private final IndexWriterConfig ramIwc;
    private volatile RAMDirectory RAMDirectory;
    private volatile IndexWriter ramIndexWriter;
    private volatile IndexReader ramIndexReader;
    //当前hdfs索引
    private volatile Path writePath;
    private volatile HdfsDirectory hdfsWriterDir;
    private volatile IndexWriter hdfsIndexWriter;
    //其他只读索引
    Map<String, IndexReader> readOnlyIndexReaderMap = new HashMap<String, IndexReader>(8, 1);
    //组合索引读取对象
    private volatile MultiReader multiReader;
    //日志对象
    private final Logger logger = LogFactory.getLogger(FrameworkLoggerEnum.LUCENE);

    public HdfsLuceneImpl(Path rootPath, FileSystem fileSystem, IndexWriterConfig iwc, TaskExecutor taskExecutor, String ip, Analyzer analyzer) throws IOException {
        this.fileSystem = fileSystem;
        this.rootPath = rootPath;
        this.iwc = iwc;
        this.taskExecutor = taskExecutor;
        this.ip = ip;
        this.analyzer = analyzer;
        //判断rootPath是否存在，否则创建
        if (!this.fileSystem.exists(this.rootPath)) {
            this.fileSystem.mkdirs(this.rootPath);
        }
        Path testPath;
        this.logger.info("load {} main index start...", this.rootPath.getName());
        //构造主索引目录
        this.mainPath = new Path(this.rootPath, "main");
        //判断mainPath是否存在，否则创建
        if (this.fileSystem.exists(this.mainPath)) {
            //主索引目录存在
            //判断是否有可用索引
            testPath = new Path(this.mainPath, IndexFileNames.SEGMENTS_GEN);
            if (this.fileSystem.exists(testPath)) {
                //如果段文件存在，则初始化主索引reader
                HdfsDirectory mainDir = new HdfsDirectory(this.fileSystem, this.mainPath);
                this.mainIndexReader = DirectoryReader.open(mainDir);
            } else {
                this.mainIndexReader = null;
            }
        } else {
            //主索引目录不存在，则创建
            this.fileSystem.mkdirs(this.mainPath);
            this.mainIndexReader = null;
        }
        this.logger.info("load {} main index success", this.rootPath.getName());
        //初始化临时索引目录对象
        this.logger.info("load read only index start...");
        Path tempPath;
        String tempPathName;
        String mainPathName = this.mainPath.getName();
        HdfsDirectory tempDir;
        IndexReader tempIndexReader;
        FileStatus[] fstats = this.fileSystem.listStatus(this.rootPath);
        for (int index = 0; index < fstats.length; index++) {
            tempPath = fstats[index].getPath();
            tempPathName = tempPath.getName();
            if (!tempPathName.equals(mainPathName)) {
                //不为主索引目录
                testPath = new Path(tempPath, IndexFileNames.SEGMENTS_GEN);
                if (this.fileSystem.exists(testPath)) {
                    tempDir = new HdfsDirectory(this.fileSystem, tempPath);
                    tempIndexReader = DirectoryReader.open(tempDir);
                    this.readOnlyIndexReaderMap.put(tempPathName, tempIndexReader);
                } else {
                    this.fileSystem.delete(tempPath, true);
                }
            }
        }
        this.logger.info("load read only index success");
        //创建当前写入目录
        String time = Long.toString(System.currentTimeMillis());
        StringBuilder dicBuilder = new StringBuilder(this.ip.length() + time.length() + 1);
        dicBuilder.append(this.ip).append('-').append(time);
        String dicName = dicBuilder.toString();
        this.writePath = new Path(this.rootPath, dicName);
        hdfsWriterDir = new HdfsDirectory(this.fileSystem, this.writePath);
        this.hdfsIndexWriter = new IndexWriter(hdfsWriterDir, this.iwc);
        this.logger.info("create hdfs index write path:{}", dicName);
        //创建当前内存索引
        this.ramIwc = new IndexWriterConfig(Version.LUCENE_41, this.analyzer);
        this.ramIwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        this.ramIwc.setMaxThreadStates(1);
        this.RAMDirectory = new RAMDirectory();
        this.ramIndexWriter = new IndexWriter(RAMDirectory, this.ramIwc);
        this.ramIndexReader = null;
        //创建组合索引对象
        this.logger.info("create multiReader...");
        this.buildMultiReader();
    }

    /**
     * 重建已有的reader
     */
    private void buildMultiReader() {
        List<IndexReader> indexReaderList = new ArrayList<IndexReader>(this.readOnlyIndexReaderMap.size() + 2);
        if (this.mainIndexReader != null) {
            indexReaderList.add(this.mainIndexReader);
        }
        if (this.ramIndexReader != null) {
            indexReaderList.add(this.ramIndexReader);
        }
        if (!this.readOnlyIndexReaderMap.isEmpty()) {
            Collection<IndexReader> indexReaderCollection = this.readOnlyIndexReaderMap.values();
            indexReaderList.addAll(indexReaderCollection);
        }
        IndexReader[] indexReaders = indexReaderList.toArray(new IndexReader[indexReaderList.size()]);
        this.multiReader = new MultiReader(indexReaders, false);
    }

    void releaseRotateLock() {
        this.rotateLock = false;
    }

    void rotate() {
        this.logger.debug("lucene rotate {} index...", this.rootPath.getName());
        //缓存已有的内存索引对象
        RAMDirectory oldRAMDirectory = this.RAMDirectory;
        IndexWriter oldRamIndexWriter = this.ramIndexWriter;
        IndexReader oldRamIndexReader = this.ramIndexReader;
        //缓存已有的hdfs索引对象
        Path oldWritePath = this.writePath;
        HdfsDirectory oldHdfsWriterDir = this.hdfsWriterDir;
        IndexWriter oldHdfsIndexWriter = this.hdfsIndexWriter;
        try {
            //建立新的内存索引对象
            this.RAMDirectory = new RAMDirectory();
            this.ramIndexWriter = new IndexWriter(RAMDirectory, this.ramIwc);
            this.ramIndexReader = null;
            //建立新的hdfs索引对象
            String time = Long.toString(System.currentTimeMillis());
            StringBuilder dicBuilder = new StringBuilder(this.ip.length() + time.length() + 1);
            dicBuilder.append(this.ip).append('-').append(time);
            String dicName = dicBuilder.toString();
            this.writePath = new Path(this.rootPath, dicName);
            this.hdfsWriterDir = new HdfsDirectory(this.fileSystem, this.writePath);
            this.hdfsIndexWriter = new IndexWriter(this.hdfsWriterDir, this.iwc);
            //提交旧的hdfs索引对象，保证所有数据写入文件系统
            oldHdfsIndexWriter.commit();
            oldHdfsIndexWriter.close();
            //读取旧hdfs索引，放入只读索引集合
            IndexReader readOnlyIndexReader = DirectoryReader.open(oldHdfsWriterDir);
            this.readOnlyIndexReaderMap.put(oldWritePath.getName(), readOnlyIndexReader);
            //刷新组合索引对象
            this.buildMultiReader();
            //关闭旧的内存索引读对象和文件对象
            oldRamIndexReader.close();
            oldRamIndexWriter.close();
            oldRAMDirectory.close();
        } catch (IOException ex) {
            this.logger.error("lucene rotate {} index error...", this.rootPath.getName(), ex);
        }
        //释放轮转锁
        this.releaseRotateLock();
    }

    private void tryToRorateIndexWriter() {
        if (!this.rotateLock) {
            this.rotateLock = true;
            Task task = new LuceneRotateTaskImpl(this);
            this.taskExecutor.submet(task);
        }
    }

    private void reopenIndexReaderWhenOperate() throws IOException {
        //判断如果内存文件大小达到8M，则触发轮转索引
        long size = this.RAMDirectory.sizeInBytes();
        if (size >= 8388608) {
            this.tryToRorateIndexWriter();
        }
        //重新读取当前写入索引目录
        IndexReader oldRamIndexReader = this.ramIndexReader;
        this.ramIndexReader = DirectoryReader.open(this.ramIndexWriter, true);
        //重建组合索引对象
        this.buildMultiReader();
        //关闭旧的写入目录读取索引
        if (oldRamIndexReader != null) {
            oldRamIndexReader.close();
        }
    }

    private void tryToCommtIndexWriter() {
        if (!this.commitLock) {
            this.commitLock = true;
            Task task = new LuceneCommitTaskImpl(this);
            this.taskExecutor.submet(task);
        }
    }

    void commit() {
        this.logger.debug("lucene commit {} index...", this.writePath.getName());
        try {
            this.hdfsIndexWriter.commit();
        } catch (IOException ex) {
            this.logger.error("lucene commit {} index error...", this.writePath.getName(), ex);
        }
        this.releaseCommitLock();
    }

    void releaseCommitLock() {
        this.commitLock = false;
    }

    private void add(Document doc) throws IOException {
        this.hdfsIndexWriter.addDocument(doc);
        this.ramIndexWriter.addDocument(doc);
    }

    @Override
    public void addDocument(Document doc) {
        try {
            //写文件
            this.add(doc);
            //尝试提交
            this.tryToCommtIndexWriter();
            //刷新内存，重新组合索引
            this.reopenIndexReaderWhenOperate();
        } catch (IOException ex) {
            this.logger.error("{} directory addDocument error.see log...", this.rootPath.getName());
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void addDocument(List<Document> docList) {
        try {
            //写文件
            for (Document document : docList) {
                this.add(document);
            }
            //尝试提交
            this.tryToCommtIndexWriter();
            //刷新内存，重新组合索引
            this.reopenIndexReaderWhenOperate();
        } catch (IOException ex) {
            this.logger.error("{} directory batch addDocument error.see log...", this.rootPath.getName());
            throw new RuntimeException(ex);
        }
    }

    private void update(Term term, Document doc) throws IOException {
        this.hdfsIndexWriter.updateDocument(term, doc);
        this.ramIndexWriter.updateDocument(term, doc);
    }

    @Override
    public void updateDocument(Term term, Document doc) {
        try {
            //写文件
            this.update(term, doc);
            //尝试提交
            this.tryToCommtIndexWriter();
            //刷新内存，重新组合索引
            this.reopenIndexReaderWhenOperate();
        } catch (IOException ex) {
            this.logger.error("{} directory updateDocument error.see log...", this.rootPath.getName());
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void updateDocument(Map<Term, Document> updateMap) {
        Set<Entry<Term, Document>> entrySet = updateMap.entrySet();
        try {
            //写文件
            for (Entry<Term, Document> entry : entrySet) {
                this.update(entry.getKey(), entry.getValue());
            }
            //尝试提交
            this.tryToCommtIndexWriter();
            //刷新内存，重新组合索引
            this.reopenIndexReaderWhenOperate();
        } catch (IOException ex) {
            this.logger.error("{} directory batch updateDocument error.see log...", this.rootPath.getName());
            throw new RuntimeException(ex);
        }
    }

    private void delete(Term... terms) throws IOException {
        this.hdfsIndexWriter.deleteDocuments(terms);
        this.ramIndexWriter.deleteDocuments(terms);
    }

    @Override
    public void deleteDocument(Term term) {
        try {
            //写文件
            this.delete(term);
            //尝试提交
            this.tryToCommtIndexWriter();
            //刷新内存，重新组合索引
            this.reopenIndexReaderWhenOperate();
        } catch (IOException ex) {
            this.logger.error("{} directory deleteDocument error.see log...", this.rootPath.getName());
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void deleteDocument(List<Term> termList) {
        Term[] terms = termList.toArray(new Term[termList.size()]);
        try {
            //写文件
            this.delete(terms);
            //尝试提交
            this.tryToCommtIndexWriter();
            //刷新内存，重新组合索引
            this.reopenIndexReaderWhenOperate();
        } catch (IOException ex) {
            this.logger.error("{} directory batch deleteDocuments error.see log...", this.rootPath.getName());
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void tryToMerge() {
        if (!this.mergeLock) {
            this.mergeLock = true;
            Task task = new LuceneMergeTaskImpl(this);
            this.taskExecutor.submet(task);
        }
    }

    void releaseMergeLock() {
        this.mergeLock = false;
    }

    void merge() {
        try {
            //获取当前索引目录，查看是否有临时只读索引目录存在
            FileStatus[] fstats = this.fileSystem.listStatus(this.rootPath);
            if (fstats.length > 1) {
                //获取临时只读索引目录
                Path tempPath;
                String mainPathName = this.mainPath.getName();
                String writePathName = this.writePath.getName();
                String tempHathName;
                List<Path> tempPathList = new ArrayList<Path>(fstats.length - 1);
                Path testPath;
                for (int index = 0; index < fstats.length; index++) {
                    tempPath = fstats[index].getPath();
                    tempHathName = tempPath.getName();
                    if (!tempHathName.equals(mainPathName) && !tempHathName.equals(writePathName)) {
                        //目录名称不是主目录并且不是当前写入目录，则为临时目录
                        //判断是否存在索引
                        testPath = new Path(tempPath, IndexFileNames.SEGMENTS_GEN);
                        if (this.fileSystem.exists(testPath)) {
                            tempPathList.add(tempPath);
                        } else {
                            this.fileSystem.delete(tempPath, true);
                        }
                    }
                }
                //如果存在临时只读目录，则开始合并
                if (!tempPathList.isEmpty()) {
                    List<Directory> tempDirList = new ArrayList<Directory>(tempPathList.size());
                    HdfsDirectory tempDir;
                    for (Path path : tempPathList) {
                        tempDir = new HdfsDirectory(this.fileSystem, path);
                        tempDirList.add(tempDir);
                    }
                    Directory[] tempDirs = tempDirList.toArray(new Directory[tempDirList.size()]);
                    //写入主索引
                    HdfsDirectory mainDir = new HdfsDirectory(this.fileSystem, this.mainPath);
                    IndexWriter mainWriter = new IndexWriter(mainDir, iwc);
                    mainWriter.addIndexes(tempDirs);
                    mainWriter.commit();
                    mainWriter.close();
                    //缓存已有主索引reader和已有的只读reader
                    IndexReader oldMainReader = this.mainIndexReader;
                    Map<String, IndexReader> oldReadOnlyIndexReaderMap = new HashMap<String, IndexReader>(this.readOnlyIndexReaderMap.size(), 1);
                    oldReadOnlyIndexReaderMap.putAll(this.readOnlyIndexReaderMap);
                    //构造新的主索引reader
                    this.mainIndexReader = DirectoryReader.open(mainDir);
                    //清空只读索引
                    this.readOnlyIndexReaderMap.clear();
                    //重建组合索引
                    this.buildMultiReader();
                    //关闭旧的主索引reader
                    if(oldMainReader != null) {
                        oldMainReader.close();
                    }
                    //关闭旧的只读索引
                    for (IndexReader indexReader : oldReadOnlyIndexReaderMap.values()) {
                        indexReader.close();
                    }
                    //删除临时只读目录
                    for (Path path : tempPathList) {
                        this.fileSystem.delete(path, true);
                    }
                }
            }
        } catch (IOException ex) {
            this.logger.error("lucene merge {} index error...", this.rootPath.getName(), ex);
        }
        this.releaseMergeLock();
    }

    private String ScoreDocToString(ScoreDoc scoreDoc) {
        StringBuilder pageIndexBuilder = new StringBuilder(24);
        String doc = Integer.toString(scoreDoc.doc);
        String score = Float.toString(scoreDoc.score);
        pageIndexBuilder.append(doc).append('_').append(score);
        return pageIndexBuilder.toString();
    }

    private ScoreDoc pageIndexToScoreDoc(String pageIndex) {
        ScoreDoc result = null;
        if (!pageIndex.isEmpty()) {
            String[] parameter = pageIndex.split("_");
            if (parameter.length == 2) {
                int doc = Integer.parseInt(parameter[0]);
                float score = Float.parseFloat(parameter[1]);
                result = new ScoreDoc(doc, score);
            }
        }
        return result;
    }

    @Override
    public DocumentResult searchAfter(String pageIndex, Query query, int pageSize) {
        DocumentResultImpl documentResult = new DocumentResultImpl();
        TopDocs results;
        List<Document> docList;
        ScoreDoc lastScoreDoc = null;
        IndexSearcher searcher = new IndexSearcher(this.multiReader);
        ScoreDoc scoreDocAfter = this.pageIndexToScoreDoc(pageIndex);
        try {
            if (scoreDocAfter == null) {
                results = searcher.search(query, pageSize);
            } else {
                results = searcher.searchAfter(scoreDocAfter, query, pageSize);
            }
            ScoreDoc[] hits = results.scoreDocs;
            if (hits.length == 0) {
                docList = new ArrayList<Document>(0);
            } else {
                docList = new ArrayList<Document>(hits.length);
                Document doc;
                for (int index = 0; index < hits.length; index++) {
                    doc = searcher.doc(hits[index].doc);
                    docList.add(doc);
                }
                if (results.totalHits > hits.length && hits.length == pageSize) {
                    //分页
                    lastScoreDoc = hits[hits.length - 1];
                }
            }
        } catch (IOException ex) {
            this.logger.error("{} directory search error.see log...", this.rootPath.getName());
            throw new RuntimeException(ex);
        }
        documentResult.setTotal(results.totalHits);
        documentResult.setResultList(docList);
        documentResult.setPageSize(pageSize);
        if (lastScoreDoc != null) {
            String nextPageIndex = this.ScoreDocToString(lastScoreDoc);
            documentResult.setNextPageIndex(nextPageIndex);
        }
        return documentResult;
    }
}

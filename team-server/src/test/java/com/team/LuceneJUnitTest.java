package com.team;

import com.wolf.framework.lucene.HdfsDirectory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author zoe
 */
public class LuceneJUnitTest {

    public LuceneJUnitTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    //

//    @Test
    public void testHdfsWrite() throws IOException {
        Configuration config = new Configuration();
        String rootPath = "hdfs://192.168.64.50:9000";
        config.set("fs.default.name", rootPath);
        FileSystem dfs = FileSystem.get(config);
        Path src = new Path("/lucene/test");
        src = new Path(src, "main");
        Directory dir = new HdfsDirectory(dfs, src);
        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_41);
        IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_41, analyzer);
        iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
        IndexWriter writer = new IndexWriter(dir, iwc);
        Document doc = new Document();
        Field field = new StringField("key", "1", Field.Store.YES);
        doc.add(field);
        field = new StringField("nickName", "aladdin", Field.Store.YES);
        doc.add(field);
        field = new StringField("userEmail", "aladdin@qq.com", Field.Store.YES);
        doc.add(field);
        field = new TextField("content", "speed", Field.Store.YES);
        doc.add(field);
        writer.addDocument(doc);
        writer.commit();
        //
        doc = new Document();
        field = new StringField("key", "2", Field.Store.YES);
        doc.add(field);
        field = new StringField("nickName", "刘建樱", Field.Store.YES);
        doc.add(field);
        field = new StringField("userEmail", "liujianying@qq.com", Field.Store.YES);
        doc.add(field);
        field = new TextField("content", "moeny", Field.Store.YES);
        doc.add(field);
        writer.addDocument(doc);
        writer.commit();
        //
        doc = new Document();
        field = new StringField("key", "3", Field.Store.YES);
        doc.add(field);
        field = new StringField("nickName", "刘建铭", Field.Store.YES);
        doc.add(field);
        field = new StringField("userEmail", "liujianming@qq.com", Field.Store.YES);
        doc.add(field);
        field = new TextField("content", "gameboy lovely sunshine", Field.Store.YES);
        doc.add(field);
        writer.addDocument(doc);
        writer.commit();
        //
        doc = new Document();
        field = new StringField("key", "4", Field.Store.YES);
        doc.add(field);
        field = new StringField("nickName", "刘榕芳", Field.Store.YES);
        doc.add(field);
        field = new StringField("userEmail", "liurongfang@qq.com", Field.Store.YES);
        doc.add(field);
        field = new TextField("content", "beautiful kindness", Field.Store.YES);
        doc.add(field);
        writer.addDocument(doc);
        writer.commit();
        //
        doc = new Document();
        field = new StringField("key", "5", Field.Store.YES);
        doc.add(field);
        field = new StringField("nickName", "刘正秀", Field.Store.YES);
        doc.add(field);
        field = new StringField("userEmail", "liuzhengxiu@qq.com", Field.Store.YES);
        doc.add(field);
        field = new TextField("content", "honest great", Field.Store.YES);
        doc.add(field);
        writer.addDocument(doc);
        writer.commit();
        //
        doc = new Document();
        field = new StringField("key", "6", Field.Store.YES);
        doc.add(field);
        field = new StringField("nickName", "朱礼琼", Field.Store.YES);
        doc.add(field);
        field = new StringField("userEmail", "zhuliqiong@qq.com", Field.Store.YES);
        doc.add(field);
        field = new TextField("content", "beautiful kindness great", Field.Store.YES);
        doc.add(field);
        writer.addDocument(doc);
        writer.commit();
        //
        doc = new Document();
        field = new StringField("key", "7", Field.Store.YES);
        doc.add(field);
        field = new StringField("nickName", "郑剑萍", Field.Store.YES);
        doc.add(field);
        field = new StringField("userEmail", "zhengjianping@qq.com", Field.Store.YES);
        doc.add(field);
        field = new TextField("content", "beautiful lovely", Field.Store.YES);
        doc.add(field);
        writer.addDocument(doc);
        writer.commit();
        writer.close();
    }

//    @Test
    public void testHdfsRead() throws IOException {
        Configuration config = new Configuration();
        String rootPath = "hdfs://192.168.64.50:9000";
        config.set("fs.default.name", rootPath);
        FileSystem dfs = FileSystem.get(config);
        Path src = new Path("/lucene/test");
        src = new Path(src, "main");
        Directory dir = new HdfsDirectory(dfs, src);
        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_41);
        IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_41, analyzer);
        iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
        IndexWriter writer = new IndexWriter(dir, iwc);
        IndexReader reader = DirectoryReader.open(writer, true);
        Document doc = new Document();
        Field field = new StringField("key", "1", Field.Store.YES);
        doc.add(field);
        field = new StringField("nickName", "aladdin", Field.Store.YES);
        doc.add(field);
        field = new StringField("userEmail", "aladdin@qq.com", Field.Store.YES);
        doc.add(field);
        field = new TextField("content", "speed", Field.Store.YES);
        doc.add(field);
        writer.addDocument(doc);
        System.out.println(writer.ramSizeInBytes());
        //
        doc = new Document();
        field = new StringField("key", "2", Field.Store.YES);
        doc.add(field);
        field = new StringField("nickName", "刘建樱", Field.Store.YES);
        doc.add(field);
        field = new StringField("userEmail", "liujianying@qq.com", Field.Store.YES);
        doc.add(field);
        field = new TextField("content", "moeny", Field.Store.YES);
        doc.add(field);
        writer.addDocument(doc);
        System.out.println(writer.ramSizeInBytes());
        //
        doc = new Document();
        field = new StringField("key", "3", Field.Store.YES);
        doc.add(field);
        field = new StringField("nickName", "刘建铭", Field.Store.YES);
        doc.add(field);
        field = new StringField("userEmail", "liujianming@qq.com", Field.Store.YES);
        doc.add(field);
        field = new TextField("content", "gameboy lovely sunshine", Field.Store.YES);
        doc.add(field);
        writer.addDocument(doc);
        System.out.println(writer.ramSizeInBytes());
        //
        doc = new Document();
        field = new StringField("key", "4", Field.Store.YES);
        doc.add(field);
        field = new StringField("nickName", "刘榕芳", Field.Store.YES);
        doc.add(field);
        field = new StringField("userEmail", "liurongfang@qq.com", Field.Store.YES);
        doc.add(field);
        field = new TextField("content", "beautiful kindness", Field.Store.YES);
        doc.add(field);
        writer.addDocument(doc);
        System.out.println(writer.ramSizeInBytes());
        //
        doc = new Document();
        field = new StringField("key", "5", Field.Store.YES);
        doc.add(field);
        field = new StringField("nickName", "刘正秀", Field.Store.YES);
        doc.add(field);
        field = new StringField("userEmail", "liuzhengxiu@qq.com", Field.Store.YES);
        doc.add(field);
        field = new TextField("content", "honest great", Field.Store.YES);
        doc.add(field);
        writer.addDocument(doc);
        System.out.println(writer.ramSizeInBytes());
        //
        doc = new Document();
        field = new StringField("key", "6", Field.Store.YES);
        doc.add(field);
        field = new StringField("nickName", "朱礼琼", Field.Store.YES);
        doc.add(field);
        field = new StringField("userEmail", "zhuliqiong@qq.com", Field.Store.YES);
        doc.add(field);
        field = new TextField("content", "beautiful kindness great", Field.Store.YES);
        doc.add(field);
        writer.addDocument(doc);
        System.out.println(writer.ramSizeInBytes());
        //
        doc = new Document();
        field = new StringField("key", "7", Field.Store.YES);
        doc.add(field);
        field = new StringField("nickName", "郑剑萍", Field.Store.YES);
        doc.add(field);
        field = new StringField("userEmail", "zhengjianping@qq.com", Field.Store.YES);
        doc.add(field);
        field = new TextField("content", "beautiful lovely", Field.Store.YES);
        doc.add(field);
        writer.addDocument(doc);
        System.out.println(writer.ramSizeInBytes());
        System.out.println("-----------------------------1");
        IndexSearcher searcher = new IndexSearcher(reader);
        TermQuery termQuery = new TermQuery(new Term("content", "lovely"));
        TopDocs results = searcher.search(termQuery, 5);
        ScoreDoc[] hits = results.scoreDocs;
        for (int index = 0; index < hits.length; index++) {
            doc = searcher.doc(hits[index].doc);
            System.out.println(doc.toString());
        }
        System.out.println(writer.ramSizeInBytes());
        System.out.println("-----------------------------2");
        reader = DirectoryReader.open(writer, true);
        searcher = new IndexSearcher(reader);
        results = searcher.search(termQuery, 5);
        hits = results.scoreDocs;
        for (int index = 0; index < hits.length; index++) {
            doc = searcher.doc(hits[index].doc);
            System.out.println(doc.toString());
        }
        writer.commit();
        System.out.println(writer.ramSizeInBytes());
        System.out.println("-----------------------------3");
        reader = DirectoryReader.open(writer, true);
        searcher = new IndexSearcher(reader);
        results = searcher.search(termQuery, 5);
        hits = results.scoreDocs;
        for (int index = 0; index < hits.length; index++) {
            doc = searcher.doc(hits[index].doc);
            System.out.println(doc.toString());
        }
        System.out.println("-----------------------------4");
        doc = new Document();
        field = new StringField("key", "8", Field.Store.YES);
        doc.add(field);
        field = new StringField("nickName", "郑剑义", Field.Store.YES);
        doc.add(field);
        field = new StringField("userEmail", "zhengjianyi@qq.com", Field.Store.YES);
        doc.add(field);
        field = new TextField("content", "beautiful lovely", Field.Store.YES);
        doc.add(field);
        writer.addDocument(doc);
        System.out.println(writer.ramSizeInBytes());
        reader.close();
    }

    @Test
    public void testDelete() throws IOException {
        Configuration config = new Configuration();
        String rootPath = "hdfs://192.168.64.50:9000";
        config.set("fs.default.name", rootPath);
        FileSystem dfs = FileSystem.get(config);
        Path src = new Path("/lucene/user/main");
        dfs.delete(src, true);
    }

//    @Test
    public void testMerge() throws IOException {
        Configuration config = new Configuration();
        String rootPath = "hdfs://192.168.64.50:9000";
        config.set("fs.default.name", rootPath);
        FileSystem dfs = FileSystem.get(config);
        Path root = new Path("/lucene/user");
        Path main = new Path(root, "main");
        FileStatus[] fstats = dfs.listStatus(root);
        Path tempPath;
        HdfsDirectory tempDir;
        List<Path> tempPathList = new ArrayList<Path>(fstats.length);
        List<Directory> tempDirList = new ArrayList<Directory>(fstats.length);
        for (int index = 0; index < fstats.length; index++) {
            tempPath = fstats[index].getPath();
            if (!tempPath.getName().equals(main.getName())) {
                tempPathList.add(tempPath);
                tempDir = new HdfsDirectory(dfs, tempPath);
                tempDirList.add(tempDir);
            }
        }
        if (!tempDirList.isEmpty()) {
            HdfsDirectory mainDir = new HdfsDirectory(dfs, main);
            Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_41);
            IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_41, analyzer);
            iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
            IndexWriter mainWriter = new IndexWriter(mainDir, iwc);
            Directory[] tempDirs = tempDirList.toArray(new Directory[tempDirList.size()]);
            mainWriter.addIndexes(tempDirs);
            mainWriter.commit();
            mainWriter.close();
            for (Path path : tempPathList) {
                dfs.deleteOnExit(path);
            }
        }
    }

//    @Test
    public void testMultiWriter() throws IOException {
        final Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_41);
        IndexWriterConfig iwc1 = new IndexWriterConfig(Version.LUCENE_41, analyzer);
        iwc1.setOpenMode(OpenMode.CREATE_OR_APPEND);
        iwc1.setMaxThreadStates(1);
        //
        RAMDirectory RAMDirectory1 = new RAMDirectory();
        IndexWriter indexWriter1 = new IndexWriter(RAMDirectory1, iwc1);
        IndexReader indexReader1 = null;
        //
        IndexWriterConfig iwc2 = new IndexWriterConfig(Version.LUCENE_41, analyzer);
        iwc2.setOpenMode(OpenMode.CREATE_OR_APPEND);
        iwc2.setMaxThreadStates(1);
        RAMDirectory RAMDirectory2 = new RAMDirectory();
        IndexWriter indexWriter2 = new IndexWriter(RAMDirectory2, iwc2);
        IndexReader indexReader2 = null;
        //
        IndexWriterConfig iwc3 = new IndexWriterConfig(Version.LUCENE_41, analyzer);
        iwc3.setOpenMode(OpenMode.CREATE_OR_APPEND);
        iwc3.setMaxThreadStates(1);
        RAMDirectory RAMDirectory3 = new RAMDirectory();
        IndexWriter indexWriter3 = new IndexWriter(RAMDirectory3, iwc3);
        IndexReader indexReader3 = null;
        Document doc;
        Field field;
        String name;
        for (int i = 0; i < 10000; i++) {
            name = Integer.toString(i);
//            System.out.println(name);
            doc = new Document();
            field = new StringField("key", name, Field.Store.YES);
            doc.add(field);
            field = new StringField("nickName", name, Field.Store.YES);
            doc.add(field);
            indexWriter1.addDocument(doc);
            indexWriter1.commit();
            //
            indexWriter2.addDocument(doc);
            indexWriter2.commit();
            //
            indexWriter3.addDocument(doc);
            indexWriter3.commit();
            if (indexReader1 != null) {
                indexReader1.close();
            }
            indexReader1 = DirectoryReader.open(indexWriter1, true);
            //
            if (indexReader2 != null) {
                indexReader2.close();
            }
            indexReader2 = DirectoryReader.open(indexWriter2, true);
            //
            if (indexReader3 != null) {
                indexReader3.close();
            }
            indexReader3 = DirectoryReader.open(indexWriter3, true);
        }
    }

//    @Test
    public void testRamSize() throws IOException {
        RAMDirectory dir = new RAMDirectory();
        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_41);
        IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_41, analyzer);
        iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
        IndexWriter writer = new IndexWriter(dir, iwc);
        Document doc = new Document();
        Field field = new StringField("key", "1", Field.Store.YES);
        doc.add(field);
        field = new StringField("nickName", "aladdin", Field.Store.YES);
        doc.add(field);
        field = new StringField("userEmail", "aladdin@qq.com", Field.Store.YES);
        doc.add(field);
        field = new TextField("content", "speed", Field.Store.YES);
        doc.add(field);
        writer.addDocument(doc);
        System.out.println(dir.sizeInBytes());
        System.out.println(writer.ramSizeInBytes());
        //
        doc = new Document();
        field = new StringField("key", "2", Field.Store.YES);
        doc.add(field);
        field = new StringField("nickName", "刘建樱", Field.Store.YES);
        doc.add(field);
        field = new StringField("userEmail", "liujianying@qq.com", Field.Store.YES);
        doc.add(field);
        field = new TextField("content", "moeny", Field.Store.YES);
        doc.add(field);
        writer.addDocument(doc);
        System.out.println(dir.sizeInBytes());
        System.out.println(writer.ramSizeInBytes());
        //
        doc = new Document();
        field = new StringField("key", "3", Field.Store.YES);
        doc.add(field);
        field = new StringField("nickName", "刘建铭", Field.Store.YES);
        doc.add(field);
        field = new StringField("userEmail", "liujianming@qq.com", Field.Store.YES);
        doc.add(field);
        field = new TextField("content", "gameboy lovely sunshine", Field.Store.YES);
        doc.add(field);
        writer.addDocument(doc);
        System.out.println(dir.sizeInBytes());
        System.out.println(writer.ramSizeInBytes());
        //
        doc = new Document();
        field = new StringField("key", "4", Field.Store.YES);
        doc.add(field);
        field = new StringField("nickName", "刘榕芳", Field.Store.YES);
        doc.add(field);
        field = new StringField("userEmail", "liurongfang@qq.com", Field.Store.YES);
        doc.add(field);
        field = new TextField("content", "beautiful kindness", Field.Store.YES);
        doc.add(field);
        writer.addDocument(doc);
        System.out.println(dir.sizeInBytes());
        System.out.println(writer.ramSizeInBytes());
        //
        doc = new Document();
        field = new StringField("key", "5", Field.Store.YES);
        doc.add(field);
        field = new StringField("nickName", "刘正秀", Field.Store.YES);
        doc.add(field);
        field = new StringField("userEmail", "liuzhengxiu@qq.com", Field.Store.YES);
        doc.add(field);
        field = new TextField("content", "honest great", Field.Store.YES);
        doc.add(field);
        writer.addDocument(doc);
        System.out.println(dir.sizeInBytes());
        System.out.println(writer.ramSizeInBytes());
        //
        doc = new Document();
        field = new StringField("key", "6", Field.Store.YES);
        doc.add(field);
        field = new StringField("nickName", "朱礼琼", Field.Store.YES);
        doc.add(field);
        field = new StringField("userEmail", "zhuliqiong@qq.com", Field.Store.YES);
        doc.add(field);
        field = new TextField("content", "beautiful kindness great", Field.Store.YES);
        doc.add(field);
        writer.addDocument(doc);
        System.out.println(dir.sizeInBytes());
        System.out.println(writer.ramSizeInBytes());
        System.out.println("-----------------------------1-reader");
        IndexReader indexReader = DirectoryReader.open(writer, true);
        System.out.println(dir.sizeInBytes());
        System.out.println(writer.ramSizeInBytes());
        System.out.println("-----------------------------2-commit");
        writer.commit();
        System.out.println(dir.sizeInBytes());
        System.out.println(writer.ramSizeInBytes());
        System.out.println("-----------------------------4---add new");
        //
        doc = new Document();
        field = new StringField("key", "7", Field.Store.YES);
        doc.add(field);
        field = new StringField("nickName", "郑剑萍", Field.Store.YES);
        doc.add(field);
        field = new StringField("userEmail", "zhengjianping@qq.com", Field.Store.YES);
        doc.add(field);
        field = new TextField("content", "beautiful lovely", Field.Store.YES);
        doc.add(field);
        writer.addDocument(doc);
        System.out.println(dir.sizeInBytes());
        System.out.println(writer.ramSizeInBytes());
        //
        System.out.println("-----------------------------5---delete");
        writer.deleteAll();
        System.out.println(dir.sizeInBytes());
        System.out.println(writer.ramSizeInBytes());
        //
        System.out.println("-----------------------------6---delete commit");
        writer.commit();
        System.out.println(dir.sizeInBytes());
        System.out.println(writer.ramSizeInBytes());
    }
}

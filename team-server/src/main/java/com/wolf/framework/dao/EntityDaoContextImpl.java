package com.wolf.framework.dao;

import com.wolf.framework.cache.DefaultCacheConfiguration;
import com.wolf.framework.config.FrameworkLoggerEnum;
import com.wolf.framework.dao.cache.InquireCache;
import com.wolf.framework.dao.cache.InquireCacheImpl;
import com.wolf.framework.hbase.HTableHandler;
import com.wolf.framework.logger.LogFactory;
import com.wolf.framework.task.TaskExecutor;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.SerialMergeScheduler;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;

/**
 * 全局信息构造类
 *
 * @author zoe
 */
public class EntityDaoContextImpl<T extends Entity> implements EntityDaoContext<T> {

    //缓存管理对象
    private final CacheManager cacheManager;
    private final HTableHandler hTableHandler;
    private final Map<String, String> existClassMap = new HashMap<String, String>(128);
    private final FileSystem fileSystem;
    private final String indexRoot = "/lucene";
    private final Analyzer analyzer;
    private final IndexWriterConfig iwc;
    private final TaskExecutor taskExecutor;
    private final String ip;
    //entity处理类集合
    private final Map<Class<T>, EntityDao<T>> entityDaoMap;

    @Override
    public final CacheManager getCacheManager() {
        return cacheManager;
    }
    //sql查询缓存对象
    private InquireCache inquireCache;

    @Override
    public final InquireCache getInquireCache() {
        return this.inquireCache;
    }

    @Override
    public final void putEntityDao(final Class<T> clazz, final EntityDao<T> entityDao, final String entityName) {
        if (this.entityDaoMap.containsKey(clazz)) {
            String existClassName = this.existClassMap.get(entityName);
            if (existClassName == null) {
                existClassName = "NULL";
            }
            StringBuilder errBuilder = new StringBuilder(1024);
            errBuilder.append("There was an error putting entityDao. Cause: entityName reduplicated : ")
                    .append(entityName).append("\n").append("exist class : ").append(existClassName).append("\n")
                    .append("this class : ").append(clazz.getName());
            throw new RuntimeException(errBuilder.toString());
        }
        this.entityDaoMap.put(clazz, entityDao);
        this.existClassMap.put(entityName, clazz.getName());
    }

    @Override
    public Map<Class<T>, EntityDao<T>> getEntityDaoMap() {
        return Collections.unmodifiableMap(this.entityDaoMap);
    }

    /**
     * 构造函数
     *
     * @param properties
     */
    public EntityDaoContextImpl(final HTableHandler hTableHandler, final CacheManager cacheManager, final FileSystem fileSystem, final TaskExecutor taskExecutor, final String ip) {
        this.entityDaoMap = new HashMap<Class<T>, EntityDao<T>>(64, 1);
        this.hTableHandler = hTableHandler;
        this.cacheManager = cacheManager;
        this.fileSystem = fileSystem;
        this.taskExecutor = taskExecutor;
        this.ip = ip;
        //创建sql cache
        final CacheConfiguration cacheConfig = new DefaultCacheConfiguration().getDefault();
        String uuid = UUID.randomUUID().toString();
        String inquireAndCountCacheName = "InquireAndCount-cache-".concat(uuid);
        cacheConfig.name(inquireAndCountCacheName).maxEntriesLocalHeap(20000);
        final Cache cache = new Cache(cacheConfig);
        this.cacheManager.addCache(cache);
        this.inquireCache = new InquireCacheImpl(cache);
        //检测lucene索引目录是否存在，如果不存在，则创建
        Path indexRootPath = new Path(this.indexRoot);
        try {
            boolean flag = this.fileSystem.exists(indexRootPath);
            if (!flag) {
                this.fileSystem.mkdirs(indexRootPath);
            }
        } catch (IOException ex) {
            Logger logger = LogFactory.getLogger(FrameworkLoggerEnum.DAO);
            logger.error("DAO:create lucene index directory error...see log");
            throw new RuntimeException(ex);
        }
        //创建索引分词对象和写入配置对象
        this.analyzer = new StandardAnalyzer(Version.LUCENE_41);
        this.iwc = new IndexWriterConfig(Version.LUCENE_41, analyzer);
        this.iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        this.iwc.setMaxThreadStates(1);
        this.iwc.setMergeScheduler(new SerialMergeScheduler());
    }

    @Override
    public boolean assertExistEntity(final Class<T> clazz) {
        return this.entityDaoMap.containsKey(clazz);
    }

    @Override
    public EntityDao getEntityDao(final Class<T> clazz) {
        return this.entityDaoMap.get(clazz);
    }

    @Override
    public HTableHandler getHTableHandler() {
        return this.hTableHandler;
    }

    @Override
    public FileSystem getFileSystem() {
        return this.fileSystem;
    }

    @Override
    public String getIndexRoot() {
        return this.indexRoot;
    }

    @Override
    public Analyzer getAnalyzer() {
        return this.analyzer;
    }

    @Override
    public IndexWriterConfig getIndexWriterConfig() {
        return this.iwc;
    }

    @Override
    public TaskExecutor getTaskExecutor() {
        return this.taskExecutor;
    }

    @Override
    public String getIP() {
        return this.ip;
    }
}

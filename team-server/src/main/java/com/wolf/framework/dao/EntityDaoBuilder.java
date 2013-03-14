package com.wolf.framework.dao;

import com.wolf.framework.cache.DefaultCacheConfiguration;
import com.wolf.framework.config.FrameworkLoggerEnum;
import com.wolf.framework.dao.cache.InquireCache;
import com.wolf.framework.dao.delete.DeleteDataHandlerImpl;
import com.wolf.framework.dao.delete.DeleteEntityCacheHandlerImpl;
import com.wolf.framework.dao.delete.DeleteHandler;
import com.wolf.framework.dao.delete.DeleteInquireCacheHandlerImpl;
import com.wolf.framework.dao.inquire.InquireByConditionFilterHandlerImpl;
import com.wolf.framework.dao.inquire.InquireByConditionFromCacheHandlerImpl;
import com.wolf.framework.dao.inquire.InquireByConditionFromDataHandlerImpl;
import com.wolf.framework.dao.inquire.InquireByConditionHandler;
import com.wolf.framework.dao.inquire.InquireByKeyFromCacheHandlerImpl;
import com.wolf.framework.dao.inquire.InquireByKeyFromDataHandlerImpl;
import com.wolf.framework.dao.inquire.InquireByKeyHandler;
import com.wolf.framework.dao.inquire.InquireKeyByConditionFilterHandlerImpl;
import com.wolf.framework.dao.inquire.InquireKeyByConditionFromCacheHandlerImpl;
import com.wolf.framework.dao.inquire.InquireKeyByConditionFromDataHandlerImpl;
import com.wolf.framework.dao.inquire.InquireKeyByConditionHandler;
import com.wolf.framework.dao.insert.InsertCacheHandlerImpl;
import com.wolf.framework.dao.insert.InsertDataHandlerImpl;
import com.wolf.framework.dao.insert.InsertHandler;
import com.wolf.framework.dao.parser.ColumnHandler;
import com.wolf.framework.dao.parser.KeyHandler;
import com.wolf.framework.dao.update.UpdateDataHandlerImpl;
import com.wolf.framework.dao.update.UpdateEntityCacheHandlerImpl;
import com.wolf.framework.dao.update.UpdateHandler;
import com.wolf.framework.dao.update.UpdateInquireCacheHandlerImpl;
import com.wolf.framework.logger.LogFactory;
import com.wolf.framework.lucene.DeleteFilterCache;
import com.wolf.framework.lucene.HdfsLucene;
import com.wolf.framework.lucene.HdfsLuceneImpl;
import com.wolf.framework.task.TaskExecutor;
import java.io.IOException;
import java.util.List;
import net.sf.ehcache.Cache;
import net.sf.ehcache.config.CacheConfiguration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.lucene.index.IndexWriterConfig;
import org.slf4j.Logger;

/**
 * 实体数据访问对象创建类
 *
 * @author aladdin
 */
public final class EntityDaoBuilder<T extends Entity> {

    //table name
    private final String tableName;
    //key
    private final KeyHandler keyHandler;
    //column
    private final List<ColumnHandler> columnHandlerList;
    //实体class
    private final Class<T> clazz;
    //是否使用缓存
    private final boolean useCache;
    private final int maxEntriesLocalHeap;
    private final int timeToIdleSeconds;
    private final int timeToLiveSeconds;
    //
    private final EntityDaoContext<T> entityDaoContext;

    public EntityDaoBuilder(String tableName, KeyHandler keyHandler, List<ColumnHandler> columnHandlerList, Class<T> clazz, boolean useCache, int maxEntriesLocalHeap, int timeToIdleSeconds, int timeToLiveSeconds, EntityDaoContext<T> entityDaoContext) {
        this.tableName = tableName;
        this.keyHandler = keyHandler;
        this.columnHandlerList = columnHandlerList;
        this.clazz = clazz;
        this.useCache = useCache;
        this.maxEntriesLocalHeap = maxEntriesLocalHeap;
        this.timeToIdleSeconds = timeToIdleSeconds;
        this.timeToLiveSeconds = timeToLiveSeconds;
        this.entityDaoContext = entityDaoContext;
    }

    public EntityDao<T> build() {
        if (this.tableName == null || this.tableName.isEmpty()) {
            throw new RuntimeException("There was an error building entityDao. Cause: tableName is null or empty");
        }
        if (this.clazz == null) {
            throw new RuntimeException("There was an error building entityDao. Cause: clazz is null");
        }
        if (this.keyHandler == null) {
            throw new RuntimeException("There was an error building entityDao. Cause: key is null");
        }
        if (this.columnHandlerList == null || this.columnHandlerList.isEmpty()) {
            throw new RuntimeException("There was an error building entityDao. Cause: columns null or empty");
        }
        //创建lucene处理对象
        final HdfsLucene hdfsLucene;
        //检测对应的索引目录是否存在
        final String indexRoot = this.entityDaoContext.getIndexRoot();
        final String tableIndexDir = indexRoot + "/" + this.tableName.toLowerCase();
        final FileSystem fileSystem = this.entityDaoContext.getFileSystem();
        Path tableIndexPath = new Path(tableIndexDir);
        try {
            boolean flag = fileSystem.exists(tableIndexPath);
            if (!flag) {
                fileSystem.mkdirs(tableIndexPath);
            }
            //创建索引管理对象
            IndexWriterConfig iwc = this.entityDaoContext.getIndexWriterConfig();
            IndexWriterConfig ramIwc = this.entityDaoContext.getRamIndexWriterConfig();
            TaskExecutor taskExecutor = this.entityDaoContext.getTaskExecutor();
            String ip = this.entityDaoContext.getIP();
            DeleteFilterCache deleteFilterCache = this.entityDaoContext.getDeleteFilterCache();
            hdfsLucene = new HdfsLuceneImpl(this.keyHandler.getName(), tableIndexPath, fileSystem, iwc, ramIwc, taskExecutor, ip, deleteFilterCache);
        } catch (IOException ex) {
            Logger logger = LogFactory.getLogger(FrameworkLoggerEnum.DAO);
            logger.error("DAO:create table:{} index directory error...see log", this.tableName);
            throw new RuntimeException(ex);
        }
        this.entityDaoContext.getApplicationContext().addHdfsLuceneList(hdfsLucene);
        //获取查询缓存对象
        final InquireCache inquireCache = entityDaoContext.getInquireCache();
        //初始化实体缓存
        Cache entityCache = null;
        if (this.useCache) {
            //获取实体缓存对象
            if (entityDaoContext.getCacheManager().cacheExists(tableName)) {
                StringBuilder mesBuilder = new StringBuilder(512);
                mesBuilder.append("There was an error parsing entity cache. Cause: exist cache name : ").append(tableName);
                mesBuilder.append("\n").append("error class is ").append(clazz.getName());
                throw new RuntimeException(mesBuilder.toString());
            }
            final CacheConfiguration entityCacheConfig = new DefaultCacheConfiguration().getDefault();
            entityCacheConfig.name(tableName).maxEntriesLocalHeap(maxEntriesLocalHeap)
                    .timeToIdleSeconds(timeToIdleSeconds).timeToLiveSeconds(timeToLiveSeconds);
            entityCache = new Cache(entityCacheConfig);
            entityDaoContext.getCacheManager().addCache(entityCache);
        }
        //
        //---------------------------构造根据key查询数据库entity处理对象
        InquireByKeyHandler<T> inquireByKeyHandler = new InquireByKeyFromDataHandlerImpl<T>(
                hdfsLucene,
                tableName,
                this.clazz,
                this.columnHandlerList,
                this.keyHandler);
        if (entityCache != null) {
            //构造根据key查询缓存处理对象
            inquireByKeyHandler = new InquireByKeyFromCacheHandlerImpl<T>(inquireByKeyHandler, entityCache);
        }
        //----------------------------------构造数据增、删、改操作对象
        //构造插入数据库处理对象
        InsertHandler<T> insertHandler = new InsertDataHandlerImpl<T>(
                hdfsLucene,
                tableName,
                this.clazz,
                this.columnHandlerList,
                this.keyHandler);
        //构造更新数据库处理对象
        UpdateHandler updateHandler = new UpdateDataHandlerImpl(
                hdfsLucene,
                tableName,
                this.clazz,
                this.columnHandlerList,
                this.keyHandler);
        //构造删除数据库处理对象
        DeleteHandler deleteHandler = new DeleteDataHandlerImpl(hdfsLucene);
        //----------------------------构造删、改实体缓存处理对象
        if (entityCache != null) {
            //构造更新数据时，实体缓存处理对象
            updateHandler = new UpdateEntityCacheHandlerImpl(entityCache, this.keyHandler, updateHandler);
            //构造删除数据时，实体缓存处理对象
            deleteHandler = new DeleteEntityCacheHandlerImpl(entityCache, deleteHandler);
        }
        //----------------------------构造增、删、改查询缓存处理对象
        //构造插入数据缓存处理对象
        insertHandler = new InsertCacheHandlerImpl<T>(inquireCache, this.tableName, insertHandler);
        //构造更新数据缓存处理对象
        updateHandler = new UpdateInquireCacheHandlerImpl(inquireCache, this.tableName, updateHandler);
        //构造删除数据缓存处理对象
        deleteHandler = new DeleteInquireCacheHandlerImpl(inquireCache, this.tableName, deleteHandler);
        //
        //-----------------------------构造根据条件查询key集合处理对象
        //构造根据条件查询key集合数据库处理对象
        InquireKeyByConditionHandler inquireKeyByConditionHandler = new InquireKeyByConditionFromDataHandlerImpl(
                this.keyHandler,
                hdfsLucene);
        //构造根据条件查询key集合缓存处理对象
        inquireKeyByConditionHandler = new InquireKeyByConditionFromCacheHandlerImpl(
                this.tableName,
                inquireCache,
                inquireKeyByConditionHandler);
        //够在根据条件查询key集合条件过滤对象
        inquireKeyByConditionHandler = new InquireKeyByConditionFilterHandlerImpl(
                inquireKeyByConditionHandler,
                this.columnHandlerList);
        //
        //-----------------------------构造根据条件查询entity集合处理对象
        //根据条件查询数据库entity集合处理对象
        InquireByConditionHandler<T> inquireByConditionHandler = new InquireByConditionFromDataHandlerImpl<T>(
                hdfsLucene,
                tableName,
                this.clazz,
                this.columnHandlerList,
                this.keyHandler);
        //根据条件查询缓存entity处理对象
        inquireByConditionHandler = new InquireByConditionFromCacheHandlerImpl<T>(
                this.tableName,
                this.entityDaoContext.getInquireCache(),
                inquireByKeyHandler,
                inquireByConditionHandler);
        //条件查询，有效条件过滤对象
        inquireByConditionHandler = new InquireByConditionFilterHandlerImpl<T>(
                this.columnHandlerList,
                inquireByConditionHandler);

        EntityDao<T> entityDao = new EntityDaoImpl(
                insertHandler,
                updateHandler,
                deleteHandler,
                inquireByKeyHandler,
                inquireByConditionHandler,
                inquireKeyByConditionHandler);
        return entityDao;
    }
}

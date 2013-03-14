package com.wolf.framework.dao;

import com.wolf.framework.context.ApplicationContext;
import com.wolf.framework.dao.cache.InquireCache;
import com.wolf.framework.hbase.HTableHandler;
import com.wolf.framework.lucene.DeleteFilterCache;
import com.wolf.framework.task.TaskExecutor;
import java.util.Map;
import net.sf.ehcache.CacheManager;
import org.apache.hadoop.fs.FileSystem;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriterConfig;

/**
 *
 * @author aladdin
 */
public interface EntityDaoContext<T extends Entity> {

    public InquireCache getInquireCache();
    
    public DeleteFilterCache getDeleteFilterCache();

    public CacheManager getCacheManager();

    public void putEntityDao(final Class<T> clazz, final EntityDao<T> entityDao, final String entityName);

    public EntityDao getEntityDao(final Class<T> clazz);

    public Map<Class<T>, EntityDao<T>> getEntityDaoMap();

    public boolean assertExistEntity(final Class<T> clazz);

    public HTableHandler getHTableHandler();

    public FileSystem getFileSystem();

    public String getIndexRoot();

    public Analyzer getAnalyzer();

    public IndexWriterConfig getIndexWriterConfig();
    
    public IndexWriterConfig getRamIndexWriterConfig();

    public TaskExecutor getTaskExecutor();

    public String getIP();
    
    public ApplicationContext getApplicationContext();
}

package com.wolf.framework.dao;

import com.wolf.framework.dao.cache.InquireCache;
import com.wolf.framework.hbase.HTableHandler;
import com.wolf.framework.task.TaskExecutor;
import java.util.Map;
import net.sf.ehcache.CacheManager;
import org.apache.hadoop.fs.FileSystem;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriterConfig;

/**
 *
 * @author zoe
 */
public interface EntityDaoContext<T extends Entity> {

    public InquireCache getInquireCache();

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

    public TaskExecutor getTaskExecutor();

    public String getIP();
}
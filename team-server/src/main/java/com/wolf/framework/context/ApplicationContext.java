package com.wolf.framework.context;

import com.wolf.framework.lucene.HdfsLucene;
import java.util.List;
import net.sf.ehcache.CacheManager;

/**
 *
 * @author aladdin
 */
public interface ApplicationContext {
    
    public ApplicationContext CONTEXT = new ApplicationContextImpl();

    public List<HdfsLucene> getHdfsLuceneList();

    void addHdfsLuceneList(HdfsLucene hdfsLucene);

    public CacheManager getCacheManager();

    void setCacheManager(CacheManager cacheManager);
}

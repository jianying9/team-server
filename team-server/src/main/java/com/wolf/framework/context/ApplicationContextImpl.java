package com.wolf.framework.context;

import com.wolf.framework.lucene.HdfsLucene;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.sf.ehcache.CacheManager;

/**
 *
 * @author aladdin
 */
public class ApplicationContextImpl implements ApplicationContext {

    private List<HdfsLucene> ALL_HDFS_LUCENE = new ArrayList<HdfsLucene>();
    //
    private CacheManager cacheManager;

    @Override
    public List<HdfsLucene> getHdfsLuceneList() {
        return Collections.unmodifiableList(this.ALL_HDFS_LUCENE);
    }

    @Override
    public void addHdfsLuceneList(HdfsLucene hdfsLucene) {
        this.ALL_HDFS_LUCENE.add(hdfsLucene);
    }

    @Override
    public CacheManager getCacheManager() {
        return this.cacheManager;
    }

    @Override
    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
}

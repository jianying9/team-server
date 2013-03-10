package com.wolf.framework.dao.insert;

import com.wolf.framework.dao.Entity;
import com.wolf.framework.dao.cache.InquireCache;
import java.util.List;
import java.util.Map;

/**
 *
 * @author aladdin
 */
public class InsertCacheHandlerImpl<T extends Entity> implements InsertHandler<T> {
    
    private final InquireCache inquireCache;
    private final String tableName;
    private final InsertHandler<T> insertHandler;

    public InsertCacheHandlerImpl(InquireCache inquireCache, String tableName, InsertHandler<T> insertHandler) {
        this.inquireCache = inquireCache;
        this.tableName = tableName;
        this.insertHandler = insertHandler;
    }

    @Override
    public T insert(Map<String, String> entityMap) {
        T t = this.insertHandler.insert(entityMap);
        this.inquireCache.removeCache(tableName);
        return t;
    }
    
    @Override
    public void batchInsert(List<Map<String, String>> entityMapList) {
        this.insertHandler.batchInsert(entityMapList);
        this.inquireCache.removeCache(tableName);
    }
}

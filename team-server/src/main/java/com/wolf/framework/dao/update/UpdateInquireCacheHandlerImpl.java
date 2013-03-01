package com.wolf.framework.dao.update;

import com.wolf.framework.dao.cache.InquireCache;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zoe
 */
public class UpdateInquireCacheHandlerImpl implements UpdateHandler {

    private final InquireCache inquireAndCountCache;
    private final String tableName;
    private final UpdateHandler updateHandler;

    public UpdateInquireCacheHandlerImpl(InquireCache inquireAndCountCache, String tableName, UpdateHandler updateHandler) {
        this.inquireAndCountCache = inquireAndCountCache;
        this.tableName = tableName;
        this.updateHandler = updateHandler;
    }

    @Override
    public String update(Map<String, String> entityMap) {
        String keyValue = this.updateHandler.update(entityMap);
        this.inquireAndCountCache.removeCache(tableName);
        return keyValue;
    }

    @Override
    public void batchUpdate(List<Map<String, String>> entityMapList) {
        this.updateHandler.batchUpdate(entityMapList);
        this.inquireAndCountCache.removeCache(tableName);
    }
}

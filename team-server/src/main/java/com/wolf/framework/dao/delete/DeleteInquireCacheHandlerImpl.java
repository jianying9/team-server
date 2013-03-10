package com.wolf.framework.dao.delete;

import com.wolf.framework.dao.cache.InquireCache;
import java.util.List;

/**
 *
 * @author aladdin
 */
public class DeleteInquireCacheHandlerImpl implements DeleteHandler {

    private final InquireCache inquireAndCountCache;
    private final String tableName;
    private final DeleteHandler deleteHandler;

    public DeleteInquireCacheHandlerImpl(InquireCache inquireAndCountCache, String tableName, DeleteHandler deleteHandler) {
        this.inquireAndCountCache = inquireAndCountCache;
        this.tableName = tableName;
        this.deleteHandler = deleteHandler;
    }

    @Override
    public void delete(String keyValue) {
        this.deleteHandler.delete(keyValue);
        this.inquireAndCountCache.removeCache(tableName);
    }

    @Override
    public void batchDelete(List<String> keyValues) {
        this.deleteHandler.batchDelete(keyValues);
        this.inquireAndCountCache.removeCache(tableName);
    }
}

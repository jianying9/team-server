package com.wolf.framework.dao.inquire;

import com.wolf.framework.dao.InquireKeyResult;
import com.wolf.framework.dao.cache.InquireCache;
import com.wolf.framework.dao.condition.InquireContext;

/**
 *
 * @author aladdin
 */
public final class InquireKeyByConditionFromCacheHandlerImpl implements InquireKeyByConditionHandler {

    private final String tableName;
    private final InquireCache inquireCache;
    private final InquireKeyByConditionHandler inquireKeyByConditionHandler;

    public InquireKeyByConditionFromCacheHandlerImpl(String tableName, InquireCache inquireCache, InquireKeyByConditionHandler inquireKeyByConditionHandler) {
        this.tableName = tableName;
        this.inquireCache = inquireCache;
        this.inquireKeyByConditionHandler = inquireKeyByConditionHandler;
    }

    @Override
    public InquireKeyResult inquireByConditon(InquireContext inquireContext) {
        InquireKeyResult inquireKeyResult = this.inquireCache.getKeysCache(tableName, inquireContext);
        if (inquireKeyResult == null) {
            inquireKeyResult = this.inquireKeyByConditionHandler.inquireByConditon(inquireContext);
            this.inquireCache.putKeysCache(tableName, inquireContext, inquireKeyResult);
        }
        return inquireKeyResult;
    }
}

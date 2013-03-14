package com.wolf.framework.dao.inquire;

import com.wolf.framework.dao.Entity;
import com.wolf.framework.dao.InquireKeyResult;
import com.wolf.framework.dao.InquireResult;
import com.wolf.framework.dao.cache.InquireCache;
import com.wolf.framework.dao.condition.InquireContext;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aladdin
 */
public final class InquireByConditionFromCacheHandlerImpl<T extends Entity> implements InquireByConditionHandler<T> {
    
    private final String tableName;
    private final InquireCache inquireCache;
    private final InquireByKeyHandler<T> inquireByKeyHandler;
    private final InquireByConditionHandler<T> inquireByConditionHandler;
    
    public InquireByConditionFromCacheHandlerImpl(String tableName, InquireCache inquireCache, InquireByKeyHandler<T> inquireByKeyHandler, InquireByConditionHandler<T> inquireByConditionHandler) {
        this.tableName = tableName;
        this.inquireCache = inquireCache;
        this.inquireByKeyHandler = inquireByKeyHandler;
        this.inquireByConditionHandler = inquireByConditionHandler;
    }
    
    @Override
    public InquireResult<T> inquireByConditon(InquireContext inquireContext) {
        InquireResult<T> inquireResult;
        InquireKeyResult inquireKeyResult = this.inquireCache.getKeysCache(tableName, inquireContext);
        if (inquireKeyResult == null) {
            //从数据源获取
            inquireResult = this.inquireByConditionHandler.inquireByConditon(inquireContext);
            //缓存
            InquireKeyResultImpl inquireKeyResultImpl = new InquireKeyResultImpl();
            inquireKeyResultImpl.setTotal(inquireResult.getTotal());
            inquireKeyResultImpl.setPageSize(inquireResult.getPageSize());
            inquireKeyResultImpl.setNextPageIndex(inquireResult.getNextPageIndex());
            List<T> tList = inquireResult.getResultList();
            List<String> keyList;
            if (tList.isEmpty()) {
                keyList = new ArrayList<String>(0);
            } else {
                keyList = new ArrayList<String>(tList.size());
                for (T t : tList) {
                    keyList.add(t.getKeyValue());
                }
            }
            inquireKeyResultImpl.setResultList(keyList);
            this.inquireCache.putKeysCache(tableName, inquireContext, inquireKeyResult);
        } else {
            //获取缓存key值
            List<String> keyValueList = inquireKeyResult.getResultList();
            List<T> tList = this.inquireByKeyHandler.inquireByKeys(keyValueList);
            InquireResultImpl<T> inquireResultImpl = new InquireResultImpl<T>();
            inquireResultImpl.setTotal(inquireKeyResult.getTotal());
            inquireResultImpl.setPageSize(inquireContext.getPageSize());
            inquireResultImpl.setNextPageIndex(inquireKeyResult.getNextPageIndex());
            inquireResultImpl.setResultList(tList);
            inquireResult = inquireResultImpl;
        }
        return inquireResult;
    }
}

package com.wolf.framework.dao.cache;

import com.wolf.framework.dao.InquireKeyResult;
import com.wolf.framework.dao.condition.InquireContext;

/**
 * 查询集合查询结果缓存管理对象
 *
 * @author neslon, aladdin
 */
public interface InquireCache {

    public void putKeysCache(final String tableName, final InquireContext inquireContext, final InquireKeyResult inquireKeyResult);

    public InquireKeyResult getKeysCache(final String tableName, final InquireContext inquireContext);

    public void removeCache(final String tableName);
}

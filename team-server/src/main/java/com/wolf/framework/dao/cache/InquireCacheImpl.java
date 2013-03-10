package com.wolf.framework.dao.cache;

import com.wolf.framework.config.FrameworkLoggerEnum;
import com.wolf.framework.dao.InquireKeyResult;
import com.wolf.framework.dao.condition.Condition;
import com.wolf.framework.dao.condition.InquireContext;
import com.wolf.framework.dao.condition.OperateTypeEnum;
import com.wolf.framework.dao.inquire.InquireKeyResultImpl;
import com.wolf.framework.logger.LogFactory;
import com.wolf.framework.utils.SecurityUtils;
import java.util.Collections;
import java.util.List;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.slf4j.Logger;

/**
 *
 * @author neslon, aladdin
 */
public final class InquireCacheImpl implements InquireCache {

    private final Logger logger = LogFactory.getLogger(FrameworkLoggerEnum.DAO);
    //缓存数据对象
    private final Cache cache;
    //
    private final ConditionSort conditionSort = new ConditionSort();

    public InquireCacheImpl(Cache cache) {
        this.cache = cache;
    }

    private String getConditionMd5(final InquireContext inquireContext) {
        String columnName;
        String columnValue;
        OperateTypeEnum operateTypeEnum;
        final List<Condition> conditionList = inquireContext.getConditionList();
        StringBuilder conditionBuilder = new StringBuilder(conditionList.size() * 24 + 24);
        conditionBuilder.append("pageIndex|").append(inquireContext.getLastPageIndex()).append("_pageSize|").append(inquireContext.getPageSize()).append('_');
        if (conditionList.size() == 1) {
            Condition con = conditionList.get(0);
            columnName = con.getColumnName();
            columnValue = con.getColumnValue();
            operateTypeEnum = con.getOperateTypeEnum();
            conditionBuilder.append(columnName).append('|').append(operateTypeEnum.name()).append('|').append(columnValue);
        } else {
            Collections.sort(conditionList, this.conditionSort);
            for (Condition condition : conditionList) {
                columnName = condition.getColumnName();
                columnValue = condition.getColumnValue();
                operateTypeEnum = condition.getOperateTypeEnum();
                conditionBuilder.append(columnName).append('|').append(operateTypeEnum.name()).append('|').append(columnValue).append('_');
            }
            conditionBuilder.setLength(conditionBuilder.length() - 1);
        }
        String conditionStr = conditionBuilder.toString();
        this.logger.debug("condition:{}", conditionStr);
        return SecurityUtils.encryptByMd5(conditionStr);
    }

    @Override
    public void putKeysCache(final String tableName, final InquireContext inquireContext, final InquireKeyResult inquireKeyResult) {
        String md5 = this.getConditionMd5(inquireContext);
        StringBuilder keyBuilder = new StringBuilder(tableName.length() + md5.length() + 1);
        keyBuilder.append(tableName).append('_').append(md5);
        String key = keyBuilder.toString();
        Element element = new Element(key, inquireKeyResult);
        this.cache.put(element);
    }

    /**
     * 缓存未命中返回null
     *
     * @param tableName
     * @param conditionList
     * @return
     */
    @Override
    public InquireKeyResult getKeysCache(final String tableName, final InquireContext inquireContext) {
        InquireKeyResultImpl inquireKeyResult = null;
        String md5 = this.getConditionMd5(inquireContext);
        StringBuilder keyBuilder = new StringBuilder(tableName.length() + md5.length() + 1);
        keyBuilder.append(tableName).append('_').append(md5);
        String key = keyBuilder.toString();
        this.logger.debug("key cache: finding Keys: {}", key);
        Element element = this.cache.getQuiet(key);
        if (element != null) {
            inquireKeyResult = (InquireKeyResultImpl) element.getObjectValue();
            this.logger.debug("key cache: find Keys: {}", key);
        } else {
            this.logger.debug("key cache: not find Keys: {}", key);
        }
        return inquireKeyResult;
    }

    /**
     * 移除缓存
     *
     * @param companyId 公司ID
     * @param tableName 表名
     */
    @Override
    public void removeCache(final String tableName) {
        this.logger.debug("remove cache: table:{}", tableName);
        String indexName = tableName.concat("_");
        List<String> keyValueList = this.cache.getKeys();
        for (String keyValue : keyValueList) {
            if (keyValue.indexOf(indexName) == 0) {
                this.cache.removeQuiet(keyValue);
            }
        }
    }
}

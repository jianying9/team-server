package com.wolf.framework.dao;

import com.wolf.framework.dao.condition.InquireContext;
import java.util.List;
import java.util.Map;

/**
 * entity dao
 *
 * @author zoe
 */
public interface EntityDao<T extends Entity> {

    /**
     * 根据主键查询
     *
     * @param key
     * @return
     */
    public T inquireByKey(final String keyValue);

    /**
     * 根据主键集合查询
     *
     * @param keyValues
     * @return
     */
    public List<T> inquireByKeys(final List<String> keyValues);

    /**
     * 单个条件查询
     *
     * @param columnName 列名
     * @param columnValue 值
     * @return
     */
    public InquireResult<T> inquireByColumn(final String columnName, final String columnValue);

    /**
     * 两个条件查询
     *
     * @param columnName 列1
     * @param columnValue 值1
     * @param columnNameTwo 列2
     * @param columnValueTwo 值2
     * @return
     */
    public InquireResult<T> inquireByColumns(final String columnName, final String columnValue, final String columnNameTwo, final String columnValueTwo);

    /**
     * 复合条件查询
     *
     * @param inquireContext
     * @return
     */
    public InquireResult<T> inquireByCondition(final InquireContext inquireContext);

    /**
     * 插入
     *
     * @param entityMap
     */
    public T insert(final Map<String, String> entityMap);

    /**
     * 批量插入，无缓存
     *
     * @param entityMapList
     */
    public void batchInsert(final List<Map<String, String>> entityMapList);

    /**
     * 更新
     *
     * @param entityMap
     */
    public String update(final Map<String, String> entityMap);

    /**
     * 批量更新
     *
     * @param entityMapList
     */
    public void batchUpdate(final List<Map<String, String>> entityMapList);

    /**
     * 更新并查询后新后值
     *
     * @param entityMap
     * @return
     */
    public T updateAndInquire(Map<String, String> entityMap);

    /**
     * 删除
     *
     * @param keyValue
     */
    public void delete(String keyValue);

    /**
     * 批量删除
     *
     * @param keyValues
     */
    public void batchDelete(final List<String> keyValues);

    /**
     * 单个条件动态查询主键集合
     *
     * @param columnName
     * @param columnValue
     * @return
     */
    public InquireKeyResult inquireKeysByColumn(final String columnName, final String columnValue);

    /**
     * 两个条件动态查询主键集合
     *
     * @param columnName 列1
     * @param columnValue 值1
     * @param columnNameTwo 列2
     * @param columnValueTwo 值2
     * @return
     */
    public InquireKeyResult inquireKeysByColumns(final String columnName, final String columnValue, final String columnNameTwo, final String columnValueTwo);

    /**
     * 单条件动态查询主键集合
     *
     * @param condition 单态条件集合
     * @return
     */
    public InquireKeyResult inquireKeysByCondition(final InquireContext condition);
}

package com.wolf.framework.dao;

import com.wolf.framework.config.FrameworkLoggerEnum;
import com.wolf.framework.dao.parser.ColumnHandler;
import com.wolf.framework.dao.parser.KeyHandler;
import com.wolf.framework.logger.LogFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;

/**
 * dynamic entity dao
 *
 * @author zoe
 */
public abstract class AbstractDaoHandler<T extends Entity> {

    protected final Logger logger = LogFactory.getLogger(FrameworkLoggerEnum.DAO);
    //实体class
    protected final Class<T> clazz;
    protected final KeyHandler keyHandler;
    protected final List<ColumnHandler> columnHandlerList;
    protected final String tableName;

    public AbstractDaoHandler(String tableName, Class<T> clazz, List<ColumnHandler> columnHandlerList, KeyHandler keyHandler) {
        this.clazz = clazz;
        this.columnHandlerList = columnHandlerList;
        this.keyHandler = keyHandler;
        this.tableName = tableName;
    }

    /**
     * 解析map数据，实例化clazz
     *
     * @param entityMap
     * @return
     */
    protected T newInstance(final Map<String, String> resultMap) {
        T t = null;
        try {
            t = clazz.newInstance();
        } catch (Exception e) {
            logger.error("There was an error instancing  class {}.Cause: {}", clazz.getName(), e.getMessage());
            throw new RuntimeException("There was an error instancing class ".concat(clazz.getName()));
        }
        t.parseMap(resultMap);
        return t;
    }
    
    protected final Map<String, String> readResultToMap(Result result) {
        Map<String, String> resultMap = new HashMap<String, String>(this.columnHandlerList.size() + 1, 1);
        //放入key
        String keyValue = Bytes.toString(result.getRow());
        resultMap.put(this.keyHandler.getName(), keyValue);
        //读取column
        String columnName;
        byte[] columnValue;
        byte[] columnFamily;
        for (ColumnHandler columnHandler : columnHandlerList) {
            columnFamily = columnHandler.getColumnFamily();
            columnName = columnHandler.getColumnName();
            columnValue = result.getValue(columnFamily, Bytes.toBytes(columnName));
            if (columnValue == null) {
                this.logger.warn("inquire table {} waring message: can not find column:{} value", this.tableName, columnName);
                resultMap.put(columnName, "-999");
            } else {
                resultMap.put(columnName, Bytes.toString(columnValue));
            }
        }
        return resultMap;
    }
    
    protected final List<Map<String, String>> readResultToMap(Result[] result) {
        List<Map<String, String>> mapList = new ArrayList<Map<String, String>>(result.length);
        Map<String, String> map;
        for (int index = 0; index < result.length; index++) {
            map = this.readResultToMap(result[index]);
            mapList.add(map);
        }
        return mapList;
    }

    protected final T readResult(Result result) {
        Map<String, String> resultMap = this.readResultToMap(result);
        return this.newInstance(resultMap);
    }

    protected final List<T> readResult(List<Result> resultList) {
        List<T> tList = new ArrayList<T>(resultList.size());
        T t;
        for (Result result : resultList) {
            t = this.readResult(result);
            tList.add(t);
        }
        return tList;
    }

    protected final List<T> readResult(Result[] result) {
        List<T> tList = new ArrayList<T>(result.length);
        T t;
        for (int index = 0; index < result.length; index++) {
            t = this.readResult(result[index]);
            tList.add(t);
        }
        return tList;
    }
    
    protected final Put createInsertPut(final String keyValue, final Map<String, String> entityMap) {
        final byte[] rowKey = Bytes.toBytes(keyValue);
        final Put put = new Put(rowKey);
        String columnName;
        String columnValue;
        byte[] columnFamily;
        for (ColumnHandler columnHandler : this.columnHandlerList) {
            columnName = columnHandler.getColumnName();
            columnValue = entityMap.get(columnName);
            if (columnValue == null) {
                this.logger.error("insert table {} failure message: can not find column:{} value", this.tableName, columnName);
                this.logger.error("insert failure value:{}", entityMap.toString());
                throw new RuntimeException("insert failure message: can not find column value...see log");
            } else {
                columnFamily = columnHandler.getColumnFamily();
                put.add(columnFamily, Bytes.toBytes(columnName), Bytes.toBytes(columnValue));
            }
        }
        return put;
    }

    protected final Put createUpdatePut(final String keyValue, final Map<String, String> dataMap) {
        final byte[] rowKey = Bytes.toBytes(keyValue);
        final Put put = new Put(rowKey);
        String columnName;
        String columnValue;
        byte[] columnFamily;
        for (ColumnHandler columnHandler : this.columnHandlerList) {
            columnName = columnHandler.getColumnName();
            columnValue = dataMap.get(columnName);
            if (columnValue != null) {
                columnFamily = columnHandler.getColumnFamily();
                put.add(columnFamily, Bytes.toBytes(columnName), Bytes.toBytes(columnValue));
            }
        }
        return put;
    }
}

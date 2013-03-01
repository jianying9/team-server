package com.wolf.framework.dao.insert;

import com.wolf.framework.dao.AbstractDaoHandler;
import com.wolf.framework.dao.Entity;
import com.wolf.framework.dao.parser.ColumnHandler;
import com.wolf.framework.dao.parser.KeyHandler;
import com.wolf.framework.hbase.HTableHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.hadoop.hbase.client.Put;

/**
 *
 * @author zoe
 */
public class InsertDataHandlerImpl<T extends Entity> extends AbstractDaoHandler<T> implements InsertHandler<T> {

    private final HTableHandler hTableHandler;

    public InsertDataHandlerImpl(HTableHandler hTableHandler, String tableName, Class clazz, List<ColumnHandler> columnHandlerList, KeyHandler keyHandler) {
        super(tableName, clazz, columnHandlerList, keyHandler);
        this.hTableHandler = hTableHandler;
    }

    @Override
    public T insert(Map<String, String> entityMap) {
        final String keyName = this.keyHandler.getName();
        String keyValue = entityMap.get(keyName);
        if (keyValue == null) {
            keyValue = this.keyHandler.nextValue();
            entityMap.put(keyName, keyValue);
        }
        final Put put = this.createInsertPut(keyValue, entityMap);
        this.hTableHandler.put(tableName, put);
        T t = this.newInstance(entityMap);
        return t;
    }

    @Override
    public void batchInsert(List<Map<String, String>> entityMapList) {
        final String keyName = this.keyHandler.getName();
        List<Put> putList = new ArrayList<Put>(entityMapList.size());
        Put put;
        String keyValue;
        for (Map<String, String> entityMap : entityMapList) {
            keyValue = entityMap.get(keyName);
            if (keyValue == null) {
                keyValue = this.keyHandler.nextValue();
                entityMap.put(keyName, keyValue);
            }
            put = this.createInsertPut(keyValue, entityMap);
            putList.add(put);
        }
        this.hTableHandler.put(tableName, putList);
    }
}

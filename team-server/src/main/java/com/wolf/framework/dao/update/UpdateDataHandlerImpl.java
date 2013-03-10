package com.wolf.framework.dao.update;

import com.wolf.framework.dao.AbstractDaoHandler;
import com.wolf.framework.dao.parser.ColumnHandler;
import com.wolf.framework.dao.parser.KeyHandler;
import com.wolf.framework.hbase.HTableHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.hadoop.hbase.client.Put;

/**
 *
 * @author aladdin
 */
public class UpdateDataHandlerImpl extends AbstractDaoHandler implements UpdateHandler {

    private final HTableHandler hTableHandler;

    public UpdateDataHandlerImpl(HTableHandler hTableHandler, String tableName, Class clazz, List<ColumnHandler> columnHandlerList, KeyHandler keyHandler) {
        super(tableName, clazz, columnHandlerList, keyHandler);
        this.hTableHandler = hTableHandler;
    }

    @Override
    public String update(Map<String, String> entityMap) {
        final String keyName = this.keyHandler.getName();
        String keyValue = entityMap.get(keyName);
        if (keyValue == null) {
            this.logger.error("update table {} failure message: can not find key:{} value", this.tableName, keyName);
            this.logger.error("update failure value:{}", entityMap.toString());
            throw new RuntimeException("update failure message: can not find key value...see log");
        } else {
            final Put put = this.createUpdatePut(keyValue, entityMap);
            if (!put.isEmpty()) {
                this.hTableHandler.put(tableName, put);
            }
        }
        return keyValue;
    }

    @Override
    public void batchUpdate(List<Map<String, String>> entityMapList) {
        final String keyName = this.keyHandler.getName();
        List<Put> putList = new ArrayList<Put>(entityMapList.size());
        Put put;
        String keyValue;
        for (Map<String, String> entityMap : entityMapList) {
            keyValue = entityMap.get(keyName);
            if (keyValue == null) {
                this.logger.error("update table {} failure message: can not find key:{} value", this.tableName, keyName);
                this.logger.error("update failure value:{}", entityMap.toString());
                throw new RuntimeException("update failure message: can not find key value...see log");
            }
            put = this.createInsertPut(keyValue, entityMap);
            if (!put.isEmpty()) {
                putList.add(put);
            }
        }
        if (!putList.isEmpty()) {
            this.hTableHandler.put(tableName, putList);
        }
    }
}

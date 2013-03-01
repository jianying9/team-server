package com.wolf.framework.dao.delete;

import com.wolf.framework.hbase.HTableHandler;
import java.util.List;

/**
 *
 * @author zoe
 */
public class DeleteDataHandlerImpl implements DeleteHandler {
    
    private final HTableHandler hTableHandler;
    private final String tableName;
    
    public DeleteDataHandlerImpl(HTableHandler hTableHandler, String tableName) {
        this.hTableHandler = hTableHandler;
        this.tableName = tableName;
    }

    @Override
    public void delete(String keyValue) {
        this.hTableHandler.delete(tableName, keyValue);
    }

    @Override
    public void batchDelete(List<String> keyValues) {
        this.hTableHandler.delete(tableName, keyValues);
    }
}

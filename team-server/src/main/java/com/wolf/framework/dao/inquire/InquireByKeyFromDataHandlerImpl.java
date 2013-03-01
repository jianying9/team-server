package com.wolf.framework.dao.inquire;

import com.wolf.framework.dao.AbstractDaoHandler;
import com.wolf.framework.dao.Entity;
import com.wolf.framework.dao.parser.ColumnHandler;
import com.wolf.framework.dao.parser.KeyHandler;
import com.wolf.framework.hbase.HTableHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.hadoop.hbase.client.Result;

/**
 *
 * @author zoe
 */
public final class InquireByKeyFromDataHandlerImpl<T extends Entity> extends AbstractDaoHandler<T> implements InquireByKeyHandler<T> {

    private final HTableHandler hTableHandler;

    public InquireByKeyFromDataHandlerImpl(HTableHandler hTableHandler, String tableName, Class<T> clazz, List<ColumnHandler> columnHandlerList, KeyHandler keyHandler) {
        super(tableName, clazz, columnHandlerList, keyHandler);
        this.hTableHandler = hTableHandler;
    }

    @Override
    public T inquireByKey(String keyValue) {
        T t = null;
        Result result = this.hTableHandler.get(tableName, keyValue);
        if (result != null) {
            t = this.readResult(result);
        }
        return t;
    }

    @Override
    public List<T> inquireByKeys(List<String> keyValues) {
        List<T> tList;
        Result[] result = this.hTableHandler.get(tableName, keyValues);
        if (result != null && result.length > 0) {
            tList = this.readResult(result);
        } else {
            tList = new ArrayList<T>(0);
        }
        return tList;
    }

    @Override
    public Map<String, String> inquireMapByKey(String keyValue) {
        Map<String, String> map = null;
        Result result = this.hTableHandler.get(tableName, keyValue);
        if (result != null) {
            map = this.readResultToMap(result);
        }
        return map;
    }

    @Override
    public List<Map<String, String>> inquireMapByKeys(List<String> keyValues) {
        List<Map<String, String>> mapList;
        Result[] result = this.hTableHandler.get(tableName, keyValues);
        if (result != null && result.length > 0) {
            mapList = this.readResultToMap(result);
        } else {
            mapList = new ArrayList<Map<String, String>>(0);
        }
        return mapList;
    }
}

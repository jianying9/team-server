package com.wolf.framework.dao.parser;

import com.wolf.framework.dao.annotation.IndexTypeEnum;
import org.apache.hadoop.hbase.util.Bytes;

/**
 *
 * @author zoe
 */
public class ColumnHandlerImpl implements ColumnHandler {
    
    private final String columnName;
    private final byte[] columnFamily;
    private final IndexTypeEnum indexTypeEnum;
    public ColumnHandlerImpl(String columnName, String columnFamily, IndexTypeEnum indexTypeEnum) {
        this.columnName = columnName;
        this.columnFamily = Bytes.toBytes(columnFamily);
        this.indexTypeEnum = indexTypeEnum;
    }
    
    @Override
    public byte[] getColumnFamily() {
        return this.columnFamily;
    }
    
    @Override
    public String getColumnName() {
        return this.columnName;
    }

    @Override
    public IndexTypeEnum getIndexTypeEnum() {
        return indexTypeEnum;
    }
}

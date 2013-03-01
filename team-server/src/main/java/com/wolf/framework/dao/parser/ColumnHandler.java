package com.wolf.framework.dao.parser;

import com.wolf.framework.dao.annotation.IndexTypeEnum;

/**
 *
 * @author zoe
 */
public interface ColumnHandler {
    
    public byte[] getColumnFamily();
    
    public String getColumnName();
    
    public IndexTypeEnum getIndexTypeEnum();
}

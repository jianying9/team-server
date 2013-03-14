package com.wolf.framework.dao.parser;

/**
 *
 * @author aladdin
 */
public class ColumnHandlerImpl implements ColumnHandler {
    
    private final String columnName;
    public ColumnHandlerImpl(String columnName) {
        this.columnName = columnName;
    }
    
    @Override
    public String getColumnName() {
        return this.columnName;
    }
}

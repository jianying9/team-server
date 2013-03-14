package com.wolf.framework.dao.condition;

import com.wolf.framework.config.FrameworkLoggerEnum;
import com.wolf.framework.dao.parser.ColumnHandler;
import com.wolf.framework.logger.LogFactory;
import org.slf4j.Logger;

/**
 *
 * @author aladdin
 */
public final class Condition {

    private final Logger logger = LogFactory.getLogger(FrameworkLoggerEnum.DAO);
    private final String columnName;
    private final OperateTypeEnum operateTypeEnum;
    private final String columnValue;
    private ColumnHandler columnHandler;

    public Condition(String columnName, OperateTypeEnum operateTypeEnum, String columnValue) {
        this.columnName = columnName;
        this.operateTypeEnum = operateTypeEnum;
        this.columnValue = columnValue;
    }

    public String getColumnName() {
        return columnName;
    }

    public OperateTypeEnum getOperateTypeEnum() {
        return operateTypeEnum;
    }

    public String getColumnValue() {
        return columnValue;
    }

    public void setColumnHandler(ColumnHandler columnHandler) {
        if (columnHandler.getColumnName().equals(this.columnName)) {
            this.columnHandler = columnHandler;
        } else {
            this.logger.error("Condition  setColumnHandler error: columnName:{},but columnHandler name:{}", this.columnName, columnHandler.getColumnName());
            throw new RuntimeException("Conditon  setColumnHandler error...see log");
        }
    }
}

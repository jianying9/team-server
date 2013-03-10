package com.wolf.framework.dao.condition;

import com.wolf.framework.dao.condition.OperateTypeEnum;
import com.wolf.framework.config.FrameworkLoggerEnum;
import com.wolf.framework.dao.parser.ColumnHandler;
import com.wolf.framework.logger.LogFactory;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.util.Bytes;
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

    public Filter createFilter() {
        if (this.columnHandler == null) {
            this.logger.error("Condition  createFilter error: un bind columnName:{} columnHandler", this.columnName);
            throw new RuntimeException("Conditon  createFilter error: un bind columnName...see log");
        }
        Filter filter;
        byte[] columnNameByte = Bytes.toBytes(columnName);
        byte[] columnValueByte = Bytes.toBytes(this.columnValue);
        byte[] columnFamilyByte = this.columnHandler.getColumnFamily();
        switch (this.operateTypeEnum) {
            case EQUAL:
                filter = new SingleColumnValueFilter(columnFamilyByte, columnNameByte, CompareOp.EQUAL, columnValueByte);
                break;
            case LESS:
                filter = new SingleColumnValueFilter(columnFamilyByte, columnNameByte, CompareOp.LESS, columnValueByte);
                break;
            case LESS_OR_EQUAL:
                filter = new SingleColumnValueFilter(columnFamilyByte, columnNameByte, CompareOp.LESS_OR_EQUAL, columnValueByte);
                break;
            case NOT_EQUAL:
                filter = new SingleColumnValueFilter(columnFamilyByte, columnNameByte, CompareOp.NOT_EQUAL, columnValueByte);
                break;
            case GREATER_OR_EQUAL:
                filter = new SingleColumnValueFilter(columnFamilyByte, columnNameByte, CompareOp.GREATER_OR_EQUAL, columnValueByte);
                break;
            case GREATER:
                filter = new SingleColumnValueFilter(columnFamilyByte, columnNameByte, CompareOp.GREATER, columnValueByte);
                break;
            case LIKE:
                SubstringComparator substringComparator = new SubstringComparator(this.columnValue);
                filter = new SingleColumnValueFilter(columnFamilyByte, columnNameByte, CompareOp.EQUAL, substringComparator);
                break;
            default:
                filter = new SingleColumnValueFilter(columnFamilyByte, columnNameByte, CompareOp.EQUAL, columnValueByte);
                break;
        }
        return filter;
    }
}

package com.wolf.framework.dao;

import com.wolf.framework.dao.condition.Condition;
import com.wolf.framework.dao.condition.InquireContext;
import com.wolf.framework.dao.condition.OperateTypeEnum;
import com.wolf.framework.dao.delete.DeleteHandler;
import com.wolf.framework.dao.inquire.InquireByConditionHandler;
import com.wolf.framework.dao.inquire.InquireByKeyHandler;
import com.wolf.framework.dao.inquire.InquireKeyByConditionHandler;
import com.wolf.framework.dao.insert.InsertHandler;
import com.wolf.framework.dao.update.UpdateHandler;
import java.util.List;
import java.util.Map;

/**
 *
 * @author aladdin
 */
public class EntityDaoImpl<T extends Entity> implements EntityDao<T> {

    private final InsertHandler<T> insertHandler;
    private final UpdateHandler updateHandler;
    private final DeleteHandler deleteHandler;
    private final InquireByKeyHandler<T> inquireByKeyHandler;
    private final InquireByConditionHandler<T> inquireByConditionHandler;
    private final InquireKeyByConditionHandler inquireKeyByConditionHandler;

    public EntityDaoImpl(InsertHandler insertHandler, UpdateHandler updateHandler, DeleteHandler deleteHandler, InquireByKeyHandler<T> inquireByKeyHandler, InquireByConditionHandler<T> inquireByConditionHandler, InquireKeyByConditionHandler inquireKeyByConditionHandler) {
        this.insertHandler = insertHandler;
        this.updateHandler = updateHandler;
        this.deleteHandler = deleteHandler;
        this.inquireByKeyHandler = inquireByKeyHandler;
        this.inquireByConditionHandler = inquireByConditionHandler;
        this.inquireKeyByConditionHandler = inquireKeyByConditionHandler;
    }

    @Override
    public T inquireByKey(String keyValue) {
        return this.inquireByKeyHandler.inquireByKey(keyValue);
    }

    @Override
    public List<T> inquireByKeys(List<String> keyValues) {
        return this.inquireByKeyHandler.inquireByKeys(keyValues);
    }

    @Override
    public InquireResult<T> inquireByColumn(String columnName, String columnValue) {
        InquireContext inquireContext = new InquireContext();
        Condition condition = new Condition(columnName, OperateTypeEnum.EQUAL, columnValue);
        inquireContext.addCondition(condition);
        return this.inquireByCondition(inquireContext);
    }

    @Override
    public InquireResult<T> inquireByColumns(String columnName, String columnValue, String columnNameTwo, String columnValueTwo) {
        InquireContext inquireContext = new InquireContext();
        Condition condition = new Condition(columnName, OperateTypeEnum.EQUAL, columnValue);
        inquireContext.addCondition(condition);
        condition = new Condition(columnNameTwo, OperateTypeEnum.EQUAL, columnValueTwo);
        inquireContext.addCondition(condition);
        return this.inquireByCondition(inquireContext);
    }

    @Override
    public InquireResult<T> inquireByCondition(InquireContext inquireContext) {
        return this.inquireByConditionHandler.inquireByConditon(inquireContext);
    }

    @Override
    public T insert(Map<String, String> entityMap) {
        return this.insertHandler.insert(entityMap);
    }

    @Override
    public void batchInsert(List<Map<String, String>> entityMapList) {
        int num = entityMapList.size();
        switch (num) {
            case 0:
                break;
            case 1:
                this.insertHandler.insert(entityMapList.get(0));
                break;
            default:
                this.insertHandler.batchInsert(entityMapList);
        }
    }

    @Override
    public String update(Map<String, String> entityMap) {
        return this.updateHandler.update(entityMap);
    }

    @Override
    public void batchUpdate(List<Map<String, String>> entityMapList) {
        int num = entityMapList.size();
        switch (num) {
            case 0:
                break;
            case 1:
                this.updateHandler.update(entityMapList.get(0));
                break;
            default:
                this.updateHandler.batchUpdate(entityMapList);
        }
    }

    @Override
    public T updateAndInquire(Map<String, String> entityMap) {
        String keyValue = this.updateHandler.update(entityMap);
        return this.inquireByKeyHandler.inquireByKey(keyValue);
    }

    @Override
    public void delete(String keyValue) {
        this.deleteHandler.delete(null);
    }

    @Override
    public void batchDelete(List<String> keyValues) {
        int num = keyValues.size();
        switch (num) {
            case 0:
                break;
            case 1:
                this.deleteHandler.delete(keyValues.get(0));
                break;
            default:
                this.deleteHandler.batchDelete(keyValues);
        }
    }

    @Override
    public InquireKeyResult inquireKeysByColumn(String columnName, String columnValue) {
        InquireContext inquireContext = new InquireContext();
        Condition condition = new Condition(columnName, OperateTypeEnum.EQUAL, columnValue);
        inquireContext.addCondition(condition);
        return this.inquireKeysByCondition(inquireContext);
    }

    @Override
    public InquireKeyResult inquireKeysByColumns(String columnName, String columnValue, String columnNameTwo, String columnValueTwo) {
        InquireContext inquireContext = new InquireContext();
        Condition condition = new Condition(columnName, OperateTypeEnum.EQUAL, columnValue);
        inquireContext.addCondition(condition);
        condition = new Condition(columnNameTwo, OperateTypeEnum.EQUAL, columnValueTwo);
        inquireContext.addCondition(condition);
        return this.inquireKeysByCondition(inquireContext);
    }

    @Override
    public InquireKeyResult inquireKeysByCondition(InquireContext condition) {
        return this.inquireKeyByConditionHandler.inquireByConditon(condition);
    }
}

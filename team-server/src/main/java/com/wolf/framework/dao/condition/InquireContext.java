package com.wolf.framework.dao.condition;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询条件
 *
 * @author zoe
 */
public final class InquireContext {

    private int pageSize = 10;
    private String lastPageIndex  = "";
    private final List<Condition> conditionList = new ArrayList<Condition>(6);

    public void clearCondition() {
        this.conditionList.clear();
    }

    public String getLastPageIndex() {
        return lastPageIndex;
    }

    public void setLastPageIndex(String nextPageIndex) {
        this.lastPageIndex = nextPageIndex;
    }
    
    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize > 100 ? 100 : pageSize;
    }

    public boolean hasCondition() {
        return this.conditionList.isEmpty() ? false : true;
    }

    public void addCondition(Condition condition) {
        this.conditionList.add(condition);
    }

    public void addCondition(List<Condition> conditionList) {
        this.conditionList.addAll(conditionList);
    }

    public List<Condition> getConditionList() {
        return conditionList;
    }

    public Condition getCondition(String columnName) {
        Condition result = null;
        for (Condition condition : conditionList) {
            if (condition.getColumnName().equals(columnName)) {
                result = condition;
                break;
            }
        }
        return result;
    }
}

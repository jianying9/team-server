package com.wolf.framework.dao.inquire;

import com.wolf.framework.dao.condition.Condition;
import com.wolf.framework.dao.condition.InquireContext;
import com.wolf.framework.dao.parser.ColumnHandler;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aladdin
 */
public abstract class AbstractInquireConditionHandler {

    private final List<ColumnHandler> columnHandlerList;

    public AbstractInquireConditionHandler(List<ColumnHandler> columnHandlerList) {
        this.columnHandlerList = columnHandlerList;
    }

    protected List<Condition> filterCondition(InquireContext inquireContext) {
        List<Condition> conditionList = inquireContext.getConditionList();
        if (conditionList.isEmpty() == false) {
            List<Condition> indexConditionList = new ArrayList<Condition>(conditionList.size());
            boolean isIndexCondition;
            for (Condition condition : conditionList) {
                isIndexCondition = false;
                for (ColumnHandler columnHandler : this.columnHandlerList) {
                    if (condition.getColumnName().equals(columnHandler.getColumnName())) {
                        isIndexCondition = true;
                        break;
                    }
                }
                if (isIndexCondition) {
                    indexConditionList.add(condition);
                }
            }
            inquireContext.clearCondition();
            inquireContext.addCondition(indexConditionList);
            conditionList = indexConditionList;
        }
        return conditionList;
    }
}

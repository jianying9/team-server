package com.wolf.framework.dao.inquire;

import com.wolf.framework.dao.Entity;
import com.wolf.framework.dao.InquireResult;
import com.wolf.framework.dao.condition.Condition;
import com.wolf.framework.dao.condition.InquireContext;
import com.wolf.framework.dao.parser.ColumnHandler;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aladdin
 */
public final class InquireByConditionFilterHandlerImpl<T extends Entity> extends AbstractInquireConditionHandler implements InquireByConditionHandler<T> {

    private final InquireByConditionHandler<T> inquireByConditionHandler;

    public InquireByConditionFilterHandlerImpl(List<ColumnHandler> columnHandlerList, InquireByConditionHandler<T> inquireByConditionHandler) {
        super(columnHandlerList);
        this.inquireByConditionHandler = inquireByConditionHandler;
    }

    @Override
    public InquireResult<T> inquireByConditon(InquireContext inquireContext) {
        InquireResult<T> inquireResult;
        List<Condition> conditionList = this.filterCondition(inquireContext);
        if (conditionList.isEmpty()) {
            InquireResultImpl<T> inquireResultImpl = new InquireResultImpl<T>();
            inquireResultImpl.setPageSize(inquireContext.getPageSize());
            inquireResultImpl.setResultList(new ArrayList<T>(0));
            inquireResult = inquireResultImpl;
        } else {
            inquireResult = this.inquireByConditionHandler.inquireByConditon(inquireContext);
        }
        return inquireResult;
    }
}

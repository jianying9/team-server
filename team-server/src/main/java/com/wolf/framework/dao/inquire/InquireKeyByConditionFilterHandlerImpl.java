package com.wolf.framework.dao.inquire;

import com.wolf.framework.dao.InquireKeyResult;
import com.wolf.framework.dao.condition.Condition;
import com.wolf.framework.dao.condition.InquireContext;
import com.wolf.framework.dao.parser.ColumnHandler;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aladdin
 */
public final class InquireKeyByConditionFilterHandlerImpl extends AbstractInquireConditionHandler implements InquireKeyByConditionHandler {

    private final InquireKeyByConditionHandler inquireKeyByConditionHandler;

    public InquireKeyByConditionFilterHandlerImpl(InquireKeyByConditionHandler inquireKeyByConditionHandler, List<ColumnHandler> columnHandlerList) {
        super(columnHandlerList);
        this.inquireKeyByConditionHandler = inquireKeyByConditionHandler;
    }

    @Override
    public InquireKeyResult inquireByConditon(InquireContext inquireContext) {
        InquireKeyResult inquireKeyResult;
        List<Condition> conditionList = this.filterCondition(inquireContext);
        if (conditionList.isEmpty()) {
            InquireKeyResultImpl inquireKeyResultImpl = new InquireKeyResultImpl();
            inquireKeyResultImpl.setPageSize(inquireContext.getPageSize());
            inquireKeyResultImpl.setResultList(new ArrayList<String>(0));
            inquireKeyResult = inquireKeyResultImpl;
        } else {
            inquireKeyResult = this.inquireKeyByConditionHandler.inquireByConditon(inquireContext);
        }
        return inquireKeyResult;
    }
}

package com.wolf.framework.dao.inquire;

import com.wolf.framework.dao.condition.InquireContext;
import java.util.ArrayList;

/**
 *
 * @author aladdin
 */
public final class InquireKeyByConditionDefaultEmptyHandlerImpl implements InquireKeyByConditionHandler {

    @Override
    public InquireKeyResultImpl inquireByConditon(InquireContext inquireContext) {
        InquireKeyResultImpl inquireKeyResult = new InquireKeyResultImpl();
        inquireKeyResult.setResultList(new ArrayList<String>(0));
        inquireKeyResult.setPageSize(inquireContext.getPageSize());
        return inquireKeyResult;
    }
}

package com.wolf.framework.dao.inquire;

import com.wolf.framework.dao.Entity;
import com.wolf.framework.dao.InquireKeyResult;
import com.wolf.framework.dao.InquireResult;
import com.wolf.framework.dao.condition.InquireContext;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aladdin
 */
public final class InquireByConditionHandlerImpl<T extends Entity> implements InquireByConditionHandler<T> {

    private final InquireKeyByConditionHandler inquireKeyByConditionHandler;
    private final InquireByKeyHandler<T> inquireByKeyHandler;

    public InquireByConditionHandlerImpl(InquireKeyByConditionHandler inquireKeyByConditionHandler, InquireByKeyHandler<T> inquireByKeyHandler) {
        this.inquireKeyByConditionHandler = inquireKeyByConditionHandler;
        this.inquireByKeyHandler = inquireByKeyHandler;
    }

    @Override
    public InquireResult<T> inquireByConditon(InquireContext inquireContext) {
        InquireResultImpl<T> inquireResultImpl = new InquireResultImpl<T>();
        InquireKeyResult inquireKeyResult = this.inquireKeyByConditionHandler.inquireByConditon(inquireContext);
        if (inquireKeyResult.isEmpty()) {
            inquireResultImpl.setResultList(new ArrayList<T>(0));
        } else {
            List<String> keyValueList = inquireKeyResult.getResultList();
            List<T> tList = this.inquireByKeyHandler.inquireByKeys(keyValueList);
            inquireResultImpl.setTotal(inquireKeyResult.getTotal());
            inquireResultImpl.setPageSize(inquireContext.getPageSize());
            inquireResultImpl.setNextPageIndex(inquireKeyResult.getNextPageIndex());
            inquireResultImpl.setResultList(tList);
        }
        return inquireResultImpl;
    }
}

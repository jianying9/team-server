package com.wolf.framework.dao.inquire;

import com.wolf.framework.dao.Entity;
import com.wolf.framework.dao.InquireResult;
import com.wolf.framework.dao.condition.InquireContext;

/**
 *
 * @author aladdin
 */
public interface InquireByConditionHandler<T extends Entity> {

    public InquireResult<T> inquireByConditon(InquireContext inquireContext);
}

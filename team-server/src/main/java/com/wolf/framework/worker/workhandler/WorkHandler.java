package com.wolf.framework.worker.workhandler;

import com.wolf.framework.worker.FrameworkMessageContext;

/**
 * 工作处理类
 *
 * @author zoe
 */
public interface WorkHandler {

    public String NULL_MESSAGE = " is NULL";
    public String EMPTY_MESSAGE = " is empty";

    public void execute(FrameworkMessageContext frameworkMessageContext);
}

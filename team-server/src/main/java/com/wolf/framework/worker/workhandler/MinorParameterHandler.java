package com.wolf.framework.worker.workhandler;

import com.wolf.framework.worker.FrameworkMessageContext;

/**
 * 次要参数处理
 *
 * @author zoe
 */
public interface MinorParameterHandler {

    public String execute(FrameworkMessageContext frameworkMessageContext);
}

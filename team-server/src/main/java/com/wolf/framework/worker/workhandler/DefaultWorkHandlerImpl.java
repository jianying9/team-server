package com.wolf.framework.worker.workhandler;

import com.wolf.framework.service.Service;
import com.wolf.framework.worker.FrameworkMessageContext;

/**
 * 默认处理类
 *
 * @author zoe
 */
public class DefaultWorkHandlerImpl implements WorkHandler {

    private final Service service;

    public DefaultWorkHandlerImpl(final Service service) {
        this.service = service;
    }

    @Override
    public void execute(FrameworkMessageContext frameworkMessageContext) {
        this.service.execute(frameworkMessageContext);
    }
}

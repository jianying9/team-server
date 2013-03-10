package com.wolf.framework.worker.workhandler;

import com.wolf.framework.service.SessionHandleTypeEnum;
import com.wolf.framework.worker.FrameworkMessageContext;

/**
 * session处理类
 *
 * @author aladdin
 */
public class SessionWorkHandlerImpl implements WorkHandler {

    private final WorkHandler nextWorkHandler;
    private final SessionHandleTypeEnum sessionHandleTypeEnum;

    public SessionWorkHandlerImpl(final WorkHandler workHandler, final SessionHandleTypeEnum sessionHandleTypeEnum) {
        this.nextWorkHandler = workHandler;
        this.sessionHandleTypeEnum = sessionHandleTypeEnum;
    }

    @Override
    public void execute(FrameworkMessageContext frameworkMessageContext) {
        this.nextWorkHandler.execute(frameworkMessageContext);
        switch(this.sessionHandleTypeEnum) {
            case SAVE:
                frameworkMessageContext.saveSession();
                break;
            case REMOVE:
                frameworkMessageContext.removeSession();
                break;
        }
    }
}

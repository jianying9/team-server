package com.wolf.framework.worker.workhandler;

import com.wolf.framework.websocket.GlobalWebSocket;
import com.wolf.framework.worker.FrameworkMessageContext;

/**
 * 次要参数处理类,保留空字符
 *
 * @author aladdin
 */
public class MinorParameterWorkHandlerImpl implements WorkHandler {

    private final WorkHandler nextWorkHandler;
    private final MinorParameterHandler minorParameterHandler;

    public MinorParameterWorkHandlerImpl(
            final MinorParameterHandler minorParameterHandler,
            final WorkHandler workHandler) {
        this.nextWorkHandler = workHandler;
        this.minorParameterHandler = minorParameterHandler;
    }

    @Override
    public void execute(FrameworkMessageContext frameworkMessageContext) {
        String errorMsg = this.minorParameterHandler.execute(frameworkMessageContext);
        if (errorMsg.isEmpty()) {
            //验证通过
            this.nextWorkHandler.execute(frameworkMessageContext);
        } else {
            frameworkMessageContext.invalid();
            frameworkMessageContext.setInfo(errorMsg);
            String message = frameworkMessageContext.getJson();
            GlobalWebSocket globalWebSocket = frameworkMessageContext.getGlobalWebSocket();
            globalWebSocket.send(message);
        }
    }
}

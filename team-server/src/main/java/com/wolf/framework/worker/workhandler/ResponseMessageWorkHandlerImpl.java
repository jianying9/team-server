package com.wolf.framework.worker.workhandler;

import com.wolf.framework.websocket.GlobalWebSocket;
import com.wolf.framework.worker.FrameworkMessageContext;

/**
 * JSON输出处理类
 *
 * @author aladdin
 */
public class ResponseMessageWorkHandlerImpl implements WorkHandler {

    private final WorkHandler nextWorkHandler;

    public ResponseMessageWorkHandlerImpl(final WorkHandler workHandler) {
        this.nextWorkHandler = workHandler;
    }

    @Override
    public void execute(FrameworkMessageContext frameworkMessageContext) {
        this.nextWorkHandler.execute(frameworkMessageContext);
        String message = frameworkMessageContext.getReponseMessage();
        GlobalWebSocket globalWebSocket = frameworkMessageContext.getGlobalWebSocket();
        globalWebSocket.send(message);
    }
}

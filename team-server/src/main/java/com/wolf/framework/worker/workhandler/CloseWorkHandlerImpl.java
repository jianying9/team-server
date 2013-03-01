package com.wolf.framework.worker.workhandler;

import com.wolf.framework.session.Session;
import com.wolf.framework.websocket.GlobalWebSocket;
import com.wolf.framework.worker.FrameworkMessageContext;

/**
 * session处理类
 *
 * @author zoe
 */
public class CloseWorkHandlerImpl implements WorkHandler {

    private final WorkHandler nextWorkHandler;

    public CloseWorkHandlerImpl(WorkHandler nextWorkHandler) {
        this.nextWorkHandler = nextWorkHandler;
    }

    @Override
    public void execute(FrameworkMessageContext frameworkMessageContext) {
        this.nextWorkHandler.execute(frameworkMessageContext);
        GlobalWebSocket globalWebSocket = frameworkMessageContext.getGlobalWebSocket();
        Session session = globalWebSocket.getSession();
        if (session == null) {
            globalWebSocket.close();
        }
    }
}

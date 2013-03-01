package com.wolf.framework.worker.workhandler;

import com.wolf.framework.session.Session;
import com.wolf.framework.websocket.GlobalWebSocket;
import com.wolf.framework.worker.FrameworkMessageContext;

/**
 * session读取及验证处理类
 *
 * @author zoe
 */
public class ValidateSessionWorkHandlerImpl implements WorkHandler {

    private final WorkHandler nextWorkHandler;

    public ValidateSessionWorkHandlerImpl(final WorkHandler workHandler) {
        this.nextWorkHandler = workHandler;
    }

    @Override
    public void execute(FrameworkMessageContext frameworkMessageContext) {
        GlobalWebSocket globalWebSocket = frameworkMessageContext.getGlobalWebSocket();
        Session session = globalWebSocket.getSession();
        if (session == null) {
            globalWebSocket.send("{\"flag\":\"UNLOGIN\"}");
            globalWebSocket.close();
        } else {
            frameworkMessageContext.setSession(session);
            this.nextWorkHandler.execute(frameworkMessageContext);
        }
    }
}

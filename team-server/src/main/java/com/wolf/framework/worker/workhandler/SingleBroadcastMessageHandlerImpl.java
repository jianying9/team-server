package com.wolf.framework.worker.workhandler;

import com.wolf.framework.websocket.GlobalApplication;
import com.wolf.framework.websocket.GlobalWebSocket;
import com.wolf.framework.worker.FrameworkMessageContext;

/**
 *
 * @author aladdin
 */
public class SingleBroadcastMessageHandlerImpl implements WorkHandler {

    private final WorkHandler nextWorkHandler;

    public SingleBroadcastMessageHandlerImpl(WorkHandler nextWorkHandler) {
        this.nextWorkHandler = nextWorkHandler;
    }

    @Override
    public void execute(FrameworkMessageContext frameworkMessageContext) {
        this.nextWorkHandler.execute(frameworkMessageContext);
        String message = frameworkMessageContext.getReponseMessage();
        GlobalApplication globalApplication = frameworkMessageContext.getGlobalApplication();
        String broadcastUserId = frameworkMessageContext.getBoradcastUserId();
        if (broadcastUserId != null) {
            GlobalWebSocket globalWebSocket = globalApplication.getGlobalWebSocket(broadcastUserId);
            if (globalWebSocket != null) {
                globalWebSocket.send(message);
            }
        }
    }
}

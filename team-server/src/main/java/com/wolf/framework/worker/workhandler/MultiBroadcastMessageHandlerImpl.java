package com.wolf.framework.worker.workhandler;

import com.wolf.framework.websocket.GlobalApplication;
import com.wolf.framework.websocket.GlobalWebSocket;
import com.wolf.framework.worker.FrameworkMessageContext;
import java.util.List;

/**
 *
 * @author aladdin
 */
public class MultiBroadcastMessageHandlerImpl implements WorkHandler {

    private final WorkHandler nextWorkHandler;

    public MultiBroadcastMessageHandlerImpl(WorkHandler nextWorkHandler) {
        this.nextWorkHandler = nextWorkHandler;
    }

    @Override
    public void execute(FrameworkMessageContext frameworkMessageContext) {
        this.nextWorkHandler.execute(frameworkMessageContext);
        String message = frameworkMessageContext.getReponseMessage();
        GlobalApplication globalApplication = frameworkMessageContext.getGlobalApplication();
        List<String> broadcastUserIdList = frameworkMessageContext.getBroadcastUserIdList();
        if (broadcastUserIdList != null) {
            GlobalWebSocket globalWebSocket;
            for (String broadcastUserId : broadcastUserIdList) {
                globalWebSocket = globalApplication.getGlobalWebSocket(broadcastUserId);
                if (globalWebSocket != null) {
                    globalWebSocket.send(message);
                }
            }
        }
    }
}

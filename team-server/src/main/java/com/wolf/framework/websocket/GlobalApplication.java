package com.wolf.framework.websocket;

import java.util.Map;

/**
 *
 * @author aladdin
 */
public interface GlobalApplication {

    public Map<String, GlobalWebSocket> getGlobalWebSockets();
    
    public void executeService(String act, Map<String, String> parameterMap);
    
    public void saveGlobalWebSocket(GlobalWebSocket globalWebSocket);
    
    public GlobalWebSocket getGlobalWebSocket(String userId);
    
    public void removGlobalWebSocket(String userId);
}

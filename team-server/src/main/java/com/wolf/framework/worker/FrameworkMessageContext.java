package com.wolf.framework.worker;

import com.wolf.framework.service.parameter.FieldHandler;
import com.wolf.framework.websocket.GlobalApplication;
import com.wolf.framework.websocket.GlobalWebSocket;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zoe
 */
public interface FrameworkMessageContext extends MessageContext {

    public GlobalWebSocket getGlobalWebSocket();

    public GlobalApplication getGlobalApplication();

    public void putParameter(String name, String value);

    public void removeParameter(String name);

    public String getAct();
    
    public void saveSession();
    
    public void removeSession();

    public void setPageParameter(String parameter, String value);

    public void invalid();
    
    public boolean isSuccess();

    public void setInfo(String msg);

    public String getJson();
    
    public String getRequestMessage();
    
    public String getReponseMessage();
    
    public void createJsonMessage(String[] fieldNames, Map<String, FieldHandler> fieldHandlerMap);

    public void createPageJsonMessage(String[] fieldNames, Map<String, FieldHandler> fieldHandlerMap);
    
    public String getBoradcastUserId();
    
    public List<String> getBroadcastUserIdList();
}

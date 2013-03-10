package com.team.websocket;

import com.sun.grizzly.tcp.Request;
import com.sun.grizzly.websockets.DataFrame;
import com.sun.grizzly.websockets.ProtocolHandler;
import com.sun.grizzly.websockets.WebSocket;
import com.sun.grizzly.websockets.WebSocketApplication;
import com.sun.grizzly.websockets.WebSocketListener;
import com.team.config.TeamLoggerEnum;
import com.wolf.framework.logger.LogFactory;
import com.wolf.framework.session.Session;
import com.wolf.framework.websocket.GlobalApplication;
import com.wolf.framework.websocket.GlobalTestSocketImpl;
import com.wolf.framework.websocket.GlobalWebSocket;
import com.wolf.framework.worker.FrameworkMessageContext;
import com.wolf.framework.worker.ServiceWorker;
import com.wolf.framework.worker.WebSocketMessageContextImpl;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;

/**
 *
 * @author aladdin
 */
public final class TestTeamGlobalApplication extends WebSocketApplication implements GlobalApplication {
    
    private final ConcurrentHashMap<String, GlobalWebSocket> webSockets = new ConcurrentHashMap<String, GlobalWebSocket>(32767, 1);
    private final GlobalWebSocket globalTestSocket = new GlobalTestSocketImpl();
    private final Map<String, ServiceWorker> serviceWorkerMap;
    private final Logger logger = LogFactory.getLogger(TeamLoggerEnum.TEAM);
    private final Pattern actPattern = Pattern.compile("(?:\"act\":\")([A-Z_]+)(?:\")");
    
    public TestTeamGlobalApplication(Map<String, ServiceWorker> serviceWorkerMap) {
        this.serviceWorkerMap = serviceWorkerMap;
    }
    
    @Override
    public WebSocket createWebSocket(ProtocolHandler protocolHandler, WebSocketListener... listeners) {
        return this.globalTestSocket;
    }
    
    @Override
    public boolean isApplicationRequest(Request request) {
        return false;
    }
    
    @Override
    public void onConnect(WebSocket socket) {
    }
    
    @Override
    public void onClose(WebSocket socket, DataFrame frame) {
    }
    
    @Override
    public void onMessage(WebSocket socket, String text) {
        String act = null;
        //获取act
        Matcher matcher = this.actPattern.matcher(text);
        if (matcher.find()) {
            act = matcher.group(1);
        }
        ServiceWorker serviceWorker = this.serviceWorkerMap.get(act);
        if (serviceWorker == null) {
            this.logger.error("invalid act value:{}", text);
            //无效的act
            socket.send("{\"flag\":\"INVALID\",\"info\":\"invalid act value\"}");
        } else {
            //创建消息对象并执行服务
            FrameworkMessageContext frameworkMessageContext = new WebSocketMessageContextImpl(this, (GlobalWebSocket) socket, act, text);
            serviceWorker.doWork(frameworkMessageContext);
        }
    }
    
    @Override
    public Map<String, GlobalWebSocket> getGlobalWebSockets() {
        return Collections.unmodifiableMap(this.webSockets);
    }
    
    @Override
    public void executeService(final String act, final Map<String, String> parameterMap) {
        ServiceWorker serviceWorker = this.serviceWorkerMap.get(act);
        if (serviceWorker == null) {
            this.logger.error("invalid act value:{}", act);
            throw new RuntimeException("invalid act value!..see log");
        } else {
            //创建消息对象并执行服务
            FrameworkMessageContext frameworkMessageContext = new WebSocketMessageContextImpl(this, this.globalTestSocket, act, parameterMap);
            serviceWorker.doWork(frameworkMessageContext);
        }
    }
    
    @Override
    public GlobalWebSocket getGlobalWebSocket(String userId) {
        return this.webSockets.get(userId);
    }
    
    public void setSession(Session session) {
        this.globalTestSocket.setSession(session);
    }

    @Override
    public void saveGlobalWebSocket(GlobalWebSocket globalWebSocket) {
    }

    @Override
    public void removGlobalWebSocket(String userId) {
    }
}

package com.wolf.framework.worker;

import com.wolf.framework.config.DefaultResponseFlagEnum;
import com.wolf.framework.config.ResponseFlagType;
import com.wolf.framework.dao.Entity;
import com.wolf.framework.service.parameter.FieldHandler;
import com.wolf.framework.session.Session;
import com.wolf.framework.utils.JsonUtils;
import com.wolf.framework.websocket.GlobalApplication;
import com.wolf.framework.websocket.GlobalWebSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author aladdin
 */
public class WebSocketMessageContextImpl implements FrameworkMessageContext {

    private final GlobalApplication globalApplication;
    private final GlobalWebSocket globalWebSocket;
    private final Map<String, String> parameterMap = new HashMap<String, String>(16, 1);
    private final String requestMessage;
    private String responseMessage;
    private final String act;
    private ResponseFlagType flag = DefaultResponseFlagEnum.FAILURE;
    private String info = "";
    private String pageIndex = "";
    private String nextPageIndex = "";
    private int pageTotal = 0;
    private int pageSize = 15;
    private Map<String, String> mapData;
    private List<Map<String, String>> mapListData;
    private Session session;
    private String boradcastUserId;
    private List<String> broadcastUserIdList;

    public WebSocketMessageContextImpl(final GlobalApplication globalApplication, final GlobalWebSocket globalWebSocket, String act, Map<String, String> parameterMap) {
        this(globalApplication, globalWebSocket, act, "");
        if (parameterMap != null) {
            this.parameterMap.putAll(parameterMap);
        }
    }

    public WebSocketMessageContextImpl(final GlobalApplication globalApplication, final GlobalWebSocket globalWebSocket, String act, String requestMessage) {
        this.globalApplication = globalApplication;
        this.globalWebSocket = globalWebSocket;
        this.act = act;
        this.requestMessage = requestMessage;
    }

    @Override
    public String getParameter(String name) {
        return this.parameterMap.get(name);
    }

    @Override
    public void putParameter(String name, String value) {
        this.parameterMap.put(name, value);
    }

    @Override
    public void removeParameter(String name) {
        this.parameterMap.remove(name);
    }

    @Override
    public String getAct() {
        return this.act;
    }

    @Override
    public String getPageIndex() {
        return this.pageIndex;
    }

    @Override
    public int getPageSize() {
        return this.pageSize;
    }

    @Override
    public void setPageParameter(String parameter, String value) {
        if (parameter.equals("pageIndex")) {
            this.pageIndex = value;
        } else if (parameter.equals("pageSize")) {
            this.pageSize = Integer.parseInt(value);
        }
    }

    @Override
    public void setNextPageIndex(String nextPageIndex) {
        this.nextPageIndex = nextPageIndex;
    }

    @Override
    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }

    @Override
    public Map<String, String> getParameterMap() {
        return this.parameterMap;
    }

    @Override
    public void setInfo(String msg) {
        this.info = msg;
    }

    @Override
    public void setMapData(Map<String, String> parameterMap) {
        this.mapData = parameterMap;
        this.mapListData = null;
    }

    @Override
    public void setMapListData(List<Map<String, String>> parameterMapList) {
        this.mapData = null;
        this.mapListData = parameterMapList;
    }

    @Override
    public <T extends Entity> void setEntityData(T t) {
        Map<String, String> entityMap = t.toMap();
        this.setMapData(entityMap);
    }

    @Override
    public <T extends Entity> void setEntityListData(List<T> tList) {
        List<Map<String, String>> entityMapList = new ArrayList<Map<String, String>>(tList.size());
        for (T t : tList) {
            entityMapList.add(t.toMap());
        }
        this.setMapListData(entityMapList);
    }

    @Override
    public void invalid() {
        this.flag = DefaultResponseFlagEnum.INVALID;
    }

    @Override
    public void success() {
        this.flag = DefaultResponseFlagEnum.SUCCESS;
    }

    @Override
    public void setFlag(ResponseFlagType responseFlagType) {
        this.flag = responseFlagType;
    }

    @Override
    public void createJsonMessage(String[] fieldNames, Map<String, FieldHandler> fieldHandlerMap) {
        StringBuilder jsonBuilder = new StringBuilder(64);
        jsonBuilder.append("{\"flag\":\"").append(this.flag.getFlagName());
        jsonBuilder.append("\",\"act\":\"").append(this.act);
        String data = "";
        if (fieldNames.length > 0) {
            if (this.mapData != null) {
                data = JsonUtils.mapToJSON(this.mapData, fieldNames, fieldHandlerMap);
            } else if (this.mapListData != null) {
                data = JsonUtils.mapListToJSON(this.mapListData, fieldNames, fieldHandlerMap);
            }
        }
        jsonBuilder.append("\",\"data\":[").append(data).append("]}");
        this.responseMessage = jsonBuilder.toString();
    }

    @Override
    public void createPageJsonMessage(String[] fieldNames, Map<String, FieldHandler> fieldHandlerMap) {
        StringBuilder jsonBuilder = new StringBuilder(128);
        jsonBuilder.append("{\"flag\":\"").append(this.flag.getFlagName());
        jsonBuilder.append("\",\"act\":\"").append(this.act);
        if (this.flag == DefaultResponseFlagEnum.INVALID) {
            jsonBuilder.append("\",\"info\":\"").append(this.info);
        }
        jsonBuilder.append("\",\"pageTotal\":\"").append(this.pageTotal);
        jsonBuilder.append("\",\"nextPageIndex\":\"").append(this.nextPageIndex);
        jsonBuilder.append("\",\"pageSize\":\"").append(this.pageSize);
        String data = "";
        if (fieldNames.length > 0) {
            if (this.mapData != null) {
                data = JsonUtils.mapToJSON(this.mapData, fieldNames, fieldHandlerMap);
            } else if (this.mapListData != null) {
                data = JsonUtils.mapListToJSON(this.mapListData, fieldNames, fieldHandlerMap);
            }
        }
        jsonBuilder.append("\",\"data\":[").append(data).append("]}");
        this.responseMessage = jsonBuilder.toString();
    }

    @Override
    public String getJson() {
        StringBuilder jsonBuilder = new StringBuilder(64);
        jsonBuilder.append("{\"flag\":\"").append(this.flag.getFlagName());
        jsonBuilder.append("\",\"act\":\"").append(this.act);
        jsonBuilder.append("\",\"info\":\"").append(this.info);
        jsonBuilder.append("\"}");
        return jsonBuilder.toString();
    }

    @Override
    public GlobalWebSocket getGlobalWebSocket() {
        return this.globalWebSocket;
    }

    @Override
    public GlobalApplication getGlobalApplication() {
        return this.globalApplication;
    }

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public Session getSession() {
        return this.session;
    }

    @Override
    public String getRequestMessage() {
        return this.requestMessage;
    }

    @Override
    public String getReponseMessage() {
        return this.responseMessage;
    }

    @Override
    public boolean isSuccess() {
        return this.flag == DefaultResponseFlagEnum.SUCCESS;
    }

    @Override
    public String getBoradcastUserId() {
        return boradcastUserId;
    }

    @Override
    public void setBoradcastUserId(String boradcastUserId) {
        this.boradcastUserId = boradcastUserId;
    }

    @Override
    public List<String> getBroadcastUserIdList() {
        return broadcastUserIdList;
    }

    @Override
    public void setBroadcastUserIdList(List<String> broadcastUserIdList) {
        this.broadcastUserIdList = broadcastUserIdList;
    }

    @Override
    public boolean isOnline(String userId) {
        boolean result = false;
        GlobalWebSocket socket = this.globalApplication.getGlobalWebSocket(userId);
        if (socket != null) {
            result = true;
        }
        return result;
    }

    @Override
    public void saveSession() {
        if (this.session == null) {
            throw new RuntimeException("session is null when save session.please check you code.");
        } else {
            //新session存在
            //判断当前接口是否重复登录
            Session socketSession = this.globalWebSocket.getSession();
            if (socketSession == null) {
                //当前socket session不存在，为首次链接,保存新的session
                this.globalWebSocket.setSession(this.session);
                //保存socket
                this.globalApplication.saveGlobalWebSocket(this.globalWebSocket);
            } else {
                //当前socket session存在，判断是和新session属于同一个用户
                String socketUserId = socketSession.getUserId();
                String userId = this.session.getUserId();
                //socketUserId == userId:重复登录,无须任何操作
                if (!socketUserId.equals(userId)) {
                    //切换用户,改变socket session,改变socket的集合id
                    this.globalApplication.removGlobalWebSocket(socketUserId);
                    this.globalWebSocket.setSession(this.session);
                    this.globalApplication.saveGlobalWebSocket(this.globalWebSocket);
                }
            }
        }
    }

    @Override
    public void removeSession() {
        this.globalWebSocket.setSession(null);
    }
}

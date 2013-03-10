package com.wolf.framework.worker;

import com.wolf.framework.config.ResponseFlagType;
import com.wolf.framework.dao.Entity;
import com.wolf.framework.session.Session;
import java.util.List;
import java.util.Map;

/**
 *
 * @author aladdin
 */
public interface MessageContext {

    public String getParameter(String name);

    public Map<String, String> getParameterMap();

    public String getPageIndex();

    public int getPageSize();

    public void setNextPageIndex(String nextPageIndex);

    public void setPageTotal(int pageTotal);

    public void setMapData(Map<String, String> parameterMap);

    public void setMapListData(List<Map<String, String>> parameterMapList);

    public <T extends Entity> void setEntityData(T t);

    public <T extends Entity> void setEntityListData(List<T> tList);

    public void setSession(Session session);

    public Session getSession();

    public void success();

    public void setBroadcastUserIdList(List<String> broadcastUserIdList);

    public void setBoradcastUserId(String boradcastUserId);

    public void setFlag(ResponseFlagType responseFlagType);

    public boolean isOnline(String userId);
}

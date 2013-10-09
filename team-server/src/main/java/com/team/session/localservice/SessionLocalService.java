package com.team.session.localservice;

import com.team.session.entity.SessionEntity;
import com.wolf.framework.local.Local;

/**
 *
 * @author aladdin
 */
public interface SessionLocalService extends Local {

    public byte SESSION_TYPE_WEB = 1;
    public byte SESSION_TYPE_IPHONE = 2;
    public byte SESSION_TYPE_ANDROID = 3;
    public byte SESSION_TYPE_DESKTOP = 4;
    public byte SESSION_TYPE_FLEX = 5;

    public SessionEntity inquireBySessionId(String sessionId);

    public SessionEntity createWebSession(String userId);
    
    public void celarInvolidSession();
}

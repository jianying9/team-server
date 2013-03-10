package com.team.session;

import com.wolf.framework.session.Session;

/**
 *
 * @author aladdin
 */
public class SessionImpl implements Session{
    
    private String userId;

    public SessionImpl(String userId) {
        this.userId = userId;
    }

    @Override
    public String getUserId() {
        return this.userId;
    }
}

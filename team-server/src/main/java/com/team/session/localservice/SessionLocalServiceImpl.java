package com.team.session.localservice;

import com.team.session.entity.SessionEntity;
import com.wolf.framework.dao.EntityDao;
import com.wolf.framework.dao.InquireResult;
import com.wolf.framework.dao.annotation.DAO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zoe
 */
//@LocalServiceConfig(
//        interfaceInfo = SessionLocalService.class,
//description = "session内部接口")
public final class SessionLocalServiceImpl implements SessionLocalService {

    @DAO(clazz = SessionEntity.class)
    private EntityDao<SessionEntity> sessionEntityDao;

    @Override
    public SessionEntity inquireBySessionId(String sessionId) {
        return this.sessionEntityDao.inquireByKey(sessionId);
    }

    private SessionEntity insertSession(String userId, byte type) {
        Map<String, String> entityMap = new HashMap<String, String>(8, 1);
        entityMap.put("userId", userId);
        entityMap.put("type", Byte.toString(type));
        long createTimeLong = System.currentTimeMillis();
        entityMap.put("createTimeLong", Long.toString(createTimeLong));
        long invalidTimeLong = createTimeLong + 2592000000L;
        entityMap.put("invalidTimeLong", Long.toString(invalidTimeLong));
        return this.sessionEntityDao.insert(entityMap);
    }

    private SessionEntity createSession(String userId, byte type) {
        SessionEntity sessionEntity = null;
        InquireResult<SessionEntity> sessionResult = this.sessionEntityDao.inquireByColumn("userId", userId);
        if (sessionResult.isEmpty()) {
            //web session 不存在 
            sessionEntity = this.insertSession(userId, type);
        } else {
            List<SessionEntity> sessionEntityList = sessionResult.getResultList();
            for (SessionEntity session : sessionEntityList) {
                if (session.getType() == type) {
                    //已经存在
                    sessionEntity = session;
                }
            }
            if (sessionEntity == null) {
                //web session 不存在 
                sessionEntity = this.insertSession(userId, type);
            }
        }
        return sessionEntity;
    }

    @Override
    public SessionEntity createWebSession(String userId) {
        return this.createSession(userId, SessionLocalService.SESSION_TYPE_WEB);
    }

    @Override
    public void celarInvolidSession() {
        String thisTime = Long.toString(System.currentTimeMillis());
//        ConditionContext condition = new ConditionContext("invalidTimeLong", ConditionTypeEnum.EQUAL_AND_LESS, thisTime);
//        List<Long> sessionIdList = this.sessionEntityDao.inquireKeysByCondition(condition);
//        this.sessionEntityDao.batchDelete(sessionIdList);
    }
}

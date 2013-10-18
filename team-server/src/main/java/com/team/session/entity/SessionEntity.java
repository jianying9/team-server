package com.team.session.entity;

import com.wolf.framework.dao.Entity;
import com.wolf.framework.data.BasicTypeEnum;
import com.wolf.framework.service.parameter.ParameterConfig;
import com.wolf.framework.service.parameter.ParametersConfig;
import java.util.HashMap;
import java.util.Map;

/**
 * session
 *
 * @author aladdin
 */
//@RDaoConfig(
//        tableName = "Session")
@ParametersConfig()
public class SessionEntity extends Entity {

    @ParameterConfig(basicTypeEnum = BasicTypeEnum.UUID, desc = "sessionId")
    private String sessionId;
    @ParameterConfig(basicTypeEnum = BasicTypeEnum.LONG, desc = "用户id")
    private String userId;
    @ParameterConfig(basicTypeEnum = BasicTypeEnum.INT, desc = "客户端类型:1:web,2:mobile,3:desktop")
    private int type;
    @ParameterConfig(basicTypeEnum = BasicTypeEnum.DATE_TIME, desc = "创建时间")
    private long createTime;
    @ParameterConfig(basicTypeEnum = BasicTypeEnum.DATE_TIME, desc = "失效时间,默认为创建时间+30天")
    private long invalidTime;

    public String getSessionId() {
        return sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public int getType() {
        return type;
    }

    public long getCreateTime() {
        return createTime;
    }

    public long getInvalidTime() {
        return invalidTime;
    }

    @Override
    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String, String>(8, 1);
        map.put("sessionId", this.sessionId);
        map.put("userId", this.userId);
        map.put("type", Integer.toString(type));
        map.put("createTime", Long.toString(this.createTime));
        map.put("invalidTime", Long.toString(this.invalidTime));
        return map;
    }

    @Override
    protected void parseMap(Map<String, String> entityMap) {
        this.sessionId = entityMap.get("sessionId");
        this.userId = entityMap.get("userId");
        this.type = Integer.parseInt(entityMap.get("type"));
        this.createTime = Long.parseLong(entityMap.get("createTime"));
        this.invalidTime = Long.parseLong(entityMap.get("invalidTime"));
    }

    @Override
    public String getKeyValue() {
        return this.sessionId;
    }
}

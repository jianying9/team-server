package com.team.session.entity;

import com.wolf.framework.dao.annotation.DaoConfig;
import com.wolf.framework.dao.Entity;
import com.wolf.framework.service.parameter.FieldConfig;
import com.wolf.framework.service.parameter.ParametersConfig;
import com.wolf.framework.service.parameter.type.FieldTypeEnum;
import java.util.HashMap;
import java.util.Map;

/**
 * session
 *
 * @author aladdin
 */
//@DaoConfig(
//tableName = "Session")
//@ParametersConfig()
public class SessionEntity extends Entity {

    @FieldConfig(type = FieldTypeEnum.UUID, fieldDesc = "sessionId")
    private String sessionId;
    @FieldConfig(type = FieldTypeEnum.BIG_INT_SIGNED, fieldDesc = "用户id")
    private long userId;
    @FieldConfig(type = FieldTypeEnum.CHAR8, fieldDesc = "客户端类型:1:web,2:mobile,3:desktop,4:flex")
    private byte type;
    @FieldConfig(type = FieldTypeEnum.DATETIME, fieldDesc = "创建时间")
    private String createTime;
    @FieldConfig(type = FieldTypeEnum.DATETIME, fieldDesc = "失效时间,默认为创建时间+30天")
    private String invalidTime;

    public String getSessionId() {
        return sessionId;
    }

    public long getUserId() {
        return userId;
    }

    public byte getType() {
        return type;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getInvalidTime() {
        return invalidTime;
    }

    @Override
    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String, String>(8, 1);
        map.put("sessionId", this.sessionId);
        map.put("userId", Long.toString(this.userId));
        map.put("type", Byte.toString(type));
        map.put("createTime", this.createTime);
        map.put("invalidTime", this.invalidTime);
        return map;
    }

    @Override
    protected void parseMap(Map<String, String> entityMap) {
        this.sessionId = entityMap.get("sessionId");
        this.userId = Long.parseLong(entityMap.get("userId"));
        this.type = Byte.parseByte(entityMap.get("type"));
        this.createTime = entityMap.get("createTime");
        this.invalidTime = entityMap.get("invalidTime");
    }

    @Override
    public String getKeyValue() {
        return this.sessionId;
    }
}

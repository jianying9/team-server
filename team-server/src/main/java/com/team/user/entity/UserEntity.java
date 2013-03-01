package com.team.user.entity;

import com.wolf.framework.dao.Entity;
import com.wolf.framework.dao.annotation.Column;
import com.wolf.framework.dao.annotation.DaoConfig;
import com.wolf.framework.dao.annotation.IndexTypeEnum;
import com.wolf.framework.dao.annotation.Key;
import com.wolf.framework.service.parameter.FieldConfig;
import com.wolf.framework.service.parameter.ParametersConfig;
import com.wolf.framework.service.parameter.type.FieldTypeEnum;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 * @author zoe
 */
@DaoConfig(
tableName = "User",
useCache = true,
maxEntriesLocalHeap = 10000)
@ParametersConfig()
public class UserEntity extends Entity {

    @FieldConfig(type = FieldTypeEnum.UUID, fieldDesc = "用户id")
    @Key()
    private String userId;
    //
    @FieldConfig(type = FieldTypeEnum.CHAR32, fieldDesc = "昵称")
    @Column(indexTypeEnum = IndexTypeEnum.STRING_INDEX)
    private String nickName;
    //
    @FieldConfig(type = FieldTypeEnum.CHAR32, fieldDesc = "密码md5")
    @Column()
    private String password;
    //
    @FieldConfig(type = FieldTypeEnum.CHAR64, fieldDesc = "邮箱")
    @Column(indexTypeEnum = IndexTypeEnum.STRING_INDEX)
    private String userEmail;
    //
    @FieldConfig(type = FieldTypeEnum.DATETIME, fieldDesc = "注册时间")
    @Column()
    private String createTime;

    public String getUserId() {
        return userId;
    }

    public String getNickName() {
        return nickName;
    }

    public String getPassword() {
        return password;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getCreateTime() {
        return createTime;
    }

    @Override
    public String getKeyValue() {
        return this.userId;
    }

    @Override
    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String, String>(8, 1);
        map.put("userId", this.userId);
        map.put("nickName", this.nickName);
        map.put("password", this.password);
        map.put("userEmail", this.userEmail);
        map.put("createTime", this.createTime);
        return map;
    }

    @Override
    protected void parseMap(Map<String, String> entityMap) {
        this.userId = entityMap.get("userId");
        this.nickName = entityMap.get("nickName");
        this.password = entityMap.get("password");
        this.userEmail = entityMap.get("userEmail");
        this.createTime = entityMap.get("createTime");
    }
}

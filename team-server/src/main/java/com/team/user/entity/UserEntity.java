package com.team.user.entity;

import com.wolf.framework.dao.Entity;
import com.wolf.framework.dao.annotation.ColumnConfig;
import com.wolf.framework.dao.annotation.ColumnTypeEnum;
import com.wolf.framework.dao.annotation.DaoConfig;
import com.wolf.framework.data.DataTypeEnum;
import com.wolf.framework.service.parameter.Parameter;
import com.wolf.framework.service.parameter.ParameterConfig;
import com.wolf.framework.service.parameter.ParametersConfig;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 * @author aladdin
 */
@DaoConfig(
tableName = "User",
useCache = false,
maxEntriesLocalHeap = 10000)
@ParametersConfig()
public class UserEntity extends Entity implements Parameter{

    @ParameterConfig(dateTypeEnum = DataTypeEnum.UUID, desc = "用户id")
    @ColumnConfig(dataTypeEnum = DataTypeEnum.UUID, columnTypeEnum = ColumnTypeEnum.KEY, desc = "用户ID")
    private String userId;
    //
    @ParameterConfig(dateTypeEnum = DataTypeEnum.CHAR_32, desc = "昵称")
    @ColumnConfig(dataTypeEnum = DataTypeEnum.CHAR_32, columnTypeEnum = ColumnTypeEnum.INDEX, desc = "昵称")
    private String nickName;
    //
    @ColumnConfig(dataTypeEnum = DataTypeEnum.CHAR_32, desc = "密码md5")
    @ParameterConfig(dateTypeEnum = DataTypeEnum.CHAR_32, desc = "密码md5")
    private String password;
    //
    @ParameterConfig(dateTypeEnum = DataTypeEnum.CHAR_60, desc = "邮箱")
    @ColumnConfig(dataTypeEnum = DataTypeEnum.CHAR_60, columnTypeEnum = ColumnTypeEnum.INDEX, desc = "邮箱")
    private String userEmail;
    //
    @ParameterConfig(dateTypeEnum = DataTypeEnum.DATE_TIME, desc = "注册时间")
    @ColumnConfig(dataTypeEnum = DataTypeEnum.DATE_TIME, desc = "注册时间")
    private long createTime;

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

    public long getCreateTime() {
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
        map.put("createTime", Long.toString(this.createTime));
        return map;
    }

    @Override
    protected void parseMap(Map<String, String> entityMap) {
        this.userId = entityMap.get("userId");
        this.nickName = entityMap.get("nickName");
        this.password = entityMap.get("password");
        this.userEmail = entityMap.get("userEmail");
        this.createTime = Long.parseLong(entityMap.get("createTime"));
    }
}

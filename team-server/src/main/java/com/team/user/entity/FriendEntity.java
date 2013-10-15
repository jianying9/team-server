package com.team.user.entity;

import com.wolf.framework.dao.Entity;
import com.wolf.framework.dao.annotation.ColumnTypeEnum;
import com.wolf.framework.dao.annotation.RColumnConfig;
import com.wolf.framework.dao.annotation.RDaoConfig;
import java.util.HashMap;
import java.util.Map;

/**
 * 好友关系表
 *
 * @author aladdin
 */
@RDaoConfig(
        tableName = "Friend")
public class FriendEntity extends Entity {

    @RColumnConfig(columnTypeEnum = ColumnTypeEnum.KEY, desc = "记录ID")
    private String recordId;
    //
    @RColumnConfig(columnTypeEnum = ColumnTypeEnum.INDEX, desc = "所属用户ID")
    private String userId;
    //
    @RColumnConfig(desc = "好友ID")
    private String friendId;
    //
    @RColumnConfig(desc = "创建时间")
    private long createTime;

    public String getRecordId() {
        return recordId;
    }

    public String getFriendId() {
        return friendId;
    }

    public String getUserId() {
        return userId;
    }

    public long getCreateTime() {
        return createTime;
    }

    @Override
    public String getKeyValue() {
        return this.recordId;
    }

    @Override
    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String, String>(4, 1);
        map.put("recordId", this.recordId);
        map.put("friendId", this.friendId);
        map.put("userId", this.userId);
        map.put("createTime", Long.toString(this.createTime));
        return map;
    }

    @Override
    protected void parseMap(Map<String, String> entityMap) {
        this.recordId = entityMap.get("recordId");
        this.friendId = entityMap.get("friendId");
        this.userId = entityMap.get("userId");
        this.createTime = Long.parseLong(entityMap.get("createTime"));
    }
}

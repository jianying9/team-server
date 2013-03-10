package com.team.user.entity;

import com.wolf.framework.dao.Entity;
import com.wolf.framework.dao.annotation.Column;
import com.wolf.framework.dao.annotation.DaoConfig;
import com.wolf.framework.dao.annotation.IndexTypeEnum;
import com.wolf.framework.dao.annotation.Key;
import java.util.HashMap;
import java.util.Map;

/**
 * 好友关系表
 *
 * @author aladdin
 */
@DaoConfig(
tableName = "Friend",
useCache = true,
maxEntriesLocalHeap = 10000)
public class FriendEntity extends Entity {

    @Key()
    private String recordId;
    //
    @Column(indexTypeEnum = IndexTypeEnum.STRING_INDEX)
    private String userId;
    //
    @Column(indexTypeEnum = IndexTypeEnum.STRING_INDEX)
    private String friendId;
    //
    @Column()
    private String createTime;

    public String getRecordId() {
        return recordId;
    }

    public String getFriendId() {
        return friendId;
    }

    public String getUserId() {
        return userId;
    }

    public String getCreateTime() {
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
        map.put("createTime", this.createTime);
        return map;
    }

    @Override
    protected void parseMap(Map<String, String> entityMap) {
        this.recordId = entityMap.get("recordId");
        this.friendId = entityMap.get("friendId");
        this.userId = entityMap.get("userId");
        this.createTime = entityMap.get("createTime");
    }
}

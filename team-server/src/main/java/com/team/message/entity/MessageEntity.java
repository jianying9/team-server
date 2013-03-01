package com.team.message.entity;

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
 * 个人消息
 *
 * @author zoe
 */
@DaoConfig(
tableName = "Message")
@ParametersConfig()
public class MessageEntity extends Entity {

    @FieldConfig(type = FieldTypeEnum.UUID, fieldDesc = "消息id")
    @Key()
    private String messageId;
    //
    @FieldConfig(type = FieldTypeEnum.UUID, fieldDesc = "发送用户id")
    @Column()
    private String sendId;
    //
    @FieldConfig(type = FieldTypeEnum.UUID, fieldDesc = "接收用户id")
    @Column(indexTypeEnum = IndexTypeEnum.STRING_INDEX)
    private String receiveId;
    //
    @FieldConfig(type = FieldTypeEnum.CHAR256, fieldDesc = "消息内容")
    @Column()
    private String message;
    //
    @FieldConfig(type = FieldTypeEnum.TINY_INT_SIGNED, fieldDesc = "是否已读:0-未读,1-已读")
    @Column(indexTypeEnum = IndexTypeEnum.STRING_INDEX)
    private byte isRead;
    //
    @FieldConfig(type = FieldTypeEnum.DATETIME, fieldDesc = "创建时间")
    @Column()
    private String createTime;

    public String getMessageId() {
        return messageId;
    }

    public String getSendId() {
        return sendId;
    }

    public String getReceiveId() {
        return receiveId;
    }

    public String getMessage() {
        return message;
    }

    public byte getIsRead() {
        return isRead;
    }

    public String getCreateTime() {
        return createTime;
    }

    @Override
    public String getKeyValue() {
        return this.messageId;
    }

    @Override
    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String, String>(8, 1);
        map.put("messageId", this.messageId);
        map.put("sendId", this.sendId);
        map.put("receiveId", this.receiveId);
        map.put("message", this.message);
        map.put("isRead", Byte.toString(this.isRead));
        map.put("createTime", this.createTime);
        return map;
    }

    @Override
    protected void parseMap(Map<String, String> entityMap) {
        this.messageId = entityMap.get("messageId");
        this.sendId = entityMap.get("sendId");
        this.receiveId = entityMap.get("receiveId");
        this.message = entityMap.get("message");
        this.isRead = Byte.parseByte(entityMap.get("isRead"));
        this.createTime = entityMap.get("createTime");
    }
}

package com.team.message.entity;

import com.wolf.framework.dao.Entity;
import com.wolf.framework.dao.annotation.ColumnTypeEnum;
import com.wolf.framework.dao.annotation.RColumnConfig;
import com.wolf.framework.dao.annotation.RDaoConfig;
import com.wolf.framework.data.DataTypeEnum;
import com.wolf.framework.service.parameter.Parameter;
import com.wolf.framework.service.parameter.ParameterConfig;
import com.wolf.framework.service.parameter.ParametersConfig;
import java.util.HashMap;
import java.util.Map;

/**
 * 个人消息
 *
 * @author aladdin
 */
@RDaoConfig(
        tableName = "Message")
@ParametersConfig()
public class MessageEntity extends Entity implements Parameter {

    @ParameterConfig(dateTypeEnum = DataTypeEnum.UUID, desc = "消息id")
    @RColumnConfig(columnTypeEnum = ColumnTypeEnum.KEY, desc = "消息id")
    private String messageId;
    //
    @ParameterConfig(dateTypeEnum = DataTypeEnum.UUID, desc = "发送用户id")
    @RColumnConfig(columnTypeEnum = ColumnTypeEnum.INDEX, desc = "发送用户id")
    private String sendId;
    //
    @ParameterConfig(dateTypeEnum = DataTypeEnum.UUID, desc = "接收用户id")
    @RColumnConfig(columnTypeEnum = ColumnTypeEnum.INDEX, desc = "接收用户id")
    private String receiveId;
    //
    @ParameterConfig(dateTypeEnum = DataTypeEnum.CHAR_255, desc = "消息内容")
    @RColumnConfig(desc = "消息内容")
    private String message;
    //
    @ParameterConfig(dateTypeEnum = DataTypeEnum.DATE_TIME, desc = "创建时间")
    @RColumnConfig(desc = "创建时间")
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
        map.put("createTime", this.createTime);
        return map;
    }

    @Override
    protected void parseMap(Map<String, String> entityMap) {
        this.messageId = entityMap.get("messageId");
        this.sendId = entityMap.get("sendId");
        this.receiveId = entityMap.get("receiveId");
        this.message = entityMap.get("message");
        this.createTime = entityMap.get("createTime");
    }
}

package com.team.message.service;

import com.team.config.ActionNames;
import com.team.message.entity.MessageEntity;
import com.team.message.localservice.MessageLocalService;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.service.ParameterTypeEnum;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.session.Session;
import com.wolf.framework.worker.context.MessageContext;
import java.util.Map;

/**
 *
 * @author aladdin
 */
@ServiceConfig(
        actionName = ActionNames.INSERT_USER_MESSAGE,
        parameterTypeEnum = ParameterTypeEnum.PARAMETER,
        importantParameter = {"receiveId", "message"},
        returnParameter = {"messageId", "sendId", "receiveId", "message", "isRead", "createTime"},
        parametersConfigs = {MessageEntity.class},
        response = true,
        broadcast = true,
        description = "发送消息")
public class InsertUserMessageServiceImpl implements Service {

    @InjectLocalService()
    private MessageLocalService messageLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        Session session = messageContext.getSession();
        String userId = session.getUserId();
        Map<String, String> parameterMap = messageContext.getParameterMap();
        String receiveId = parameterMap.get("receiveId");
        String message = parameterMap.get("message");
        MessageEntity messageEntity = this.messageLocalService.insertAndInquireUserMessage(userId, receiveId, message);
        messageContext.addBroadcastUserId(receiveId);
        messageContext.setEntityData(messageEntity);
        messageContext.success();
    }
}

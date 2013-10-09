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
import java.util.List;

/**
 *
 * @author aladdin
 */
@ServiceConfig(
        actionName = ActionNames.INQUIRE_UNREAD_USER_MESSAGE,
        parameterTypeEnum = ParameterTypeEnum.NO_PARAMETER,
        returnParameter = {"messageId", "receiveId", "message", "createTime"},
        parametersConfigs = {MessageEntity.class},
        response = true,
        description = "查询未读消息")
public class InquireUnreadUserMessageServiceImpl implements Service {

    @InjectLocalService()
    private MessageLocalService messageLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        Session session = messageContext.getSession();
        String userId = session.getUserId();
        List<MessageEntity> userMessageEntityList = this.messageLocalService.inquireUnReadUserMessage(userId);
        messageContext.setEntityListData(userMessageEntityList);
        messageContext.success();
    }
}

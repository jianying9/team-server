package com.team.message.service;

import com.team.config.ActionNames;
import com.team.message.entity.MessageEntity;
import com.team.message.localservice.MessageLocalService;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.service.ParameterTypeEnum;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.worker.context.MessageContext;

/**
 *
 * @author aladdin
 */
@ServiceConfig(
        actionName = ActionNames.READ_USER_MESSAGE,
        parameterTypeEnum = ParameterTypeEnum.PARAMETER,
        importantParameter = {"messageId"},
        parametersConfigs = {MessageEntity.class},
        response = false,
        description = "确认消息已读")
public class ReadUserMessageServiceImpl implements Service {

    @InjectLocalService()
    private MessageLocalService messageLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        String messageId = messageContext.getParameter("messageId");
        this.messageLocalService.readUserMessage(messageId);
        messageContext.success();
    }
}

package com.team.message.service;

import com.team.config.ActionGroupNames;
import com.team.config.ActionNames;
import com.team.message.entity.MessageEntity;
import com.team.message.parameter.CanvasParameter;
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
        actionName = ActionNames.SEND_CANVAS_COMMAND,
        parameterTypeEnum = ParameterTypeEnum.PARAMETER,
        importantParameter = {"receiveId", "x", "y"},
        returnParameter = {"sendId", "receiveId", "x", "y"},
        parametersConfigs = {MessageEntity.class, CanvasParameter.class},
        response = false,
        broadcast = true,
        group = ActionGroupNames.MESSAGE,
        description = "发送画布指令")
public class SendCanvasCommandServiceImpl implements Service {

    @Override
    public void execute(MessageContext messageContext) {
        Session session = messageContext.getSession();
        String userId = session.getUserId();
        Map<String, String> parameterMap = messageContext.getParameterMap();
        String receiveId = parameterMap.get("receiveId");
        parameterMap.put("sendId", userId);
        messageContext.addBroadcastUserId(receiveId);
        messageContext.setMapData(parameterMap);
        messageContext.success();
    }
}

package com.team.user.service;

import com.team.config.ActionGroupNames;
import com.team.config.ActionNames;
import com.team.user.entity.UserEntity;
import com.wolf.framework.service.ParameterTypeEnum;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.worker.context.MessageContext;

/**
 *
 * @author aladdin
 */
@ServiceConfig(
        actionName = ActionNames.ASSERT_FRIEND_ONLINE,
parameterTypeEnum = ParameterTypeEnum.PARAMETER,
importantParameter = {"userId"},
returnParameter = {"userId"},
parametersConfigs = {UserEntity.class},
response = true,
group = ActionGroupNames.FRIEND,
description = "查询某个好友是否在线")
public class AssertFriendOnlineServiceImpl implements Service {

    @Override
    public void execute(MessageContext messageContext) {
        String userId = messageContext.getParameter("userId");
        messageContext.setMapData(messageContext.getParameterMap());
        boolean result = messageContext.isOnline(userId);
        if (result) {
            messageContext.success();
        }
    }
}

package com.team.user.service;

import com.team.config.ActionGroupNames;
import com.team.config.ActionNames;
import com.team.config.TeamResponseFlags;
import com.team.user.entity.UserEntity;
import com.team.user.localservice.UserLocalService;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.service.ParameterTypeEnum;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.session.Session;
import com.wolf.framework.worker.context.MessageContext;

/**
 *
 * @author aladdin
 */
@ServiceConfig(
        actionName = ActionNames.INSERT_FRIEND_BY_USER_ID,
        parameterTypeEnum = ParameterTypeEnum.PARAMETER,
        importantParameter = {"userId"},
        returnParameter = {"nickName", "userId"},
        parametersConfigs = {UserEntity.class},
        response = true,
        group = ActionGroupNames.FRIEND,
        description = "根据userId增加好友")
public class InsertFriendByUserIdServiceImpl implements Service {

    @InjectLocalService()
    private UserLocalService userLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        String friendId = messageContext.getParameter("userId");
        Session session = messageContext.getSession();
        String userId = session.getUserId();
        if (userId.equals(friendId)) {
            //不能添加自己为好友
            messageContext.setFlag(TeamResponseFlags.FAILURE_ADD_FRIEND_MYSELF);
        } else {
            UserEntity friendUserEntity = this.userLocalService.inquireUserByUserId(friendId);
            if (friendUserEntity == null) {
                messageContext.setFlag(TeamResponseFlags.FAILURE_USER_ID_NOT_EXIST);
            } else {
                //好友的userId存在
                synchronized (this) {
                    boolean flag = this.userLocalService.isFriendIdExist(userId, friendId);
                    if (flag) {
                        messageContext.setFlag(TeamResponseFlags.FAILURE_FRIEND_EXIST);
                    } else {
                        this.userLocalService.insertFriend(userId, friendId);
                        messageContext.setEntityData(friendUserEntity);
                        messageContext.success();
                    }
                }
            }
        }
    }
}

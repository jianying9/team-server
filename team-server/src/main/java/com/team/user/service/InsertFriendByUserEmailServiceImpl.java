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
        actionName = ActionNames.INSERT_FRIEND_BY_USER_EMAIL,
        parameterTypeEnum = ParameterTypeEnum.PARAMETER,
        importantParameter = {"userEmail"},
        returnParameter = {"nickName", "userId"},
        parametersConfigs = {UserEntity.class},
        response = true,
        group = ActionGroupNames.FRIEND,
        description = "根据userEmail增加好友")
public class InsertFriendByUserEmailServiceImpl implements Service {

    @InjectLocalService()
    private UserLocalService userLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        String userEmail = messageContext.getParameter("userEmail");
        UserEntity friendUserEntity = this.userLocalService.inquireUserByUserEmail(userEmail);
        if (friendUserEntity == null) {
            //邮箱不存在
            messageContext.setFlag(TeamResponseFlags.FAILURE_EMAIL_NOT_EXIST);
        } else {
            Session session = messageContext.getSession();
            String userId = session.getUserId();
            String friendId = friendUserEntity.getUserId();
            if (userId.equals(friendId)) {
                //不能添加自己为好友
                messageContext.setFlag(TeamResponseFlags.FAILURE_ADD_FRIEND_MYSELF);
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

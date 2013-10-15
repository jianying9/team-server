package com.team.user.service;

import com.team.config.ActionNames;
import com.team.config.ResponseFlagEnum;
import com.team.user.entity.FriendEntity;
import com.team.user.entity.UserEntity;
import com.team.user.localservice.UserLocalService;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.service.ParameterTypeEnum;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.service.SessionHandleTypeEnum;
import com.wolf.framework.session.Session;
import com.wolf.framework.worker.context.MessageContext;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aladdin
 */
@ServiceConfig(
        actionName = ActionNames.LOGOUT,
        parameterTypeEnum = ParameterTypeEnum.NO_PARAMETER,
        returnParameter = {"nickName", "userId"},
        parametersConfigs = {UserEntity.class},
        sessionHandleTypeEnum = SessionHandleTypeEnum.REMOVE,
        response = true,
        broadcast = true,
        description = "用户退出")
public class LogoutServiceImpl implements Service {

    @InjectLocalService()
    private UserLocalService userLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        Session session = messageContext.getSession();
        String userId = session.getUserId();
        UserEntity userEntity = this.userLocalService.inquireUserByUserId(userId);
        if (userEntity == null) {
            messageContext.setFlag(ResponseFlagEnum.FAILURE_USER_ID_NOT_EXIST);
        } else {
            //通知好友
            List<FriendEntity> friendEntityList = this.userLocalService.inquireFriendByUserId(userId, 1, 100);
            if (friendEntityList.isEmpty() == false) {
                List<String> friendIdList = new ArrayList<String>(friendEntityList.size());
                for (FriendEntity friendEntity : friendEntityList) {
                    friendIdList.add(friendEntity.getFriendId());
                }
                messageContext.addBroadcastUserIdList(friendIdList);
            }
            messageContext.setNewSession(null);
            messageContext.setEntityData(userEntity);
            messageContext.success();
        }
    }
}

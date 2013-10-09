package com.team.user.service;

import com.team.config.ActionNames;
import com.team.user.entity.FriendEntity;
import com.team.user.entity.UserEntity;
import com.team.user.localservice.UserLocalService;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.service.ParameterTypeEnum;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.session.Session;
import com.wolf.framework.worker.context.MessageContext;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aladdin
 */
@ServiceConfig(
        actionName = ActionNames.INQUIRE_FRIEND,
        parameterTypeEnum = ParameterTypeEnum.NO_PARAMETER,
        returnParameter = {"nickName", "userId"},
        parametersConfigs = {UserEntity.class},
        response = true,
        description = "查找当前用户的好友")
public class InquireFriendServiceImpl implements Service {

    @InjectLocalService()
    private UserLocalService userLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        Session session = messageContext.getSession();
        String userId = session.getUserId();
        List<FriendEntity> friendEntityList = this.userLocalService.inquireFriendByUserId(userId);
        if (!friendEntityList.isEmpty()) {
            //如果存在好友
            List<String> friendIdList = new ArrayList<String>(friendEntityList.size());
            for (FriendEntity friendEntity : friendEntityList) {
                friendIdList.add(friendEntity.getFriendId());
            }
            List<UserEntity> userEntityList = this.userLocalService.inquireUserByUserIdList(friendIdList);
            messageContext.setEntityListData(userEntityList);
        }
        messageContext.success();
    }
}

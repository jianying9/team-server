package com.team.user.service;

import com.team.config.ActionNames;
import com.team.user.entity.UserEntity;
import com.team.user.localservice.UserLocalService;
import com.wolf.framework.dao.InquireResult;
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
        actionName = ActionNames.SEARCH_USER_BY_NICKNAME,
        parameterTypeEnum = ParameterTypeEnum.PARAMETER,
        page = true,
        importantParameter = {"nickName"},
        returnParameter = {"nickName", "userId"},
        parametersConfigs = {UserEntity.class},
        response = true,
        description = "根据用户昵称查找用户")
public class SearchUserByNickNameServiceImpl implements Service {

    @InjectLocalService()
    private UserLocalService userLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        String nickName = messageContext.getParameter("nickName");
        InquireResult<UserEntity> userResult = this.userLocalService.searchUserByNickName(nickName, messageContext.getPageIndex(), messageContext.getPageSize());
        if (!userResult.isEmpty()) {
            messageContext.setPageTotal(userResult.getTotal());
            messageContext.setPageNum(userResult.getPageNum());
            messageContext.setEntityListData(userResult.getResultList());
        }
        messageContext.success();
    }
}

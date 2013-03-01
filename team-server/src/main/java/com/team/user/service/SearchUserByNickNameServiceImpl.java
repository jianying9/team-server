package com.team.user.service;

import com.team.config.ActionNames;
import com.team.user.entity.UserEntity;
import com.team.user.localservice.UserLocalService;
import com.wolf.framework.dao.InquireResult;
import com.wolf.framework.local.LocalService;
import com.wolf.framework.service.ParameterTypeEnum;
import com.wolf.framework.service.ResponseDataTypeEnum;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.worker.MessageContext;

/**
 *
 * @author zoe
 */
@ServiceConfig(
        actionName = ActionNames.SEARCH_USER_BY_NICKNAME,
parameterTypeEnum = ParameterTypeEnum.PARAMETER,
responseDataTypeEnum = ResponseDataTypeEnum.PAGE,
importantParameter = {"nickName"},
returnParameter = {"nickName", "userId"},
parametersConfigs = {UserEntity.class},
response = true,
description = "根据用户昵称查找用户")
public class SearchUserByNickNameServiceImpl implements Service {
    
    @LocalService()
    private UserLocalService userLocalService;
    
    @Override
    public void execute(MessageContext messageContext) {
        String nickName = messageContext.getParameter("nickName");
        InquireResult<UserEntity> userResult = this.userLocalService.searchUserByNickName(messageContext.getPageIndex(), messageContext.getPageSize(), nickName);
        if (!userResult.isEmpty()) {
            messageContext.setPageTotal(userResult.getTotal());
            messageContext.setNextPageIndex(userResult.getNextPageIndex());
            messageContext.setEntityListData(userResult.getResultList());
        }
        messageContext.success();
    }
}

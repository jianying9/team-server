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
import com.wolf.framework.worker.context.MessageContext;
import java.util.Map;

/**
 *
 * @author aladdin
 */
@ServiceConfig(
        actionName = ActionNames.REGISTER,
        parameterTypeEnum = ParameterTypeEnum.PARAMETER,
        importantParameter = {"nickName", "userEmail", "password"},
        returnParameter = {"userId", "userEmail"},
        parametersConfigs = {UserEntity.class},
        validateSession = false,
        response = true,
        requireTransaction = true,
        group = ActionGroupNames.USER,
        description = "用户注册:FAILURE_USER_EMAIL_USED-邮箱已经被使用")
public class RegisterServiceImpl implements Service {

    @InjectLocalService()
    private UserLocalService userLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        Map<String, String> parameterMap = messageContext.getParameterMap();
        String userEmail = parameterMap.get("userEmail");
        synchronized (this) {
            //查询邮箱是否被使用
            boolean isUserEmailExists = this.userLocalService.isUserEmailExist(userEmail);
            if (isUserEmailExists) {
                //邮箱已经被使用
                messageContext.setFlag(TeamResponseFlags.FAILURE_USER_EMAIL_USED);
            } else {
                //新增加用户
                UserEntity userEntity = this.userLocalService.insertUser(parameterMap);
                messageContext.setEntityData(userEntity);
                messageContext.success();
            }
        }
    }
}

package com.team.user.service;

import com.team.config.ActionGroupNames;
import com.team.config.ActionNames;
import com.team.config.TeamResponseFlags;
import com.team.session.SessionImpl;
import com.team.user.entity.UserEntity;
import com.team.user.localservice.UserLocalService;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.service.ParameterTypeEnum;
import com.wolf.framework.service.ResponseFlag;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.service.SessionHandleTypeEnum;
import com.wolf.framework.session.Session;
import com.wolf.framework.worker.context.MessageContext;
import java.util.Map;

/**
 *
 * @author aladdin
 */
@ServiceConfig(
        actionName = ActionNames.LOGIN,
        parameterTypeEnum = ParameterTypeEnum.PARAMETER,
        importantParameter = {"userEmail", "password"},
        returnParameter = {"userId", "userEmail", "nickName"},
        parametersConfigs = {UserEntity.class},
        validateSession = false,
        sessionHandleTypeEnum = SessionHandleTypeEnum.SAVE,
        response = true,
        description = "用户登录",
        group = ActionGroupNames.USER,
        responseFlags = {
    @ResponseFlag(flag = TeamResponseFlags.FAILURE_EMAIL_NOT_EXIST,
            description = "邮箱已经被使用")
})
public class LoginServiceImpl implements Service {

    @InjectLocalService()
    private UserLocalService userLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        Map<String, String> parameterMap = messageContext.getParameterMap();
        String userEmail = parameterMap.get("userEmail");
        UserEntity userEntity = this.userLocalService.inquireUserByUserEmail(userEmail);
        if (userEntity == null) {
            //邮箱不存在
            messageContext.setFlag(TeamResponseFlags.FAILURE_EMAIL_NOT_EXIST);
        } else {
            //邮箱存在
            String password = parameterMap.get("password");
            if (userEntity.getPassword().equals(password)) {
                String userId = userEntity.getUserId();
                //密码正确
                Session session = new SessionImpl(userId);
                messageContext.setNewSession(session);
                messageContext.setEntityData(userEntity);
                messageContext.success();
            } else {
                //密码错误
                messageContext.setFlag(TeamResponseFlags.FAILURE_PASSWORD_ERROR);
            }
        }
    }
}

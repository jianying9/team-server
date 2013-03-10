package com.team.config;

import com.wolf.framework.config.*;

/**
 *
 * @author aladdin
 */
public enum ResponseFlagEnum implements ResponseFlagType {

    //---------------------user-----------------------
    //邮箱不存在
    FAILURE_EMAIL_NOT_EXIST,
    //用户id不存在
    FAILURE_USER_ID_NOT_EXIST,
    //密码错误
    FAILURE_PASSWORD_ERROR,
    //邮箱已经被使用
    FAILURE_USER_EMAIL_USED,
    //好友已经存在
    FAILURE_FRIEND_EXIST,
    //不能添加自己为好友
    FAILURE_ADD_FRIEND_MYSELF;

    @Override
    public String getFlagName() {
        return this.name();
    }
}

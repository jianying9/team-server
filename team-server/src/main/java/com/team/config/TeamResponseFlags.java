package com.team.config;

/**
 *
 * @author aladdin
 */
public class TeamResponseFlags {

    //邮箱不存在
    public final static String FAILURE_EMAIL_NOT_EXIST = "FAILURE_EMAIL_NOT_EXIST";
    //用户id不存在
    public final static String FAILURE_USER_ID_NOT_EXIST = "FAILURE_USER_ID_NOT_EXIST";
    //密码错误
    public final static String FAILURE_PASSWORD_ERROR = "FAILURE_PASSWORD_ERROR";
    //邮箱已经被使用
    public final static String FAILURE_USER_EMAIL_USED = "FAILURE_USER_EMAIL_USED";
    //好友已经存在
    public final static String FAILURE_FRIEND_EXIST = "FAILURE_FRIEND_EXIST";
    //不能添加自己为好友
    public final static String FAILURE_ADD_FRIEND_MYSELF = "FAILURE_ADD_FRIEND_MYSELF";
}

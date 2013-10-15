package com.team.config;

/**
 *
 * @author aladdin
 */
public class ActionNames {
    
    //-----------------------session-------------------
    public final static String CLEAN_INVOLID_SESSION = "CLEAN_INVOLID_SESSION";

    //-----------------------用户------------------------//
    //注册
    public final static String REGISTER = "REGISTER";
    //登录
    public final static String LOGIN = "LOGIN";
    //退出
    public final static String LOGOUT = "LOGOUT";
    //增加好友
    public final static String  INSERT_FRIEND_BY_USER_ID = "INSERT_FRIEND_BY_USER_ID";
    //增加好友
    public final static String  INSERT_FRIEND_BY_USER_EMAIL = "INSERT_FRIEND_BY_USER_EMAIL";
    //查询当前用户好友列表
    public final static String  INQUIRE_FRIEND = "INQUIRE_FRIEND";
    //查询某个好友是否在线
    public final static String  ASSERT_FRIEND_ONLINE = "ASSERT_FRIEND_ONLINE";
    //发送消息
    public final static String  INSERT_USER_MESSAGE = "INSERT_USER_MESSAGE";
    //发送canvas指令
    public final static String SEND_CANVAS_COMMAND = "SEND_CANVAS_COMMAND";
    
}

package com.team.config;

/**
 *
 * @author zoe
 */
public class ActionNames {
    
    //-----------------------system-------------------
    public final static String MERGE_LUCENE_INDEX = "MERGE_LUCENE_INDEX";
    //-----------------------session-------------------
    public final static String CLEAN_INVOLID_SESSION = "CLEAN_INVOLID_SESSION";

    //-----------------------用户------------------------//
    //注册
    public final static String REGISTER = "REGISTER";
    //登录
    public final static String LOGIN = "LOGIN";
    //退出
    public final static String LOGOUT = "LOGOUT";
    //根据昵称查找用户
    public final static String  SEARCH_USER_BY_NICKNAME = "SEARCH_USER_BY_NICKNAME";
    //增加好友
    public final static String  INSERT_FRIEND = "INSERT_FRIEND";
    //查询当前用户好友列表
    public final static String  INQUIRE_FRIEND = "INQUIRE_FRIEND";
    //查询某个好友是否在线
    public final static String  ASSERT_FRIEND_ONLINE = "ASSERT_FRIEND_ONLINE";
    //发送消息
    public final static String  INSERT_USER_MESSAGE = "INSERT_USER_MESSAGE";
    //确认消息已读
    public final static String  READ_USER_MESSAGE = "READ_USER_MESSAGE";
    //查询未读消息
    public final static String  INQUIRE_UNREAD_USER_MESSAGE = "INQUIRE_UNREAD_USER_MESSAGE";
    //发送canvas指令
    public final static String SEND_CANVAS_COMMAND = "SEND_CANVAS_COMMAND";
    
}

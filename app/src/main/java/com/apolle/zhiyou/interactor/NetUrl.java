package com.apolle.zhiyou.interactor;

import java.lang.reflect.Array;
import java.util.HashMap;

/**
 * Created by huangtao on 2016/2/2619:54.
 * modify by huangtao on 19:54
 */
public class NetUrl {
    private static final String BASE_URL = "http://localhost:8080/index.php/";
    public final static String HOME_TITLE=BASE_URL+"api/square/default/content-title";//广场页标题
    public final static String HOME_CONTENT=BASE_URL+"api/square/default/content";//广场页各个主体下面的内容
    public final static String ARTICLE_COMMENTS=BASE_URL+"api/square/article/comments";//文章下面的评论
    public static final String ARTICLE_FORWARD = BASE_URL+"api/square/article/forward";//文章转发
    public static final String ARTICLE_COLLECT = BASE_URL+"api/square/article/collect";//文章收藏
    public static final String ARTICLE_FAVOURITE = BASE_URL+"api/square/article/favourite";//文章顶
    public static final String MY_NOTEPAD = BASE_URL+"api/note/notepad/my";//我的笔记本
    public static final String MY_NOTEPADS = BASE_URL+"api/note/notepad/myall";//我的所有笔记本
    public static final String PAD_NOTES = BASE_URL+"api/note/note/pad-notes";//我的笔记本和笔记
    public static final String USER_LOGIN =BASE_URL+"api/user/index/login";//登陆
    public static final String SAVE_SMS_SERVER =BASE_URL+"api/user/index/save-sms";//将本地生成的验证码发送到服务器上
    public static final String USER_REGISTER =BASE_URL+"api/user/index/register";//注册
    public static final String CHECK_VERIFYCODE =BASE_URL+"api/user/index/checkcode";//检验验证码是否正确
    public static final String SAVE_NOTE =BASE_URL+"api/note/note/collect-note";//收藏笔记
    public static final String FORWARD_COLLECT_NODE =BASE_URL+"api/note/note/forward-collect";//收藏笔记并转发
    public static final String FORWARD_NODE =BASE_URL+"api/note/note/forward";//转发笔记
    public static final String ADD_NOTE =BASE_URL+"api/note/notepad/add" ;

    //消息系统
    public static final String CHAT_NEWS =BASE_URL+"api/chat/news/lastest" ;
    public static final String CHAT_CONCACT =BASE_URL+"api/chat/concact/myfriends" ;
    public static final String CHAT_TREND =BASE_URL+"api/chat/trend/other-share" ;

    public static HashMap<String,String> initParams(){
        HashMap<String,String> params=new  HashMap<String,String>();

        return params;
    }

}

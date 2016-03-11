package com.apolle.zhiyou.interactor;

/**
 * Created by huangtao on 2016/2/2619:54.
 * modify by huangtao on 19:54
 */
public class NetUrl {
    private static final String BASE_URL = "http://192.168.42.216:8090/";
    public final static String HOME_TITLE=BASE_URL+"api/square/default/content-title";//广场页标题
    public final static String HOME_CONTENT=BASE_URL+"api/square/default/content";//广场页各个主体下面的内容
    public static final String  ARTICLE_FORWARD = BASE_URL+"api/square/article/forward";//文章转发
    public static final String  ARTICLE_COLLECT = BASE_URL+"api/square/article/collect";//文章收藏
    public static final String  ARTICLE_FAVOURITE = BASE_URL+"api/square/article/favourite";//文章顶
    public static final String  MY_NOTEPAD = BASE_URL+"api/note/notepad/my";//我的笔记本
    public static final String  MY_NOTEPADS = BASE_URL+"api/note/notepad/myall";//我的所有笔记本
}

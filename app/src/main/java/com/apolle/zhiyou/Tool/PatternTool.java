package com.apolle.zhiyou.Tool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by huangtao on 2016/3/1417:29.
 * modify by huangtao on 17:29
 */
public class PatternTool {

    private static boolean match(String string, String pattern){
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(string);
        return m.find();
    }
    /**
     * 检测是否是手机
     * @param mobile
     * @return
     */
    public static boolean isMobile(String mobile){
        return match(mobile,"^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
    }
    public static boolean isEmail(String email){
       return match(email,"[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?");
    }
}

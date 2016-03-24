package com.apolle.zhiyou.Tool;

import android.content.Context;
import android.content.SharedPreferences;

import com.apolle.zhiyou.Model.User;

/**
 * Created by huangtao on 2016/3/1320:48.
 * modify by huangtao on 20:48
 */
public class SharedPreTool {
     private static SharedPreTool sharedPreTool;
    private static final String  TAG_NAME ="zhiyou";
    private static SharedPreferences sharePreference;
    private static SharedPreferences.Editor editor;

    private SharedPreTool(Context context){
     this(context,TAG_NAME);
    }
    private SharedPreTool(Context context,String name){
        sharePreference= context.getSharedPreferences(name,Context.MODE_PRIVATE);
        editor=sharePreference.edit();
    }

    public static SharedPreTool newInstance(Context context) {
        if(null==sharedPreTool){
            sharedPreTool=new SharedPreTool(context);
        }
        return sharedPreTool;
    }
    public static SharedPreTool newInstance(Context context,String name) {
        if(null==sharedPreTool){
            sharedPreTool=new SharedPreTool(context,name);
        }
        return sharedPreTool;
    }

     public static boolean getIsLogin(Context context){
         newInstance(context);
        return sharePreference.getBoolean("isLogin",false);
     }
    public static void saveUserLogin(Context context,User user){
        newInstance(context);
        String username=user.getUsername();
        String nickname=user.getNickname();
        String headpic=user.getHeadpic();

        editor.putString("nickname",nickname!=null?nickname:"");
        editor.putString("username",username!=null?username:"");
        editor.putString("headpic",headpic!=null?headpic:"");
        editor.putBoolean("isLogin",true);
        editor.commit();
    }
    public static User getUser(Context context){
        newInstance(context);
        User user=new User();
        user.setNickname(sharePreference.getString("nickname",""));
        user.setHeadpic(sharePreference.getString("headpic",""));
        user.setUsername(sharePreference.getString("username",""));
        return user;
    }
    public static void saveSessionId(Context context,String value){
          newInstance(context);
        editor.putString("cookie",value);
        editor.commit();
    }
    public static String getSessionId(Context context){
        newInstance(context);
       return   sharePreference.getString("cookie","zhiyouzhiyouzhiyou123456");
    }
    public static void userLogout(Context context){
         newInstance(context);
        editor.putBoolean("isLogin",false).commit();

    }
}

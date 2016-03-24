package com.apolle.zhiyou.Model;

import com.apolle.zhiyou.Tool.Encrypt;

/**
 * Created by huangtao on 2016/3/1323:21.
 * modify by huangtao on 23:21
 */
public class User {
    private static final String TAG_SALT="@zhiyou.cn";

    /**
     * uid : 2
     * username : china123
     * nickname : 普罗米修斯会思考
     * headpic : null
     */

    private String uid;
    private String username;
    private String nickname;
    private String headpic;
    private String password;

    public void setUid(String uid) {
        this.uid = uid;
    }

    public User() {
    }

    public User(String username, String nickname, String headpic) {
        this.username = username;
        this.nickname = nickname;
        this.headpic = headpic;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setHeadpic(String headpic) {
        this.headpic = headpic;
    }

    public String getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public String getHeadpic() {
        return headpic;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password=EncryptPwd(password);
    }
    public static String EncryptPwd(String password){
        try {
            return password = Encrypt.Md5(password+TAG_SALT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}

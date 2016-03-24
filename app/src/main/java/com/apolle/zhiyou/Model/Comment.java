package com.apolle.zhiyou.Model;

import java.io.Serializable;

/**
 * Created by huangtao on 2016/3/1618:13.
 * modify by huangtao on 18:13
 */
public class Comment implements Serializable{

    /**
     * pid : 1
     * tid : 2
     * comment : null
     * dateline : null
     * uid : 2
     * peak : null
     * nickname : 唐僧
     * username : zhiyou2
     * headpic : null
     */

    private String pid;
    private String tid;
    private Object comment;
    private Object dateline;
    private String uid;
    private Object peak;
    private String nickname;
    private String username;
    private Object headpic;

    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public void setComment(Object comment) {
        this.comment = comment;
    }

    public void setDateline(Object dateline) {
        this.dateline = dateline;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setPeak(Object peak) {
        this.peak = peak;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setHeadpic(Object headpic) {
        this.headpic = headpic;
    }

    public String getPid() {
        return pid;
    }

    public String getTid() {
        return tid;
    }

    public Object getComment() {
        return comment;
    }

    public Object getDateline() {
        return dateline;
    }

    public String getUid() {
        return uid;
    }

    public Object getPeak() {
        return peak;
    }

    public String getNickname() {
        return nickname;
    }

    public String getUsername() {
        return username;
    }

    public Object getHeadpic() {
        return headpic;
    }
}

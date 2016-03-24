package com.apolle.zhiyou.Model;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by huangtao on 2016/3/2116:52.
 * modify by huangtao on 16:52
 */
public class NewBean implements Serializable{

    /**
     * new_id : 1
     * sendor : zhiyou3
     * receiver : zhiyou2
     * sendid : 3
     * receiverid : 2
     * details : 你的文章我很喜欢
     * sendtime : 1
     * receivetime : null
     * isread : 0
     * uid : 3
     * nickname : 孙悟空
     * username : zhiyou3
     * headpic :
     * newsCount : 2
     */

    private String new_id;
    private String sendor;
    private String receiver;
    private String sendid;
    private String receiverid;
    private String details;
    private String sendtime;
    private Object receivetime;
    private String isread;
    private String uid;
    private String nickname;
    private String username;
    private String headpic;
    private String newsCount;

    public void setNew_id(String new_id) {
        this.new_id = new_id;
    }

    public void setSendor(String sendor) {
        this.sendor = sendor;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setSendid(String sendid) {
        this.sendid = sendid;
    }

    public void setReceiverid(String receiverid) {
        this.receiverid = receiverid;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public void setReceivetime(Object receivetime) {
        this.receivetime = receivetime;
    }

    public void setIsread(String isread) {
        this.isread = isread;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setHeadpic(String headpic) {
        this.headpic = headpic;
    }

    public void setNewsCount(String newsCount) {
        this.newsCount = newsCount;
    }

    public String getNew_id() {
        return new_id;
    }

    public String getSendor() {
        return sendor;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getSendid() {
        return sendid;
    }

    public String getReceiverid() {
        return receiverid;
    }

    public String getDetails() {
        return details;
    }

    public String getSendtime() {
        return sendtime;
    }

    public Object getReceivetime() {
        return receivetime;
    }

    public String getIsread() {
        return isread;
    }

    public String getUid() {
        return uid;
    }

    public String getNickname() {
        return nickname;
    }

    public String getUsername() {
        return username;
    }

    public String getHeadpic() {
        return headpic;
    }

    public String getNewsCount() {
        return newsCount;
    }

}

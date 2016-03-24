package com.apolle.zhiyou.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangtao on 2016/3/1218:06.
 * modify by huangtao on 18:06
 */
public class Note implements Serializable{

    private String un_id;
    private String uid;
    private String np_id;
    private String np_title;
    private String subject;
    private String content;
    private String created_at;
    private String dateline;
    private String displayorder;
    private String show;

    public void setUn_id(String un_id) {
        this.un_id = un_id;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setNp_id(String np_id) {
        this.np_id = np_id;
    }

    public void setNp_title(String np_title) {
        this.np_title = np_title;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }

    public void setDisplayorder(String displayorder) {
        this.displayorder = displayorder;
    }

    public void setShow(String show) {
        this.show = show;
    }

    public String getUn_id() {
        return un_id;
    }

    public String getUid() {
        return uid;
    }

    public String getNp_id() {
        return np_id;
    }

    public String getNp_title() {
        return np_title;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getDateline() {
        return dateline;
    }

    public String getDisplayorder() {
        return displayorder;
    }

    public String getShow() {
        return show;
    }
}

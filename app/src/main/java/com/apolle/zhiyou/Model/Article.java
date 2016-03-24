package com.apolle.zhiyou.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangtao on 2016/2/2712:51.
 * modify by huangtao on 12:51
 */
public class Article implements Serializable {


    private String tid;
    private String cid;
    private String created_at;
    private String dateline;
    private String authid;
    private String author;
    private String subject;
    private String content;
    private String recommends;
    private String closed;
    private String report;
    private String favtimes;
    private String forwardtimes;
    private String commentimes;
    private String uid;
    private String nickname;
    private String headpic;


    public void setTid(String tid) {
        this.tid = tid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }

    public void setAuthid(String authid) {
        this.authid = authid;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setRecommends(String recommends) {
        this.recommends = recommends;
    }

    public void setClosed(String closed) {
        this.closed = closed;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public void setFavtimes(String favtimes) {
        this.favtimes = favtimes;
    }

    public void setForwardtimes(String forwardtimes) {
        this.forwardtimes = forwardtimes;
    }

    public void setCommentimes(String commentimes) {
        this.commentimes = commentimes;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setHeadpic(String headpic) {
        this.headpic = headpic;
    }

    public String getTid() {
        return tid;
    }

    public String getCid() {
        return cid;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getDateline() {
        return dateline;
    }

    public String getAuthid() {
        return authid;
    }

    public String getAuthor() {
        return author;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public String getRecommends() {
        return recommends;
    }

    public String getClosed() {
        return closed;
    }

    public String getReport() {
        return report;
    }

    public String getFavtimes() {
        return favtimes;
    }

    public String getForwardtimes() {
        return forwardtimes;
    }

    public String getCommentimes() {
        return commentimes;
    }

    public String getUid() {
        return uid;
    }

    public String getNickname() {
        return nickname;
    }

    public String getHeadpic() {
        return headpic;
    }



}

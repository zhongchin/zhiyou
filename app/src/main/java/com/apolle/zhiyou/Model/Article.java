package com.apolle.zhiyou.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangtao on 2016/2/2712:51.
 * modify by huangtao on 12:51
 */
public class Article implements Serializable {

    /**
     * tid : 1
     * cid : 2
     * created_at : 1456568708
     * dateline : 1456568711
     * authid : 2
     * author : 第一种
     header('Content-type: application/json');
     另一种
     header('Content-type: 普罗米修斯会思考
     * subject : 第一种
     header('Content-type: application/json');
     另一种
     header('Content-type: text/json');
     * content : 第一种
     header('Content-type: application/json');
     另一种
     header('Content-type: text/json');
     * recommends : 5
     * closed : 0
     * report : 0
     * favtimes : 1
     * forwardtimes : 3
     * commentimes : 2
     * uid : 2
     * nickname : 普罗米修斯会思考
     * headpic : null
     * attachments : [{"ta_id":"1","tid":"1","picpath":"http://b.hiphotos.baidu.com/image/h%3D200/sign=9d3833093f292df588c3ab158c305ce2/d788d43f8794a4c274c8110d0bf41bd5ad6e3928.jpg"},{"ta_id":"2","tid":"1","picpath":"http://www.people.com.cn/mediafile/pic/20150331/77/8172027402265324077.jpg"}]
     */

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
    /**
     * ta_id : 1
     * tid : 1
     * picpath : http://b.hiphotos.baidu.com/image/h%3D200/sign=9d3833093f292df588c3ab158c305ce2/d788d43f8794a4c274c8110d0bf41bd5ad6e3928.jpg
     */

    private List<AttachmentsEntity> attachments;

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

    public void setAttachments(List<AttachmentsEntity> attachments) {
        this.attachments = attachments;
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

    public List<AttachmentsEntity> getAttachments() {
        return attachments;
    }

    public static class AttachmentsEntity {
        private String ta_id;
        private String tid;
        private String picpath;

        public void setTa_id(String ta_id) {
            this.ta_id = ta_id;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }

        public void setPicpath(String picpath) {
            this.picpath = picpath;
        }

        public String getTa_id() {
            return ta_id;
        }

        public String getTid() {
            return tid;
        }

        public String getPicpath() {
            return picpath;
        }
    }
}

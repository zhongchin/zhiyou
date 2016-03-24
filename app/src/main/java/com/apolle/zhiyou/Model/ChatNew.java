package com.apolle.zhiyou.Model;

import java.util.List;

/**
 * Created by huangtao on 2016/3/1918:05.
 * modify by huangtao on 18:05
 */
public class ChatNew {


    /**
     * username : aaa
     * id : 12
     * headpic : http://localhost/1.jpg
     * news : [{"nid":"1233","sender":"hello wolrd","headpic":"","sendtime":"","content":"dddd"}]
     */

    private String username;
    private String id;
    private String headpic;
    /**
     * nid : 1233
     * sender : hello wolrd
     * headpic :
     * sendtime :
     * content : dddd
     */

    private List<NewsEntity> news;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setHeadpic(String headpic) {
        this.headpic = headpic;
    }

    public void setNews(List<NewsEntity> news) {
        this.news = news;
    }

    public String getUsername() {
        return username;
    }

    public String getId() {
        return id;
    }

    public String getHeadpic() {
        return headpic;
    }

    public List<NewsEntity> getNews() {
        return news;
    }

    public static class NewsEntity {
        private String nid;
        private String sender;
        private String headpic;
        private String sendtime;
        private String content;

        public void setNid(String nid) {
            this.nid = nid;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public void setHeadpic(String headpic) {
            this.headpic = headpic;
        }

        public void setSendtime(String sendtime) {
            this.sendtime = sendtime;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getNid() {
            return nid;
        }

        public String getSender() {
            return sender;
        }

        public String getHeadpic() {
            return headpic;
        }

        public String getSendtime() {
            return sendtime;
        }

        public String getContent() {
            return content;
        }
    }
}

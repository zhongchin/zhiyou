package com.apolle.zhiyou.Model;

import java.io.Serializable;

/**
 * Created by huangtao on 2016/2/2619:48.
 * modify by huangtao on 19:48
 */
public class Channel implements Serializable{
    public int id;
    public int cid;
    public String ctitle;

    public Channel(int cid, String ctitle) {
        this.cid = cid;
        this.ctitle = ctitle;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getCtitle() {
        return ctitle;
    }

    public void setCtitle(String ctitle) {
        this.ctitle = ctitle;
    }
}

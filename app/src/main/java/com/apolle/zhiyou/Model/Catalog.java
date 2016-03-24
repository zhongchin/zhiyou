package com.apolle.zhiyou.Model;

import java.io.Serializable;

/**
 * Created by huangtao on 2016/3/1817:40.
 * modify by huangtao on 17:40
 */
public class Catalog implements Serializable{

    public int id;
    public String title;
    public String href;
    public String type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

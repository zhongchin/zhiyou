package com.apolle.zhiyou.Model;

import android.content.Context;

import com.apolle.zhiyou.mUtil.MDbUtil;
import com.lidroid.xutils.DbUtils;

/**
 * Created by huangtao on 2016/3/418:10.
 * modify by huangtao on 18:10
 */
public class LocalBook {
    public int id;
    public String name;
    public String type;
    public String icon;
    public  String path;
    public boolean iconType;

    public LocalBook(){

    }



    public LocalBook(String name, String type, String icon, String path,boolean iconType) {
        this.name = name;
        this.type = type;
        this.icon = icon;
        this.path = path;
    }

    public LocalBook(String name, String type, String icon, String path) {
        this(name,type,icon,path,false);
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isIconType() {
        return iconType;
    }

    public void setIconType(boolean iconType) {
        this.iconType = iconType;
    }
}

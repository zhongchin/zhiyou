package com.apolle.zhiyou.Model;

/**
 * Created by huangtao on 2016/3/62:55.
 * modify by huangtao on 2:55
 */
public class FileDir {

    public String name;
    public String type;

    public FileDir() {
    }

    public FileDir(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "{ name:"+getName()+", type:"+type+" }";
    }
}

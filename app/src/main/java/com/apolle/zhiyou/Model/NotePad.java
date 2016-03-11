package com.apolle.zhiyou.Model;



import java.io.Serializable;
import java.util.List;

/**
 * Created by huangtao on 2016/3/312:48.
 * modify by huangtao on 12:48
 */
public class NotePad implements Serializable{


    /**
     * nid : 1
     * ntitle : 科技
     * uid : 2
     * pid : 0
     * level : 1
     * childs : [{"nid":2,"ntitle":"科技1","uid":2,"pid":1,"level":2,"childs":[]}]
     */

    private int nid;
    private String ntitle;
    private int uid;
    private int pid;
    private int level;
    /**
     * nid : 2
     * ntitle : 科技1
     * uid : 2
     * pid : 1
     * level : 2
     * childs : []
     */

    private List<NotePad> childs;

    public void setNid(int nid) {
        this.nid = nid;
    }

    public void setNtitle(String ntitle) {
        this.ntitle = ntitle;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setChilds(List<NotePad> childs) {
        this.childs = childs;
    }

    public int getNid() {
        return nid;
    }

    public String getNtitle() {
        return ntitle;
    }

    public int getUid() {
        return uid;
    }

    public int getPid() {
        return pid;
    }

    public int getLevel() {
        return level;
    }

    public List<NotePad> getChilds() {
        return childs;
    }

   /* public static class ChildsEntity {
        private int nid;
        private String ntitle;
        private int uid;
        private int pid;
        private int level;
        private List<NotePad> childs;

        public void setNid(int nid) {
            this.nid = nid;
        }

        public void setNtitle(String ntitle) {
            this.ntitle = ntitle;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public void setChilds(List<NotePad> childs) {
            this.childs = childs;
        }

        public int getNid() {
            return nid;
        }

        public String getNtitle() {
            return ntitle;
        }

        public int getUid() {
            return uid;
        }

        public int getPid() {
            return pid;
        }

        public int getLevel() {
            return level;
        }

        public List<NotePad> getChilds() {
            return childs;
        }
    }*/
}

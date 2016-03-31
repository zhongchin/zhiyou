package com.apolle.zhiyou.Tool;

import android.os.Build;

/**
 * Created by huangtao on 2016/3/3022:49.
 * modify by huangtao on 22:49
 */
public class DateTimeTool {

    public static int YEAR=0;
    public static int MONTH=0;
    public static int DAY=0;
    public static int HOUR=0;
    public static int MINUTE=0;
    public static int SECOND=0;

    /**
     * 距今多长时间
     * @param oldtime
     * @return
     */
    public static String LongAge(int oldtime){
        DateTimeTool date= new DateTimeTool();
        long curTime=System.currentTimeMillis()/1000;
        long time=curTime-(oldtime);
        date.DAY= (int)(time/(24*3600));


           date.MONTH=0;
        if(date.DAY>30){
            date.MONTH=1;
            date.DAY=date.DAY-30;
        }
        long sTime=time%(24*3600);
        date.HOUR=(int)(sTime/3600);
        sTime=sTime%3600;
        date.MINUTE=(int)(sTime/60);
        date.SECOND=(int)(sTime%60);
         String result="";
        if(date.MONTH>0){
            result+=date.MONTH+"月";
        }
        if(date.DAY>0){
            result+=date.DAY+"日";
        }
        if(date.HOUR>0){
            result+=date.HOUR+"时";
        }
        if(date.MINUTE>0){
            result+=date.MINUTE+"分";
        }
        if(date.SECOND>0){
            result+=date.SECOND+"秒前";
        }
        return result;
    }
}

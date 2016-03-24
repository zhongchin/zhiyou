package com.apolle.zhiyou.Tool;

import android.content.Context;
import android.location.Location;
import android.text.style.LocaleSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by huangtao on 2016/3/618:58.
 * modify by huangtao on 18:58
 */
public class DisplayMerticsTool {

    public static DisplayMetrics getWindowDisplayMertics(Context context){
           WindowManager windowManager= (WindowManager) context.getSystemService( Context.WINDOW_SERVICE );
           DisplayMetrics displayMetrics = new DisplayMetrics();
           windowManager.getDefaultDisplay().getMetrics(  displayMetrics);
          return displayMetrics;
    }
    public static int  getWindowHeight(Context context){
        return getWindowDisplayMertics(context ).heightPixels;
    }
    public static int getWindowWidth(Context context){
        return getWindowDisplayMertics(context ).widthPixels;
    }

    /**
     * 获取元素在全局坐标系中x值
     * @param view
     * @return
     */
    public static int getScreenTop(View view){
        int[] location=new int[2];
        view.getLocationInWindow(location);
        return location[1];
    }
    public static int getScreenLeft(View view){
        int[] location=new int[2];
        view.getLocationInWindow(location);
        return location[0];
    }
    public static int getInWindowTop(View view){
        int[]  location=new int[2];
        view.getLocationInWindow(location);
        return location[1];
    }
    public static int getInWindowLeft(View view){
        int[]  location=new int[2];
        view.getLocationInWindow(location);
        return location[0];
    }

}

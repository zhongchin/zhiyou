package com.apolle.zhiyou.Tool;

import android.content.Context;
import android.util.DisplayMetrics;
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

}

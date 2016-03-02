package com.apolle.zhiyou.Http;

import android.content.Context;

import com.android.volley.VolleyError;
import com.apolle.zhiyou.mUtil.NetUrl;

import java.util.HashMap;

/**
 * Created by huangtao on 2016/3/219:07.
 * modify by huangtao on 19:07
 */
public class NotePadAction {
    public static void MY(Context context, HashMap<String,String> params){

        VolleyStringRequest.getInstance(context).RequestData(NetUrl.MY_NOTEPAD, params, new VolleyStringRequest.ResponseCallback() {
            @Override
            public void onSuccess(String response) {

            }

            @Override
            public void onFailed(VolleyError error) {

            }
        });
    }
    public static void ALLNOTEPAD(Context context, HashMap<String,String> params){

        VolleyStringRequest.getInstance(context).RequestData(NetUrl.MY_NOTEPADS, params, new VolleyStringRequest.ResponseCallback() {
            @Override
            public void onSuccess(String response) {

            }

            @Override
            public void onFailed(VolleyError error) {

            }
        });
    }


}

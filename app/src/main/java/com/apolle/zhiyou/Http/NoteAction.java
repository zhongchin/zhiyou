package com.apolle.zhiyou.Http;

import android.content.Context;

import com.android.volley.VolleyError;
import com.apolle.zhiyou.interactor.NetUrl;

import java.util.HashMap;

/**
 * Created by huangtao on 2016/3/219:06.
 * modify by huangtao on 19:06
 */
public class NoteAction {

    //我的笔记本
    public static void my(Context context, HashMap<String,String> params){

        VolleyStringRequest.getInstance(context).RequestData(NetUrl.ARTICLE_COLLECT, params, new VolleyStringRequest.ResponseCallback() {
            @Override
            public void onSuccess(String response) {

            }

            @Override
            public void onFailed(VolleyError error) {

            }
        });
    }

    public static void Favourite(Context context,HashMap<String,String> params){
        VolleyStringRequest.getInstance(context).RequestData(NetUrl.ARTICLE_FAVOURITE, params, new VolleyStringRequest.ResponseCallback() {
            @Override
            public void onSuccess(String response) {

            }

            @Override
            public void onFailed(VolleyError error) {

            }
        });
    }
}

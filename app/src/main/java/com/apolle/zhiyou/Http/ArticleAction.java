package com.apolle.zhiyou.Http;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.apolle.zhiyou.mUtil.NetUrl;
import java.util.HashMap;

/**
 * Created by huangtao on 2016/3/21:06.
 * modify by huangtao on 1:06
 */
public class ArticleAction {

    private Context mContext;
    private static RequestQueue mQueue;


    public static void Collect(Context context, HashMap<String,String> params){

        VolleyStringRequest.getInstance(context).RequestData(NetUrl.ARTICLE_COLLECT, params, new VolleyStringRequest.ResponseCallback() {
            @Override
            public void onSuccess(String response) {

            }

            @Override
            public void onFailed(VolleyError error) {

            }
        });
    }
    public static void Forward(Context context,HashMap<String,String> params){

        VolleyStringRequest.getInstance(context).RequestData(NetUrl.ARTICLE_FORWARD, params, new VolleyStringRequest.ResponseCallback() {
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

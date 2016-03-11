package com.apolle.zhiyou.Http;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.apolle.zhiyou.Model.Article;
import com.apolle.zhiyou.Model.Channel;
import com.apolle.zhiyou.interactor.NetUrl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
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
    public static void HomeTitle(Context context,HashMap<String,String> params,final renderCallback renderCallback){
        VolleyStringRequest.getInstance(context).RequestData(NetUrl.HOME_TITLE, params, new VolleyStringRequest.ResponseCallback() {
            @Override
            public void onSuccess(String response) {
                if(null!=response){
                    try{
                        JSONObject json=new JSONObject(response);
                        String errorcode= json.getString("errorcode");
                        if(0==Integer.parseInt(errorcode)){
                            Gson gson= new Gson();
                            String channel=json.getString("content");
                            ArrayList<Channel> titles= gson.fromJson(channel, new TypeToken<ArrayList<Channel>>(){ }.getType());
                            renderCallback.SuccessRender(titles);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailed(VolleyError error) {

            }
        });
    }
    public static void HomeContent(Context context,HashMap<String,String> params,final renderCallback renderCallback){
        VolleyStringRequest.getInstance(context).RequestData(NetUrl.HOME_CONTENT, params, new VolleyStringRequest.ResponseCallback() {
            @Override
            public void onSuccess(String response) {
                if(null!=response){
                    try{
                        JSONObject json=new JSONObject(response);
                        String errorcode= json.getString("errorcode");
                        if(0==Integer.parseInt(errorcode)){
                            Gson gson= new Gson();
                            String content=json.getString("content");
                            ArrayList<Article> articles= gson.fromJson(content, new TypeToken<ArrayList<Article>>(){ }.getType());
                            renderCallback.SuccessRender(articles);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailed(VolleyError error) {

            }
        });
    }

    public interface  renderCallback{
        void SuccessRender(ArrayList<? extends Serializable> content);
        void FailRender();
    }

}

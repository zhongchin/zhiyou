package com.apolle.zhiyou.Http;

import android.content.ContentProvider;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.apolle.zhiyou.Model.Article;
import com.apolle.zhiyou.Model.Channel;
import com.apolle.zhiyou.Model.Comment;
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



    public static void Favourite(final Context context, HashMap<String,String> params){
          VolleyStringRequest.getInstance(context).RequestData(NetUrl.ARTICLE_FAVOURITE, params, new VolleyStringRequest.ResponseCallback() {
              @Override
              public void onSuccess(String response) {

              }

              @Override
              public void onFailed(VolleyError error) {
                  Toast.makeText(context, "未知错误", Toast.LENGTH_SHORT).show();
              }
          });
    }

    /**
     *顶部标题
     * @param context
     * @param params
     * @param renderCallback
     */
    public static void HomeTitle(Context context,HashMap<String,String> params,final renderCallback renderCallback){
        VolleyStringRequest.getInstance(context).RequestData(NetUrl.HOME_TITLE, params, new VolleyStringRequest.ResponseCallback() {
            @Override
            public void onSuccess(String response) {
                String[] result =new String[3];
                VolleyStringRequest.covertToArray(result,response);
                int code=Integer.parseInt(result[0]);
                if(renderCallback!=null){
                    if(code==0){
                        Gson gson= new Gson();
                        ArrayList<Channel> titles= gson.fromJson(result[1], new TypeToken<ArrayList<Channel>>(){ }.getType());
                        renderCallback.SuccessRender(titles);
                    }else{
                        renderCallback.FailRender(code,result[2]);
                    }
                }
            }

            @Override
            public void onFailed(VolleyError error) {
                if(renderCallback!=null){
                    renderCallback.FailRender(8,error.getMessage());
                }
            }
        });
    }

    /**
     * 广场内容
     * @param context
     * @param params
     * @param renderCallback
     */
    public static void HomeContent(Context context,HashMap<String,String> params,final renderCallback renderCallback){
        VolleyStringRequest.getInstance(context).RequestData(NetUrl.HOME_CONTENT, params, new VolleyStringRequest.ResponseCallback() {
            @Override
            public void onSuccess(String response) {
                System.out.println("response"+response);
                String[] result =new String[3];
                VolleyStringRequest.covertToArray(result,response);
                int code=Integer.parseInt(result[0]);
                if(renderCallback!=null){
                    if(code==0){
                        Gson gson= new Gson();
                        ArrayList<Article> articles= gson.fromJson(result[1], new TypeToken<ArrayList<Article>>(){ }.getType());
                        renderCallback.SuccessRender(articles);
                    }else{
                      renderCallback.FailRender(code,result[2]);
                    }
                }
            }

            @Override
            public void onFailed(VolleyError error) {
                if(renderCallback!=null){
                    renderCallback.FailRender(8,error.getMessage());
                }
            }
        });
    }

    public  static void getArticleComments(Context context,HashMap<String,String> params,final renderCallback renderCallback){

        VolleyStringRequest.getInstance(context).RequestData(NetUrl.ARTICLE_COMMENTS, params, new VolleyStringRequest.ResponseCallback() {
            @Override
            public void onSuccess(String response) {
                System.out.println("response"+response);
                String[] result =new String[3];
                VolleyStringRequest.covertToArray(result,response);
                int code=Integer.parseInt(result[0]);
                if(renderCallback!=null){
                    if(code==0){
                        Gson gson= new Gson();
                        ArrayList<Comment> comments= gson.fromJson(result[1], new TypeToken<ArrayList<Comment>>(){ }.getType());
                        renderCallback.SuccessRender(comments);
                    }else{
                        renderCallback.FailRender(code,result[2]);
                    }
                }
            }

            @Override
            public void onFailed(VolleyError error) {
                if(renderCallback!=null){
                    renderCallback.FailRender(8,error.getMessage());
                }
            }
        });

    }


    public interface  renderCallback{
        void SuccessRender(ArrayList<? extends Serializable> content);
        void FailRender(int code,String error);
    }

}

package com.apolle.zhiyou.Http;

import android.content.Context;

import com.android.volley.VolleyError;
import com.apolle.zhiyou.Model.NewBean;
import com.apolle.zhiyou.interactor.NetUrl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by huangtao on 2016/3/1918:10.
 * modify by huangtao on 18:10
 */
public class ChatNewsAction {

//获取消息列表
    public static void  getNewList(Context context, HashMap<String,String> params, final NewsResponseCallback newsResponseCallback){
        VolleyStringRequest.getInstance(context).RequestData(NetUrl.CHAT_NEWS, params, new VolleyStringRequest.ResponseCallback() {
            @Override
            public void onSuccess(String response) {
                String[] result=new String[3];
                VolleyStringRequest.covertToArray(result,response);
                int code=Integer.parseInt(result[0]);
                System.out.println("huangtao"+result);
                if(newsResponseCallback!=null){
                    if(code==0){
                        Gson gson=new Gson();
                        ArrayList<NewBean> news=gson.fromJson(result[1],new TypeToken<ArrayList<NewBean>>(){}.getType());
                        newsResponseCallback.onSuccess((ArrayList<NewBean>) news);
                    }else{
                        newsResponseCallback.onFail(code,result[2]);
                    }
                }

            }

            @Override
            public void onFailed(VolleyError error) {
                if(newsResponseCallback!=null){
                    newsResponseCallback.onFail(8,error.getMessage());
                }
            }
        });
    }

    public interface  NewsResponseCallback{
        void onSuccess(ArrayList<? extends Serializable> message);
        void onFail(int code,String error);
    }

}

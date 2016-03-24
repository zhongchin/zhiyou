package com.apolle.zhiyou.Http;

import android.content.Context;

import com.android.volley.VolleyError;
import com.apolle.zhiyou.Model.Article;
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
public class ChatTrendAction {

    //获取消息列表
    public static void  getTrendList(Context context, HashMap<String,String> params, final TrendResponseCallback trendResponseCallback){
        VolleyStringRequest.getInstance(context).RequestData(NetUrl.CHAT_TREND, params, new VolleyStringRequest.ResponseCallback() {
            @Override
            public void onSuccess(String response) {
                String[] result=new String[3];
                VolleyStringRequest.covertToArray(result,response);
                int code=Integer.parseInt(result[0]);
                System.out.println("huangtao"+result);
                if(trendResponseCallback!=null){
                    if(code==0){
                        Gson gson=new Gson();
                        ArrayList<Article> news=gson.fromJson(result[1],new TypeToken<ArrayList<Article>>(){}.getType());
                        trendResponseCallback.onSuccess((ArrayList<Article>) news);
                    }else{
                        trendResponseCallback.onFail(code,result[2]);
                    }
                }

            }

            @Override
            public void onFailed(VolleyError error) {
                if(trendResponseCallback!=null){
                    trendResponseCallback.onFail(8,error.getMessage());
                }
            }
        });
    }

    public interface  TrendResponseCallback{
        void onSuccess(ArrayList<? extends Serializable> message);
        void onFail(int code,String error);
    }


}

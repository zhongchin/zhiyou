package com.apolle.zhiyou.Http;

import android.content.Context;

import com.android.volley.VolleyError;
import com.apolle.zhiyou.interactor.NetUrl;

import java.util.HashMap;

/**
 * Created by huangtao on 2016/3/1418:56.
 * modify by huangtao on 18:56
 */
public class UserRegister {

    public static void doRegister(Context context, HashMap<String ,String> params, final RegisterCallBack registerCallBack){

        VolleyStringRequest.getInstance(context).RequestData(NetUrl.USER_REGISTER, params, new VolleyStringRequest.ResponseCallback() {
            @Override
            public void onSuccess(String response) {
                  System.out.println(response);
                  String[] content=new String[3];
                  VolleyStringRequest.covertToArray(content,response);
                  int code=Integer.valueOf(content[0]);
                if(registerCallBack!=null){
                    if(code==0){
                        registerCallBack.onSuccess(content[1]);
                    }else{
                        registerCallBack.onFail(code,content[2]);
                    }
                }

            }

            @Override
            public void onFailed(VolleyError error) {
                if(registerCallBack!=null){
                    registerCallBack.onFail(6,error.getMessage());
                }
            }
        });
    }
    public interface RegisterCallBack{
        void onSuccess(String message);
        void onFail(int code,String error);
    }
}

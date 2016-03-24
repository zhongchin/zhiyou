package com.apolle.zhiyou.Http;

import android.content.Context;

import com.android.volley.VolleyError;
import com.apolle.zhiyou.interactor.NetUrl;

import java.util.HashMap;

/**
 * Created by huangtao on 2016/3/1515:11.
 * modify by huangtao on 15:11
 */
public class UserResetPwd {

    //去服务器校验检证码是否正确
    public static void checkVerifyCode(Context context, HashMap<String,String> params, final OnVerifyCodeCheckComplete onVerifyCodeCheckComplete){

        VolleyStringRequest.getInstance(context).RequestData(NetUrl.CHECK_VERIFYCODE, params, new VolleyStringRequest.ResponseCallback() {
            @Override
            public void onSuccess(String response) {
                   String[] result=new String[3];
                    VolleyStringRequest.covertToArray(result,response);
                    int code=Integer.parseInt(result[0]);
                if(onVerifyCodeCheckComplete!=null){
                    if(code==0){
                        onVerifyCodeCheckComplete.onSuccess(result[1]);
                    }else{
                        onVerifyCodeCheckComplete.onFail(code,result[2]);
                    }
                }
            }

            @Override
            public void onFailed(VolleyError error) {
                if(onVerifyCodeCheckComplete!=null){
                        onVerifyCodeCheckComplete.onFail(8,error.getMessage());
                }
            }
        });
    }
    //验证码校验结束时回调
    public interface  OnVerifyCodeCheckComplete{
        void onSuccess(String message);
        void onFail(int code,String error);
    }
}


package com.apolle.zhiyou.Http;

import android.content.Context;
import android.util.TypedValue;

import com.android.volley.VolleyError;
import com.apolle.zhiyou.Model.NotePad;
import com.apolle.zhiyou.Model.User;
import com.apolle.zhiyou.interactor.NetUrl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by huangtao on 2016/3/1322:24.
 * modify by huangtao on 22:24
 */
public class UserLogin {


    public static void doLogin(Context context, HashMap<String,String> params, final requestOnCallback requestOnCallback){
        VolleyStringRequest.getInstance(context).RequestData(NetUrl.USER_LOGIN, params, new VolleyStringRequest.ResponseCallback() {
            @Override
            public void onSuccess(String response) {
                String[] content =new String[3];
                VolleyStringRequest.covertToArray(content,response);
                int code=Integer.parseInt(content[0]);
                if(requestOnCallback!=null){
                    if(code==0){
                        Gson gson = new Gson();
                        System.out.println(content+"===="+content[1]);
                        User user=gson.fromJson(content[1], new TypeToken<User>(){}.getType());
                        if(requestOnCallback!=null){
                            requestOnCallback.onSuccess(user);
                        }
                        requestOnCallback.onSuccess(user);
                    }else{
                        requestOnCallback.onFail(code,content[2]);
                    }
                }
            }
            @Override
            public void onFailed(VolleyError error) {
                if(requestOnCallback!=null){
                    requestOnCallback.onFail(8,error.getMessage());
                }
            }
        });
    }
    private requestOnCallback requestOnCallback;
    public interface  requestOnCallback{
        void onSuccess(User user);
        void onFail(int code,String error);
    }

}

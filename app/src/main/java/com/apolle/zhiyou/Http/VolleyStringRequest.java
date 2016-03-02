package com.apolle.zhiyou.Http;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangtao on 2016/3/218:49.
 * modify by huangtao on 18:49
 */
public class VolleyStringRequest {
    public Context context;
    public static VolleyStringRequest instance;
    public static RequestQueue mQueue;
    public static ResponseCallback responseCallback;
    public static VolleyStringRequest getInstance(Context context){
        if(null==instance){
            instance=new VolleyStringRequest(context);
        }
        return instance;
    }

    public VolleyStringRequest(Context context){
          this.context=context;
          this.mQueue= Volley.newRequestQueue(context);
    }

    public static  void RequestData(String url, final HashMap<String,String> newParams, final ResponseCallback responseCallback){

        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(null!=responseCallback){
                    responseCallback.onSuccess(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(null!=responseCallback){
                    responseCallback.onFailed(error);
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params=newParams;
                return params;
            }
        };
        if(null!=mQueue){
            mQueue.add(request);
        }
    }
    public interface ResponseCallback{
        void  onSuccess(String response);
        void onFailed(VolleyError error);
    }

}

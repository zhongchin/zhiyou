package com.apolle.zhiyou.Http;

import android.content.Context;
import android.support.annotation.Size;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.apolle.zhiyou.Model.User;
import com.apolle.zhiyou.Tool.SharedPreTool;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.internal.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by huangtao on 2016/3/218:49.
 * modify by huangtao on 18:49
 */
public class VolleyStringRequest {
    public static Context context;
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
          mQueue= Volley.newRequestQueue(context);
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

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {

                try {
                    TreeMap<String,String> responseHeaders= (TreeMap<String, String>) response.headers;
                    String rawCookie=responseHeaders.get("Set-Cookie");
                    SharedPreTool.saveSessionId(context,rawCookie);
                    String dataString=new String(response.data, "UTF-8");
                    return Response.success(dataString, HttpHeaderParser.parseCacheHeaders(response));
                    
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                 HashMap<String,String> headers=new HashMap<String,String>();
                  String cookie=SharedPreTool.getSessionId(context);
                  headers.put("Cookie",cookie);
                return headers;
            }
        };
        if(null!=mQueue){
            System.out.println("请求"+mQueue.toString());
            System.out.println("请求"+request.toString());
            mQueue.add(request);
        }
    }

    /**
     * 获取sessionid
     * @param response
     * @return
     */
    private  static String  getSessionId(String response){
         String id="";
        try {
            JSONObject jsonObject=new JSONObject(response);
             id=jsonObject.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return id;

    }
    public interface ResponseCallback{
        void  onSuccess(String response);
        void onFailed(VolleyError error);
    }

    public  static void covertToArray(@Size(3) String[] result,String content){
        if(result.length<3){
            throw new IllegalArgumentException("location must be an array of two String");
        }
        int errorcode=0;
        String message=" ";
        String error="";
        JSONObject jsonObject= null;
        try {
            jsonObject = new JSONObject(content);
            errorcode=jsonObject.getInt("errorcode");
            if(errorcode==0) {
                message = jsonObject.getString("content");
            }
            error=jsonObject.getString("errormsg");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        result[0]=String.valueOf(errorcode);
        result[1]=message.toString();
        result[2]=error.toString();
    }

}

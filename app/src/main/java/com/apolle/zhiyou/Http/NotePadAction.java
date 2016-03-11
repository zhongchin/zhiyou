package com.apolle.zhiyou.Http;

import android.content.Context;

import com.android.volley.VolleyError;
import com.apolle.zhiyou.Model.NotePad;
import com.apolle.zhiyou.interactor.NetUrl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by huangtao on 2016/3/219:07.
 * modify by huangtao on 19:07
 */
public class NotePadAction {

    public static void mynotepad(Context context, HashMap<String,String> params, final RenderCallback renderCallback){
        getNetNotepads(context,NetUrl.MY_NOTEPAD,params,renderCallback);
    }

    public static void allNotepads(Context context,HashMap<String,String> params,final RenderCallback renderCallback){
        getNetNotepads(context,NetUrl.MY_NOTEPADS,params,renderCallback);

    }
    private static void  getNetNotepads(Context context,String url,HashMap<String,String> params,final RenderCallback renderCallback){
        VolleyStringRequest.getInstance(context).RequestData(url, params, new VolleyStringRequest.ResponseCallback() {
            @Override
            public void onSuccess(String response) {
                if(null!=response){
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        int  errorcode=jsonObject.getInt("errorcode");
                        if(errorcode==0){
                            String content=jsonObject.getString("content");
                            if(!content.equals(null)&&!content.equals("")){
                                Gson gson=new Gson();
                                ArrayList<NotePad> notePads =gson.fromJson(content, new TypeToken<ArrayList<NotePad>>(){}.getType());
                                renderCallback.renderOnSuccess(notePads);
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailed(VolleyError error) {
                renderCallback.renderOnFailed(error);
            }
        });
    }
     public interface RenderCallback{
        void renderOnSuccess(ArrayList<? extends  Serializable> content);
         void renderOnFailed(VolleyError error);
     }


}

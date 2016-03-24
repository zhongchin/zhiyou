package com.apolle.zhiyou.Http;

import android.content.Context;

import com.android.volley.VolleyError;
import com.apolle.zhiyou.Model.Note;
import com.apolle.zhiyou.Model.NotePad;
import com.apolle.zhiyou.interactor.NetUrl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.Serializable;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by huangtao on 2016/3/219:07.
 * modify by huangtao on 19:07
 */
public class NotePadAction {
    private static final int  PAD_NOTE= 1;
    private static final int NOTE=2;
    private static final int PAD=3;

    public static void mynotepad(Context context, HashMap<String,String> params, final RenderCallback renderCallback){
        getNetNotepads(context,NetUrl.MY_NOTEPAD,params,renderCallback,PAD);
    }

    public static void allNotepads(Context context,HashMap<String,String> params,final RenderCallback renderCallback){
        getNetNotepads(context,NetUrl.MY_NOTEPADS,params,renderCallback,PAD);
    }


    /**
     * 获取笔记本和笔记
     * @param context
     * @param renderCallback
     */

    public static void padAndNotes(Context context,HashMap<String,String> params,final MultiRenderCallback renderCallback){
        getNetNotepads(context,NetUrl.PAD_NOTES,params,renderCallback,PAD_NOTE);
    }

    private static void  getNetNotepads(Context context, String url, HashMap<String,String> params, final  AbRenderCallback renderCallback, final int type){
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
                               switch (type){
                                   case NOTE:
                                           ArrayList<Note> note1s =gson.fromJson(content, new TypeToken<ArrayList<Note>>(){}.getType());
                                         ((RenderCallback)renderCallback).renderOnSuccess(note1s);
                                       break;
                                   case PAD:
                                       ArrayList<NotePad> notePads =gson.fromJson(content, new TypeToken<ArrayList<NotePad>>(){}.getType());
                                       ((RenderCallback)renderCallback).renderOnSuccess(notePads);
                                       break;
                                   case PAD_NOTE:
                                         JSONObject js=new JSONObject(content);
                                         String notes=js.getString("notes");
                                         String pads=js.getString("pads");
                                         ArrayList<Note> NotesList =gson.fromJson(notes, new TypeToken<ArrayList<Note>>(){}.getType());
                                          ArrayList<NotePad> padsList =gson.fromJson(pads, new TypeToken<ArrayList<NotePad>>(){}.getType());
                                         HashMap<String,ArrayList<? extends Serializable>> result=new HashMap<String, ArrayList<? extends Serializable>>();
                                         result.put("notes",NotesList);
                                         result.put("pads",padsList);
                                       ((MultiRenderCallback)renderCallback).renderOnSuccess(result);
                                       break;
                               }

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
    public interface  AbRenderCallback{
        void renderOnFailed(VolleyError error);
    }
     public interface RenderCallback extends AbRenderCallback{
        void renderOnSuccess(ArrayList<? extends  Serializable> content);

     }
    public interface MultiRenderCallback extends AbRenderCallback{
        void renderOnSuccess(HashMap<String,ArrayList<? extends  Serializable>> content);
    }

    /**
     * 如果是字符串返回
     */
    public interface  StringReponse{
        void onSuccess(String message);
        void onFail(int code,String error);
    }
    /**
     * 添加一个笔记本
     * @param context
     * @param params
     * @param renderCallback
     */
   public static void addNotePad(Context context, HashMap<String,String> params, final StringReponse renderCallback){
        VolleyStringRequest.getInstance(context).RequestData(NetUrl.ADD_NOTE, params, new VolleyStringRequest.ResponseCallback() {
            @Override
            public void onSuccess(String response) {
                 String[] result=new String[3];
                   VolleyStringRequest.covertToArray(result,response);
                  int code=Integer.parseInt(result[0]);
                 if(renderCallback!=null){
                     if(code==0){
                         renderCallback.onSuccess(result[1]);
                     }else{
                         renderCallback.onFail(code,result[2]);
                     }

                 }
            }

            @Override
            public void onFailed(VolleyError error) {
                    if(renderCallback!=null){
                        renderCallback.onFail(8,error.getMessage());
                    }
            }
        });
   }
}

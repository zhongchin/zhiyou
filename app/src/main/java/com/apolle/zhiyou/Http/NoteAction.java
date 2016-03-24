package com.apolle.zhiyou.Http;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.apolle.zhiyou.interactor.NetUrl;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by huangtao on 2016/3/219:06.
 * modify by huangtao on 19:06
 */
public class NoteAction {

    //我的笔记本
    public static void my(Context context, HashMap<String,String> params){

        VolleyStringRequest.getInstance(context).RequestData(NetUrl.MY_NOTEPAD, params, new VolleyStringRequest.ResponseCallback() {
            @Override
            public void onSuccess(String response) {
                String[] result=new String[3];
                VolleyStringRequest.covertToArray(result,response);

            }
            @Override
            public void onFailed(VolleyError error) {

            }
        });
    }
    //收藏笔记
    public static  void CollectNote(Context context, HashMap<String,String> params, final NoteOperatorCallback noteOperatorCallback){
        VolleyStringRequest.getInstance(context).RequestData(NetUrl.SAVE_NOTE, params, new VolleyStringRequest.ResponseCallback() {
            @Override
            public void onSuccess(String response) {
                String[] result=new String[3];
                VolleyStringRequest.covertToArray(result,response);
                int code=Integer.parseInt(result[0]);
                if(noteOperatorCallback!=null){
                    if(code==0){
                        noteOperatorCallback.onSuccess(result[1]);
                    }else{
                        noteOperatorCallback.onFail(code,result[2]);
                    }
                }
            }
            @Override
            public void onFailed(VolleyError error) {
                if(noteOperatorCallback!=null){
                     noteOperatorCallback.onFail(8,error.getMessage());
                }
            }
        });
    }

    //转发笔记
    public static void Forward(final Context context, HashMap<String,String> params){

        VolleyStringRequest.getInstance(context).RequestData(NetUrl.ARTICLE_FORWARD, params, new VolleyStringRequest.ResponseCallback() {
            @Override
            public void onSuccess(String response) {
                Toast.makeText(context, "转发成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(VolleyError error) {
                Toast.makeText(context, "转发失败", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public static  void CollectAndForwardNote(Context context, HashMap<String,String> params, final NoteOperatorCallback noteOperatorCallback){
        VolleyStringRequest.getInstance(context).RequestData(NetUrl.SAVE_NOTE, params, new VolleyStringRequest.ResponseCallback() {
            @Override
            public void onSuccess(String response) {
                String[] result=new String[3];
                VolleyStringRequest.covertToArray(result,response);
                int code=Integer.parseInt(result[0]);
                if(noteOperatorCallback!=null){
                    if(code==0){
                        noteOperatorCallback.onSuccess(result[1]);
                    }else{
                        noteOperatorCallback.onFail(code,result[2]);
                    }
                }
            }
            @Override
            public void onFailed(VolleyError error) {
                if(noteOperatorCallback!=null){
                    noteOperatorCallback.onFail(8,error.getMessage());
                }
            }
        });
    }
   public interface  NoteOperatorCallback{
        void onSuccess(String s);
        void onFail(int code, String s);
   }

}

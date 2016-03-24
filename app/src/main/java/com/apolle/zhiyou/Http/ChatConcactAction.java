package com.apolle.zhiyou.Http;

import android.content.Context;

import com.android.volley.VolleyError;
import com.apolle.zhiyou.Model.Person;
import com.apolle.zhiyou.interactor.NetUrl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by huangtao on 2016/3/2220:55.
 * modify by huangtao on 20:55
 */
public class ChatConcactAction {
    public static void getMyFriends(Context context, HashMap<String,String> params, final ConcactResponseCallback concactResponseCallback){
         VolleyStringRequest.getInstance(context).RequestData(NetUrl.CHAT_CONCACT, params, new VolleyStringRequest.ResponseCallback() {
             @Override
             public void onSuccess(String response) {
                    String[] result=new String[3];
                    VolleyStringRequest.covertToArray(result,response);
                    int code=Integer.parseInt(result[0]);
                    if(concactResponseCallback!=null){
                        if(code==0){
                            Gson gson=new Gson();
                            ArrayList<Person> persons=gson.fromJson(result[1],new TypeToken<ArrayList<Person>>(){}.getType());
                          /*  for (int i=0;i<persons.size();i++){
                                  Person person=persons.get(i);
                                  String username=person.getNickname()!=null?person.getNickname():person.getUsername();

                                String pinyin= PinyinUtils.getPinYin(username);
                                String firstPy=pinyin.substring(0,1).toUpperCase();

                            }*/
                            concactResponseCallback.onSuccess(persons);

                        }else{
                            concactResponseCallback.onFail(code,result[2]);
                        }
                    }
             }

             @Override
             public void onFailed(VolleyError error) {
                 concactResponseCallback.onFail(8,error.getMessage());
             }
         });
    }
    public interface ConcactResponseCallback{
        void onSuccess(List<? extends  Object> obj);
        void onFail(int code,String error);
    }

}

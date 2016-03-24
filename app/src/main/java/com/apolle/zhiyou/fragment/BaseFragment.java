package com.apolle.zhiyou.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.apolle.zhiyou.interactor.TabInteractor;


/**
 * Created by Administrator on 2016/2/25.
 */
public abstract class BaseFragment extends Fragment {

    public static TabInteractor tabInteractor;
    public static Context context;

    public FragmentManager getFm(){
        return getChildFragmentManager();
    }
    public void showTopic(String topic){
        Toast.makeText(getContext(),topic,Toast.LENGTH_LONG).show();

    }
    public ProgressDialog showDialogProgress(String title, String body){
        return   ProgressDialog.show(getContext(),title,body,true,false);
    }
    public void test(Object tag){
        System.out.println("huangtao "+tag);
    }

    public void goActivity(Class<?> cls){
        Intent intent=new Intent(getContext(),cls);
        startActivity(intent);
    }
    public void goActivity(Class<?> cls,Bundle bundle){
        Intent intent=new Intent(getContext(),cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void toast(String topic,int time){
        Toast.makeText(getContext(),topic,time).show();
    }

    public void toast(String topic){
        Toast.makeText(getActivity(),topic,Toast.LENGTH_SHORT).show();
    }
      /*public static Context getContext(){
            if(null==context){
                context=getActivity();
            }
          return context;
      }*/

}

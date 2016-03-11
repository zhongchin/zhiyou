package com.apolle.zhiyou.fragment;

import android.content.Context;
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

      /*public static Context getContext(){
            if(null==context){
                context=getActivity();
            }
          return context;
      }*/

}

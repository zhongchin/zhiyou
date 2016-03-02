package com.apolle.zhiyou.fragment;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/2/25.
 */
public class BaseFragment extends Fragment {


    public FragmentManager getFm(){
        return getChildFragmentManager();
    }
    public void showTopic(String topic){
        Toast.makeText(getActivity(),topic,Toast.LENGTH_LONG).show();
    }

}

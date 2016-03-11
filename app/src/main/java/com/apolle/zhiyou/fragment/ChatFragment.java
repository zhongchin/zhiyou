package com.apolle.zhiyou.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apolle.zhiyou.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends BaseFragment {

    private static ChatFragment chatFragment;
    public ChatFragment() {
        // Required empty public constructor
    }
    public static ChatFragment getChatFragment(){
        if(null==chatFragment){
            chatFragment=new ChatFragment();
        }
        return chatFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

}

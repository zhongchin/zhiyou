package com.apolle.zhiyou.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apolle.zhiyou.R;
import com.apolle.zhiyou.activity.MainActivity;
import com.rey.material.widget.TabPageIndicator;
import com.rey.material.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    private static ChatFragment chatFragment;
    private int[] tabs=new int[]{R.string.information,R.string.concact,R.string.newest};
    private View rootView;
    private TabPageIndicator chatTab;
    private ViewPager ChatPager;

    public ChatFragment() {

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
        rootView= inflater.inflate(R.layout.fragment_chat, container, false);
        chatTab= (TabPageIndicator) rootView.findViewById(R.id.chat_tab);
        ChatPager=(ViewPager) rootView.findViewById(R.id.chatPager);
        ChatPager.setAdapter(new RecyclerAdapter(getChildFragmentManager()));
        chatTab.setViewPager(ChatPager);
        chatTab.setOnPageChangeListener(this);
        return rootView;
    }


    public  class RecyclerAdapter extends FragmentPagerAdapter{
        public RecyclerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
             String title= (String)getPageTitle(position);
            return   ChatContentFragment.newInstance(position,title);

        }

        @Override
        public int getCount() {
            return tabs.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getResources().getString(tabs[position]);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        MainActivity activity= (MainActivity) getActivity();
        TextView headTitle= (TextView) activity.findViewById(R.id.header_title);
        String title= (String) ChatPager.getAdapter().getPageTitle(position);
        headTitle.setText(title);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}

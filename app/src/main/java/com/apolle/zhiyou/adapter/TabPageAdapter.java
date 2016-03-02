package com.apolle.zhiyou.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.apolle.zhiyou.Model.Channel;
import com.apolle.zhiyou.fragment.HomeContentFragment;
import com.apolle.zhiyou.fragment.HomeSquareFragment;

import java.util.ArrayList;


/**
 * Created by huangtao on 2016/2/2615:45.
 * modify by huangtao on 15:45
 */
public class TabPageAdapter extends FragmentPagerAdapter {
    public ArrayList<Channel>  titles;
    public TabPageAdapter(FragmentManager fm) {
        super(fm);
    }
    public TabPageAdapter(FragmentManager fm, ArrayList<Channel> titles){
        super(fm);
        this.titles=titles;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=new HomeContentFragment();
        Bundle bundle=new Bundle();
        Channel channel=titles.get(position);
        bundle.putString("ctitle",channel.getCtitle());
        bundle.putString("cid",String.valueOf(channel.getCid()));
        fragment.setArguments(bundle);
        return  fragment;
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Channel channel=titles.get(position);

        return channel.getCtitle();
    }

}

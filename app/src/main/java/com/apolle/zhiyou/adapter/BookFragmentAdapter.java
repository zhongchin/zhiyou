package com.apolle.zhiyou.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.apolle.zhiyou.fragment.BookContentFragment;

import java.util.ArrayList;

/**
 * Created by huangtao on 2016/2/2814:21.
 * modify by huangtao on 14:21
 */
public class BookFragmentAdapter extends FragmentPagerAdapter {
    private ArrayList<String> booklist;

        public BookFragmentAdapter(FragmentManager fm, ArrayList<String> booklist) {
            super(fm);
          this.booklist=booklist;
        }

        @Override
        public Fragment getItem(int position) {
            BookContentFragment contentFragment=new BookContentFragment();
            Bundle bundle=new Bundle();
            bundle.putInt(BookContentFragment.TAB_TAG,position);
            contentFragment.setArguments(bundle);
            return contentFragment;
        }

        @Override
        public int getCount() {
            return booklist.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return booklist.get(position).toString();
        }

}

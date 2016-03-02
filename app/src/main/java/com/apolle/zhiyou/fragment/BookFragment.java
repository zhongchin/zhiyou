package com.apolle.zhiyou.fragment;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apolle.zhiyou.R;
import com.apolle.zhiyou.adapter.BookFragmentAdapter;
import com.rey.material.widget.TabPageIndicator;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookFragment extends BaseFragment {
    private View containerView;
    private ArrayList<String> booklist;

    private TabPageIndicator zy_book_actionbar;
    private ViewPager zy_book_content;

    public BookFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        containerView=inflater.inflate(R.layout.fragment_book, container, false);
        Resources resources=getResources();

        booklist=new ArrayList<String>(Arrays.asList(resources.getString(R.string.bookoffline),resources.getString(R.string.bookOnline)));

        zy_book_actionbar=(TabPageIndicator) containerView.findViewById(R.id.zy_book_actionbar);
        zy_book_content=(ViewPager) containerView.findViewById(R.id.zy_book_content);
        System.out.print("booklist"+booklist+"zy_book_content"+zy_book_content);
        FragmentPagerAdapter adapter=new BookFragmentAdapter(getChildFragmentManager(),booklist);

        zy_book_content.setAdapter(adapter);
        zy_book_actionbar.setViewPager(zy_book_content);
        return containerView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

    }
}
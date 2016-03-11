package com.apolle.zhiyou.fragment;


import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.apolle.zhiyou.R;
import com.apolle.zhiyou.adapter.BookFragmentAdapter;
import com.apolle.zhiyou.interactor.TabInteractor;
import com.rey.material.widget.TabPageIndicator;
import com.rey.material.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookFragment extends BaseFragment {
    private View containerView;
    private ArrayList<String> booklist;
    public static BookFragment bookFragment;

    private TabPageIndicator zy_book_actionbar;
    private ViewPager zy_book_content;

    public BookFragment() {
    }
    public static BookFragment getBookFragment(){
        if(null==bookFragment){
            bookFragment=new BookFragment();
        }
        return bookFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        containerView=inflater.inflate(R.layout.fragment_book, container, false);
        final Resources resources=getResources();
        //改变toolbar标题

        booklist=new ArrayList<String>(Arrays.asList(resources.getString(R.string.bookoffline),resources.getString(R.string.bookOnline)));

        zy_book_actionbar=(TabPageIndicator) containerView.findViewById(R.id.zy_book_actionbar);
        zy_book_content=(ViewPager) containerView.findViewById(R.id.zy_book_content);
        System.out.print("booklist"+booklist+"zy_book_content"+zy_book_content);
        FragmentPagerAdapter adapter=new BookFragmentAdapter(getChildFragmentManager(),booklist);

        zy_book_content.setAdapter(adapter);
        zy_book_actionbar.setViewPager(zy_book_content);
        zy_book_content.setOnPageChangeListener( new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
              /*  tabInteractor=new TabInteractor() {
                    @Override
                    public void OnTabChange(Context context, View toolbar_content) {
                        TextView textView=new TextView(context);
                           textView.setText(booklist.get( position ));
                                ((LinearLayout)(toolbar_content)).addView(textView);
                    }
                };*/
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        } );
        return containerView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

    }
}
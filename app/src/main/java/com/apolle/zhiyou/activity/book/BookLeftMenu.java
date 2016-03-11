package com.apolle.zhiyou.activity.book;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.apolle.zhiyou.R;
import com.apolle.zhiyou.adapter.BookLeftItemAdapter;
import com.unnamed.b.atv.model.TreeNode;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import nl.siegmann.epublib.domain.Guide;
import nl.siegmann.epublib.domain.GuideReference;

public class BookLeftMenu extends Fragment {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private View rootView;
    private static BookLeftMenu bookLeftMenu;
    private static int[] tabs=new int[]{R.string.bookmark,
                                 R.string.note,
                                 R.string.book_catalog
                                 };

    private static  OnCatalogSelectListener onCatalogSelectListener;
    private Bundle arguments;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView=inflater.inflate(R.layout.activity_book_left_menu,null);
        arguments=getArguments();
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager(),arguments);
        mViewPager = (ViewPager) rootView.findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
         mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
             @Override
             public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

             }

             @Override
             public void onPageSelected(int position) {
                if(position==2){

                }
             }

             @Override
             public void onPageScrollStateChanged(int state) {

             }
         });
        super.onActivityCreated(savedInstanceState);
    }


    public  static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";
        private  ListViewCompat book_catalog_list;
        private   ArrayList<GuideReference> guideReferences;
        private   Bundle args;
        private   BookLeftItemAdapter adapter;

        public PlaceholderFragment() {
        }
       public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
             Bundle bundle=new Bundle();
             bundle.putInt(ARG_SECTION_NUMBER, sectionNumber);
             fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View placeView = inflater.inflate(R.layout.fragment_book_left_menu, container, false);

            book_catalog_list= (ListViewCompat) placeView.findViewById(R.id.book_catalog_list);
            args=getArguments();

            return placeView;
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            int sectionNumber=args.getInt(ARG_SECTION_NUMBER);
            if(sectionNumber==2){//目录
                Guide guide= (Guide) args.getSerializable("guide");
                guideReferences= (ArrayList<GuideReference>) guide.getReferences();
                adapter=new BookLeftItemAdapter(getContext(),guideReferences,"title");
                if(null!=adapter){
                    book_catalog_list.setAdapter(adapter);
                    book_catalog_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                GuideReference peference=guideReferences.get(position);
                                if(peference!=null&&onCatalogSelectListener!=null){
                                    onCatalogSelectListener.onClick(peference);
                                }
                        }
                    });
                }
            }else{
                guideReferences=new ArrayList<GuideReference>();
            }


            super.onActivityCreated(savedInstanceState);
        }

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private Bundle bundle;
        public SectionsPagerAdapter(FragmentManager fm, Bundle arguments) {
            super(fm);
            this.bundle=arguments;
        }

        @Override
        public Fragment getItem(int position) {
              PlaceholderFragment placeholderFragment= PlaceholderFragment.newInstance(position + 1);
                  Bundle arg=new Bundle();
               Guide parent= (Guide) bundle.getSerializable("guide");
                arg.putSerializable("guide",parent);
                arg.putInt(PlaceholderFragment.ARG_SECTION_NUMBER,position);
                placeholderFragment.setArguments(arg);

            return placeholderFragment;
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
    public static BookLeftMenu newInstance() {

        if(bookLeftMenu==null){
            bookLeftMenu=new BookLeftMenu();
        }
        return bookLeftMenu;
    }

    public static void setOnCatalogSelectListener(OnCatalogSelectListener listener) {
        onCatalogSelectListener = listener;
    }

    public interface  OnCatalogSelectListener{
       void onClick(GuideReference preference);
  }

    /**
     * 填充数据
     */


}

package com.apolle.zhiyou.fragment;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apolle.zhiyou.Http.ArticleAction;
import com.apolle.zhiyou.Model.Channel;
import com.apolle.zhiyou.R;
import com.apolle.zhiyou.activity.MainActivity;
import com.apolle.zhiyou.adapter.TabPageAdapter;
import com.apolle.zhiyou.interactor.NetUrl;
import com.apolle.zhiyou.mUtil.MDbUtil;
import com.hp.hpl.sparta.Text;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.rey.material.widget.Spinner;
import com.rey.material.widget.TabPageIndicator;
import com.rey.material.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    private static final String  TAG_ONE= "huangtao===";

    ArrayList<Channel> titles;//页面标题
    public FragmentTransaction ft;
    public View containerView;
    public static HomeFragment homeFragment;
    public FragmentPagerAdapter adapter;
    public DbUtils db;
    private ViewPager pager;
    private TabPageIndicator indicator;
    private  Spinner spinner;
    private String[] spinnerItems;
    public HomeFragment() {

    }
    public static HomeFragment getHomeFragment(){
        if(null==homeFragment){
             homeFragment = new HomeFragment();
        }
        return homeFragment;
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onAttach(Context context) {
        System.out.println("onattach");
        super.onAttach( context );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        containerView= inflater.inflate(R.layout.fragment_home, container, false);
         indicator=(TabPageIndicator) containerView.findViewById(R.id.zy_home_title);
         pager= (ViewPager) containerView.findViewById(R.id.zy_home_container);

         db=DbUtils.create(getActivity(),"mchannel.db");
        //获取用户的home title;
        titles=new ArrayList<Channel>();
        titles=initLocaleHomeTitleData();
        System.out.println("title数据"+titles);
        if(titles.size()<1){
            initNetHomeTileData();
        }else{
            adapter=new TabPageAdapter(getChildFragmentManager(),titles);
            pager.setAdapter(adapter);
            indicator.setViewPager(pager);
        }
        indicator.setOnPageChangeListener(this);
        return containerView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    //使用本地存储的home标题数据
    private ArrayList<Channel> initLocaleHomeTitleData(){
        ArrayList<Channel> mtitles=new ArrayList<Channel>();
        try{
             db.createTableIfNotExist(Channel.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            Selector selector=Selector.from(Channel.class);
            List<Channel> lists=db.findAll(selector);//获取本地数据库中的home title数据
            mtitles=(ArrayList<Channel>) lists;
        }catch (Exception e){
            e.printStackTrace();
        }
        return mtitles;
    }

    //加载tab标题
   private void initNetHomeTileData(){
//         请求参数头
       final HashMap<String,String> params= NetUrl.initParams();

          params.put("uid","2");//当前登录用户id
         ArticleAction.HomeTitle(getActivity(), params, new ArticleAction.renderCallback() {
             @Override
             public void SuccessRender(ArrayList<? extends Serializable> titles) {
                 adapter=new TabPageAdapter(getFm(),(ArrayList<Channel>)titles);
                 pager.setAdapter(adapter);
                 indicator.setViewPager(pager);
                 try{
                     MDbUtil.newInstance(getContext()).saveNoInsert(titles,"title");//将数据存储到本地
                 }catch (Exception e){
                     e.printStackTrace();
                 }
             }

             @Override
             public void FailRender(int code, String error) {
                 if(code==4){
                     toast(error);
                 }else{
                     toast(error);
                 }

             }
         });
   }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        MainActivity activity= (MainActivity) getActivity();
        TextView headTitle= (TextView) activity.findViewById(R.id.header_title);
        String title= (String) pager.getAdapter().getPageTitle(position);
        headTitle.setText(title);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}

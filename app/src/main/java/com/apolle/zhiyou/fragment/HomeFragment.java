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
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.rey.material.widget.Spinner;
import com.rey.material.widget.TabPageIndicator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends BaseFragment {

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
          MainActivity.onBottomTabChangeListener onBottomTabChangeListener=new MainActivity.onBottomTabChangeListener(){
              @Override
              public void onBottomTabChange(Toolbar toolbar) {

              }
          };
      /*  tabInteractor=new TabInteractor() {//当切换到此页面时更换toolbar中内容
            @Override
            public void OnTabChange(Context context, View toolbar_content) {
                  spinner=new Spinner(context);
                  spinnerItems=getResources().getStringArray(R.array.articleTypes);
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,android.R.id.text1,spinnerItems);
                 spinner.setAdapter(adapter);
                ((LinearLayout)toolbar_content).addView(spinner);
            }
        };
        if(null!=spinner){
            spinner.setOnItemClickListener( new Spinner.OnItemClickListener() {
                @Override
                public boolean onItemClick(Spinner parent, View view, int position, long id) {
                    if(null!=spinnerItems){

                    }
                    return false;
                }
            });
        }*/

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
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                System.out.println(TAG_ONE+" onPageScrolled:"+position+" positionOffset:"+"positionOffsetPixels"+positionOffsetPixels);
            }
            @Override
            public void onPageSelected(int position) {
                    pager.setCurrentItem(position);
                System.out.println(TAG_ONE+"onPageSelected"+position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                System.out.println(TAG_ONE+"onPageScrollStateChanged state"+state);
            }
        });

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
   private void initNetHomeTileData(){
//         请求参数头
       final HashMap<String,String> params=new HashMap<String,String>();
       params.put("uid","2");//当前登录用户id
       System.out.println("title:"+titles);
         ArticleAction.HomeTitle(getActivity(), params, new ArticleAction.renderCallback() {
             @Override
             public void SuccessRender(ArrayList<? extends Serializable> titles) {
                 adapter=new TabPageAdapter(getFm(),(ArrayList<Channel>)titles);
                 pager.setAdapter(adapter);
                 indicator.setViewPager(pager);
                 try{
                     db.saveAll(titles);//将数据存储到本地
                 }catch (Exception e){
                     e.printStackTrace();
                 }
             }

             @Override
             public void FailRender() {

             }
         });
   }





}

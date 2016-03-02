package com.apolle.zhiyou.fragment;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.apolle.zhiyou.Model.Channel;
import com.apolle.zhiyou.R;
import com.apolle.zhiyou.adapter.TabPageAdapter;
import com.apolle.zhiyou.mUtil.NetUrl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.rey.material.widget.TabPageIndicator;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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




    public static HomeFragment getHomeFragment(){
        if(null==homeFragment){
             homeFragment=new HomeFragment();
        }
        return homeFragment;
    }

    public HomeFragment() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
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

       Context context=getActivity().getApplicationContext();
        RequestQueue requestQueue= Volley.newRequestQueue(context);

       StringRequest request=new StringRequest(Request.Method.POST, NetUrl.HOME_TITLE, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {
               System.out.println("接收数据"+response);
             if(null!=response){
                 try{
                     JSONObject json=new JSONObject(response);
                     String errorcode= json.getString("errorcode");
                     if(0==Integer.parseInt(errorcode)){
                         Gson gson= new Gson();
                         String channel=json.getString("content");
                          titles=gson.fromJson(channel, new TypeToken<List<Channel>>(){ }.getType());
                            System.out.println("title:"+titles);
                            adapter=new TabPageAdapter(getFm(),titles);
                            pager.setAdapter(adapter);
                            indicator.setViewPager(pager);
                            db.saveAll(titles);//将数据存储到本地
                     }
                     System.out.println(TAG_ONE+"标题"+titles);
                 }catch (Exception e){
                     e.printStackTrace();
                 }
           }

           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_LONG).show();
           }
       }){
           @Override
           protected Map<String, String> getParams() throws AuthFailureError {
                    return params;
           }
       };
       requestQueue.add(request);

   }





}

package com.apolle.zhiyou.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.apolle.zhiyou.Http.ArticleAction;
import com.apolle.zhiyou.Model.Article;
import com.apolle.zhiyou.R;
import com.apolle.zhiyou.adapter.HomeArticleContentAdapter;
import com.apolle.zhiyou.interactor.NetUrl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;


public class HomeContentFragment extends BaseFragment {
    private static final int CONTENT_NUM = 10;

    private static HomeContentFragment homeContentFragment;
    private HomeArticleContentAdapter adapter;
    private View rootView;
    private String cid;//当前频道id;
    private ArrayList<Article> articles;
//    private LinearLayout refreshLayout;
    private PullToRefreshListView zy_home_content;
    private  HashMap<String,String> params;
    private long lastRefreshTime=0,oldRefreshTime=0;
    private int getDataTimes=1;//获取数据的次数
    private int lastVisibleItem;
    private  LinearLayoutManager llm;
    private boolean isLastItem=false;
    private boolean scrollFlag=false;//判断元素滚动状态
    private float scrollY=0;
    private Bundle bundle;
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         rootView= inflater.inflate(R.layout.fragment_home_square, container, false);
        bundle=getArguments();
        cid=bundle.getString("cid");
        zy_home_content=(PullToRefreshListView) rootView.findViewById(R.id.zy_home_content);
        articles=new ArrayList<Article>();

        adapter= new HomeArticleContentAdapter(getActivity(),articles);
        zy_home_content.setAdapter(adapter);//为recyviewer设置adapter

        oldRefreshTime= System.currentTimeMillis();
        lastRefreshTime=System.currentTimeMillis();
        initData(CONTENT_NUM,0,lastRefreshTime,"old");//获取小于当前时间的数据
        zy_home_content.setMode(PullToRefreshBase.Mode.BOTH);

        zy_home_content.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                    getLastData();//获取最新数据

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {//向上拉
                int offset=CONTENT_NUM*getDataTimes-1;
                initData(CONTENT_NUM,offset,oldRefreshTime,"old");//获取小于当前时间的数据

            }
        });

        return rootView;
    }
    public static  HomeContentFragment getHomeContentFragment(){
        if(null==homeContentFragment){
            homeContentFragment=new HomeContentFragment();
        }
        return homeContentFragment;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 获取最新的10条数据
     */
    public void getLastData(){
        initData(CONTENT_NUM,0,lastRefreshTime,"new");
    }
    public void initData(int num, int offset, long timeline, final String type){//new是最新数据,old是老数据
        params=NetUrl.initParams();
        params.put("cid",cid);
        params.put("num",String.valueOf(num));
        params.put("offset",String.valueOf(offset));
        params.put("timeline", String.valueOf(timeline/1000));
        params.put("type",type);
        test("参数"+params+"===");
        ArticleAction.HomeContent(getActivity(), params, new ArticleAction.renderCallback() {
            @Override
            public void SuccessRender(ArrayList<? extends Serializable> content) {
                if("new".equals(type)){
                    adapter.addItems((ArrayList<Article>) content);
                    lastRefreshTime=System.currentTimeMillis();

                }else if("old".equals(type)){
                    adapter.addMoreItems((ArrayList<Article>) content);
                }
                zy_home_content.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        zy_home_content.onRefreshComplete();
                    }
                },1000);
            }

            @Override
            public void FailRender(int code, String error) {
                zy_home_content.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        zy_home_content.onRefreshComplete();
                    }
                },1000);
                if(code==4){
                    toast("没有更多数据");

                }else{
                    toast(error);
                }
            }
        });

    }
}

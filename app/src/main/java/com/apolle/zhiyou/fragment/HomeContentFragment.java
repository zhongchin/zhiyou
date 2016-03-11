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
import com.apolle.zhiyou.Model.Article;
import com.apolle.zhiyou.R;
import com.apolle.zhiyou.adapter.HomeArticleContentAdapter;
import com.apolle.zhiyou.interactor.NetUrl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


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
    private int lastRefreshTime=0,oldRefreshTime=0;
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

        zy_home_content=(PullToRefreshListView) rootView.findViewById(R.id.zy_home_content);
//        cid=bundle.getString("cid");
        articles=new ArrayList<Article>();
        Article article1=new Article();
        article1.setCid("1");
        article1.setTid("2");
        article1.setAuthid("2");
        article1.setAuthor("haugntao");
        article1.setClosed("0");
        article1.setCommentimes("zhefgasdhgasiohsfiugafs");
        article1.setCreated_at("78天前");
        article1.setContent("Android系统中TextView默认行间距比较窄，不美观。\n" +
                "    我们可以设置每行的行间距，可以通过属性android:lineSpacingExtra或android:lineSpacingMultiplier来做");
        article1.setHeadpic("http://www.people.com.cn/mediafile/pic/20150331/77/8172027402265324077.jpg");
        article1.setRecommends("2");
        article1.setNickname("zhofadshog");
        article1.setFavtimes("234");
        article1.setSubject("在你要设置的TextView中加入如下代码：");
        article1.setDateline("3749374");
        article1.setReport("fdsafds");
        article1.setUid("3");
        ArrayList<Article.AttachmentsEntity> attachments=new ArrayList<Article.AttachmentsEntity>();

        article1.setAttachments(attachments);
        articles.add(article1);
        Article article2=article1;
        article2.setTid("2");
        articles.add(article2);

        System.out.println("huangtao文章"+articles.size()+articles);

        adapter= new HomeArticleContentAdapter(getActivity(),articles);
        zy_home_content.setAdapter(adapter);//为recyviewer设置adapter

        oldRefreshTime=lastRefreshTime= (int)System.currentTimeMillis()/1000;
        initData(CONTENT_NUM,0,lastRefreshTime,"old");//获取小于当前时间的数据
        zy_home_content.setMode(PullToRefreshBase.Mode.BOTH);

        zy_home_content.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                    getLastData();//获取最新数据
                   zy_home_content.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {//向上拉
                System.out.println("是否加载到最底部"+isLastItem);
                if(isLastItem){

                }
                int offset=CONTENT_NUM*getDataTimes-1;
                initData(CONTENT_NUM,offset,oldRefreshTime,"old");//获取小于当前时间的数据
                zy_home_content.onRefreshComplete();
            }
        });

        zy_home_content.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                isLastItem=true;
            }
        });
        zy_home_content.setActivated(true);

        zy_home_content.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

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
    public void initData(int num, int offset, int timeline, final String type){//new是最新数据,old是老数据
        params=new HashMap<String,String>();
        params.put("cid",cid);
        params.put("num",String.valueOf(num));
        params.put("offset",String.valueOf(offset));
        params.put("timeline", String.valueOf(timeline));
        params.put("type",type);
        System.out.println("数据url"+NetUrl.HOME_CONTENT);
        final RequestQueue  requestQueue=Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest request= new StringRequest(
                Request.Method.POST, NetUrl.HOME_CONTENT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(null!=response){
                    try {
                        JSONObject js=new JSONObject(response);
                        String code=js.getString("errorcode");
                        System.out.println("hello"+code);
                        if(code.equals("0")){
                            System.out.println("hello ok");
                            String content=js.getString("content");
                            Gson gson=new Gson();
                            articles=gson.fromJson(content,new TypeToken<ArrayList<Article>>(){ }.getType());

                            System.out.println("数据content:"+articles.get(0).getContent());
                            if("new".equals(type)){
                                adapter.addItems(articles);
                                lastRefreshTime=(int)System.currentTimeMillis()/1000;
                            }else if("old".equals(type)){
                                adapter.addMoreItems(articles);
                            }
                        }else{
                            showTopic(js.getString("errormsg"));
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }


                }
                System.out.println("获取的数据"+response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("错误"+error.getMessage());
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                System.out.println("参数"+params);
                return params;
            }
        };

        requestQueue.add(request);

    }
}

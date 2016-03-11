package com.apolle.zhiyou.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.apolle.zhiyou.Model.Article;
import com.apolle.zhiyou.R;
import com.apolle.zhiyou.activity.ArticleDetailActivity;
import com.apolle.zhiyou.adapter.HomeContentAdapter;
import com.apolle.zhiyou.interactor.NetUrl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class HomeSquareFragment extends BaseFragment {
    private static final int CONTENT_NUM = 10;



    private HomeContentAdapter adapter;
    private View rootView;
    private String cid;//当前频道id;
    private ArrayList<Article> articles;
    private SwipeRefreshLayout refreshLayout;
    private  RecyclerView zy_home_content;
    private  HashMap<String,String> params;
    private int lastRefreshTime=0,oldRefreshTime=0;
    private int getDataTimes=1;//获取数据的次数
    private int lastVisibleItem;
    private  LinearLayoutManager llm;
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         rootView= inflater.inflate(R.layout.fragment_home_square, container, false);
        final Bundle bundle=getArguments();
        cid=bundle.getString("cid");

//        refreshLayout=(SwipeRefreshLayout) rootView.findViewById(R.id.zy_home_wrapper);
        zy_home_content=(RecyclerView) rootView.findViewById(R.id.zy_home_content);

        refreshLayout.setColorSchemeColors(R.color.swipefreshcolor1,R.color.swipefreshcolor2,R.color.swipefreshcolor3,R.color.swipefreshcolor4);
        refreshLayout.setProgressViewOffset(false,0,(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,24,getResources().getDisplayMetrics()));

        articles=new ArrayList<Article>();
        final Article article1=new Article();
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
        final Article article2=article1;
        article2.setTid("2");
        articles.add(article2);

        System.out.println("huangtao文章"+articles.size()+articles);
        llm=new LinearLayoutManager(getActivity());
        llm.setOrientation(OrientationHelper.VERTICAL);
        zy_home_content.setLayoutManager(llm);
        adapter=new HomeContentAdapter(getActivity(),articles);
        zy_home_content.setAdapter(adapter);//为recyviewer设置adapter
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                  adapter.addItems(articles);
                  System.out.println("huangtao刷新"+articles);
                  refreshLayout.setRefreshing(false);

            }
        });


        oldRefreshTime=lastRefreshTime= (int)System.currentTimeMillis()/1000;
        initData(CONTENT_NUM,0,lastRefreshTime,"old");//获取小于当前时间的数据

        zy_home_content.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                super.onScrollStateChanged(recyclerView, newState);
                if(newState==RecyclerView.SCROLL_STATE_IDLE&&lastVisibleItem+1==adapter.getItemCount()){
                        getLastData();//获取最新数据
                    int offset=CONTENT_NUM*getDataTimes-1;
                    initData(CONTENT_NUM,offset,oldRefreshTime,"old");//获取小于当前时间的数据
                    adapter.addMoreItems(articles);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem=llm.findLastCompletelyVisibleItemPosition();
            }
        });
        zy_home_content.setActivated(true);

        adapter.setOnItemClickListener(new HomeContentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                       System.out.println("huangtao onitemclick"+position+"===="+v);
                       Intent intent=new Intent(getActivity(),ArticleDetailActivity.class);
                       Bundle bundle=new Bundle();
                       bundle.putSerializable("article", articles.get(position) );
                       intent.putExtras(bundle);
                      startActivity(intent);
            }
        });

        return rootView;
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
        lastRefreshTime=(int)System.currentTimeMillis()/1000;
    }
    public void initData(int num, int offset, int lastRefreshTime, final String type){//new是最新数据,old是老数据
        params=new HashMap<String,String>();
        params.put("cid",cid);
        params.put("num",String.valueOf(num));
        params.put("offset",String.valueOf(offset));
        params.put("timeline", String.valueOf(lastRefreshTime));
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

package com.apolle.zhiyou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.apolle.zhiyou.Model.Article;
import com.apolle.zhiyou.R;
import com.apolle.zhiyou.Tool.LruImageCache;
import com.apolle.zhiyou.adapter.HomeArticleGridViewAdapter;
import com.apolle.zhiyou.mUtil.NetUrl;
import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.rey.material.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ArticleDetailActivity extends BaseActivity {
    private RequestQueue mQueue;
    private HashMap<String,String> params;
    private   Article article;
    private ImageLoader imageLoader;

    @ViewInject(R.id.toolbar)
        Toolbar toolbar;
    @ViewInject(R.id.home_content_item_headpic)
        NetworkImageView home_content_item_headpic;
    @ViewInject(R.id.content_article_subject)
        TextView  content_article_subject;
    @ViewInject(R.id.home_content_item_username)
        TextView home_content_item_username;
    @ViewInject(R.id.home_content_item_view)
        TextView home_content_item_view;
    @ViewInject(R.id.home_content_attachment)
        GridView home_content_attachment;
    @ViewInject(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbar_layout;
    @ViewInject(R.id.toolbarBg)
    ImageView toolbarBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         Bundle bundle=getIntent().getExtras();
        article= (Article) bundle.getSerializable("article");
        setSupportActionBar(toolbar);
        mQueue=Volley.newRequestQueue(this);
        LruImageCache imageCache=LruImageCache.getInstance();
         imageLoader=new ImageLoader(mQueue,imageCache);
        System.out.println("article:"+article+"headpic"+article.getHeadpic()+"imageloader"+imageLoader);
        initData();

    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_article_detail;
    }

    public void initData(){
        toolbar_layout.setTitle(article.getSubject());
        home_content_item_headpic.setErrorImageResId(R.drawable.ic_arrow_to_drawer);
        home_content_item_headpic.setDefaultImageResId(R.drawable.ic_stop_to_play);
        String imgSrc=article.getHeadpic();
        if(null!=imgSrc){
            home_content_item_headpic.setImageUrl(imgSrc,imageLoader);
        }

        content_article_subject.setText(article.getSubject());
        home_content_item_username.setText(article.getAuthor());
        home_content_item_view.setText(article.getContent());
        ArrayList<Article.AttachmentsEntity> attachments=(ArrayList<Article.AttachmentsEntity>) article.getAttachments();

        if(attachments.size()>0){
           BaseAdapter adapter =new HomeArticleGridViewAdapter(this,attachments);
            home_content_attachment.setAdapter(adapter);
        }
        toolbarBg.setImageResource(R.drawable.barbg);
        toolbarBg.setAlpha(0.5f);
    }
    public void Actions(){

    }
    public void getNetData(int num,int offset,int lastRefreshTime,String type){
        params=new HashMap<String,String>();
        params.put("cid",article.getTid());
        params.put("num",String.valueOf(num));
        params.put("offset",String.valueOf(offset));
        params.put("timeline", String.valueOf(lastRefreshTime));
        params.put("type",type);
        System.out.println("数据url"+ NetUrl.HOME_CONTENT);
        final RequestQueue  requestQueue= Volley.newRequestQueue(getApplicationContext());
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
                        }else{

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent= new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

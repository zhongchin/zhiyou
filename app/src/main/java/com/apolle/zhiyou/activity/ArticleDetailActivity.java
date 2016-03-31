package com.apolle.zhiyou.activity;

import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AbsListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.apolle.zhiyou.Http.ArticleAction;
import com.apolle.zhiyou.Model.Article;
import com.apolle.zhiyou.R;
import com.apolle.zhiyou.Tool.DateTimeTool;
import com.apolle.zhiyou.Tool.LruImageCache;
import com.apolle.zhiyou.interactor.NetUrl;
import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.rey.material.widget.TextView;

import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ArticleDetailActivity extends BaseActivity {
    private RequestQueue mQueue;
    private HashMap<String,String> params;
    private   Article article;
    private ImageLoader imageLoader;

    @ViewInject(R.id.appbarlayout)
    AppBarLayout appBarLayout;
    @ViewInject(R.id.toolbar)
        Toolbar toolbar;
    @ViewInject(R.id.article_title)
    TextView article_title;
    @ViewInject(R.id.userheadpic)
    BootstrapCircleThumbnail userheadpic;
    @ViewInject(R.id.author)
    TextView author;
    @ViewInject(R.id.publish_time)
    TextView publish_time;

    @ViewInject(R.id.article_content)
    WebView article_content;
    @ViewInject(R.id.comments)
    ListViewCompat comments;
    @ViewInject(R.id.send_comment)
    AppCompatEditText send_comment;
    @ViewInject(R.id.share)
    AppCompatButton share;
    @ViewInject(R.id.collect)
    AppCompatButton collect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
         Bundle bundle=getIntent().getExtras();
        article= (Article) bundle.getSerializable("article");
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        mQueue=Volley.newRequestQueue(this);
        LruImageCache imageCache=LruImageCache.getInstance();
         imageLoader=new ImageLoader(mQueue,imageCache);
        initData();

    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_article_detail;
    }

    public void initData(){
        ImageLoader.ImageListener listener=imageLoader.getImageListener(userheadpic,R.drawable.user_headpic,R.drawable.user_error);
        String imgSrc=article.getHeadpic();
         imageLoader.get(imgSrc,listener,60,60);

        article_title.setText(article.getSubject());
        author.setText(article.getAuthor());
        String dateline=article.getDateline();

        String  time=DateTimeTool.LongAge(Integer.parseInt(article.getDateline()));

        publish_time.setText(time);
        article_content.getSettings().setJavaScriptEnabled(true);
        article_content.getSettings().setDefaultTextEncodingName("UTF-8");
        article_content.loadData(article.getContent(),"text/html; charset=UTF-8",null);
    }

    public void getNetData(int num,int offset,int lastRefreshTime,String type){
        params=NetUrl.initParams();
        params.put("tid",article.getTid());
        params.put("num",String.valueOf(num));
        params.put("offset",String.valueOf(offset));
        ArticleAction.getArticleComments(ArticleDetailActivity.this, params, new ArticleAction.renderCallback() {
            @Override
            public void SuccessRender(ArrayList<? extends Serializable> content) {

            }
            @Override
            public void FailRender(int code, String error) {

            }
        });
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
    @OnClick({R.id.collect,R.id.share})
    public void ViewOnClick(View view){
        switch (view.getId()){
            case R.id.collect:
                Bundle bundle=new Bundle();
                bundle.putSerializable("article", article);
                bundle.putInt("operator",1);
                goActivity(SelectNodeActivity.class,bundle);
                break;
            case R.id.share:
                    doShare();
                break;
        }

    }

    @Override
    public AppCompatActivity getActivity() {
        return ArticleDetailActivity.this;
    }
    private void doShare(){

    }
}

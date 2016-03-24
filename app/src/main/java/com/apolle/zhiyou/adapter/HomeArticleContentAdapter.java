package com.apolle.zhiyou.adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.apolle.zhiyou.Http.ArticleAction;
import com.apolle.zhiyou.Http.NoteAction;
import com.apolle.zhiyou.Model.Article;
import com.apolle.zhiyou.R;
import com.apolle.zhiyou.Tool.LruImageCache;
import com.apolle.zhiyou.activity.ArticleDetailActivity;
import com.apolle.zhiyou.activity.SelectNodeActivity;
import com.apolle.zhiyou.interactor.NetUrl;
import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.rey.material.widget.Button;
import com.rey.material.widget.LinearLayout;
import com.rey.material.widget.RadioButton;
import com.rey.material.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by huangtao on 2016/2/2712:47.
 * modify by huangtao on 12:47
 */
public class HomeArticleContentAdapter extends BaseAdapter implements View.OnClickListener{
    public Context mContext;
    public ArrayList<Article> articles;
    private  ImageLoader imageLoader;
    private String flag="";

    public HomeArticleContentAdapter(Context context, ArrayList<Article> articles) {
       this.mContext=context;
        this.articles=articles;

        RequestQueue mQueue= Volley.newRequestQueue(mContext);
        LruImageCache lruImageCache=new LruImageCache();
        imageLoader=new ImageLoader(mQueue,lruImageCache);

    }

    @Override
    public int getCount() {
        return articles.size();
    }

    @Override
    public Object getItem(int position) {
        return articles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
          HomeViewHolder myViewHolder =new HomeViewHolder();
         if(null==convertView){
             LayoutInflater inflater=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
             convertView=inflater.inflate(R.layout.home_content_item,null);
             myViewHolder.avatar=(BootstrapCircleThumbnail) convertView.findViewById(R.id.home_content_item_headpic);
             myViewHolder.userTextView=(TextView)convertView.findViewById(R.id.home_content_item_username);
             myViewHolder.otherTopicTextView=(TextView)convertView.findViewById(R.id.home_content_item_other);
             myViewHolder.contentTextView=(TextView) convertView.findViewById(R.id.home_content_item_view);
             myViewHolder.subjectTextView=(TextView)convertView.findViewById(R.id.content_article_subject);
             myViewHolder.ArticleContentView=(TextView) convertView.findViewById(R.id.home_content_item_view);
             myViewHolder.FavouriteBtn=(Button) convertView.findViewById(R.id.home_content_favourite);
             myViewHolder.ForwardBtn=(Button)convertView.findViewById(R.id.home_content_forward);
             myViewHolder.CollectBtn=(Button)convertView.findViewById(R.id.home_content_collect);
             myViewHolder.savemynote=(LinearLayout)convertView.findViewById(R.id.savetomynote);
             myViewHolder.checksaveNote=(RadioButton) convertView.findViewById(R.id.checksaveNote);
             myViewHolder.saveBtn=(Button)convertView.findViewById(R.id.save);
             convertView.setTag(myViewHolder);
         }else{
             myViewHolder=(HomeViewHolder) convertView.getTag();
         }
          Article article=articles.get(position);
          String imgSrc= article.getHeadpic();
           ImageLoader.ImageListener listener=imageLoader.getImageListener(myViewHolder.avatar,R.drawable.user_headpic,R.drawable.user_error);
            if(imgSrc==null||imgSrc.length()<1){
                myViewHolder.avatar.setImageResource(R.drawable.user_headpic);
            }else{
                imageLoader.get(imgSrc,listener,64,64);
            }


          myViewHolder.userTextView.setText(article.getAuthor());
          String dateline=article.getDateline();
          SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy年MM月dd HH:mm:ss");
          String time=dateFormat.format(new Date(Integer.parseInt(dateline)*1000));
          myViewHolder.otherTopicTextView.setText(time);
          myViewHolder.ArticleContentView.setText(article.getContent());
          myViewHolder.subjectTextView.setText(article.getSubject());

            String favouriteStr=mContext.getResources().getString(R.string.favouriteBtn);
            String collectStr=mContext.getResources().getString(R.string.collectBtn);
            String forwardStr=mContext.getResources().getString(R.string.forwardBtn);
              String favCount=article.getFavtimes().equals("0")?"":article.getFavtimes();
             String forwardCount=article.getForwardtimes().equals("0")?"":article.getForwardtimes();

            myViewHolder.FavouriteBtn.setText(favCount+" "+favouriteStr);
            myViewHolder.CollectBtn.setText(collectStr);
            myViewHolder.ForwardBtn.setText(forwardCount+" "+forwardStr);

            HashMap<String,Object> obj=new HashMap<String,Object>();
                 obj.put("position",position);
                 obj.put("view",myViewHolder);
            myViewHolder.FavouriteBtn.setTag(obj);
            myViewHolder.ForwardBtn.setTag(obj);
            myViewHolder.CollectBtn.setTag(obj);
            myViewHolder.subjectTextView.setTag(obj);
            myViewHolder.contentTextView.setTag(obj);
            myViewHolder.ForwardBtn.setTag(obj);
            myViewHolder.saveBtn.setTag(obj);
            myViewHolder.checksaveNote.setTag(obj);

            myViewHolder.subjectTextView.setOnClickListener(this);
            myViewHolder.contentTextView.setOnClickListener(this);
            myViewHolder.CollectBtn.setOnClickListener(this);
            myViewHolder.FavouriteBtn.setOnClickListener(this);
            myViewHolder.ForwardBtn.setOnClickListener(this);
            myViewHolder.saveBtn.setOnClickListener(this);
           myViewHolder.checksaveNote.setOnClickListener(this);
        return convertView;
    }

    public void addItems(ArrayList<Article> newDatas){
        newDatas.addAll(articles);
        articles.removeAll(articles);
        articles.addAll(newDatas);
        notifyDataSetChanged();

    }
    public void addMoreItems(ArrayList<Article> newDatas){
        articles.addAll(newDatas);
        notifyDataSetChanged();
    }


    public class HomeViewHolder{
        public BootstrapCircleThumbnail avatar;
        public TextView  userTextView,otherTopicTextView,subjectTextView,ArticleContentView;
        TextView contentTextView;
        Button FavouriteBtn,ForwardBtn,CollectBtn,saveBtn;
        LinearLayout savemynote;
        RadioButton checksaveNote;


    }

    @Override
    public void onClick(View v) {
                HashMap<String,Object> map=(HashMap<String, Object>) v.getTag();
                HomeViewHolder myViewHolder=(HomeViewHolder) map.get("view");
                int  position=(int)map.get("position");
                Article article=(Article)getItem(position);

                HashMap<String,String> params= NetUrl.initParams();
                params.put("tid",article.getTid());
            switch (v.getId()){
               case R.id.home_content_favourite://点击了赞一个
                          if(myViewHolder.savemynote.getVisibility()==View.VISIBLE){
                              myViewHolder.savemynote.setVisibility(View.GONE);
                          }

                         ArticleAction.Favourite(mContext,params);
                        int  favtime=Integer.parseInt(article.getFavtimes())+1;
                        myViewHolder.FavouriteBtn.setText(favtime+" 赞一个");
                    break;
               case R.id.home_content_collect://点击收藏
                       Intent intent1=new Intent(mContext,SelectNodeActivity.class);
                       Bundle bundle=new Bundle();
                       bundle.putSerializable("article", article);
                       bundle.putInt("operator",1);
                       intent1.putExtras(bundle);
                       mContext.startActivity(intent1);
                    break;
                case R.id.home_content_forward://点击转发显示是否收藏到我的笔记
                    if("forward".equals(flag)){
                        myViewHolder.savemynote.setVisibility(View.GONE);
                        flag="";
                    }else{
                        myViewHolder.savemynote.setVisibility(View.VISIBLE);
                        myViewHolder.checksaveNote.setText(mContext.getResources().getString(R.string.forwardtomynote));
                        flag="forward";
                    }
                     break;
                case R.id.save://转发同时保存数据到我的笔记
                    myViewHolder.savemynote.setVisibility(View.GONE);
                    if(flag=="forward"&&myViewHolder.checksaveNote.isSelected()){
                        Intent intent=new Intent(mContext,SelectNodeActivity.class);
                        Bundle bundle2=new Bundle();
                        bundle2.putSerializable("article", article);
                        bundle2.putInt("operator",4);
                        intent.putExtras(bundle2);
                        mContext.startActivity(intent);
                    }else{
                        NoteAction.Forward(mContext,params);//直接转发笔记
                    }
                    break;
                case R.id.checksaveNote://点击选择是否转发到我的笔记
                      if(myViewHolder.checksaveNote.isSelected()){
                          myViewHolder.checksaveNote.setSelected(false);
                      }else{
                          myViewHolder.checksaveNote.setSelected(true);
                      }
                      break;
                case R.id.content_article_subject:
                case R.id.home_content_item_view:
                        Intent intent=new Intent(mContext,ArticleDetailActivity.class);
                        Bundle bundle1=new Bundle();
                        bundle1.putSerializable("article", (Article)getItem(position));
                        intent.putExtras(bundle1);
                        mContext.startActivity(intent);
                    break;
            }
    }

    private void toast(String topic){
        Toast.makeText(mContext,topic,Toast.LENGTH_SHORT).show();
    }
}

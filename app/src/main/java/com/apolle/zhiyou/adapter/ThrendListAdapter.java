package com.apolle.zhiyou.adapter;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.apolle.zhiyou.Model.Article;
import com.apolle.zhiyou.R;
import com.apolle.zhiyou.Tool.DateTimeTool;
import com.apolle.zhiyou.Tool.LruImageCache;
import com.apolle.zhiyou.view.CircleImageView;
import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.rey.material.widget.ImageButton;
import com.rey.material.widget.TextView;
import java.util.ArrayList;

/**
 * Created by huangtao on 2016/3/2322:54.
 * modify by huangtao on 22:54
 */
public class ThrendListAdapter extends BaseAdapter implements View.OnClickListener {
    private Context mContext;
    private ArrayList<Article> articles;
    private  ImageLoader imageLoader;
    public  final static String VIEW_TAG="article_tag";
    public  final static String VIEW_POSITION="article_position";
    public ThrendListAdapter(Context context,ArrayList<Article> articles){
       this.mContext=context;
       this.articles=articles;
        RequestQueue queue=Volley.newRequestQueue(context);
         imageLoader=new ImageLoader(queue,new LruImageCache());
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
          TrendViewHolder myViewholder;
        if(convertView==null){
            myViewholder=new TrendViewHolder();
              convertView= LayoutInflater.from(mContext).inflate(R.layout.chat_trend_item,null);
              myViewholder.user_headpic= (CircleImageView) convertView.findViewById(R.id.userheadpic);
              myViewholder.username= (TextView) convertView.findViewById(R.id.username);
              myViewholder.subject= (TextView) convertView.findViewById(R.id.note_title);
              myViewholder.content= (TextView) convertView.findViewById(R.id.note_content);
              myViewholder.dateline= (TextView) convertView.findViewById(R.id.dateline);
              myViewholder.showComment= (ImageButton) convertView.findViewById(R.id.showCommentBtn);
              myViewholder.commentBtn= (TextView) convertView.findViewById(R.id.commentBtn);
              myViewholder.likeBtn= (TextView) convertView.findViewById(R.id.likeBtn);
              myViewholder.commentContainer= (LinearLayout) convertView.findViewById(R.id.commentContainer);
            convertView.setTag(myViewholder);
        }else{
            myViewholder= (TrendViewHolder) convertView.getTag();
        }
          ImageLoader.ImageListener listener=imageLoader.getImageListener(myViewholder.user_headpic,R.drawable.user_headpic,R.drawable.user_error);
          Article article=articles.get(position);
          if(article.getHeadpic()!=null){
              imageLoader.get(article.getHeadpic(),listener);
          }else{
              myViewholder.user_headpic.setImageResource(R.drawable.user_headpic);
          }


          myViewholder.username.setText(article.getNickname());
          myViewholder.subject.setText(article.getSubject());
          myViewholder.content.setText(Html.fromHtml(article.getContent()));
          String  time= DateTimeTool.LongAge(Integer.parseInt(article.getDateline()));
          myViewholder.dateline.setText(time);

           Bundle bundle=new Bundle();
           bundle.putSerializable(VIEW_TAG,article);
           bundle.putInt(VIEW_POSITION,position);
           myViewholder.commentBtn.setTag(bundle);
           myViewholder.likeBtn.setTag(bundle);
           final View parentView=convertView;
           myViewholder.showComment.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                    LinearLayout commentContainer= (LinearLayout) parentView.findViewById(R.id.commentContainer);
                   if(commentContainer.getVisibility()==View.VISIBLE){
                       commentContainer.setVisibility(View.INVISIBLE);
                   }else{
                       commentContainer.setVisibility(View.VISIBLE);
                   }
               }
           });
           myViewholder.commentBtn.setOnClickListener(this);
           myViewholder.likeBtn.setOnClickListener(this);
        return convertView;
    }

    public class TrendViewHolder{
        public CircleImageView user_headpic;
        public TextView username,content,subject,dateline,commentBtn,likeBtn;
        public ImageButton showComment;
        public LinearLayout commentContainer;
    }

    public void addItems(ArrayList<Article> items){
         items.addAll(articles);
         articles.removeAll(articles);
         articles.addAll(items);
         notifyDataSetChanged();
    }
    @Override
    public void onClick(View v) {
          Bundle tag= (Bundle) v.getTag();
         int position=tag.getInt(VIEW_POSITION);
    }


}

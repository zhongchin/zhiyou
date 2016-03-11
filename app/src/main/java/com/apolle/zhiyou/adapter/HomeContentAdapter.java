package com.apolle.zhiyou.adapter;


import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.apolle.zhiyou.Model.Article;
import com.apolle.zhiyou.R;
import com.apolle.zhiyou.Tool.LruImageCache;

import com.rey.material.widget.Button;
import com.rey.material.widget.TextView;

import java.util.ArrayList;

/**
 * Created by huangtao on 2016/2/2712:47.
 * modify by huangtao on 12:47
 */
public class HomeContentAdapter extends RecyclerView.Adapter<HomeContentAdapter.HomeContentViewHolder> implements View.OnClickListener{

    private ArrayList<Article> articles;
    private Context mContext;
    private View mView;    private LayoutInflater mInflater;

    private ImageLoader imageLoader;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    public OnItemClickListener itemClickListener;
    private int curPosition=0;
    public HomeContentAdapter(Context context, ArrayList<Article> articles) {
        super();
        this.mContext=context;
        this.articles=articles;
        System.out.println("单个articles"+articles);
        this.mInflater=LayoutInflater.from(context);
        RequestQueue mQueue= Volley.newRequestQueue(mContext);
        LruImageCache lruImageCache=new LruImageCache();
        imageLoader=new ImageLoader(mQueue,lruImageCache);

    }

    @Override
    public HomeContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            mView=  mInflater.inflate(R.layout.home_content_item,parent,false);
             HomeContentViewHolder viewHolder=new HomeContentViewHolder(mView);
           /* mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(null!=itemClickListener){
                        itemClickListener.onItemClick(v);
                    }


                }
            });*/
          return viewHolder;
    }

    @Override
    public void onBindViewHolder(HomeContentViewHolder myViewHolder, int position) {

        Article article=articles.get(position);

        myViewHolder.avatar.setDefaultImageResId(android.R.drawable.ic_lock_idle_alarm);
        myViewHolder.avatar.setErrorImageResId(android.R.drawable.ic_lock_idle_alarm);
        String imgSrc= article.getHeadpic();
        myViewHolder.avatar.setImageUrl(imgSrc,imageLoader);

        myViewHolder.userTextView.setText(article.getAuthor());
        myViewHolder.otherTopicTextView.setText(article.getDateline());
        myViewHolder.ArticleContentView.setText("   "+article.getContent());
        myViewHolder.subjectTextView.setText(article.getSubject());

        ArrayList<Article.AttachmentsEntity> attachments=(ArrayList) article.getAttachments();

        if (attachments.size()>=1){
            myViewHolder.gridView.setAdapter(new HomeArticleGridViewAdapter(mContext,attachments));
        }else{
            myViewHolder.gridView.setVisibility(View.GONE);
        }

        myViewHolder.FavouriteBtn.setText(mContext.getResources().getString(R.string.favouriteBtn));
        myViewHolder.CollectBtn.setText(mContext.getResources().getString(R.string.collectBtn));
        myViewHolder.FavouriteBtn.setText(mContext.getResources().getString(R.string.favouriteBtn));
        myViewHolder.itemView.setTag(position);
        curPosition=position;
        myViewHolder.subjectTextView.setOnClickListener(this);
        myViewHolder.contentTextView.setOnClickListener(this);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onClick(View v) {
    }

    public void addItems(ArrayList<Article> newDatas){
        newDatas.addAll(articles);
        articles.removeAll(articles);
        articles.addAll(newDatas);
        System.out.println("huangtao articlesize"+articles+articles.size()+""+newDatas);
        notifyItemRangeInserted(0, articles.size());
        notifyDataSetChanged();

    }
    public void addMoreItems(ArrayList<Article> newDatas){
        articles.addAll(newDatas);
        notifyItemRangeInserted(0, articles.size());
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return articles.size();
    }

     class HomeFooterViewHolder extends RecyclerView.ViewHolder{
        public TextView loadText;

        public HomeFooterViewHolder(View itemView) {
            super(itemView);

        }
    }

    @Override
    public int getItemViewType(int position) {
        if(getItemCount()==position+1){
            return TYPE_FOOTER;
        }else{
            return TYPE_ITEM;
        }
    }
    public interface OnItemClickListener{
           void onItemClick(View v, int position);
    }
    public void setOnItemClickListener(OnItemClickListener itemClickListener){
            this.itemClickListener=itemClickListener;
    }

  public   class HomeContentViewHolder extends RecyclerView.ViewHolder{
        public NetworkImageView avatar;
        public TextView  userTextView,otherTopicTextView,subjectTextView,ArticleContentView;
        public TextView contentTextView;
        public GridView gridView;
        public  Button FavouriteBtn,ForwardBtn,CollectBtn;

        public HomeContentViewHolder(final View convertView) {
            super(convertView);
            System.out.println("huangtao convertView"+convertView+convertView.getId());
            avatar=(NetworkImageView) convertView.findViewById(R.id.home_content_item_headpic);
            userTextView=(TextView)convertView.findViewById(R.id.home_content_item_username);
            otherTopicTextView=(TextView)convertView.findViewById(R.id.home_content_item_other);
            contentTextView=(TextView) convertView.findViewById(R.id.home_content_item_view);
            subjectTextView=(TextView)convertView.findViewById(R.id.content_article_subject);
            ArticleContentView=(TextView) convertView.findViewById(R.id.home_content_item_view);

            gridView=(GridView) convertView.findViewById(R.id.home_content_attachment);
            FavouriteBtn=(Button) convertView.findViewById(R.id.home_content_favourite);
            ForwardBtn=(Button)convertView.findViewById(R.id.home_content_forward);
            CollectBtn=(Button)convertView.findViewById(R.id.home_content_collect);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(null!=itemClickListener){
                        itemClickListener.onItemClick(v,(int)convertView.getTag());
                    }
                }
            });

        }
    }



}
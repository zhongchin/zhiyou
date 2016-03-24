package com.apolle.zhiyou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.apolle.zhiyou.Model.NewBean;
import com.apolle.zhiyou.R;
import com.apolle.zhiyou.Tool.LruImageCache;
import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.rey.material.widget.TextView;

import java.util.ArrayList;

/**
 * Created by huangtao on 2016/3/1918:16.
 * modify by huangtao on 18:16
 */
public class ChatNewListAdapter extends BaseAdapter{
    private Context mContext;
    private ArrayList<NewBean> dataSources;
    private ImageLoader imageLoader;

    public ChatNewListAdapter(Context context, ArrayList<NewBean> dataSources){
        this.mContext=context;
        this.dataSources=dataSources;
        RequestQueue queue=Volley.newRequestQueue(context);
        imageLoader=new ImageLoader(queue,new LruImageCache());
    }
    @Override
    public int getCount() {
        return dataSources.size();
    }

    @Override
    public Object getItem(int position) {
        return dataSources.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatNewViewHolder myViewHolder;
          if(convertView==null){
              myViewHolder=new ChatNewViewHolder();
              convertView=LayoutInflater.from(mContext).inflate(R.layout.chat_news,null);
              myViewHolder.avatar= (BootstrapCircleThumbnail) convertView.findViewById(R.id.avatar);
              myViewHolder.nameText= (TextView) convertView.findViewById(R.id.nameText);
              myViewHolder.lastNews= (TextView) convertView.findViewById(R.id.lastNew);
              myViewHolder.dateline= (TextView) convertView.findViewById(R.id.dateline);
              myViewHolder.countTxt= (TextView) convertView.findViewById(R.id.nums);
              convertView.setTag(myViewHolder);
          }else{
              myViewHolder= (ChatNewViewHolder) convertView.getTag();
          }
        NewBean myNew=dataSources.get(position);
        ImageLoader.ImageListener listener=imageLoader.getImageListener(myViewHolder.avatar,R.drawable.user_headpic,R.drawable.user_error);
        if(myNew.getHeadpic()!=null) {
            imageLoader.get(myNew.getHeadpic(), listener);
        }
        myViewHolder.nameText.setText(myNew.getUsername());
        myViewHolder.lastNews.setText(myNew.getDetails());
        myViewHolder.dateline.setText(myNew.getSendtime());
        myViewHolder.countTxt.setText(myNew.getNewsCount());
        myViewHolder.countTxt.setEnabled(true);
        return convertView;
    }

    public void addItems(ArrayList<NewBean> news){
        news.addAll(dataSources);
        dataSources.removeAll(dataSources);
        dataSources.addAll(news);
        notifyDataSetChanged();
    }
    public class ChatNewViewHolder{
        public BootstrapCircleThumbnail avatar;
        public TextView nameText;
        public TextView lastNews;
        public TextView dateline;
        public TextView countTxt;

    }
}

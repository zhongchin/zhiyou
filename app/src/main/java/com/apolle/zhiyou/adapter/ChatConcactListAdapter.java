package com.apolle.zhiyou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.apolle.zhiyou.Model.Person;
import com.apolle.zhiyou.R;
import com.apolle.zhiyou.Tool.LruImageCache;
import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.rey.material.widget.TextView;

import java.util.ArrayList;

/**
 * Created by huangtao on 2016/3/2217:18.
 * modify by huangtao on 17:18
 */
public class ChatConcactListAdapter extends BaseAdapter{
    private Context mContext;
    private ArrayList<Person> persons;
    private ImageLoader imageLoader;
    public ChatConcactListAdapter(Context context,ArrayList<Person> persons){
           this.mContext=context;
           this.persons=persons;
           RequestQueue queue=Volley.newRequestQueue(context);
           imageLoader=new ImageLoader(queue,new LruImageCache());
    }
    @Override
    public int getCount() {
        return persons.size();
    }

    @Override
    public Object getItem(int position) {
        return persons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ConcactViewHolder myViewHolder;
        if(convertView==null){
              convertView= LayoutInflater.from(mContext).inflate(R.layout.chat_concact,null);
              myViewHolder=new ConcactViewHolder();
              myViewHolder.headpic= (BootstrapCircleThumbnail) convertView.findViewById(R.id.headpic);
              myViewHolder.username= (TextView) convertView.findViewById(R.id.username);
              convertView.setTag(myViewHolder);
        }else{
             myViewHolder= (ConcactViewHolder) convertView.getTag();
        }
          Person person=persons.get(position);

        return convertView;
    }


    private class ConcactViewHolder{
        public BootstrapCircleThumbnail headpic;
        public TextView username;
    }
}

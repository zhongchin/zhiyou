package com.apolle.zhiyou.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.apolle.zhiyou.Model.Article;
import com.apolle.zhiyou.R;
import com.apolle.zhiyou.Tool.LruImageCache;

import java.util.ArrayList;

/**
 * Created by huangtao on 2016/3/114:56.
 * modify by huangtao on 14:56
 */
public class HomeArticleGridViewAdapter extends BaseAdapter {
    private RequestQueue mQueue;
    private ImageLoader imageLoader;
    private Context mContext;
    private ArrayList<Article.AttachmentsEntity> attachments;
    private LayoutInflater mInflater;
    public HomeArticleGridViewAdapter(Context mContext,ArrayList<Article.AttachmentsEntity> attachments) {
        this.mContext=mContext;
        this.attachments=attachments;
        this.mInflater=LayoutInflater.from(mContext);
        mQueue= Volley.newRequestQueue(mContext);
        LruImageCache imageCache=LruImageCache.getInstance();
        imageLoader=new ImageLoader(mQueue,imageCache);
    }

    @Override
    public int getCount() {
        return attachments.size();
    }

    @Override
    public Object getItem(int position) {
        return attachments.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
          GirdViewHolder myholder;
        if(null==convertView){
               convertView=mInflater.inflate(R.layout.simple_item_image_list,null);
               myholder=new GirdViewHolder();
               myholder.imageview=(NetworkImageView) convertView.findViewById(R.id.article_content_image);
                 convertView.setTag(myholder);
        }else{
            myholder=(GirdViewHolder) convertView.getTag();
        }

        myholder.imageview.setDefaultImageResId(android.R.drawable.ic_lock_idle_alarm);
        myholder.imageview.setErrorImageResId(android.R.drawable.ic_lock_idle_alarm);
        Article.AttachmentsEntity attachment= attachments.get(position);
        String imgSrc=attachment.getPicpath();
        myholder.imageview.setImageUrl(imgSrc,imageLoader);
/*
        int imgWidth=myholder.imageview.getWidth();
        //获取设备分辨率
        DisplayMetrics displayMetrics=new DisplayMetrics();
        WindowManager windowManager=(WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int winWidth=displayMetrics.widthPixels;
        int imgMaxWidth=(winWidth-12)/3;
        float bl=(float)(myholder.imageview.getMaxWidth()/myholder.imageview.getWidth());

        int imgMaxHeight=(int)bl*myholder.imageview.getHeight();


        myholder.imageview.setMaxWidth(imgMaxWidth);
        myholder.imageview.setMaxHeight(imgMaxHeight);
*/
        return convertView;
    }
    public class GirdViewHolder{
         public NetworkImageView imageview;
    }
}

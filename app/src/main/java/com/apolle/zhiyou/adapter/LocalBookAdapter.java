package com.apolle.zhiyou.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apolle.zhiyou.Model.LocalBook;
import com.apolle.zhiyou.R;
import com.apolle.zhiyou.Tool.DisplayMerticsTool;
import com.apolle.zhiyou.Tool.FileTool;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by huangtao on 2016/3/418:40.
 * modify by huangtao on 18:40
 */
public class LocalBookAdapter extends BaseAdapter{


    private Context context;
    private ArrayList<LocalBook> books;
    private LayoutInflater mInflator;
    public LocalBookAdapter(Context context, ArrayList<LocalBook> books) {
         this.context=context;
         this.books=books;
        this.mInflator=LayoutInflater.from( context );
    }

    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Object getItem(int position) {
        return books.get( position );
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
          BookViewHolder viewHolder=new BookViewHolder();
           if(null==convertView){
               convertView=mInflator.inflate( R.layout.simple_book_item,null);
               viewHolder.nameText=(TextView) convertView.findViewById( R.id.book_name);
               viewHolder.bookImage=(ImageView) convertView.findViewById( R.id.book_icon );
               convertView.setTag(viewHolder);
           }else{
               viewHolder=(BookViewHolder) convertView.getTag();
           }
             LocalBook book=books.get(position);
             viewHolder.nameText.setText(book.getName());
             int height=DisplayMerticsTool.getWindowHeight(context);
             int width=DisplayMerticsTool.getWindowWidth(context);

              convertView.setLayoutParams(new AbsListView.LayoutParams(width/3,(height-200)/3,Gravity.CENTER_VERTICAL));
            if(book.type=="add"){
                Bitmap cover= BitmapFactory.decodeResource(context.getResources(),R.mipmap.cover_net);
                viewHolder.bookImage.setImageBitmap(cover);
            }else{
                BitmapFactory.decodeFile(book.icon);
                if(!book.iconType){//没有图片使用系统默认图片
                    viewHolder.bookImage.setImageResource(FileTool.getFileImage(book.path));
                }else{
                    if(book.icon!=null){
                        Bitmap bitmap=BitmapFactory.decodeFile(book.icon);
                        viewHolder.bookImage.setImageBitmap(bitmap);
                    }
                }

            }
        return convertView;
    }

    class BookViewHolder{
        TextView nameText;
        ImageView bookImage;
    }

}

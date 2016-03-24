package com.apolle.zhiyou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by huangtao on 2016/3/102:09.
 * modify by huangtao on 2:09
 */
public class BookLeftItemAdapter extends BaseAdapter{
        private static   BookLeftItemAdapter adapter;
        private Context context;
        private String attribute;
        private List<? extends Serializable> items;
        private String property;//需要填充的属性值

        public BookLeftItemAdapter(Context context, List<? extends Serializable> items, String property) {
            this.context=context;
            this.items=items;
            this.attribute=property;
        }
        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                convertView= LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1,null);
            }
            TextView textView= (TextView) convertView.findViewById(android.R.id.text1);
            Object object=items.get(position);
            try {
                this.attribute=attribute.substring(0,1).toUpperCase()+attribute.substring(1);

                Method method=object.getClass().getMethod("get"+attribute);
                if(method!=null){
                    String propertyValue= (String) method.invoke(object);
                    textView.setText(propertyValue);
                }else{
                    Field field =object.getClass().getField(this.attribute);
                    String propertyValue= (String) field.get(object);
                    textView.setText(propertyValue);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return convertView;
        }
    }


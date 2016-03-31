package com.apolle.zhiyou.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.apolle.zhiyou.Model.Person;
import com.apolle.zhiyou.R;
import com.apolle.zhiyou.Tool.LruImageCache;
import com.apolle.zhiyou.Tool.PinyinUtils;
import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by huangtao on 2016/3/2222:01.
 * modify by huangtao on 22:01
 */
public class ConcactExpandListAdapter  extends BaseExpandableListAdapter{

    private Context mContext;
    private ArrayList<Person> persons;
    private ImageLoader imageLoader;
    private HashMap<String,ArrayList<Person>> groupPersons;
    private static String[] alphabet={"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#"};
    private ArrayList<String> letters;
    public ConcactExpandListAdapter(Context context,ArrayList<Person> persons){
        this.mContext=context;
        this.persons=persons;
        letters=new ArrayList<String>();
        groupPerson();
        sortMap();
        RequestQueue queue= Volley.newRequestQueue(context);
        imageLoader=new ImageLoader(queue,new LruImageCache());
    }

    /**
     * 给人分组
     */
    private void groupPerson(){
        groupPersons=new HashMap<String,ArrayList<Person>>();
        for (int i=0;i<persons.size();i++){
            Person person=persons.get(i);
            String username=person.getNickname()!=null?person.getNickname():person.getUsername();

            String pinYin=PinyinUtils.getPinYin(username);
            String firstPy=pinYin.substring(0,1).toUpperCase();
            System.out.println("第一个字母"+firstPy);
            ArrayList<Person> groups;
              if(firstPy.matches("[A-Z]")){
                  if(groupPersons.get(firstPy)!=null){
                    groups=groupPersons.get(firstPy);
                  }else{
                    groups=new ArrayList<Person>();
                  }
                  groups.add(person);
                  groupPersons.put(firstPy, groups);
                  if(letters.indexOf(firstPy)<0){
                    letters.add(firstPy);
                  }

              }else{
                  if(groupPersons.get("#")!=null){
                      groups=groupPersons.get("#");
                  }else{
                      groups=new ArrayList<Person>();
                  }
                  groups.add(person);
                  groupPersons.put("#", groups);
                  if(letters.indexOf("#")<0){
                      letters.add("#");
                  }
              }
        }
    }
    @Override
    public int getGroupCount() {
        return groupPersons.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        if(letters.get(groupPosition)!=null&&groupPersons.get(letters.get(groupPosition))!=null){
            return groupPersons.get(letters.get(groupPosition)).size();
        }
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        if(letters.get(groupPosition)!=null&&groupPersons.get(letters.get(groupPosition))!=null){
            return groupPersons.get(letters.get(groupPosition));
        }
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if(letters.get(groupPosition)!=null&&groupPersons.get(letters.get(groupPosition))!=null){
            ArrayList<Person> persons= groupPersons.get(letters.get(groupPosition));
            if(persons!=null){
                return  persons.get(childPosition);
            }
        }
        return null;
    }
    public int getPersonIndex(String key){
        if(groupPersons.size()>0){
            return  letters.indexOf(key);
        }
        return 0;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if(letters.get(groupPosition)!=null&&groupPersons.get(letters.get(groupPosition))!=null){
            if(convertView==null){
                convertView=LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1,null);
                AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 30);

                convertView.setLayoutParams(layoutParams);
                convertView.setBackgroundColor(Color.parseColor("#E6E6E6"));
            }
            TextView textView= (TextView) convertView.findViewById(android.R.id.text1);
            textView.setTextSize(14);
            textView.setText(letters.get(groupPosition));
            ((ExpandableListView)parent).expandGroup(groupPosition);
            return convertView;

        }
        return null;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ConcactViewHolder myViewHolder;
        if(letters.get(groupPosition)!=null&&groupPersons.get(letters.get(groupPosition))!=null){
            ArrayList<Person> persons=groupPersons.get(letters.get(groupPosition));
            if(convertView==null){
                convertView= LayoutInflater.from(mContext).inflate(R.layout.chat_concact,null);
                myViewHolder=new ConcactViewHolder();
                myViewHolder.headpic= (BootstrapCircleThumbnail) convertView.findViewById(R.id.headpic);
                myViewHolder.username= (com.rey.material.widget.TextView) convertView.findViewById(R.id.username);
                convertView.setTag(myViewHolder);
            }else{
                myViewHolder= (ConcactViewHolder) convertView.getTag();
            }
            Person person=persons.get(childPosition);
            ImageLoader.ImageListener listener=imageLoader.getImageListener(myViewHolder.headpic,R.drawable.user_headpic,R.drawable.user_error);
            imageLoader.get(person.getHeadpic(),listener);
            String username=person.getNickname()!=null?person.getNickname():person.getUsername();
            myViewHolder.username.setText(username);
            return convertView;
         }
        return null;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
    }


    private class ConcactViewHolder{
        public BootstrapCircleThumbnail headpic;
        public com.rey.material.widget.TextView username;
    }
    public void sortMap(){
        Collections.sort(letters, new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
               return  lhs.compareTo(rhs);

            }
        });
    }

}

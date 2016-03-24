package com.apolle.zhiyou.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.apolle.zhiyou.R;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.rey.material.widget.CheckBox;
import com.unnamed.b.atv.model.TreeNode;

/**
 * Created by huangtao on 2016/3/1622:49.
 * modify by huangtao on 22:49
 */
public class NotePadTreeView extends  TreeNode.BaseNodeViewHolder<NotePadTreeView.PadIconTreeItem> {

    public NotePadTreeView(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode node, PadIconTreeItem value) {
        View view=LayoutInflater.from(context).inflate(R.layout.simple_profile_node,null,false);
        CheckBox checkBox= (CheckBox) view.findViewById(R.id.checkNode);
        TextView nodeValue= (TextView) view.findViewById(R.id.node_value);
        final Bundle bundle=new Bundle();
        bundle.putInt("nid",value.id);
        checkBox.setTag(bundle);
        nodeValue.setText(value.title);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(onCheckboxCheckListener!=null){
                    onCheckboxCheckListener.onCheck(buttonView,isChecked);
                }
            }
        });
        return view;
    }


    public static class PadIconTreeItem{
            int id;
            String title;

        public PadIconTreeItem() {
        }

        public PadIconTreeItem(int id,String title) {
            this.id=id;
            this.title = title;
        }
    }
    public static  PadIconTreeItem getTreeItem(){
        return new PadIconTreeItem();
    }
    public OnCheckboxCheckListener onCheckboxCheckListener;
   public interface  OnCheckboxCheckListener{
        void onCheck(CompoundButton buttonView, boolean isChecked);
    }

    public void setOnCheckboxCheckListener(OnCheckboxCheckListener onCheckboxCheckListener) {
        this.onCheckboxCheckListener = onCheckboxCheckListener;
    }
}

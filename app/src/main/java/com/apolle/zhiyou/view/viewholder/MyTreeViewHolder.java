package com.apolle.zhiyou.view.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.apolle.zhiyou.R;
import com.rey.material.widget.CheckedImageView;
import com.unnamed.b.atv.model.TreeNode;

/**
 * Created by huangtao on 2016/3/316:43.
 * modify by huangtao on 16:43
 */
 public class  MyTreeViewHolder extends TreeNode.BaseNodeViewHolder<MyTreeViewHolder.IconTreeItem> {
        private Context context;
        private LayoutInflater mInflator;

        public MyTreeViewHolder(Context context) {
            super(context);
            this.context = context;
            mInflator = LayoutInflater.from(context);
        }

        @Override

        public View createNodeView(TreeNode node, IconTreeItem value) {
            View view = mInflator.inflate(R.layout.simple_profile_node, null, false);
            TextView textView=(TextView) view.findViewById(R.id.node_value);
            CheckedImageView icon=(CheckedImageView) view.findViewById(R.id.checkNode);
            textView.setText(value.text);
            return view;
        }

        public static class IconTreeItem {
            public int icon;
            public String text;
            public   IconTreeItem(int mIcon,String mText){
                icon=mIcon;
                text=mText;
            }
        }
    }


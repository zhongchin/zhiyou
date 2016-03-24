package com.apolle.zhiyou.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.apolle.zhiyou.Model.Note;
import com.apolle.zhiyou.Model.NotePad;
import com.apolle.zhiyou.R;
import com.rey.material.widget.CheckBox;
import com.unnamed.b.atv.model.TreeNode;

import java.util.ArrayList;


/**
 * Created by huangtao on 2016/3/1216:14.
 * modify by huangtao on 16:14
 */
public class NodeView {
    private static final int TYPE_NODE = 0;
    private static final int TYPE_PAD = 1;
    private Context context;
    public NodeView(Context context){
        this.context=context;
    }

    /**
     * render pad
     * @param parentNode
     * @param notePads
     * @return
     */
    public TreeNode renderPad(TreeNode parentNode, final ArrayList<NotePad> notePads){
        ArrayList<TreeNode> noteTrees=new ArrayList<TreeNode>();
      for (int i=0;i<notePads.size();i++){
          NotePad notePad=notePads.get(i);
          IconTreeItem iconTree=new IconTreeItem(notePad.getNtitle(),TYPE_PAD);
          TreeNode node=new TreeNode(iconTree).setViewHolder(new NodePadItem(context));
            final int position=i;
           node.setClickListener(new TreeNode.TreeNodeClickListener() {//为每个节点添加点击事件
              @Override
              public void onClick(TreeNode node, Object value) {
                  if(onClickListener!=null){
                      onClickListener.onNotePadClick(node, value,notePads,position);
                  }
              }
          });
          noteTrees.add(node);
      }
        parentNode.addChildren(noteTrees);
        return parentNode;
    }

    /**
     * render笔记本
     * @param parentNode
     * @param notes
     * @return
     */
    public TreeNode renderNote(TreeNode parentNode, final ArrayList<Note> notes){
        ArrayList<TreeNode> noteTrees=new ArrayList<TreeNode>();
        for (int i=0;i<notes.size();i++){
            Note note=notes.get(i);
            final int position=i;
            IconTreeItem iconTree=new IconTreeItem(note.getSubject(),TYPE_NODE);
            TreeNode node=new TreeNode(iconTree).setViewHolder(new NodePadItem(context));
            node.setClickListener(new TreeNode.TreeNodeClickListener() {//为每个节点添加点击事件
                @Override
                public void onClick(TreeNode node, Object value) {
                    if(onClickListener!=null){
                        onClickListener.onNoteClick(node, value,notes,position);
                    }
                }
            });
            noteTrees.add(node);
        }
        parentNode.addChildren(noteTrees);
        return parentNode;
    }

    /**
     * 绑定click事件
     * @param node
     */
    private void onBindClickListener(TreeNode node){

    }
    public class NodePadItem extends TreeNode.BaseNodeViewHolder<IconTreeItem>{

        public NodePadItem(Context context) {
            super(context);
        }

        @Override
        public View createNodeView(TreeNode node, IconTreeItem value) {
            View view=LayoutInflater.from(context).inflate(R.layout.simple_profile_node,null,false);
            CheckBox checkBox= (CheckBox) view.findViewById(R.id.checkNode);
            checkBox.setVisibility(View.GONE);
             ImageView imageView = (ImageView) view.findViewById(R.id.nodeType);
            switch (value.type){
                case TYPE_PAD:
                    imageView.setImageResource(R.drawable.ic_notepad_deep);
                    break;
                case TYPE_NODE:
                    imageView.setImageResource(R.drawable.ic_note_deep);
                    break;
            }
            TextView nodeValue= (TextView) view.findViewById(R.id.node_value);
            nodeValue.setText(value.text);
            return view;
        }

    }

    /**
     * 每个条目
     */
    public class IconTreeItem{
        public  int icon;
        public String text;
        public  int  type;

        public IconTreeItem() {
            this(null,0);
        }

        public IconTreeItem(String text, int type) {
            this(0,text,type);
        }

        public IconTreeItem(int icon, String text, int type) {
            this.icon = icon;
            this.text = text;
            this.type=type;
        }
    }
    public interface  onNoteClickCallback{
        void onNoteClick(TreeNode node, Object value, ArrayList<Note> notes, int i);
    }
    public interface  onNotePadClickCallback{
        void onNotePadClick(TreeNode node, Object value, ArrayList<NotePad> notePads, int i);
    }
    public onItemClickListener onClickListener;

    public void setOnClickListener(onItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface  onItemClickListener extends onNoteClickCallback,onNotePadClickCallback{

    }
}



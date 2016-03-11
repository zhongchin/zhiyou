package com.apolle.zhiyou.activity;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import com.apolle.zhiyou.Model.NotePad;
import com.apolle.zhiyou.R;
import com.apolle.zhiyou.view.viewholder.MyTreeViewHolder;
import com.lidroid.xutils.view.annotation.ViewInject;

import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;
import com.unnamed.b.atv.view.TreeNodeWrapperView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


public class CollectActivity extends AppCompatActivity {

    private TreeNode root;
    private  AndroidTreeView treeView;

    private LinearLayout nodeTree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_collect);
        View parent=LayoutInflater.from(this).inflate(R.layout.activity_collect,null);
        initTreeView();
        if(null!=root){
            nodeTree=(LinearLayout)parent.findViewById(R.id.nodeTree);
            treeView=new AndroidTreeView(CollectActivity.this,root);
            treeView.setDefaultAnimation(true);
            ViewGroup viewParent =(ViewGroup)treeView.getView();
            viewParent.removeAllViews();
            //nodeTree.addView(treeView.getView());
            nodeTree.addView(viewParent);

            setContentView(nodeTree);

        }
        if(savedInstanceState!=null){
              String state=savedInstanceState.getString("treeState");
            if(!TextUtils.isEmpty(state)){
                treeView.restoreState(state);
            }
        }
    }

    private  void initTreeView(){

        ArrayList<NotePad> content=new ArrayList<NotePad>();
           NotePad notePad1=new NotePad();
          notePad1.setNid(1);
          notePad1.setNtitle("科技");
          notePad1.setPid(0);
          notePad1.setLevel(1);
          notePad1.setUid(2);
          notePad1.setChilds(null);
          NotePad notepad2=notePad1;
            notePad1.setNid(2);
            notePad1.setNtitle("设计");
          content.add(notePad1);
          content.add(notepad2);

        root=TreeNode.root();
        System.out.println("huangtao"+root+"==="+content);
        renderTree( content,root);

/*        HashMap<String,String> params=new HashMap<String,String>();
        NotePadAction.allNotepads(CollectActivity.this, params, new NotePadAction.RenderCallback() {
            @Override
            public void renderOnSuccess(ArrayList<? extends Serializable> content) {

                root=TreeNode.root();
                renderTree((ArrayList<NotePad>) content,root);

            }

            @Override
            public void renderOnFailed(VolleyError error) {

            }
        });*/
    }

 /*   @Override
    public int getLayoutId() {
        return R.layout.activity_collect;
    }*/


    public void renderTree(ArrayList<NotePad> content,TreeNode root){

        for (NotePad notePad:content){
            MyTreeViewHolder.IconTreeItem iconTreeItem=new MyTreeViewHolder.IconTreeItem(R.drawable.ic_radio_selected,notePad.getNtitle().toString());

            TreeNode treeNode=new TreeNode(iconTreeItem).setViewHolder(new MyTreeViewHolder(CollectActivity.this));
        /*    ArrayList<NotePad>  childPads=(ArrayList<NotePad>) notePad.getChilds();

             if(null!=childPads){
                  renderTree((ArrayList<NotePad>) notePad.getChilds(),treeNode);
             }*/
            root.addChild(treeNode);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString("treeState",treeView.getSaveState());
    }
}

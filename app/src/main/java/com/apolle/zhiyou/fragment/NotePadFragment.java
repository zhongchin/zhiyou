package com.apolle.zhiyou.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import com.android.volley.VolleyError;
import com.apolle.zhiyou.Http.NotePadAction;
import com.apolle.zhiyou.Model.NotePad;
import com.apolle.zhiyou.R;
import com.apolle.zhiyou.activity.MainActivity;
import com.apolle.zhiyou.interactor.NetUrl;
import com.apolle.zhiyou.ui.NodeView;
import com.apolle.zhiyou.ui.NotePadTreeView;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import java.io.Serializable;
import java.util.ArrayList;

public class NotePadFragment extends BaseFragment implements NotePadTreeView.OnCheckboxCheckListener {

    private View rootView;
    private RelativeLayout treeContainer;
    private TreeNode rootTree;
    private AndroidTreeView treeView;

    public NotePadFragment() {

    }
public static NotePadFragment newInstance() {
        NotePadFragment fragment = new NotePadFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         rootView=inflater.inflate(R.layout.fragment_note_pad, container, false);
         treeContainer= (RelativeLayout) rootView.findViewById(R.id.treeNode);
        return rootView;

    }
    private void initTreeView(){
        treeView=new AndroidTreeView(getActivity(),rootTree);
        treeView.setDefaultAnimation(true);
        treeView.setUse2dScroll(true);
        treeView.setDefaultContainerStyle(com.unnamed.b.atv.R.style.TreeNodeStyle);
        treeContainer.addView(treeView.getView());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        rootTree=TreeNode.root();
        initNotePad();
        super.onActivityCreated(savedInstanceState);
    }

    private void initNotePad(){

        NotePadAction.allNotepads(getActivity(), NetUrl.initParams(), new NotePadAction.RenderCallback() {
            @Override
            public void renderOnSuccess(ArrayList<? extends Serializable> content) {
                renderTreeNode((ArrayList<NotePad>) content,rootTree);
                initTreeView();
            }
            @Override
            public void renderOnFailed(VolleyError error) {

            }
        });
    }
    private void renderTreeNode(ArrayList<NotePad> notePads,TreeNode parentNode){

            for (NotePad notePad:notePads){
                NotePadTreeView.PadIconTreeItem iconTreeItem=new NotePadTreeView.PadIconTreeItem(notePad.getNid(),notePad.getNtitle());
                NotePadTreeView notePadTreeView=new NotePadTreeView(getActivity());
                notePadTreeView.setOnCheckboxCheckListener(this);
                TreeNode childNode=new TreeNode(iconTreeItem).setViewHolder(notePadTreeView);
                parentNode.addChild(childNode);
                if(notePad.getChilds()!=null&&notePad.getChilds().size()>0){
                    renderTreeNode((ArrayList<NotePad>) notePad.getChilds(),childNode);
                }
            }
    }
     private int checkedId=0;
    @Override
    public void onCheck(CompoundButton buttonView, boolean isChecked) {
         checkedId=0;
        if(isChecked){
            Bundle bundle= (Bundle) buttonView.getTag();
            int nid=bundle.getInt("nid");
            checkedId=nid;
        }
        if(checkBoxCheckedListener!=null){
            checkBoxCheckedListener.checkedCode(checkedId);
        }
    }
   public CheckBoxCheckedListener checkBoxCheckedListener;
    public interface  CheckBoxCheckedListener{
        void   checkedCode(int checkCode);
    }

    public void setCheckBoxCheckedListener(CheckBoxCheckedListener checkBoxCheckedListener) {
        this.checkBoxCheckedListener = checkBoxCheckedListener;
    }
}

package com.apolle.zhiyou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.FrameLayout;

import com.apolle.zhiyou.Http.NoteAction;
import com.apolle.zhiyou.Http.NotePadAction;
import com.apolle.zhiyou.Model.Article;
import com.apolle.zhiyou.R;
import com.apolle.zhiyou.activity.node.AddNoteActivity;
import com.apolle.zhiyou.fragment.NotePadFragment;
import com.apolle.zhiyou.interactor.NetUrl;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.rey.material.app.Dialog;
import com.rey.material.widget.TextView;

import java.util.HashMap;


public class SelectNodeActivity extends BaseActivity {

    @ViewInject(R.id.nodeTree)
    private FrameLayout nodeTree;
    @ViewInject(R.id.datermine)
    AppCompatButton datermine;
    private int checkId;
    private int  operator;
    private Bundle globalArg;

    @ViewInject(R.id.zy_node_title)
    private TextView zy_node_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        Intent intent=getIntent();
        globalArg=intent.getExtras();
         operator=globalArg.getInt("operator");
        switch (operator){
            case 1:
            case 4:
                  zy_node_title.setText(R.string.nodeSelect);
                break;
            case 2:
            case 3:
                zy_node_title.setText(R.string.saveToNotepad);
                break;
        }
         initFragment();
    }


    private  void initFragment(){
           FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
           NotePadFragment notePadFragment=NotePadFragment.newInstance();
            notePadFragment.setCheckBoxCheckedListener(new NotePadFragment.CheckBoxCheckedListener() {
                @Override
                public void checkedCode(int checkCode) {
                       checkId=checkCode;
                }
            });
            ft.replace(R.id.nodeTree,notePadFragment ,null).commit();
    }

    @Override
    public AppCompatActivity getActivity() {
        return SelectNodeActivity.this;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_node;
    }

    @OnClick(R.id.datermine)
    public void ViewOnclick(View view){
        switch (view.getId()){
            case R.id.datermine:
                    if(checkId<=0){
                        toast("请选择一个笔记本");
                        return ;
                    }
                     doOperator();
                break;
        }

    }
    public void  doOperator(){
        switch (operator){
            case 1://收藏
                doCollectNote();
                break;
            case 2://保存笔记本
                doSaveNotePad();
                break;
            case 3://保存笔记
                doSaveNote();
                break;
            case 4://转发笔记
                doForward();
                break;
        }
    }

    /**
     * 收藏笔记
     */
    public void doCollectNote(){
        Article article= (Article) globalArg.getSerializable("article");
         String tid=article.getTid();

        HashMap<String,String> params=NetUrl.initParams();
        params.put("tid",tid);
        params.put("npid",String.valueOf(checkId));
        NoteAction.CollectNote(SelectNodeActivity.this, params, new NoteAction.NoteOperatorCallback() {
            @Override
            public void onSuccess(String s) {
                toast("笔记成功收藏到你的笔记");
            }

            @Override
            public void onFail(int code, String error) {
                toast(error);
            }
        });
    }

    /**
     * 保存笔记本
     */
    public void doSaveNotePad(){
        Bundle bundle=new Bundle();
        bundle.putInt("npid",checkId);
        goActivity(AddNoteActivity.class,bundle);
    }

    /**
     * 保存笔记
     */
    private String notepadName;
    public void doSaveNote(){
         Dialog.Builder builder=new Dialog.Builder();
            builder.title("请输入你的笔记本名");
            builder.build(getActivity()).cancelable(false).setContentView(R.layout.layout_add_notepad);
            final Dialog dialog=builder.getDialog();
            dialog.show();
            AppCompatEditText editText= (AppCompatEditText) dialog.findViewById(R.id.pad_name);
            notepadName=editText.getText().toString();
            dialog.findViewById(R.id.datermine).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     dialog.dismiss();
                      HashMap<String,String> params= NetUrl.initParams();
                      params.put("npid",String.valueOf(checkId));
                      params.put("name",notepadName);
                    NotePadAction.addNotePad(getActivity(), params, new NotePadAction.StringReponse() {
                        @Override
                        public void onSuccess(String message) {
                            toast("添加成功");
                        }

                        @Override
                        public void onFail(int code, String error) {
                            toast(error);
                        }
                    });
                }
            });
          dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  dialog.dismiss();
              }
          });
    }

    /**
     * 转发
     */
    public void doForward(){
       Article article= (Article) globalArg.getSerializable("article");
        String tid=article.getTid();
        HashMap<String,String> params=NetUrl.initParams();
        params.put("tid",tid);
        params.put("npid",String.valueOf(checkId));
         NoteAction.CollectAndForwardNote(SelectNodeActivity.this, params, new NoteAction.NoteOperatorCallback() {
             @Override
             public void onSuccess(String s) {
                 toast("收藏并转发成功");
             }

             @Override
             public void onFail(int code, String s) {
                toast(s);
             }
         });

    }
}

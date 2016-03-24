package com.apolle.zhiyou.activity.node;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.apolle.zhiyou.Model.Note;
import com.apolle.zhiyou.R;
import com.apolle.zhiyou.activity.BaseActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.rey.material.widget.TextView;

import org.w3c.dom.Node;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ReadNoteActivity extends BaseActivity implements View.OnClickListener{

    @ViewInject(R.id.toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.ll_share)
    LinearLayout ll_share;
    @ViewInject(R.id.note_title)
    TextView note_title;
    @ViewInject(R.id.note_content)
    WebView note_content;
    @ViewInject(R.id.ll_collection)
    LinearLayout ll_collection;
    @ViewInject(R.id.ll_edit)
    LinearLayout ll_edit;
    @ViewInject(R.id.ll_more)
    LinearLayout ll_more;

   private Bundle args;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ViewUtils.inject(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.inflateMenu(R.menu.menu_read_note);
        Intent intent=getIntent();
        args=intent.getExtras();
        initView();
        initBindClick();
    }
    private void initView(){
        Note note= (Note) args.get("note");
        note_title.setText(note.getSubject());

        note_content.getSettings().setDefaultTextEncodingName("UTF-8");
        note_content.loadData(note.getContent(),"text/html; charset=UTF-8",null);
    }
    private void initBindClick(){
        ll_share.setOnClickListener(this);
        ll_edit.setOnClickListener(this);
        ll_more.setOnClickListener(this);
    }

    @Override
    public AppCompatActivity getActivity() {
        return ReadNoteActivity.this;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_read_note;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_share:

                break;
            case R.id.ll_edit:

                break;
            case R.id.ll_more:

                break;
        }
    }
    private void initShare(){

    }
}

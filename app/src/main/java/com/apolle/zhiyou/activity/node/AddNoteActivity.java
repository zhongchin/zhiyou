package com.apolle.zhiyou.activity.node;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.apolle.zhiyou.R;
import com.apolle.zhiyou.activity.BaseActivity;
import com.lidroid.xutils.ViewUtils;

public class AddNoteActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
    }

    @Override
    public AppCompatActivity getActivity() {
        return AddNoteActivity.this;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_note;
    }
}

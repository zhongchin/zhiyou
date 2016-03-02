package com.apolle.zhiyou.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import com.lidroid.xutils.ViewUtils;


public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
           setContentView(getLayoutId());
           ViewUtils.inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public abstract int getLayoutId();


}

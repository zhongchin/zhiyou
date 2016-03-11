package com.apolle.zhiyou.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


import com.lidroid.xutils.ViewUtils;


public abstract class BaseActivity extends AppCompatActivity {
    private long firstTime=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
           setContentView(getLayoutId());
    }

    public abstract  AppCompatActivity getActivity();
    @Override
    protected void onResume() {
        super.onResume();
    }

    public abstract int getLayoutId();

    @Override
    public void onBackPressed() {
        long secondTime= System.currentTimeMillis();
        //如果两次按键时间大于1000毫秒，则不退出
        if(secondTime-firstTime>1000){
            Toast.makeText(BaseActivity.this,"再按一次退出应用",Toast.LENGTH_SHORT).show();
            firstTime=secondTime;
        }else{
            finish();
        }
    }


}

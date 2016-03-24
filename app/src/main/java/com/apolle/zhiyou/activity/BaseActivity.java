package com.apolle.zhiyou.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


import com.lidroid.xutils.ViewUtils;

import java.security.KeyStore;


public abstract class BaseActivity extends AppCompatActivity {

    public ProgressDialog progressDialog;
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

    public void toast(String topic,int time){
         Toast.makeText(getApplicationContext(),topic,time).show();
    }

    public void toast(String topic){
        Toast.makeText(getApplicationContext(),topic,Toast.LENGTH_SHORT).show();
    }


    public ProgressDialog showDialogProgress(String title,String body){
      return   ProgressDialog.show(getActivity(),title,body,true,false);
    }
    public void test(Object tag){
        System.out.println("huangtao "+tag);
    }

    public void goActivity(Class<?> cls){
        Intent intent=new Intent(getActivity(),cls);
        startActivity(intent);
        finish();
    }
    public void goActivity(Class<?> cls,Bundle bundle){
            Intent intent=new Intent(getActivity(),cls);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
    }

}

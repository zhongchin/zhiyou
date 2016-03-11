package com.apolle.zhiyou.activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.apolle.zhiyou.R;
import com.lidroid.xutils.ViewUtils;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

    }

//    @Override
    public AppCompatActivity getActivity() {
        return LoginActivity.this;
    }

//    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }
}


package com.apolle.zhiyou.activity;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.apolle.zhiyou.Http.UserLogin;
import com.apolle.zhiyou.Model.User;
import com.apolle.zhiyou.R;
import com.apolle.zhiyou.Tool.SharedPreTool;
import com.apolle.zhiyou.interactor.NetUrl;
import com.beardedhen.androidbootstrap.BootstrapWell;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnFocusChange;
import com.rey.material.widget.Button;
import com.rey.material.widget.TextView;

import java.util.HashMap;

public class LoginActivity extends BaseActivity {

    @ViewInject(R.id.login_username)
    AppCompatEditText loginUsername;
    @ViewInject(R.id.login_pwd)
    AppCompatEditText loginPwd;
    @ViewInject(R.id.loginBtn)
    Button  loginBtn;
  /*  @ViewInject(R.id.forget)
    TextView  forget;*/
    @ViewInject(R.id.register)
    TextView  register;
    @ViewInject(R.id.login_topic)
    TextView login_topic;
    @ViewInject(R.id.go_back)
    ImageView go_back;

    private String username;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        ViewUtils.inject(this);
        initView();

    }

    private void initView(){
//        forget.setText(Html.fromHtml("忘记密码了? <a href=''>找回密码</a>"));
        register.setText(Html.fromHtml("还没有智友账户? <a href=''>注册</a>"));
    }
//    @Override
    public AppCompatActivity getActivity() {
        return LoginActivity.this;
    }

//    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @OnFocusChange({R.id.login_username,R.id.login_pwd,R.id.go_back})
     public void focusChangeBindEvent(View view, boolean hasFocus){
           switch (view.getId()){
               case  R.id.login_username:
               case R.id.login_pwd:
                   if(view.isFocused()){
                       login_topic.setVisibility(View.GONE);
                       view.setPadding(10,0,0,0);
                   }
                break;
               case R.id.go_back:
                    goActivity(MainActivity.class);
                   break;
           }
    }

//    @OnClick({R.id.forget,R.id.register,R.id.loginBtn})
    @OnClick({R.id.register,R.id.loginBtn})
   public void ViewOnClick(View view){
       switch (view.getId()){
           case R.id.register:
                    goActivity(RegisterActivity.class);
               break;
        /*   case R.id.forget:
               goActivity(ResetPwdActivity.class);
               break;*/
           case R.id.loginBtn:
               doLogin();
               break;
       }
    }
       private boolean checkLogin(){
            username=loginUsername.getText().toString();
            password=loginPwd.getText().toString();
           if(username.length()<5){
              login_topic.setText("用户名不能少于六位");
               login_topic.setVisibility(View.VISIBLE);
               return false;
           }
           if(password.length()<6){
               login_topic.setText("密码不能少于六位");
               login_topic.setVisibility(View.VISIBLE);
               return false;
           }
           if(password.length()>16){
               login_topic.setText("密码不能多于16位");
               login_topic.setVisibility(View.VISIBLE);
               return false;
           }
           return true;
       }
    /**
     * 处理登录
     */
    private void doLogin(){
        if(!checkLogin()){//联网前线检查用户输入是否正确
            return ;
        }
        HashMap<String,String> params= NetUrl.initParams();
        params.put("username",username);
        params.put("password",User.EncryptPwd(password));
        test(params);
        progressDialog=showDialogProgress("登陆","正在加载数据,请等待");
        UserLogin.doLogin(LoginActivity.this, params, new UserLogin.requestOnCallback() {
            @Override
            public void onSuccess(User user) {
                progressDialog.dismiss();
                toast("登录成功",Toast.LENGTH_SHORT);
                SharedPreTool.saveUserLogin(LoginActivity.this,user);
                goActivity(MainActivity.class);
            }

            @Override
            public void onFail(int code, String error) {
                  progressDialog.dismiss();
                  login_topic.setText(error);
                  login_topic.setVisibility(View.VISIBLE);
            }
        });
    }

}


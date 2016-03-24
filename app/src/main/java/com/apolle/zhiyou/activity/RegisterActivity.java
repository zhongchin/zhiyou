package com.apolle.zhiyou.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.apolle.zhiyou.Http.SmsServer;
import com.apolle.zhiyou.Http.UserRegister;
import com.apolle.zhiyou.Model.User;
import com.apolle.zhiyou.R;
import com.apolle.zhiyou.Tool.PatternTool;
import com.apolle.zhiyou.interactor.NetUrl;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.rey.material.app.ToolbarManager;
import com.rey.material.widget.Button;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends BaseActivity {

  @ViewInject(R.id.email_register)
    TextView email_register;
    @ViewInject(R.id.mobile_register)
    TextView mobile_register;
    @ViewInject(R.id.email_register_ll)
    TextInputLayout email_register_ll;
    @ViewInject(R.id.mobile_register_ll)
    LinearLayout mobile_register_ll;
    @ViewInject(R.id.username)
    AppCompatEditText username;
    @ViewInject(R.id.email)
    AppCompatEditText email;
    @ViewInject(R.id.mobile)
    AppCompatEditText mobile;
    @ViewInject(R.id.verifyCode)
    AppCompatEditText verifyCode;
    @ViewInject(R.id.sendCode)
    Button sendCode;
    @ViewInject(R.id.userPwd)
    AppCompatEditText userPwd;
    @ViewInject(R.id.confirmPwd)
    AppCompatEditText confirmPwd;
    @ViewInject(R.id.registerBtn)
    AppCompatEditText registerBtn;
    @ViewInject(R.id.go_login)
    TextView go_login;

    private boolean mobileRegister=true;
    private String tmpSmsCode="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        registerSmsReceiver();
    }

    private void registerSmsReceiver(){
        IntentFilter filter=new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        filter.setPriority(1000);
        registerReceiver(SmsReceiver,filter);
    }
    @Override
    public AppCompatActivity getActivity() {
        return RegisterActivity.this;
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }
    @OnClick({R.id.email_register,R.id.mobile_register,R.id.sendCode,R.id.registerBtn,R.id.go_login})
    public void ViewOnclick(View view){
        switch (view.getId()){
            case R.id.email_register:
                    email_register.setBackgroundResource(R.drawable.ic_triangle_top);
                    mobile_register.setBackgroundResource(R.drawable.ic_triangle_top_light);
                    email_register_ll.setVisibility(View.VISIBLE);
                    mobile_register_ll.setVisibility(View.GONE);
                    mobileRegister=false;
                break;
            case R.id.mobile_register:
                    email_register.setBackgroundResource(R.drawable.ic_triangle_top_light);
                    mobile_register.setBackgroundResource(R.drawable.ic_triangle_top);
                    email_register_ll.setVisibility(View.GONE);
                    mobile_register_ll.setVisibility(View.VISIBLE);
                mobileRegister=true;
                break;
            case R.id.sendCode://发送手机验证码
                doSendCode();
                break;
            case R.id.registerBtn://点击注册按钮
                doRegister();
                break;
            case R.id.go_login:
                goActivity(LoginActivity.class);
                break;

        }
    }
    private boolean checkMobile(){
        String mobileStr=mobile.getText().toString();
        if(!PatternTool.isMobile(mobileStr)){
             toast("手机号码不正确", Toast.LENGTH_LONG);
            return false;
        }
        return true;
    }


    private void doSendCode(){
        if(!checkMobile()) return;
        SmsServer.sendSms(RegisterActivity.this, new SmsServer.OnSendCodeSuccessEvent() {
            @Override
            public void doComplete(String code) {
                tmpSmsCode=code;
                doSendCodeToServer();
            }
        });

    }



    /**
     * 验证码倒计时
     */
    private int countTimer=60;
    private  Timer timer;
    private TimerTask timeTask;

  private String SENDCODE_TIP="秒之后重新发送";
   private Handler handler=new Handler(){
       @Override
       public void handleMessage(Message msg) {
           switch (msg.what){
               case 1:
                   String timerStr=String.valueOf(countTimer);
                   sendCode.setClickable(false);
                   sendCode.setText(timerStr+"秒之后重新发送");
                   if(countTimer<0){
                       timer.cancel();
                       sendCode.setClickable(true);
                       sendCode.setText("重新发送");
                   }
                   break;
           }
       }
   };
    /**
     * 将验证码发送给服务器
     */
    private void doSendCodeToServer(){
        HashMap<String,String> params=NetUrl.initParams();
         params.put("code",tmpSmsCode);
        String mobileStr=mobile.getText().toString();
        params.put("phone",mobileStr);
        SmsServer.SendCode(RegisterActivity.this, params, new SmsServer.ResponseStatusCallback() {
            @Override
            public void onSuccess(String message) {
                toast("验证码发送成功");
                timeTask=new TimerTask() {
                    @Override
                    public void run() {
                        countTimer--;
                        Message message=new Message();
                        message.what=1;
                        handler.sendMessage(message);
                    }
                };
                if(countTimer<=0){
                    countTimer=60;
                }
                timer=new Timer();
                timer.schedule(timeTask,1000,1000);
            }

            @Override
            public void onFail(int code, String error) {
                toast(error);
            }
        });
    }

    private BroadcastReceiver SmsReceiver=new  BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle=intent.getExtras();
            if(bundle!=null){
                String body=bundle.getString("pdus");
                Pattern pattern=Pattern.compile("[0-9]{6}");
                Matcher m=pattern.matcher(body);
                if(m.find()){
                    System.out.println("广播接收"+m.group());
                    verifyCode.setText(m.group().toString());
                }

            }
        }
    };

    private void doRegister(){
         String userName= username.getText().toString();
        String phone="";
        String userEmail="";
        if(mobileRegister){
             phone=mobile.getText().toString();
        }else{
             userEmail=email.getText().toString();
        }
         String Vfcode=verifyCode.getText().toString();
         String pwd=userPwd.getText().toString();
         String cfPwd=confirmPwd.getText().toString();

         if(mobileRegister){
             if(!PatternTool.isMobile(phone)||phone.length()<1){
                toast("请填写正确手机号码");
                 return ;
             }
         }else{
             if(!PatternTool.isEmail(userEmail)){
                 toast("请填写正确邮箱账户");
                 return ;
             }
         }
        if(!pwd.equals(cfPwd)){
            toast("密码和确认密码不相同");
            return ;
        }
        HashMap<String,String> params=NetUrl.initParams();
        params.put("username",userName);
        params.put("password", User.EncryptPwd(pwd));
        params.put("vc",Vfcode);
        params.put("email",userEmail);
        params.put("phone",phone);
        params.put("type",mobileRegister?"1":"0");
        progressDialog=showDialogProgress("正在注册","正在注册,请等待");
        UserRegister.doRegister(RegisterActivity.this, params, new UserRegister.RegisterCallBack() {
            @Override
            public void onSuccess(String message) {
                    progressDialog.dismiss();
                    goActivity(LoginActivity.class);

            }
            @Override
            public void onFail(int code, String error) {
                   progressDialog.dismiss();
                  toast(error);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(SmsReceiver!=null){
            unregisterReceiver(SmsReceiver);
            SmsReceiver=null;
        }
    }
}

package com.apolle.zhiyou.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apolle.zhiyou.Http.SmsServer;
import com.apolle.zhiyou.Http.UserResetPwd;
import com.apolle.zhiyou.R;
import com.apolle.zhiyou.Tool.PatternTool;
import com.apolle.zhiyou.fragment.BaseFragment;
import com.apolle.zhiyou.interactor.NetUrl;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class ResetPwdActivity extends BaseActivity implements View.OnClickListener {

    private static boolean useMobile=true;
    private static String mEmail="";
    private static String mPhone="";
    private int setup=1;

    @ViewInject(R.id.go_login)
    TextView go_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        go_login.setOnClickListener(this);
    }
    public   void replaceFragment(BaseFragment fragment){
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fl_reset,fragment,fragment.getTag());
        ft.commit();

    }
    private void  initView(){
        FirstFragment firstFragment=FirstFragment.newInstance();
            firstFragment.setOnNextListener(new FirstFragment.OnNextListener() {
                @Override
                public void changeByNext() {
                      if(useMobile){
                          SecondFragment secondFragment= SecondFragment.newInstance();
                          replaceFragment(secondFragment);
                      }else{
                          verifyEamilSetup();
                      }
                    setup=1;
                }
            });
         replaceFragment(firstFragment);
    }

    @Override
    public AppCompatActivity getActivity() {
        return ResetPwdActivity.this;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_reset_pwd;
    }

    /**
     * 校验邮箱
     */
    private void verifyEamilSetup(){
        EmailFragment emailFragment=EmailFragment.newInstance();
        emailFragment.setOnNextListener(new EmailFragment.OnNextListener() {
            @Override
            public void changeByNext() {
                    setNewPwd();
            }
        });
           setup=2;
        replaceFragment(emailFragment);
    }

    /**
     *
     * @param v
     */
    private void setNewPwd(){
        SecondFragment secondFragment= SecondFragment.newInstance();
        secondFragment.setOnNextListener(new SecondFragment.OnNextListener() {
            @Override
            public void changeByNext() {

            }
        });
        setup=3;
        replaceFragment(secondFragment);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.go_login:
                  if(setup==1){
                      initView();
                  }
                if(setup==2){

                }
                break;
        }
    }


    /**
     * 第一次显示找回密码界面
     */
    public static class FirstFragment extends BaseFragment implements View.OnClickListener {
        private static View firstRootview;
        private TextView email_reset;
        private TextView mobile_reset;
        private LinearLayout mobile_reset_ll;
        private LinearLayout email_reset_ll;
        private  Button sendCode;
        private AppCompatEditText mobile;
        private AppCompatEditText email;
        private AppCompatEditText vcode;
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
              firstRootview=inflater.inflate(R.layout.reset_first,container,false);
             email_reset= (TextView) firstRootview.findViewById(R.id.email_reset);
             mobile_reset= (TextView) firstRootview.findViewById(R.id.mobile_reset);
             mobile_reset_ll= (LinearLayout) firstRootview.findViewById(R.id.mobile_register_ll);
             email_reset_ll= (LinearLayout) firstRootview.findViewById(R.id.email_reset_ll);
             Button nextBtn= (Button) firstRootview.findViewById(R.id.nextBtn);
             mobile= (AppCompatEditText) firstRootview.findViewById(R.id.mobile);
             email=(AppCompatEditText)firstRootview.findViewById(R.id.email);
            vcode= (AppCompatEditText) firstRootview.findViewById(R.id.verifyCode);
             sendCode= (Button) firstRootview.findViewById(R.id.sendCode);


            nextBtn.setOnClickListener(this);
            email_reset.setOnClickListener(this);
            mobile_reset.setOnClickListener(this);
            sendCode.setOnClickListener(this);
            return firstRootview;
        }

        public static FirstFragment newInstance() {
            Bundle args = new Bundle();
            FirstFragment fragment = new FirstFragment();
            fragment.setArguments(args);
             return fragment;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.email_reset:
                    email_reset.setBackgroundResource(R.drawable.ic_triangle_top);
                    mobile_reset.setBackgroundResource(R.drawable.ic_triangle_top_light);
                    email_reset_ll.setVisibility(View.VISIBLE);
                    mobile_reset_ll.setVisibility(View.GONE);
                    useMobile=false;
                    break;
                case R.id.mobile_reset:
                    email_reset.setBackgroundResource(R.drawable.ic_triangle_top_light);
                    mobile_reset.setBackgroundResource(R.drawable.ic_triangle_top);
                    email_reset_ll.setVisibility(View.GONE);
                    mobile_reset_ll.setVisibility(View.VISIBLE);
                    useMobile=true;
                    break;
                case R.id.nextBtn:
                    goNext();
                    break;
                case R.id.sendCode:
                      sendVerifyCode();
                    break;
            }
        }
        //点击下一步按钮
        private void goNext() {
            if (useMobile) {//手机验证
                String phone = mobile.getText().toString();
                if (!PatternTool.isMobile(phone)) {
                    toast("手机号码不正确");
                    return;
                }
                String code=vcode.getText().toString();
                if(code.length()<6){
                    toast("请填写完整验证码");
                    return ;
                }
                mPhone=phone;
                //去服务器校验验证码是否正确
                  sheckSeverCode(phone,code);

            } else {//邮箱验证
                mEmail=email.getText().toString();
                if(!PatternTool.isEmail(mEmail)){
                    return ;
                }
                if(onNextListener!=null){
                    onNextListener.changeByNext();
                }
            }
        }

        /**
         * 去服务器检验验证码是否正确
         * @param phone
         * @param code
         */
        private void sheckSeverCode(String phone,String code){
            HashMap<String,String> params= NetUrl.initParams();
            params.put("code",code);
            params.put("type","phone");
            params.put("phone",mPhone);
            UserResetPwd.checkVerifyCode(getActivity(), params, new UserResetPwd.OnVerifyCodeCheckComplete() {
                @Override
                public void onSuccess(String message) {
                        if(onNextListener!=null){
                            onNextListener.changeByNext();
                        }
                }

                @Override
                public void onFail(int code, String error) {
                        toast(error);
                }
            });
        }

        private void  sendVerifyCode(){
          final  String phone = mobile.getText().toString();
            if (!PatternTool.isMobile(phone)) {
                toast("手机号码不正确");
            }
            SmsServer.sendSms(getActivity(), new SmsServer.OnSendCodeSuccessEvent() {
                @Override
                public void doComplete(String code) {
                    doSendCodeToServer(phone,code);
                }
            });
        }
        /**
         * 验证码倒计时
         */
        private int countTimer=120;
        private  Timer timer=new Timer();
        private TimerTask timeTask=new TimerTask() {
            @Override
            public void run() {
                countTimer--;
                Message message=new Message();
                message.what=1;
                handler.sendMessage(message);
            }
        };
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
//                       timer.cancel();
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
        private void doSendCodeToServer(String phone,String code){
            HashMap<String,String> params=NetUrl.initParams();
            params.put("code",code);
            params.put("phone",phone);
            SmsServer.SendCode(getActivity(), params, new SmsServer.ResponseStatusCallback() {
                @Override
                public void onSuccess(String message) {
                    toast("验证码发送成功");
                    if(timer==null){
                        timer= new Timer();
                    }
                    timer.schedule(timeTask,1000,1000);
                }

                @Override
                public void onFail(int code, String error) {
                    toast(error);
                }
            });
        }
        //点击下一步回调的接口
        public interface OnNextListener{
            void changeByNext();
        }
        private OnNextListener onNextListener;

        public void setOnNextListener(OnNextListener onNextListener) {
            this.onNextListener = onNextListener;
        }
    }

    /**
     * 使用邮箱找回
     */
    public static class EmailFragment extends BaseFragment{
        private static View EmailRootView;
        private AppCompatEditText emailCode;
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            EmailRootView=inflater.inflate(R.layout.reset_email,null);
            Button nextBtn= (Button) EmailRootView.findViewById(R.id.nextBtn);
            emailCode= (AppCompatEditText) EmailRootView.findViewById(R.id.emailCode);

            nextBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String code=emailCode.getText().toString();
                    if(code.length()<6){
                        toast("邮箱验证码不正确");
                        return ;
                    }
                    HashMap<String,String> params= NetUrl.initParams();
                    params.put("code",code);
                    params.put("type","email");
                    params.put("email",mEmail);
                    UserResetPwd.checkVerifyCode(getActivity(), params, new UserResetPwd.OnVerifyCodeCheckComplete() {
                        @Override
                        public void onSuccess(String message) {
                            if(onNextListener!=null){
                                onNextListener.changeByNext();
                            }
                        }

                        @Override
                        public void onFail(int code, String error) {
                            toast(error);
                        }
                    });
                }
            });
            return EmailRootView;

        }

        public static EmailFragment newInstance() {

            Bundle args = new Bundle();
            EmailFragment fragment = new EmailFragment();
            fragment.setArguments(args);
            return fragment;
        }
        //点击下一步回调的接口
        public interface OnNextListener{
            void changeByNext();
        }
        private OnNextListener onNextListener;

        public void setOnNextListener(OnNextListener onNextListener) {
            this.onNextListener = onNextListener;
        }
    }

    /**
     * 使用手机找回
     */
    public static class SecondFragment extends BaseFragment{
        private static View secondRootView;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            secondRootView=inflater.inflate(R.layout.reset_second,null);
            return secondRootView;
        }

        public static SecondFragment newInstance() {

            Bundle args = new Bundle();

            SecondFragment fragment = new SecondFragment();
            fragment.setArguments(args);
            return fragment;
        }
        //点击下一步回调的接口
        public interface OnNextListener{
            void changeByNext();
        }
        private OnNextListener onNextListener;

        public void setOnNextListener(OnNextListener onNextListener) {
            this.onNextListener = onNextListener;
        }

    }

}

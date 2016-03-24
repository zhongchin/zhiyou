package com.apolle.zhiyou.Http;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.TelephonyManager;

import com.android.volley.VolleyError;
import com.apolle.zhiyou.interactor.NetUrl;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by huangtao on 2016/3/1419:15.
 * modify by huangtao on 19:15
 */
public class SmsServer {

    /**
     * 发送验证码到服务器
     * @param context
     * @param code
     * @param responseStatusCallback
     */
    public static void SendCode(Context context,  HashMap<String,String> params, final ResponseStatusCallback responseStatusCallback){
            System.out.println("短信发送参数"+params);
        VolleyStringRequest.getInstance(context).RequestData(NetUrl.SAVE_SMS_SERVER, params, new VolleyStringRequest.ResponseCallback() {
            @Override
            public void onSuccess(String response) {
                String[] receives=new String[3];
                VolleyStringRequest.covertToArray(receives,response);
                int code=Integer.parseInt(receives[0]);
                String message=receives[1];
                if(code==0){
                    if(responseStatusCallback!=null){
                        responseStatusCallback.onSuccess(message);
                    }
                }else{
                    if(responseStatusCallback!=null){
                        responseStatusCallback.onFail(code,receives[2]);
                    }
                }

            }

            @Override
            public void onFailed(VolleyError error) {
                if(responseStatusCallback!=null){
                    responseStatusCallback.onFail(8,error.getMessage());
                }
            }
        });
    }
    public interface  ResponseStatusCallback{
        void onSuccess(String message);
        void onFail(int code,String error);
    }

    //发送短信验证码
    public static void sendSms(Context context,OnSendCodeSuccessEvent onSendCodeSuccessEvent){
        String defaultSmsPackage="";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            defaultSmsPackage= Telephony.Sms.getDefaultSmsPackage(context);
            String myPkg= context.getPackageName();
            if(!defaultSmsPackage.equals(myPkg)){
                Intent intent=new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
                intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME,myPkg);
                context.startActivity(intent);
            }
        }else{

        }
        TelephonyManager telephonyManager= (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);//获取本手机的手机号
        String myphone=telephonyManager.getLine1Number();//获取sim卡的手机号码

        //向收件箱发送一条短信
        ContentResolver resolver=context.getContentResolver();

        ContentValues contentValues=new ContentValues();
        contentValues.put(Telephony.Sms.ADDRESS,myphone);
        contentValues.put(Telephony.Sms.DATE, System.currentTimeMillis());
        long dateSend=System.currentTimeMillis()-5000;
        contentValues.put(Telephony.Sms.DATE_SENT,dateSend);
        contentValues.put(Telephony.Sms.READ,false);
        contentValues.put(Telephony.Sms.SEEN,false);
        contentValues.put(Telephony.Sms.STATUS,Telephony.Sms.STATUS_COMPLETE);
        String smsBody=SmsBody();
        contentValues.put(Telephony.Sms.BODY,smsBody);
        contentValues.put(Telephony.Sms.PERSON,"98888");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
//            doSendCodeToServer();//先把验证码发送到服务器
            if(onSendCodeSuccessEvent!=null){
                onSendCodeSuccessEvent.doComplete(tmpSmsCode);
            }
            resolver.insert(Telephony.Sms.CONTENT_URI,contentValues);
            Intent intent=new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
            intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME,defaultSmsPackage);
            context.startActivity(intent);
        }else{
            Uri uri=Uri.parse("content://sms/inbox");
            if(onSendCodeSuccessEvent!=null){
                onSendCodeSuccessEvent.doComplete(tmpSmsCode);
            }
          //  doSendCodeToServer();//先把验证码发送到服务器
            resolver.insert(uri,contentValues);
        }

        Intent intent=new Intent();
        intent.setAction("android.provider.Telephony.SMS_RECEIVED");
        Bundle bundle=new Bundle();
        bundle.putString("pdus",smsBody);
        intent.putExtras(bundle);
        context.sendBroadcast(intent);
    }
      public interface OnSendCodeSuccessEvent{
             void doComplete(String code);
      }
    /**
     *发送消息的消息体
     * @return
     */
    private static  String tmpSmsCode;
    private static String SmsBody(){
        tmpSmsCode=randomNumberCode();
        return "你在某某平台的短信验证码是:"+tmpSmsCode+",打死也不能告诉别人";
    }

    /**
     * 生成随机数字验证码
     * @return
     */
    private static String randomNumberCode(){
        Random random=new Random();
        int code= random.nextInt(899999)+100000;
        return String.valueOf(code);
    }
}

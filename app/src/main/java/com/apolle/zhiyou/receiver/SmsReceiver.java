package com.apolle.zhiyou.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by huangtao on 2016/3/1420:26.
 * modify by huangtao on 20:26
 */
public class SmsReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle=intent.getExtras();
        if(bundle!=null){
            System.out.println("广播接收"+bundle.get("pdus"));

        }
        System.out.println("广播接收"+context);
    }
    private receiverCallback receiverCallback;
    public interface  receiverCallback{
        void onSuccess(String body);
    }

}

package com.apolle.zhiyou.Tool;

import java.security.MessageDigest;

/**
 * Created by huangtao on 2016/2/2720:54.
 * modify by huangtao on 20:54
 */
public class Encrypt {
    public static String Md5(String input)throws  Exception{
        MessageDigest md5=null;
        try {
            md5=MessageDigest.getInstance("md5");
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
         byte[] byteArray=input.getBytes("UTF-8");
         byte[] md5Bytes=md5.digest(byteArray);
        StringBuffer hexValue=new StringBuffer();
        for (int i=0;i<md5Bytes.length;i++){
            int val=((int)md5Bytes[i])&0xff;
            if(val<16){
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
}

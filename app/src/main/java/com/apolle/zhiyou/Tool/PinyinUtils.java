package com.apolle.zhiyou.Tool;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * Created by huangtao on 2016/3/2014:27.
 * modify by huangtao on 14:27
 */
public class PinyinUtils {

    public static String getPinYin(String inputString){
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        char[] input=inputString.trim().toCharArray();
        String output="";
        try{
            for (char curChar:input){
                 if(Character.toString(curChar).matches("[\\u4E00-\\u9FA5]+")){
                     String[] temp= PinyinHelper.toHanyuPinyinStringArray(curChar,format);
                     output+=temp[0];
                 }else{
                     output=Character.toString(curChar);
                 }
            }
        }catch (BadHanyuPinyinOutputFormatCombination e){
            e.printStackTrace();
        }
        return output;
    }
}

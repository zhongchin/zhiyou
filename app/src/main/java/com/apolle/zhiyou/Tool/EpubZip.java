package com.apolle.zhiyou.Tool;


import android.content.Context;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.GregorianCalendar;
import java.util.List;


import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.GuideReference;
import nl.siegmann.epublib.domain.Metadata;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.epub.EpubReader;
import nl.siegmann.epublib.epub.HtmlProcessor;

/**
 * Created by huangtao on 2016/3/818:06.
 * modify by huangtao on 18:06
 */
public class EpubZip {
    private File epubFile;
    private Context context;
    private static EpubZip epubZip;
    private List<Resource> contents;
    private Book book;


    public static EpubZip newInstance() {
        return epubZip;
    }
    public EpubZip(Context context, String path){
        File file=new File( path );
        if(file.exists()) {
             book = null;
            try {
                FileInputStream inputStream = new FileInputStream(new File(path));
                book = (new EpubReader()).readEpub(inputStream);
                this.contents=book.getContents();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 利用反射获取各个资源的值
     * @param res
     * @return
     */
    public Resource getResource(String res){
        String resId=res.substring(0,1).toUpperCase()+res.substring(1);
        try {
           Method method= Book.class.getDeclaredMethod("get"+resId);
           return (Resource)method.invoke(book);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取某本书的mate信息
     * @return
     */
    public Metadata getBookMeta(){
        return book.getMetadata();
    }

    public List<Resource> getContents(){
        return this.contents;
    }
    public void page(){

    }
    public static int getIndexResouce(List<GuideReference> guideReferences ,GuideReference reference){
         for(int i=0;i<guideReferences.size();i++){
               GuideReference guideReference=guideReferences.get(i);
               if(guideReference.getResourceId()==reference.getResourceId()){
                  return i;
               }
         }
        return 0;
    }

}

package com.apolle.zhiyou.Tool;


import com.apolle.zhiyou.Model.FileDir;
import com.apolle.zhiyou.Model.LocalBook;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;


/**
 * Created by huangtao on 2016/3/417:49.
 * modify by huangtao on 17:49
 */
public class BookScan {

    private static BookScan bookScan;
    private String[] suffixs=new String[]{".txt", ".html", ".epub", ".oeb", ".fb2", ".mobi", ".rtf" ,".umd"};
    private String endWith="";
    ArrayList<LocalBook> books;
    public  ArrayList<LocalBook> getAllBook(String path){
        books=getAllBook( path,null,null);
        return books;
    }
    public  ArrayList<LocalBook> getAllBook(String path, onScanListener onScanListener, OnScanCompletedListener onScanCompletedListener){
        books=new ArrayList<LocalBook>();
        scanBookList(path,onScanListener);
        if(null!=onScanCompletedListener){
            onScanCompletedListener.onCompleted(books);
        }
        return books;

    }

    private BookScan(){

    }
    public static BookScan getInstance(){
        if(null==bookScan){
            bookScan=new BookScan();
        }
        return bookScan;
    }
       public ArrayList<LocalBook> scanBookList(String path){
           scanBookList( path,null);
           return books;
       }
    public ArrayList<LocalBook> scanBookList(String path,onScanListener listener){

        File file=new File(path);
        if(!file.exists()) return null;
        String filename=file.getName();

        if(file.isDirectory()){
            File[] files=file.listFiles();
            if(null!=files&&files.length>0){
                for (File file1:files){
                    scanBookList(file1.getPath(),listener);
                }
            }
        }else if(isBook(filename)){
            LocalBook book=new LocalBook();
            book.setPath(file.getAbsolutePath());
            book.setName(file.getName());
            book.setIconType(false);
            if(null!=listener){
                listener.onScan(book);
            }
            if(endWith!=""){
                 book.setType(endWith);
            }
            System.out.println("book path"+book.getPath()+",type:"+book.getType());
            books.add(book);
        }
        return books;
    }

    public boolean isBook(String filename){
        for (String suffix: suffixs) {
            if(filename.endsWith(suffix)){
                endWith=suffix;
                return true;
            }
        }
        endWith="";
        return false;
    }

    public  static  ArrayList<FileDir> scanDir(String path){
        ArrayList<FileDir> filedirs=new ArrayList<FileDir>();
          File file=new File( path );
          if(file.exists()){
              File[] files=file.listFiles();
              for (File f:files) {
                  String ftype="";
                    if(f.isFile()){
                         ftype="other";
                        if(f.getName().endsWith(".epub")) ftype="epub";
                        if(f.getName().endsWith(".umd")) ftype="umd";
                        if(f.getName().endsWith(".ebk")) ftype="ebk";
                        if(f.getName().endsWith(".txt")) ftype="txt";
                        if(f.getName().endsWith(".pdf")) ftype="pdf";
                        if(f.getName().endsWith(".chm")) ftype="chm";
                        if(f.getName().endsWith(".mobi")) ftype="mobi";
                        FileDir f1= new FileDir(f.getName(),ftype);
                        filedirs.add(f1);
                    }else if(f.getName()!="."&&f.getName()!=".."){
                        ftype="folder";
                        FileDir f1= new FileDir(f.getName(),ftype);
                        filedirs.add(f1);
                    }

              }
          }
        return filedirs;
    }
    public class FileSuffixFilter implements  FilenameFilter{
        @Override
        public boolean accept(File dir, String filename) {
           return isBook( filename );
        }
    }
    public interface onScanListener{
           void onScan(LocalBook book);
    }
   public interface OnScanCompletedListener{
       void onCompleted(ArrayList<LocalBook> books);
   }
}

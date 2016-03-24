package com.apolle.zhiyou.Tool;

import android.support.annotation.NonNull;

import com.apolle.zhiyou.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by huangtao on 2016/3/818:19.
 * modify by huangtao on 18:19
 */
public class FileTool {
    private static final String  DIR_SPERATOR = "/";

    public static boolean mkdir(String path){
        return mkdir(path,0);
    }

    public static boolean mkdir(String path,int index){
        //除掉第一个/
          if(path.indexOf(DIR_SPERATOR)==0){
              path=path.substring(1);
          }
        Pattern pattern=Pattern.compile("/");
        String[] dirs=pattern.split(path);
        if(dirs.length>0){
            String dir=dirs[0];
            if(index>0){
                for(int i=1;i<=index;i++){
                    dir+=DIR_SPERATOR+dirs[i];
                }
            }
            Boolean flag=false;
            File file= new File(dir);
            if(!file.exists()){
                file.mkdir();
            }
            if(index<dirs.length-1){
                mkdir(path,++index);
            }
        }
        return true;
    }
      private static boolean touchFile(String path){
          String dir=path.substring(0,path.lastIndexOf( DIR_SPERATOR)+1);
          //String filename=path.substring(path.lastIndexOf( DIR_SPERATOR)+1);
          if(mkdir(dir)){
              File file=new File( path );
              if(!file.exists()){
                  try {
                      return  file.createNewFile();
                  } catch (IOException e) {
                      e.printStackTrace();
                  }
              }
          }
          return false;
      }

    public static void ReadZipFile(String filePath,String savePath){
        ZipFile zipFile = null;
        try {
            File file=new File(filePath);
            zipFile = new ZipFile(file,ZipFile.OPEN_READ);
            Enumeration zipFileEntries =zipFile.entries();
            String zipFileName=file.getName();
            String dirName=zipFileName.substring(0,zipFileName.lastIndexOf("."));
            dirName=dirName!=""?dirName:zipFileName;
            while(zipFileEntries.hasMoreElements()){
                ZipEntry zipElement=(ZipEntry)zipFileEntries.nextElement();
                InputStream inputStream=zipFile.getInputStream( zipElement);
                String filename=zipElement.getName();
                if(filename!=null){
                    unzipFile(zipElement,inputStream,savePath+dirName+"/");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 解压文件
     * @param zipEntry
     * @param inputStream
     * @param savePath
     */
    public static void unzipFile(ZipEntry zipEntry, InputStream inputStream,String savePath){
        String fileName=zipEntry.getName();
        String saveFileName=savePath+fileName;
        touchFile(saveFileName);//创建解压缩后的文件
        try {
            BufferedOutputStream out=new BufferedOutputStream( new FileOutputStream( new File(saveFileName) ) );
            int binary=0;
            while((binary=inputStream.read())!=-1){
                out.write(binary);
            }
            out.flush();
            out.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @NonNull
    public static String getFileType(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
    public static String getFileName(String fileName){//获取文件真实名称
        if(fileName.indexOf("/")<=0){
            return fileName.substring(0,fileName.lastIndexOf("."));
        }else{
            return fileName.substring(fileName.lastIndexOf("/"),fileName.lastIndexOf("."));
        }

    }
    public static int getFileImage(String fileName){
          int image=0;
         switch (getFileType(fileName)){
             case "chm":
                  image=R.mipmap.cover_chm;
                 break;
             case "pdf":
                  image=R.mipmap.cover_pdf;
                 break;
             case "epub":
                  image=R.mipmap.cover_epub;
                 break;
             case "ebk":
                  image=R.mipmap.cover_ebk;
                 break;
             case "txt":
                  image=R.mipmap.cover_txt;
                 break;
             case "umd":
                  image=R.mipmap.cover_umd;
                 break;
             case "mobi":
                  image=R.mipmap.cover_mobi;
                 break;
         }
        return  image;
    }
    /**
     *删除文件或者目录
     * @param path
     */
    public static void rmdirOrFile(String path){
        File file=new File(path);
        if(file.exists()){
            if(file.isFile()){
                File[] files=file.listFiles();
                for (File file1:files) {
                    if(file1.isFile()){
                        file1.deleteOnExit();
                    }else{
                        rmdirOrFile(file1.getAbsolutePath());
                    }
                }
            }else{
                file.deleteOnExit();
            }
        }
    }

    public void getZipFile(String path,String savePath,String infoPath){


    }


}

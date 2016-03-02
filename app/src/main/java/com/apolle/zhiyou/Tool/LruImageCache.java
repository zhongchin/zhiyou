package com.apolle.zhiyou.Tool;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;
import com.lidroid.xutils.bitmap.core.BitmapCache;

/**
 * Created by huangtao on 2016/2/2720:33.
 * modify by huangtao on 20:33
 */
public class LruImageCache implements ImageLoader.ImageCache {
    private static LruCache<String,Bitmap> mMemryCache;
    private static LruImageCache lruImageCache;

    public LruImageCache() {
        int maxMemory=(int) Runtime.getRuntime().maxMemory();
        int cacheSize=maxMemory/2;
        mMemryCache=new LruCache<String,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes()*bitmap.getHeight();
            }
        };
    }
    public static LruImageCache getInstance(){
        if(null==lruImageCache){
           lruImageCache=new LruImageCache();
        }
        return lruImageCache;
    }

    @Override
    public Bitmap getBitmap(String url) {

        try{
            return  mMemryCache.get(Encrypt.Md5(url));
        }catch (Exception e){
            System.out.println("图片加载错误"+e.getMessage());
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        if(null==getBitmap(url)){
            mMemryCache.put(url,bitmap);
               try {
                   mMemryCache.put(Encrypt.Md5(url),bitmap);
               }catch (Exception e){
                   e.printStackTrace();
               }
        }
    }
}

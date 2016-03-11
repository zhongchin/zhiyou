package com.apolle.zhiyou.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.v4.graphics.BitmapCompat;
import android.util.AttributeSet;
import android.widget.GridView;

import com.apolle.zhiyou.R;

/**
 * Created by huangtao on 2016/3/60:21.
 * modify by huangtao on 0:21
 */
public class BookGirdView extends GridView {
     private Bitmap background;
     public BookGirdView(Context context, AttributeSet attrs){
            super(context,attrs);
            background= BitmapFactory.decodeResource(getResources(),R.drawable.bookshelf_layer_center);
     }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        int count=getChildCount();
        int top=count>0?getChildAt( 0 ).getTop():0;
        int backgroundWidth=background.getWidth();
        int backgroundHeight=background.getHeight()+2;
        int width=getWidth();
        int height=getHeight();
        Matrix matrix=new Matrix();
     /*     float values=[
                2.0,0.0,
                ];*/

        Bitmap newBitmap=Bitmap.createScaledBitmap(background,width,(height-50)/3,true);
//            Bitmap newBitmap=Bitmap.createBitmap(background,0,0,backgroundWidth/3,height/3);
        for (int y=top;y<height;y+=(height-50)/3){
            for (int x=0;x<width;x+=backgroundWidth){
                canvas.drawBitmap(newBitmap,x,y,null);
            }
        }
        super.dispatchDraw( canvas );
    }
}

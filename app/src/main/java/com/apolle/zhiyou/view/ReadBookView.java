package com.apolle.zhiyou.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Icon;
import android.hardware.camera2.CameraConstrainedHighSpeedCaptureSession;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;

import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * Created by huangtao on 2016/3/91:17.
 * modify by huangtao on 1:17
 */
public class ReadBookView extends WebView {
    public ReadBookView(Context context) {
        super(context);
    }

    public ReadBookView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ReadBookView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ReadBookView(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
        super(context, attrs, defStyleAttr, privateBrowsing);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
      /*  Paint paint=new Paint();
        paint.setColor(Color.BLUE);
        canvas.drawRect(new Rect(0,0,getWidth(),getHeight()/5),paint);
        canvas.drawRect(new Rect(0,0,getWidth()/3,getHeight()),paint);
        canvas.save();
        paint.setColor(Color.RED);
        canvas.drawRect(new Rect(0,(4*getHeight()/5),getWidth(),getHeight()),paint);
        canvas.drawRect(new Rect(2*getWidth()/3,0,getWidth(),getHeight()),paint);
        canvas.save();*/
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int height=getHeight();
        int width=getWidth();
        boolean isPoint=false;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                System.out.println("手指点击");
                  isPoint=true;
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                    float pointX=event.getX();
                    float pointY=event.getY();
                    if((0<pointX&&pointX<width/3*2)&&(pointY<height/5&&pointY>0)){//顶部1/5显示上一页
                        if(null!=onTouchEventListener){
                            onTouchEventListener.onTouchOnTop();
                        }
                    }
                    if((0<pointX&&pointX<width/3)&&(pointY<height/5*4&&pointY>0)){//左边1/3显示上一页
                        if(null!=onTouchEventListener){
                            onTouchEventListener.onTouchOnLeft();
                        }

                    }
                    if((width/3*2<pointX&&pointX<width)&&(pointY<height&&pointY>0)){//右边1/3显示下一页
                        if(null!=onTouchEventListener){
                            onTouchEventListener.onTouchOnRight();
                        }

                    }
                    if((width/3<pointX&&pointX<width)&&(pointY<height&&pointY>width/5*4)){//下边1/5显示下一页
                        if(null!=onTouchEventListener){
                            onTouchEventListener.onTouchOnBottom();
                        }
                    }
                if(pointX>width/3&&pointX<width/3*2&&pointY>height/5&&pointY<height/5*4){//点击中间部分
                    if(null!=onTouchEventListener){
                        onTouchEventListener.onTouchCenter();
                    }
                }

                break;
        }
        return super.onTouchEvent(event);
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }
    public interface  OnTouchEventListener{
        void onTouchOnTop();
        void onTouchOnLeft();
        void onTouchOnRight();
        void onTouchOnBottom();
        void onTouchCenter();

    }

    private OnTouchEventListener onTouchEventListener;


    public void setOnTouchEventListener(OnTouchEventListener onTouchEventListener) {
        this.onTouchEventListener = onTouchEventListener;
    }

}

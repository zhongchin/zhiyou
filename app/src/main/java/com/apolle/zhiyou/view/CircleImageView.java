package com.apolle.zhiyou.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.apolle.zhiyou.R;

/**
 * Created by huangtao on 2016/3/3021:33.
 * modify by huangtao on 21:33
 */
public class CircleImageView extends ImageView{

    private static final int default_border_width=0;
    private static final int default_border_color=Color.BLUE;

    private int mBorderColor=default_border_color;
    private int mBorderWidth=default_border_width;

    public CircleImageView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }


    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array=context.obtainStyledAttributes(attrs,R.styleable.CircleImageView);

        mBorderColor=array.getColor(R.styleable.CircleImageView_border_color, default_border_color);
        mBorderWidth=array.getDimensionPixelSize(R.styleable.CircleImageView_border_width, default_border_width);

        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        BitmapDrawable drawable=(BitmapDrawable) getDrawable();
        int size=Math.min(drawable.getIntrinsicHeight(),drawable.getIntrinsicWidth());
        int widthSize= MeasureSpec.getSize(widthMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int width=widthMeasureSpec;
        int height=heightMeasureSpec;
        switch (widthMode){
            case MeasureSpec.EXACTLY:
                width=widthSize;
                break;
        }
        switch (heightMode) {
            case MeasureSpec.EXACTLY:
                height=heightSize;
                break;
        }
        int measureSize=Math.min(height,width);
        size=Math.min(measureSize, size);
        System.out.println("view"+widthMeasureSpec+"|"+heightMeasureSpec);
        System.out.println("view"+widthSize+"|"+heightSize);
        setMeasuredDimension(size,size);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        if(getDrawable()==null){
            return ;
        };
        BitmapDrawable drawable=(BitmapDrawable) getDrawable();

        Bitmap bitmap=drawable.getBitmap();
        BitmapShader mBitmapShader=new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        Paint paint=new Paint();
        int width=getMeasuredWidth();
        int height=getMeasuredWidth();
        int size=Math.min(width, height);

        int scaleSize=Math.min(drawable.getIntrinsicHeight(),drawable.getIntrinsicWidth());
        float scale=1.0f;
        if(scaleSize<size){
            scale=(float)scaleSize/(float)size;
        }else{
            scale=(float)size/(float)scaleSize;
        }

        Matrix martix=new Matrix();
        martix.setScale(scale, scale);

        mBitmapShader.setLocalMatrix(martix);
        paint.setShader(mBitmapShader);

        int leftTop=6;//设置偏移量
        int rightBottom=leftTop*2;
        int radius=(size-rightBottom)/2;
        RectF rect=new RectF(leftTop,leftTop,size-leftTop,size-leftTop);

        canvas.drawRoundRect(rect,radius,radius, paint);

        paint.setStyle(Paint.Style.STROKE);
        Paint mPaint=new Paint();
        mPaint.setColor(mBorderColor);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mBorderWidth);
        //画边框
        int pos=(size-rightBottom)/2;

        canvas.drawCircle(size/2, size/2, radius+leftTop-2, mPaint);

    }

}
package com.apolle.zhiyou.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apolle.zhiyou.R;


/**
 * Created by huangtao on 2016/3/1922:47.
 * modify by huangtao on 22:47
 */
public class SlideListView extends View {
    private OnTouchChangedListener onTouchChangedListener;
    public SlideListView(Context context) {
        super(context);
    }

    public SlideListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlideListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private static String[] alphabet={"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#"};
    private int choose=-1;
    private Paint paint=new Paint();
    private TextView mTextDialog;

    public void setTextView(TextView mTextDialog){
        this.mTextDialog=mTextDialog;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        ViewGroup viewGroup= (ViewGroup) getParent();
        int width=getWidth();
        int singleHeight=viewGroup.getHeight()/alphabet.length-2;

        for (int i=0;i<alphabet.length;i++){
            paint.setColor(Color.rgb(33,23,23));
            paint.setTypeface(Typeface.DEFAULT);
            paint.setAntiAlias(true);
            paint.setTextSize(18);

            if(i==choose){
                paint.setColor(Color.parseColor("#FF0000"));
                paint.setFakeBoldText(true);
            }
            float xPos=width/2-paint.measureText(alphabet[i])/2;
            float yPos=singleHeight*i+singleHeight;

            canvas.drawText(alphabet[i],xPos,yPos,paint);
            paint.reset();

        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action=event.getAction();
        final float y=event.getY();
        final int oldChoose=choose;
        final int c=(int)(y/getHeight()*alphabet.length);

        switch (action){
            case MotionEvent.ACTION_UP:
                 setBackgroundDrawable(new ColorDrawable(0x000000));
                choose=-1;
                invalidate();
                if(mTextDialog!=null){
                    mTextDialog.setVisibility(View.INVISIBLE);
                }
                System.out.println("右侧slideListView手抬起"+choose);
                break;
            case MotionEvent.ACTION_MOVE:
                setBackgroundColor(Color.parseColor("#DFE0E0"));
                if(oldChoose!=c){
                    if(c>=0&&c<alphabet.length){
                        if(onTouchChangedListener!=null){
                            onTouchChangedListener.onTouchChange(alphabet[c]);
                        }
                        if(mTextDialog!=null){
                            mTextDialog.setText(alphabet[c]);
                            mTextDialog.setVisibility(View.VISIBLE);
                        }
                        choose =c;
                        invalidate();
                    }
                }
                break;

        }
        return true;
    }
    public interface  OnTouchChangedListener{
        void onTouchChange(String s);
    }

    public void setOnTouchChangedListener(OnTouchChangedListener onTouchChangedListener) {
        this.onTouchChangedListener = onTouchChangedListener;
    }
}

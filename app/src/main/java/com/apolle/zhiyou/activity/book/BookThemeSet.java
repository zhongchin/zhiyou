package com.apolle.zhiyou.activity.book;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.SeekBar;

import com.apolle.zhiyou.R;
import com.apolle.zhiyou.activity.BaseActivity;
import com.apolle.zhiyou.fragment.BookFragment;
import com.apolle.zhiyou.view.ReadBookView;
import com.lidroid.xutils.ViewUtils;
import com.rey.material.widget.Button;
import com.rey.material.widget.TextView;

public class BookThemeSet extends Fragment implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {

    private static BookThemeSet  bookThemeSet;
    private View rootView;
    private int curFontSize=20;
    private TextView fontText;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.activity_book_theme,null);
        ReadBookActivity activity=(ReadBookActivity)getActivity();
        WebView book_content= (WebView) activity.findViewById(R.id.book_content);
        curFontSize=book_content.getSettings().getDefaultFontSize();
        fontText= (TextView) rootView.findViewById(R.id.tv);
        fontText.setText(String.valueOf(curFontSize));

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        SeekBar seekBar=(SeekBar)rootView.findViewById(R.id.seebar);
        seekBar.setOnSeekBarChangeListener(this);
        Button EnlargeBtn= (Button) rootView.findViewById(R.id.fontEnlarge);
        Button ReduceBtn= (Button) rootView.findViewById(R.id.fontReduce);
        EnlargeBtn.setOnClickListener(this);
        ReduceBtn.setOnClickListener(this);
        ImageButton bg1= (ImageButton) rootView.findViewById(R.id.bg1);
        ImageButton bg2= (ImageButton) rootView.findViewById(R.id.bg2);
        ImageButton bg3= (ImageButton) rootView.findViewById(R.id.bg3);
        ImageButton bg4= (ImageButton) rootView.findViewById(R.id.bg4);

        bg1.setOnClickListener(this);
        bg2.setOnClickListener(this);
        bg3.setOnClickListener(this);
        bg4.setOnClickListener(this);

        super.onActivityCreated(savedInstanceState);
    }

    public static BookThemeSet newInstance() {

          if(bookThemeSet==null){
              bookThemeSet=new BookThemeSet();
          }
        return bookThemeSet;
    }
   private OnBookThemeSetChangeListener onBookThemeSetChangeListener;

    @Override
    public void onClick(View v) {
        Drawable drawable;
        switch (v.getId()){
            case R.id.fontEnlarge:
                if(curFontSize<90){
                    curFontSize+=2;
                    fontText.setText(String.valueOf(curFontSize));
                    if(onBookThemeSetChangeListener!=null){
                        onBookThemeSetChangeListener.onFontSizeChange(curFontSize);
                    }
                }
                break;
            case R.id.fontReduce:
                if(curFontSize>8){
                    curFontSize-=2;
                    fontText.setText(String.valueOf(curFontSize));
                    if(onBookThemeSetChangeListener!=null){
                        onBookThemeSetChangeListener.onFontSizeChange(curFontSize);

                    }
                }

                break;
            case R.id.bg1:
                    drawable =v.getBackground();
                if(onBookThemeSetChangeListener!=null){
                    onBookThemeSetChangeListener.onBackgroundChange(drawable);
                }
                break;
            case R.id.bg2:
                drawable=v.getBackground();
                if(onBookThemeSetChangeListener!=null){
                    onBookThemeSetChangeListener.onBackgroundChange(drawable);
                }
                break;
            case R.id.bg3:
                 drawable =v.getBackground();
                if(onBookThemeSetChangeListener!=null){
                    onBookThemeSetChangeListener.onBackgroundChange(drawable);
                }
                break;
            case R.id.bg4:
                 drawable =v.getBackground();
                if(onBookThemeSetChangeListener!=null){
                    onBookThemeSetChangeListener.onBackgroundChange(drawable);
                }
                break;
        }
    }

    public interface OnBookThemeSetChangeListener{
          void onBrightChange(float progress);
          void onFontSizeChange(int fontSize);
          void onBackgroundChange(Drawable drawable);

    }

    public void setOnBookThemeSetChangeListener(OnBookThemeSetChangeListener onBookThemeSetChangeListener) {
        this.onBookThemeSetChangeListener = onBookThemeSetChangeListener;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
         if(onBookThemeSetChangeListener!=null){

             float bright=0;
             if(progress/100<0.1){
                 bright=0.1f;
             }else{
                 bright=progress/100;
             }
             onBookThemeSetChangeListener.onBrightChange(bright);

         }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}

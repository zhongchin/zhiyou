package com.apolle.zhiyou.activity.book;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.SeekBar;

import com.apolle.zhiyou.Model.LocalBook;
import com.apolle.zhiyou.R;
import com.apolle.zhiyou.Tool.DisplayMerticsTool;
import com.apolle.zhiyou.Tool.EpubZip;
import com.apolle.zhiyou.Tool.FileTool;
import com.apolle.zhiyou.activity.BaseActivity;
import com.apolle.zhiyou.mUtil.MDbUtil;
import com.apolle.zhiyou.view.ReadBookView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnLongClick;
import com.rey.material.widget.LinearLayout;
import com.rey.material.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.GuideReference;
import nl.siegmann.epublib.epub.EpubReader;


public class ReadBookActivity extends BaseActivity {

    @ViewInject( R.id.book_content)
    ReadBookView book_content;
    @ViewInject( R.id.zy_fl_read_book)
    FrameLayout zy_fl_read_book;
    @ViewInject(R.id.zy_fl_book)
    FrameLayout zy_fl_book;

    private Book book;//最重要的数据,当前正在看的书
    private ArrayList<GuideReference> bookReferences;//书的章节目录表
    private boolean isUnzip=false;
    private PopupWindow bottomMenu;
    private  ReadBookViewHolder viewHolder;
    private boolean dayOrnight=true;
    private SharedPreferences sharedPreference;
    private SharedPreferences.Editor editor;
    private    String abFileDir=Environment.getExternalStorageDirectory()+"/zhiyou/books/";
    private  MDbUtil dbUtil;


    private int curGuidePerfencePosition;//当前章节信息

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        ViewUtils.inject( this );
        Intent intent=getIntent();
        String type=intent.getType();
        Uri uri=intent.getData();
        Log.i("uri",uri.toString());
        Log.i("path",uri.getAuthority()+uri.getPath());
        String path=uri.getAuthority()+uri.getPath();//文件的绝对路径
        sharedPreference=getSharedPreferences("book",MODE_PRIVATE);
        editor=sharedPreference.edit();

        File file=new File( path );
        String fileDir=path.substring(path.lastIndexOf("/")+1,path.lastIndexOf("."));//文件目录
        abFileDir=abFileDir+fileDir;
        //查询本地数据库中，看看是否有
         dbUtil=MDbUtil.newInstance(ReadBookActivity.this);
        LocalBook localBook =new LocalBook(file.getName(),FileTool.getFileType(path),String.valueOf(FileTool.getFileImage(path)),path);
        if(!dbUtil.hasEntity(localBook,"path")){
            dbUtil.save(localBook);
        }
        if(!isUnzip){
            FileTool.ReadZipFile(path, Environment.getExternalStorageDirectory()+"/zhiyou/books/");//将文件解压到某个目录，待优化
            isUnzip=true;
        }

        readEpub(file,localBook);
        contentViewAction();
        fanpage();
    }
    private void readEpub(File file,LocalBook localBook){
        try{
          if(file.exists()){
              FileInputStream inputStream = new FileInputStream(file);
              book=(new EpubReader()).readEpub( inputStream );
              //更新数据库中书籍的图片
              localBook.icon=abFileDir+"/OPS/"+book.getCoverImage().getHref();
              dbUtil.update(localBook);
              bookReferences= (ArrayList<GuideReference>) book.getGuide().getReferences();
              curGuidePerfencePosition=sharedPreference.getInt("chapter",0);
              loadUrlData(bookReferences.get(curGuidePerfencePosition).getCompleteHref());
          }else{
              Snackbar.make(zy_fl_read_book,"没有电子书",Snackbar.LENGTH_SHORT).show();
          }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void loadUrlData(String href){
        book_content.getSettings().setJavaScriptEnabled(true);
        book_content.setWebViewClient(new WebViewClient(){

        });
        book_content.getSettings().setSupportZoom(true);
        String phref=abFileDir+"/OPS/"+href;
        Uri uri=Uri.fromFile(new File(phref));
        book_content.loadUrl(uri.toString());
    }

   @OnLongClick(R.id.book_content)
   public void OnLongClick(View v){
        System.out.println("长按复制");
   }

    private void fanpage(){
        book_content.setOnTouchEventListener(new ReadBookView.OnTouchEventListener() {
            @Override
            public void onTouchOnTop() {
                System.out.println("你点击了顶部");
                prevPreference();
            }

            @Override
            public void onTouchOnLeft() {
                System.out.println("你点击了左部");
                prevPreference();

            }

            @Override
            public void onTouchOnRight() {
                System.out.println("你点击了右3部");
                nextPreference();

            }

            @Override
            public void onTouchOnBottom() {
                System.out.println("你点击了下部");
                nextPreference();
            }
            @Override
            public void onTouchCenter() {

                System.out.println("你点击了中间部分");
                showPopMenu();
            }
        });
        book_content.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {

                super.onPageFinished(view, url);
            }
        });

    }
     private void prevPreference(){
         if(curGuidePerfencePosition>1){
             curGuidePerfencePosition=curGuidePerfencePosition-1;
             String href=bookReferences.get(curGuidePerfencePosition).getCompleteHref();
             loadUrlData(href);
             editor.putInt("chapter",curGuidePerfencePosition);

         }
     }
    private void nextPreference(){
        if(curGuidePerfencePosition<bookReferences.size()-1){
            curGuidePerfencePosition=curGuidePerfencePosition+1;
            String href=bookReferences.get(curGuidePerfencePosition).getCompleteHref();
            loadUrlData(href);
            editor.putInt("chapter",curGuidePerfencePosition);
        }
    }
    private void contentViewAction(){
        book_content.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("你好啊");
                  v.setFocusable(true);

                 zy_fl_read_book.clearFocus();
                 zy_fl_read_book.setFocusable(true);
                if(viewHolder!=null){
                    FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
                    if(viewHolder.bookLeftMenu!=null){
                        ft.setCustomAnimations(R.anim.slide_left_enter,R.anim.slide_left_out);
                        ft.remove(viewHolder.bookLeftMenu).commit();
                    }
                    if(viewHolder.bookThemeSet!=null){
                        ft.setCustomAnimations(R.anim.pop_enter_top_anim,R.anim.pop_exit_top_anim);
                        ft.remove(viewHolder.bookThemeSet).commit();
                    }
                }
                return false;
            }
        });
    }
    private void showPopMenu(){
        bottomMenu=new PopupWindow(this);
        if(!bottomMenu.isShowing()){
            View popView=LayoutInflater.from(this).inflate(R.layout.book_settings,null);
            bottomMenu.setContentView(popView);
            bottomMenu.setHeight(DisplayMerticsTool.getWindowHeight(ReadBookActivity.this)/4);
            bottomMenu.setWidth(DisplayMerticsTool.getWindowWidth(ReadBookActivity.this));
            bottomMenu.setFocusable(true);
            bottomMenu.setAnimationStyle(R.anim.pop_enter_top_anim);
            bottomMenu.showAtLocation(zy_fl_read_book, Gravity.BOTTOM,0,0);
            bottomMenu.setBackgroundDrawable(getResources().getDrawable(R.drawable.popup_bg));
            viewHolder=new ReadBookViewHolder();
            viewHolder.ViewToId(popView);
        }
    }
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_MENU){
            System.out.println("你点击了菜单键");

        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public AppCompatActivity getActivity() {
        return ReadBookActivity.this;
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_read_book;
    }

    private class ReadBookViewHolder implements View.OnClickListener{
        private ArrayList<HashMap<String,Object>> themes;
        private FragmentTransaction ft;
        public TextView prexText;
        public TextView nextText;
        public AppCompatSeekBar seekBar;
        public LinearLayout book_ll_catalog,book_ll_day,book_ll_theme;
        public ImageView dayOrNight;
        public TextView dayOrNightText;
        public Fragment bookThemeSet,bookLeftMenu;
        public ReadBookViewHolder(){

        }
        public void  ViewToId(View view){
            this.prexText=(TextView) view.findViewById(R.id.prevText);
            this.nextText=(TextView)view.findViewById(R.id.nextText);
            this.seekBar=(AppCompatSeekBar)view.findViewById(R.id.seebar);
            this.book_ll_catalog=(LinearLayout)view.findViewById(R.id.book_ll_catalog);
            this.book_ll_day=(LinearLayout)view.findViewById(R.id.book_ll_day);
            this.book_ll_theme=(LinearLayout)view.findViewById(R.id.book_ll_theme);
            this.dayOrNight=(ImageView) view.findViewById(R.id.dayOrnight);
            this.dayOrNightText=(TextView)view.findViewById(R.id.dayOrnightText);
             ViewOnclick(prexText,nextText,book_ll_catalog,book_ll_day,book_ll_theme);
        }
        private void ViewOnclick(View... views){
            for (View view:views) {
                if(null!=view){
                    view.setOnClickListener(this);
                }
            }
        }
        private void seekBarFenpage(){
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
        }
        public void goToCatalog(){

              zy_fl_read_book.setLayoutParams(new FrameLayout.LayoutParams(300, ViewGroup.LayoutParams.MATCH_PARENT));
              ft=getSupportFragmentManager().beginTransaction();
              ft.setCustomAnimations(R.anim.slide_left_enter,R.anim.slide_left_out);
              bookLeftMenu=BookLeftMenu.newInstance();
             BookLeftMenu.setOnCatalogSelectListener(new BookLeftMenu.OnCatalogSelectListener() {
                 @Override
                 public void onClick(GuideReference preference) {
                        System.out.println("preference preference preference"+preference+""+preference.getCompleteHref());
                        loadUrlData( preference.getCompleteHref());
                         int index= EpubZip.getIndexResouce(book.getGuide().getReferences(),preference);
                            curGuidePerfencePosition=index;
                            editor.putInt("chapter",index);

                 }
             });
              Bundle bundle=new Bundle();
               bundle.putSerializable("guide", book.getGuide());
              bookLeftMenu.setArguments(bundle);
              ft.replace(R.id.zy_fl_read_book,bookLeftMenu,null).commit();

             bottomMenu.dismiss();
        }
        public void goToThemeSet(){

            zy_fl_read_book.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300,Gravity.BOTTOM));
            ft=getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.anim.pop_enter_top_anim,R.anim.pop_exit_top_anim);
            bookThemeSet= BookThemeSet.newInstance();
            ((BookThemeSet)bookThemeSet).setOnBookThemeSetChangeListener(new BookThemeSet.OnBookThemeSetChangeListener(){
                @Override
                public void onBrightChange(float progress) {
                    Window window=getWindow();
                    WindowManager.LayoutParams layoutParams=window.getAttributes();
                    layoutParams.screenBrightness=progress;

                    editor.putFloat("brightscreen",progress);

                    window.setAttributes(layoutParams);

                }

                @Override
                public void onFontSizeChange(int fontSize) {
                     book_content.getSettings().setDefaultFontSize(fontSize);

                }

                @Override
                public void onBackgroundChange(Drawable drawable) {
                   book_content.setBackgroundDrawable(drawable);
                }
            });
            ft.replace(R.id.zy_fl_read_book,bookThemeSet,null).commit();
            bottomMenu.dismiss();
        }
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.book_ll_catalog:
                       goToCatalog();
                break;
                case R.id.book_ll_theme:
                      goToThemeSet();
                    break;
                case R.id.book_ll_day:
                        if(dayOrnight){//白天
                            dayOrNight.setImageResource(R.drawable.ic_setting_night);
                            dayOrNightText.setText(R.string.book_setting_night);
                            book_content.setBackgroundColor(Color.parseColor("#CC000000"));
                            editor.putBoolean("dayornight",false);
                            dayOrnight=false;
                        }else{//黑夜
                            dayOrNight.setImageResource(R.drawable.ic_setting_day);
                            dayOrNightText.setText(R.string.book_setting_day);
                            book_content.setBackgroundColor(Color.parseColor("#CCFFFFFF"));
                            editor.putBoolean("dayornight",true);
                            dayOrnight=true;
                        }
                    break;
                case R.id.prevText:
                        prevPreference();
                    break;
                case R.id.nextText:
                        nextPreference();
                    break;
            }
        }
    }

}

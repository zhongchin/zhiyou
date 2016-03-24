package com.apolle.zhiyou.activity.book;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.apolle.zhiyou.Model.Catalog;
import com.apolle.zhiyou.Model.LocalBook;
import com.apolle.zhiyou.R;
import com.apolle.zhiyou.Tool.DisplayMerticsTool;
import com.apolle.zhiyou.Tool.FileTool;
import com.apolle.zhiyou.activity.BaseActivity;
import com.apolle.zhiyou.activity.MainActivity;
import com.apolle.zhiyou.mUtil.MDbUtil;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnLongClick;
import com.rey.material.widget.LinearLayout;
import com.rey.material.widget.TextView;


import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.SpineReference;
import nl.siegmann.epublib.epub.EpubReader;


public class ReadBookActivity extends BaseActivity {

    @ViewInject( R.id.book_content)
    WebView book_content;
    @ViewInject( R.id.zy_fl_read_book)
    FrameLayout zy_fl_read_book;
    @ViewInject(R.id.zy_fl_book)
    FrameLayout zy_fl_book;

    private Book book;//最重要的数据,当前正在看的书
    private ArrayList<Catalog> bookCatalog;//书的章节目录表
    private boolean isUnzip=false;
    private PopupWindow bottomMenu;
    private  ReadBookViewHolder viewHolder;
    private boolean dayOrnight=true;
    private SharedPreferences sharedPreference;
    private SharedPreferences.Editor editor;
    private  String abFileDir=Environment.getExternalStorageDirectory()+"/zhiyou/books/";
    private  MDbUtil dbUtil;
    private LocalBook localBook;
    private String path;//文件的绝对路径
    private String fileType;


    private int curGuidePerfencePosition;//当前章节信息

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        ViewUtils.inject( this );

        Intent intent=getIntent();
        fileType=intent.getType();
        Uri uri=intent.getData();
        path=uri.getAuthority()+uri.getPath();//文件的绝对路径
        sharedPreference=getSharedPreferences("book",MODE_PRIVATE);
        editor=sharedPreference.edit();

        bookCatalog=new ArrayList<Catalog>();

        File file=new File( path );
        String fileDir=path.substring(path.lastIndexOf("/")+1,path.lastIndexOf("."));//文件目录
        abFileDir=abFileDir+fileDir;
        //查询本地数据库中，看看是否有
         dbUtil=MDbUtil.newInstance(ReadBookActivity.this);
         localBook =new LocalBook(FileTool.getFileName(file.getName()),FileTool.getFileType(path),String.valueOf(FileTool.getFileImage(path)),path);
         insertNewBook(localBook);//向本地数据库中插入新的数据
        this.readBook(file);
        contentViewAction();

    }
   private void readBook(File file){
       switch (fileType){
           case "epub":
               if(!isUnzip){
                   FileTool.ReadZipFile(path, Environment.getExternalStorageDirectory()+"/zhiyou/books/");//将文件解压到某个目录，待优化
                   isUnzip=true;
               }
               readEpub(file,localBook);
               break;
           case "txt":
                readTxt();
               break;
           case "html":
               readTxt();
               break;
           case "mobi":
               noSupport();
               break;


       }
   }
    private void readEpub(File file,LocalBook localBook){
        try{
          if(file.exists()){
              FileInputStream inputStream = new FileInputStream(file);
              book=(new EpubReader()).readEpub( inputStream );
              //更新数据库中书籍的图片
              localBook.icon=abFileDir+"/OPS/"+book.getCoverImage().getHref();
              dbUtil.update(localBook);
              //获取书籍的目录
              String href=book.getOpfResource().getHref();
              String parentDir="";
              if(href.indexOf("/")>0){
                  parentDir=href.substring(0,href.lastIndexOf("/")+1);
              }
              //获取书籍的章节信息
               ArrayList<SpineReference> bookReferences= (ArrayList<SpineReference>) book.getSpine().getSpineReferences();

               for(int i=0;i<bookReferences.size();i++){
                   SpineReference reference=bookReferences.get(i);
                     Catalog catalog=new Catalog();
                     catalog.setId(i);
                      Resource resource=reference.getResource();

                     catalog.setHref(parentDir+resource.getHref());
                     catalog.setTitle(resource.getTitle()!=null?resource.getTitle():resource.getHref());
                     catalog.setType(FileTool.getFileType(resource.getHref()));
                     bookCatalog.add(catalog);
               }

              curGuidePerfencePosition=sharedPreference.getInt("chapter",0);//获取上一次看到某一个章节的记录
              String progressHref=bookCatalog.get(curGuidePerfencePosition).getHref();
              loadUrlData(progressHref);
          }else{
              Snackbar.make(zy_fl_read_book,"没有电子书",Snackbar.LENGTH_SHORT).show();
          }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void readPdf(){
    }
    /**
     * 阅读txt文件
     * @param file
     */
    private void readTxt(){
        this.webViewSetting();
        book_content.loadUrl(path);
    }

    /**
     * 不支持的电子书格式提示
     */
    private void noSupport(){
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setTitle("提示").setMessage("暂时不支持的电子书,点击返回按钮返回").setNegativeButton("返回", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                gotoBookShelf();
            }
        }).show();
    }
    @SuppressLint("NewApi")
    private void loadUrlData(String href){
        webViewSetting();
        String phref=abFileDir+"/"+href;
        test("path:"+phref);
        Uri uri=Uri.fromFile(new File(phref));
        book_content.loadUrl(uri.toString());
    }
    //web设置
    private void webViewSetting(){
        book_content.getSettings().setJavaScriptEnabled(true);
        book_content.getSettings().setDomStorageEnabled(true);
        book_content.getSettings().setSupportZoom(true);
        book_content.getSettings().setDefaultTextEncodingName("UTF-8");
        book_content.getSettings().setSupportZoom(true);
    }


   @OnLongClick(R.id.book_content)
   public void OnLongClick(View v){
        System.out.println("长按复制");
   }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int height=DisplayMerticsTool.getWindowHeight(getActivity());
        int width=DisplayMerticsTool.getWindowWidth(getActivity());
        boolean isPoint=false;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                isPoint=true;
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                float pointX=event.getX();
                float pointY=event.getY();
                if((0<pointX&&pointX<width/3*2)&&(pointY<height/5&&pointY>0)){//顶部1/5显示上一页
                        this.prevPage();
                }
                if((0<pointX&&pointX<width/3)&&(pointY<height/5*4&&pointY>0)){//左边1/3显示上一页
                        this.prevPage();
                }
                if((width/3*2<pointX&&pointX<width)&&(pointY<height&&pointY>0)){//右边1/3显示下一页
                        this.nextPage();
                }
                if((width/3<pointX&&pointX<width)&&(pointY<height&&pointY>width/5*4)){//下边1/5显示下一页
                    this.nextPage();
                }
                if(pointX>width/3&&pointX<width/3*2&&pointY>height/5&&pointY<height/5*4){//点击中间部分
                        this.showPopMenu();
                }

                break;
        }
        return super.onTouchEvent(event);
    }

    private void prevPage(){
        switch (fileType){
            case "epub":
                  prevPreference();
                break;

        }
    }
    private void nextPage(){
        switch (fileType){
            case "epub":
                nextPreference();
                break;

        }
    }
//epub电子书显示上一页
     private void prevPreference(){
         if(curGuidePerfencePosition>1){
             curGuidePerfencePosition=curGuidePerfencePosition-1;
             String href=bookCatalog.get(curGuidePerfencePosition).getHref();
             loadUrlData(href);
             editor.putInt("chapter",curGuidePerfencePosition).commit();

         }
     }
    //epub电子书显示下一页
    private void nextPreference(){
        if(curGuidePerfencePosition<bookCatalog.size()-1){
            curGuidePerfencePosition=curGuidePerfencePosition+1;
            String href=bookCatalog.get(curGuidePerfencePosition).getHref();
            loadUrlData(href);
            editor.putInt("chapter",curGuidePerfencePosition).commit();
        }
    }
    //移除左边的菜单和底部的菜单
    private void contentViewAction(){
        book_content.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
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

        }
        public void goToCatalog(){
            int width=DisplayMerticsTool.getWindowWidth(getActivity());

              zy_fl_read_book.setLayoutParams(new FrameLayout.LayoutParams(width/5*4, ViewGroup.LayoutParams.MATCH_PARENT));
              ft=getSupportFragmentManager().beginTransaction();
              ft.setCustomAnimations(R.anim.slide_left_enter,R.anim.slide_left_out);
              bookLeftMenu=BookLeftMenu.newInstance();

               Bundle bundle=new Bundle();
               bundle.putSerializable("guide", bookCatalog);
               bundle.putString("bookname",localBook.getName());
               bookLeftMenu.setArguments(bundle);

               BookLeftMenu.setOnCatalogSelectListener(new BookLeftMenu.OnCatalogSelectListener() {
                    @Override
                    public void onClick(Catalog catalog) {
                        loadUrlData( catalog.getHref());
                        curGuidePerfencePosition=catalog.getId();
                        editor.putInt("chapter",catalog.getId()).commit();

                    }
                });
              ft.replace(R.id.zy_fl_read_book,bookLeftMenu,null).commit();

             bottomMenu.dismiss();
        }
        //主题设置
        public void goToThemeSet(){
                int height=DisplayMerticsTool.getWindowHeight(getActivity());

            zy_fl_read_book.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height/2,Gravity.BOTTOM));
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
                            editor.putBoolean("dayornight",false).commit();
                            dayOrnight=false;
                        }else{//黑夜
                            dayOrNight.setImageResource(R.drawable.ic_setting_day);
                            dayOrNightText.setText(R.string.book_setting_day);
                            book_content.setBackgroundColor(Color.parseColor("#CCFFFFFF"));
                            editor.putBoolean("dayornight",true).commit();
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
    public void insertNewBook(LocalBook localBook){
        Class<?> tableName=localBook.getClass();
           DbUtils dbUtils= dbUtil.newInstance(getActivity()).getDbUtils();
        try {
            if(dbUtils.tableIsExist(tableName)){
                  Selector selector=Selector.from(tableName).where("name","=",localBook.getName());
                   LocalBook tmpBook= dbUtils.findFirst(selector);
                  if(tmpBook==null){
                      dbUtils.save(localBook);
                  }
            }else{
                 dbUtils.createTableIfNotExist(tableName);
                dbUtils.save(localBook);

            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 跳到书架
     */
    private void gotoBookShelf(){
        Bundle bundle=new Bundle();
        bundle.putInt("tab",2);
        bundle.putInt("fg",1);
        goActivity(MainActivity.class);
    }

}

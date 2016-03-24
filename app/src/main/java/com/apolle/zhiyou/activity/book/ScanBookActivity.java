package com.apolle.zhiyou.activity.book;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import com.apolle.zhiyou.Model.FileDir;
import com.apolle.zhiyou.Model.LocalBook;
import com.apolle.zhiyou.R;
import com.apolle.zhiyou.Tool.BookScan;
import com.apolle.zhiyou.Tool.DisplayMerticsTool;
import com.apolle.zhiyou.Tool.FileTool;
import com.apolle.zhiyou.activity.BaseActivity;
import com.apolle.zhiyou.activity.MainActivity;

import com.apolle.zhiyou.mUtil.MDbUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnItemClick;
import com.rey.material.widget.Button;
import com.rey.material.widget.ListView;
import com.rey.material.widget.RadioButton;
import com.rey.material.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class ScanBookActivity extends BaseActivity {

    @ViewInject(R.id.homeup)
    TextView homeup;
    @ViewInject(R.id.scan_path)
       TextView scan_path;
    @ViewInject( R.id.local_dir )
    ListView local_dir;
    @ViewInject( R.id.local_up )
    Button local_up;
    @ViewInject( R.id.scanBtn )
    FloatingActionButton scanBtn;
    @ViewInject( R.id.layout )
    FrameLayout layout;
    @ViewInject( R.id.book_rl_scan )
    RelativeLayout book_rl_scan;

    private ArrayList<FileDir> fileDirs;
    private File scanFile;//要扫描的文件和文件夹
    private static final String DIRECTORY_SEPARATOR= "/";
    private  DirAdapter adapter;
    private boolean isAddFile;//是否添加文件
    private ArrayList<File> scanFiles;
    private ArrayList<LocalBook> books;
    private  TextView scanState;
    private AsyncTask  mytask;
    private   PopupWindow popup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        ViewUtils.inject(this);
            scanBtn=(FloatingActionButton) findViewById( R.id.scanBtn);

       if(Environment.getExternalStorageState().equals( Environment.MEDIA_MOUNTED )){//存储卡存在
             File sdPath=Environment.getExternalStorageDirectory();
              fileDirs=new ArrayList<FileDir>();
              scan_path.setText(sdPath.getPath());
              fileDirs=scanDir(sdPath.getAbsolutePath());
               adapter=new DirAdapter();
               System.out.println("数据"+fileDirs);
               local_dir.setAdapter(adapter);
        }

    }

    @Override
    public AppCompatActivity getActivity() {
        return ScanBookActivity.this;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_scan_book;
    }

    private ArrayList<FileDir> scanDir(String path){
        return BookScan.scanDir(path);
    }
    public class DirAdapter extends BaseAdapter{
        public  DirAdapter(){
            scanFiles=new ArrayList<File>();
        }
        @Override
        public int getCount() {
            return fileDirs.size();
        }

        @Override
        public Object getItem(int position) {
            return fileDirs.get( position );
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder=new ViewHolder();
              if(null==convertView){
                  LayoutInflater inflater=(LayoutInflater) getSystemService( LAYOUT_INFLATER_SERVICE );
                  convertView=inflater.inflate( R.layout.simp_dir_item,null);
                  viewHolder.icon=(ImageView) convertView.findViewById( R.id.fileImage );
                  viewHolder.fileNameText=(TextView) convertView.findViewById( R.id.filename );
                  viewHolder.checkBox=(RadioButton) convertView.findViewById( R.id.checkfile);
                  convertView.setTag(viewHolder);
              }else{
                  viewHolder=(ViewHolder) convertView.getTag();
              }
            final FileDir fileDir=fileDirs.get( position );

            viewHolder.fileNameText.setText(fileDir.getName());
            switch (fileDir.type){//文件夹
                case "folder":
                    viewHolder.icon.setImageResource( R.mipmap.file_type_folder);
                    break;
                case "chm":
                    viewHolder.icon.setImageResource( R.mipmap.file_type_chm);
                    break;
                case "pdf":
                    viewHolder.icon.setImageResource( R.mipmap.file_type_pdf);
                    break;
                case "epub":
                    viewHolder.icon.setImageResource( R.mipmap.file_type_epub);
                    break;
                case "ebk":
                    viewHolder.icon.setImageResource( R.mipmap.file_type_ebk);
                    break;
                case "txt":
                    viewHolder.icon.setImageResource( R.mipmap.file_type_txt);
                    break;
                case "umd":
                    viewHolder.icon.setImageResource( R.mipmap.file_type_umd);
                    break;
                case "mobi":
                    viewHolder.icon.setImageResource( R.mipmap.file_type_mobi);
                    break;
                default:
                    viewHolder.icon.setImageResource( R.mipmap.file_type_other);
                    break;
            }

            viewHolder.checkBox.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if(isChecked){
                        for (int i=0;i<local_dir.getChildCount();i++){
                             View myView=local_dir.getChildAt(i);
                             RadioButton radio=(RadioButton)myView.findViewById( R.id.checkfile );
                             radio.setChecked(false);
                        }
                        String path=scan_path.getText().toString()+DIRECTORY_SEPARATOR+fileDir.name;
                        buttonView.setChecked(true);
                        File file=new File(path);
                        scanFile=file;
                        local_dir.refreshDrawableState();

                    }else{
                        buttonView.setChecked(false);
                    }
                }
            } );

            return convertView;
        }
        public void refreshData(ArrayList<FileDir> data){
              fileDirs=data;
              notifyDataSetChanged();
        }
    }
      public class ViewHolder{
            public TextView fileNameText;
            public ImageView icon;
            public RadioButton checkBox;


      }

    @OnClick({R.id.homeup,R.id.local_up,R.id.scanBtn})
    public void ClickView(View view){
        switch (view.getId()){
            case R.id.homeup:
                Intent intent=  new Intent( ScanBookActivity.this,MainActivity.class );
                startActivity( intent );
                break;
            case R.id.local_up:
                String path=scan_path.getText().toString();
                  File file=new File( path );
                  String parent =file.getParent();
                  File parentFile=new File( parent );
                  scan_path.setText( parentFile.getAbsolutePath());
                  fileDirs=scanDir( parentFile.getAbsolutePath() );
                  scanFiles=new ArrayList<File>();
                  adapter.notifyDataSetChanged();
                break;
            case R.id.scanBtn:
                  if(null==scanFile){
                      Snackbar.make(layout,R.string.noSelectFile,Snackbar.LENGTH_SHORT).show();
                  }else{
                      if(scanFile.isFile()){
                          goToReadBookActivity(scanFile.getAbsolutePath());
                      }else{
                          initPopupWindowView();
                      }
                  }
                  break;
        }
    }
    private void initPopupWindowView(){
        int winWidth=DisplayMerticsTool.getWindowWidth(ScanBookActivity.this);
        int winHeight=DisplayMerticsTool.getWindowHeight( ScanBookActivity.this);
       if(null==popup){
           popup= new PopupWindow(ScanBookActivity.this);
       }

        if(!popup.isShowing()){
            final FrameLayout frame=new FrameLayout(this);
            FrameLayout.LayoutParams layoutParams=new FrameLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT );
            frame.setLayoutParams( layoutParams );
            frame.setBackgroundColor(Color.parseColor("#66ff0000"));

            book_rl_scan.addView(frame,layoutParams);
            book_rl_scan.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
            popup.setAnimationStyle( R.anim.pop_enter_top_anim);
            View popView=LayoutInflater.from( ScanBookActivity.this ).inflate(R.layout.popup_scan_item,null);
            popup.setContentView(popView);
            popup.setWidth(winWidth);
            int popuHeight=winHeight/6;
            popup.setHeight(popuHeight);
            popup.setFocusable(false);
            int[] location=new int[2];
            scanBtn.getLocationInWindow(location);
            Animation anim=new TranslateAnimation(0,0,location[1],location[1]-popuHeight-10);
            anim.setDuration(200);
            scanBtn.setAnimation(anim);
            scanBtn.startAnimation( anim );
            popup.showAtLocation(layout, Gravity.BOTTOM,0,0);
            Button cancelBtn=(Button)popView.findViewById( R.id.cancel_btn);
            if(null!=scanFile){
                scanState=(TextView) popView.findViewById( R.id.scan_topic);
                System.out.println("扫描路径"+scanFile.getAbsolutePath());
                scanBook(scanFile.getAbsolutePath());
            }
            cancelBtn.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(popup.isShowing()){
                            popup.setAnimationStyle( R.anim.pop_exit_top_anim);
                            popup.dismiss();
                           book_rl_scan.removeView(frame);
                           book_rl_scan.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
                          if(null!=mytask){
                              mytask.cancel(true);
                          }
                    }
                }
            } );

        }

    }
    @OnItemClick( R.id.local_dir)
     public void ItemClick(AdapterView<?> parent, View view, int position, long id){
        FileDir fileDir=fileDirs.get( position );
        File file= new File( scan_path.getText().toString()+DIRECTORY_SEPARATOR+fileDir.getName());

        if(fileDir.getType()=="folder"){
            scan_path.setText( file.getAbsolutePath() );
            fileDirs=scanDir( file.getAbsolutePath());
            adapter.refreshData(fileDirs);
        }else{
            goToReadBookActivity(file.getAbsolutePath());
        }
    }

      public void scanBook(final String scanPath){
           mytask=new AsyncTask<String,
                    LocalBook,
                    ArrayList<LocalBook>>(){

                @Override
                protected ArrayList<LocalBook> doInBackground(String... params) {

                      books=  BookScan.getInstance().getAllBook((String)params[0], new BookScan.onScanListener() {
                            @Override
                            public void onScan(LocalBook book) {
                                publishProgress(book);
                                Log.i("开始扫描",book.getPath());
                                System.out.println("开始扫描");
                            }
                        }, new BookScan.OnScanCompletedListener() {
                            @Override
                            public void onCompleted(ArrayList<LocalBook> books) {
                                System.out.println("结束"+books);
                            }
                        });

                    return books;
                }

                @Override
                protected void onProgressUpdate(LocalBook... book) {
                    if (null != scanState) {
                        scanState.setText( "正在扫描" + book[0].getPath() );
                    }
                }

                @Override
                protected void onPostExecute(ArrayList<LocalBook> books) {
                    if (null != scanState) {
                        scanState.setText( "总共扫描" + books.size());
                    }
                    popup.setAnimationStyle( R.anim.pop_exit_top_anim);
                    popup.dismiss();
                    saveBookToDb(books);
                    gotoBookShelf();
                }

                @Override
                protected void onCancelled() {
                    if (null != scanState) {
                        scanState.setText( "正在取消");
                        popup.setAnimationStyle( R.anim.pop_exit_top_anim);
                        popup.dismiss();
                    }
                    super.onCancelled();
                }
            }.execute(scanPath);
      }

    @Override
    protected void onPause() {
        if(null!=popup){
            popup.dismiss();
        }
        super.onPause();
    }

    private void saveBookToDb(ArrayList<LocalBook> books){
         MDbUtil.newInstance( ScanBookActivity.this ).saveNoInsert(books,"path");
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
    private void goToReadBookActivity(String path){
         Intent intent=new Intent( ScanBookActivity.this,ReadBookActivity.class );
        intent.addCategory( "android.intent.action.view" );
        String type=FileTool.getFileType(path);
        intent.setDataAndType(Uri.fromFile(new File( path ) ),type);
        startActivity(intent);
        finish();
    }

}

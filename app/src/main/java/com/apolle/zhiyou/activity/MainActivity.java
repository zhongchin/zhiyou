package com.apolle.zhiyou.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.apolle.zhiyou.Model.User;
import com.apolle.zhiyou.R;
import com.apolle.zhiyou.Tool.LruImageCache;
import com.apolle.zhiyou.Tool.SharedPreTool;
import com.apolle.zhiyou.fragment.BookFragment;
import com.apolle.zhiyou.fragment.ChatFragment;

import com.apolle.zhiyou.fragment.HomeFragment;
import com.apolle.zhiyou.fragment.NoteFragment;
import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import nl.siegmann.epublib.epub.Main;

public class MainActivity extends BaseActivity implements  NavigationView.OnNavigationItemSelectedListener,View.OnClickListener{

    @ViewInject(R.id.zy_ll_square)
    LinearLayout zy_ll_square;
    @ViewInject(R.id.zy_img_square)
    ImageView zy_img_square;
    @ViewInject(R.id.zy_text_square)
    TextView zy_text_square;

    @ViewInject(R.id.zy_ll_book)
    LinearLayout zy_ll_book;
    @ViewInject(R.id.zy_img_book)
    ImageView zy_img_book;
    @ViewInject(R.id.zy_text_book)
    TextView zy_text_book;

    @ViewInject(R.id.zy_ll_note)
    LinearLayout zy_ll_note;
    @ViewInject(R.id.zy_img_note)
    ImageView zy_img_note;
    @ViewInject(R.id.zy_text_note)
    TextView zy_text_note;


    @ViewInject(R.id.zy_ll_news)
    LinearLayout zy_ll_news;
    @ViewInject(R.id.zy_img_news)
    ImageView zy_img_news;
    @ViewInject(R.id.zy_text_news)
    TextView zy_text_news;


    @ViewInject(R.id.appbar)
     AppBarLayout appbar;
    @ViewInject(R.id.drawer_layout)
     DrawerLayout drawer_layout;
    @ViewInject(R.id.toolbar)
     Toolbar toolbar;
    //左侧导航
    BootstrapCircleThumbnail user_headpic;
    TextView nickname;


    private Bundle globalArg;
    FragmentManager fg;
    FragmentTransaction ft;
    private int choiceIndex=-1;
    public Fragment [] fragments;
    private HomeFragment homeFragment;
    private BookFragment bookFragment;
    private NoteFragment noteFragment;
    private ChatFragment chatFragment;

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject( this );
        fg=getSupportFragmentManager();
        fragments=new Fragment[4];
        if(null==savedInstanceState) {
            ViewOnClick(zy_ll_square);
        }
        Intent intent=getIntent();
        globalArg=intent.getExtras();
        if(globalArg!=null){
            goTab(globalArg.getInt("tab"));
        }
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer_layout, toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer_layout.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView=(NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //为用户头像和nickname注册点击事件
        View headerView=navigationView.getHeaderView(0);
        user_headpic= (BootstrapCircleThumbnail) headerView.findViewById(R.id.user_headpic);
        nickname= (TextView) headerView.findViewById(R.id.nickname);
        userInfo();
    }

    /**
     * 如果用户已经登录
     * 显示用户头像和用户nickname;
     */
    private void userInfo(){
        RequestQueue requestQueue =Volley.newRequestQueue(MainActivity.this);
        ImageLoader imageLoader=new ImageLoader(requestQueue,new LruImageCache());

        if(SharedPreTool.getIsLogin(MainActivity.this)){
            ImageLoader.ImageListener listener=imageLoader.getImageListener(user_headpic,R.drawable.user_headpic,R.drawable.user_error);
            User user=SharedPreTool.getUser(MainActivity.this);
            if(user.getHeadpic()!=null&&user.getHeadpic()!=""){
                imageLoader.get(user.getHeadpic(),listener);
            }
            String username= user.getNickname();
            if(username.length()<1) username=user.getUsername();
            nickname.setText(username);
        }
        user_headpic.setOnClickListener(this);
        nickname.setOnClickListener(this);
    }
     private void goTab(int tab){
         switch (tab){
             case 1:
                 ViewOnClick(zy_ll_square);
                 break;
             case 2:
                 ViewOnClick(zy_ll_book);
                 break;
             case 3:
                 ViewOnClick(zy_ll_note);
                 break;
             case 4:
                 ViewOnClick(zy_ll_news);
                 break;
         }
     }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }
    @OnClick({R.id.zy_ll_square,R.id.zy_ll_book,R.id.zy_ll_news,R.id.zy_ll_note})
    public void ViewOnClick(View view){
        ft= fg.beginTransaction();
        Bundle arguments=new Bundle();

        switch (view.getId()){
                case R.id.zy_ll_book:
                    if(1!=choiceIndex){
                            bookFragment=BookFragment.getBookFragment();
                            fragments[1]=bookFragment;
                            ft.replace( R.id.zy_home_container,bookFragment,"book").commit();
                            tabBgChange(1);
                            choiceIndex=1;
                        }
                    break;
                case R.id.zy_ll_note:
                    if(2!=choiceIndex){
                        noteFragment=NoteFragment.getNoteFragment();
                        fragments[2]=noteFragment;
                        ft.replace( R.id.zy_home_container,noteFragment,"note").commit();
                        tabBgChange(2);
                        choiceIndex=2;
                    }
                    break;
                case R.id.zy_ll_news:
                    if(3!=choiceIndex){
                        chatFragment=ChatFragment.getChatFragment();
                        fragments[3]=chatFragment;
                        ft.replace( R.id.zy_home_container,chatFragment,"chat").commit();
                        tabBgChange(3);
                        choiceIndex=3;
                    }

                    break;
                case R.id.zy_ll_square:
                default:
                    if(0!=choiceIndex){
                        homeFragment=HomeFragment.getHomeFragment();
                        fragments[0]=homeFragment;
                        ft.replace( R.id.zy_home_container,homeFragment,"home").commit();
                        tabBgChange(0);
                        choiceIndex=0;
                    }
                    break;
        }

    }
    private void tabBgChange(int index){
        resetBgColor();
        switch (index){
            case 0:
                zy_img_square.setSelected(true);
                zy_text_square.setTextColor(getResources().getColor(R.color.checkBgColor));

                break;
            case 1:
                zy_img_book.setSelected(true);
                zy_text_book.setTextColor(getResources().getColor(R.color.checkBgColor));
                break;
            case 2:
                zy_img_note.setSelected(true);
                zy_text_note.setTextColor(getResources().getColor(R.color.checkBgColor));
                break;
            case 3:
                zy_img_news.setSelected(true);
                zy_text_news.setTextColor(getResources().getColor(R.color.checkBgColor));
                break;
        }
    }
    private void resetBgColor(){
            zy_img_square.setSelected(false);
            zy_text_square.setTextColor(getResources().getColor(R.color.nocheckBgColor));
            zy_img_book.setSelected(false);
            zy_text_book.setTextColor(getResources().getColor(R.color.nocheckBgColor));
            zy_img_note.setSelected(false);
            zy_text_note.setTextColor(getResources().getColor(R.color.nocheckBgColor));
            zy_img_news.setSelected(false);
            zy_text_news.setTextColor(getResources().getColor(R.color.nocheckBgColor));
    }
    @OnClick({R.id.user_headpic,R.id.nickname})
    public void bindLoginEvent(View view){
       switch (view.getId()){
           case R.id.user_headpic:
           case R.id.nickname:
                    test("hello ");
               if(SharedPreTool.getIsLogin(MainActivity.this)){//检查是否登录
                    test("已经登录");
               }else{
                   Intent intent =  new Intent(MainActivity.this,LoginActivity.class);
                   startActivity(intent);
               }
               break;
       }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
           switch (item.getItemId()){
               case R.id.logout:
                    SharedPreTool.userLogout(getActivity());
                    goActivity(LoginActivity.class);
                   break;
           }
        return false;
    }


    @Override
    public AppCompatActivity getActivity() {
        return MainActivity.this;
    }

    @Override
    public void onClick(View v) {
        bindLoginEvent(v);
    }

    //点击两次退出应用
    private long firstTime=0;
    @Override
    public void onBackPressed() {
        long secondTime= System.currentTimeMillis();
        //如果两次按键时间大于1000毫秒，则不退出
        if(secondTime-firstTime>1000){
          toast("再按一次退出应用");
            firstTime=secondTime;
        }else{
            finish();
        }
    }

}

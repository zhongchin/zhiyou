package com.apolle.zhiyou.activity;

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
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apolle.zhiyou.R;
import com.apolle.zhiyou.fragment.BaseFragment;
import com.apolle.zhiyou.fragment.BookFragment;
import com.apolle.zhiyou.fragment.ChatFragment;

import com.apolle.zhiyou.fragment.HomeFragment;
import com.apolle.zhiyou.fragment.NoteFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class MainActivity extends BaseActivity implements  NavigationView.OnNavigationItemSelectedListener{

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


    FragmentManager fg;
    FragmentTransaction ft;
    private int choiceIndex=0;
    public  boolean ToolbarIsShow=false;
    public Fragment [] fragments;
    private HomeFragment homeFragment;
    private BookFragment bookFragment;
    private NoteFragment noteFragment;
    private ChatFragment chatFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject( this );
        fg=getSupportFragmentManager();

        fragments=new Fragment[4];
        if(null==savedInstanceState) {
            ViewOnClick(zy_ll_square);
        }
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer_layout, toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer_layout.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView=(NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//        toolbar.getViewTreeObserver().addOnGlobalLayoutListener();

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
    private void  hideFragment(int index){
          if(null!=fragments&&fragments.length>0){
              for (int i=0;i<fragments.length;i++) {
                  if(i!=index){
                      ft.hide(fragments[i]);
                  }
              }
//              ft.commit();
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


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }
    /**
     * 处理向上拉操作
     */
    public  void showToolBar(){
        appbar.animate().translationY(0).setInterpolator(new AccelerateDecelerateInterpolator());
        ToolbarIsShow=true;
    }

    @Override
    public AppCompatActivity getActivity() {
        return MainActivity.this;
    }

    /**
     * 处理向下拉操作
     */
    public  void hideToolBar(){
        appbar.animate().translationY(-toolbar.getHeight()).setInterpolator(new AccelerateDecelerateInterpolator());
        ToolbarIsShow=false;
    }
    public interface onBottomTabChangeListener{
           void onBottomTabChange(Toolbar toolbar);
    }

}

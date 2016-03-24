package com.apolle.zhiyou.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.apolle.zhiyou.Http.ChatConcactAction;
import com.apolle.zhiyou.Http.ChatNewsAction;

import com.apolle.zhiyou.Http.ChatTrendAction;
import com.apolle.zhiyou.Model.Article;
import com.apolle.zhiyou.Model.NewBean;
import com.apolle.zhiyou.Model.Person;
import com.apolle.zhiyou.R;
import com.apolle.zhiyou.adapter.ChatConcactListAdapter;
import com.apolle.zhiyou.adapter.ChatNewListAdapter;
import com.apolle.zhiyou.adapter.ConcactExpandListAdapter;
import com.apolle.zhiyou.adapter.ThrendListAdapter;
import com.apolle.zhiyou.interactor.NetUrl;
import com.apolle.zhiyou.view.SlideListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Delayed;

public class ChatContentFragment extends BaseFragment {

    private static final String ARG_COLUMN_INDEX = "column-index";
    private static final String TAB_TITLE= "";
    private int[] layouts=new int[]{R.layout.fragment_chat_news,R.layout.fragment_chat_concact,R.layout.fragment_chat_trend};
    private View rootView;
    private Bundle globalArgs;
    private PullToRefreshListView chatNews,chatTrend;
    private ExpandableListView chatConcact;
    private ChatNewListAdapter newAdapter;


    public ChatContentFragment() {
    }

    public static ChatContentFragment newInstance(int columnIndex,String title) {
        ChatContentFragment fragment = new ChatContentFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_INDEX, columnIndex);
        args.putString(TAB_TITLE,title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
         globalArgs =getArguments();
        String tabTitle=globalArgs.getString(TAB_TITLE);
        int position=globalArgs.getInt(ARG_COLUMN_INDEX);
        rootView = inflater.inflate(getCurLayoutId(position), container, false);

        return rootView;
    }

    private int getCurLayoutId(int position){
        return layouts[position];
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        int position=globalArgs.getInt(ARG_COLUMN_INDEX);
        if(position==0){
            initChatInfo();
        }else if(position==1){
            initChatConcact();
        }else if(position==2){
             initChatNewest();
        }

    }

    /**
     * 显示消息界面的内容
      */
    private int newOffset=0;
    private void initChatInfo(){
         chatNews= (PullToRefreshListView) rootView.findViewById(R.id.chat_news);
         chatNews.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
         ArrayList<NewBean> news = new ArrayList<NewBean>();
         newAdapter =new ChatNewListAdapter(getContext(),news);
         chatNews.setAdapter(newAdapter);
          getChatNews(newOffset);

          chatNews.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
              @Override
              public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                     newOffset++;
                    getChatNews(newOffset);
                   chatNews.postDelayed(new Runnable() {
                       @Override
                       public void run() {
                           chatNews.onRefreshComplete();
                       }
                   },1000);
              }
          });
    }
    public void getChatNews(final int offset){
        HashMap<String,String> params= NetUrl.initParams();
        params.put("offset",String.valueOf(offset));
        ChatNewsAction.getNewList(getActivity(), params, new ChatNewsAction.NewsResponseCallback() {
            @Override
            public void onSuccess(ArrayList<? extends Serializable> infos) {
                 ArrayList<NewBean> news= (ArrayList<NewBean>) infos;
                if(offset==0){
                    test("获取最新的消息"+news);
                    newAdapter.addItems(news);
                 }else{
                    newAdapter.addItems(news);
                 }
            }

            @Override
            public void onFail(int code, String error) {
                toast(error);
            }
        });
    }

    /**
     * 显示最近联系人界面
     */
    private SlideListView slideBar;
    private  ConcactExpandListAdapter expandListAdapter;
    private TextView mDialog;
    private void initChatConcact(){
        chatConcact= (ExpandableListView) rootView.findViewById(R.id.chat_concact);
        chatConcact.setGroupIndicator(null);
        getConcactList();
        slideBar= (SlideListView) rootView.findViewById(R.id.slideBar);
        mDialog= (TextView) rootView.findViewById(R.id.select_dialog);
        slidebarListView();
    }
    public void getConcactList(){
        HashMap<String,String> params=new HashMap<String,String>();
        ChatConcactAction.getMyFriends(context, params, new ChatConcactAction.ConcactResponseCallback() {
            @Override
            public void onSuccess(List<? extends Object> obj) {
                    ArrayList<Person> persons= (ArrayList<Person>) obj;
                    test(obj);
                    expandListAdapter =new ConcactExpandListAdapter(getActivity(),persons);
                    chatConcact.setAdapter(expandListAdapter);
            }

            @Override
            public void onFail(int code, String error) {

            }
        });
    }

    private void slidebarListView(){
       /* PopupWindow popupWindow=new PopupWindow(getActivity());
        popupWindow.setHeight(80);
        popupWindow.setWidth(80);
        dialog=new TextView(getActivity());
        dialog.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        dialog.setAlpha(1);
        popupWindow.setContentView(dialog);
        popupWindow.showAtLocation(getView(), Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL,0,0);*/

        slideBar.setTextView(mDialog);
        slideBar.setOnTouchChangedListener(new SlideListView.OnTouchChangedListener() {
            @Override
            public void onTouchChange(String s) {

                System.out.println("右侧slideListView"+s);
                int index=expandListAdapter.getPersonIndex(s);
                chatConcact.setSelected(true);
                chatConcact.setSelection(index);
            }
        });
    }

    private ThrendListAdapter threndListAdapter;
    private int TrendOffset=0;
    private ArrayList<Article> articles;
    /**
     * 显示最近关注的好友分享的笔记
     */
    private void initChatNewest(){
        chatTrend= (PullToRefreshListView) rootView.findViewById(R.id.chat_trend);
        articles=new ArrayList<Article>();
        threndListAdapter=new ThrendListAdapter(getActivity(), articles);
         chatTrend.setAdapter(threndListAdapter);
         requestTrendList(TrendOffset);
         chatTrend.setMode(PullToRefreshBase.Mode.PULL_FROM_START);

         chatTrend.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
             @Override
             public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                 TrendOffset++;
                 requestTrendList(TrendOffset);
                 chatTrend.postDelayed(new Runnable() {
                     @Override
                     public void run() {
                              chatTrend.onRefreshComplete();
                     }
                 },2000);
             }
         });
    }
    private void  requestTrendList(int  offset){
         HashMap<String,String> params=NetUrl.initParams();
         params.put("num","10");
         params.put("offset",String.valueOf(offset));
         test("trendoffset"+offset);
         ChatTrendAction.getTrendList(getActivity(), params, new ChatTrendAction.TrendResponseCallback() {
             @Override
             public void onSuccess(ArrayList<? extends Serializable> message) {
                 test("trend"+message);
                 if(TrendOffset==0){
                     threndListAdapter.addItems((ArrayList<Article>) message);
                     chatTrend.setAdapter(threndListAdapter);
                 }else{
                     threndListAdapter.addItems((ArrayList<Article>) message);
                 }

             }
             @Override
             public void onFail(int code, String error) {
                 toast(error);
             }
         });
    }
}

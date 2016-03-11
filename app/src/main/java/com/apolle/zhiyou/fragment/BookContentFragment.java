package com.apolle.zhiyou.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.apolle.zhiyou.Model.LocalBook;
import com.apolle.zhiyou.R;
import com.apolle.zhiyou.Tool.BookScan;
import com.apolle.zhiyou.Tool.DisplayMerticsTool;
import com.apolle.zhiyou.activity.book.ReadBookActivity;
import com.apolle.zhiyou.activity.book.ScanBookActivity;
import com.apolle.zhiyou.adapter.LocalBookAdapter;
import com.apolle.zhiyou.engine.LocalBookAction;
import com.apolle.zhiyou.view.BookGirdView;


import java.io.File;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookContentFragment extends BaseFragment {

    public final static String TAB_TAG="tab_tag";

    private View rootView;
    private BookGirdView LocalBookShelf;
    private int curSelectType;//当前选择的是本地书库还是线上书库
    private ArrayList<LocalBook> books;
    private LocalBookAdapter bookAdapter;
    public BookContentFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        Bundle arguments= getArguments();
        int curSelectType=arguments.getInt(TAB_TAG);
        if(curSelectType==0){//本地书库
            rootView=inflater.inflate(R.layout.book_local_content, container, false);
            LocalBookShelf=(BookGirdView) rootView.findViewById(R.id.local_bookself);
              int height= DisplayMerticsTool.getWindowHeight(getContext());
                books= LocalBookAction.getBookList(getContext());
                bookAdapter=new LocalBookAdapter(getContext(),books);
                LocalBookShelf.setAdapter( bookAdapter);


              LocalBookShelf.setOnItemClickListener( new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    if(books.get( position).getType()=="add"){
                        Intent intent= new Intent(getContext(), ScanBookActivity.class);
                        getContext().startActivity(intent);
                    }else{
                        goToReadBookActivity( books.get( position ).getPath());
                    }
                }
            });

        }else{
            rootView=inflater.inflate(R.layout.book_opds_content, container, false);
        }
        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if(curSelectType==0){//本地书库

        }else{//线上书库

        }
        super.onActivityCreated(savedInstanceState);
    }
    private void goToReadBookActivity(String path){
        Intent intent=new Intent( getContext(),ReadBookActivity.class );
        intent.addCategory( "android.intent.action.view" );
        intent.setDataAndType( Uri.fromFile(new File( path ) ),"epub");
        startActivity(intent);

    }


}

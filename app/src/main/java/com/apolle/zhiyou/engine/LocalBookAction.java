package com.apolle.zhiyou.engine;

import android.content.Context;
import android.os.AsyncTask;

import com.apolle.zhiyou.Model.LocalBook;
import com.apolle.zhiyou.R;
import com.apolle.zhiyou.mUtil.MDbUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangtao on 2016/3/518:44.
 * modify by huangtao on 18:44
 */
public class LocalBookAction{


      public static ArrayList<LocalBook> getBookList(Context context){
            ArrayList<LocalBook> books=new ArrayList<LocalBook>();
            ArrayList<LocalBook> localBooks=(ArrayList<LocalBook>) MDbUtil.newInstance(context).fetchList( LocalBook.class );
            if(null!=localBooks&&localBooks.size()>0){
                books.addAll(localBooks);
            }
            books.add(bookAddButton());
            return books;
      }
        private static LocalBook bookAddButton(){
            return new LocalBook("","add","","");
        }
     /* public class ReadBookTask extends AsyncTask<String,Void,List<String>>{
          @Override
          protected List<String> doInBackground(String... params) {
              return null;
          }
      }*/

}

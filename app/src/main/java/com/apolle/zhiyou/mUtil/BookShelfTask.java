package com.apolle.zhiyou.mUtil;

import android.content.Context;
import android.os.AsyncTask;

import com.apolle.zhiyou.Model.LocalBook;

import java.util.ArrayList;

/**
 * Created by huangtao on 2016/3/714:56.
 * modify by huangtao on 14:56
 */
public class BookShelfTask  extends AsyncTask<Void,Void,ArrayList<LocalBook>>{
    private Context context;
    private OnPreUIExecute onPreUIExecute;
    private OnPostUIExecute onPostUIExecute;

    public BookShelfTask(Context context) {
        this.context = context;
    }

    public BookShelfTask(Context context, OnPreUIExecute onPreExecute, OnPostUIExecute onPostExecute) {
        this.context=context;
        this.onPreUIExecute=onPreExecute;
        this.onPostUIExecute=onPostExecute;
    }

    @Override
    protected ArrayList<LocalBook> doInBackground(Void... params) {
          ArrayList<LocalBook> books=(ArrayList<LocalBook>) MDbUtil.newInstance(context).fetchList( LocalBook.class );
        return books;
    }

    @Override
    protected void onPreExecute() {
        if(null!=onPreUIExecute){
            onPreUIExecute.preUiExecute();
        }else{
            super.onPreExecute();
        }
    }

    @Override
    protected void onPostExecute(ArrayList<LocalBook> books) {
        if(null!=onPostUIExecute){
            onPostUIExecute.postUiExecute(books);
        }
    }
    public interface  OnPreUIExecute{
        void preUiExecute();
    }
    public interface  OnPostUIExecute{
        void postUiExecute(ArrayList<LocalBook> books);
    }
}

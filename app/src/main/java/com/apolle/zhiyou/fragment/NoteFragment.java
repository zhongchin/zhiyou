package com.apolle.zhiyou.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apolle.zhiyou.R;
import com.lidroid.xutils.ViewUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoteFragment extends BaseFragment {

    private static NoteFragment noteFragment;

    public NoteFragment() {
        // Required empty public constructor
    }
   public static NoteFragment getNoteFragment(){
      if(null==noteFragment){
          noteFragment=new NoteFragment();
      }
       return noteFragment;
   }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_note, container, false);
        ViewUtils.inject(rootView);
        return rootView;
    }


}

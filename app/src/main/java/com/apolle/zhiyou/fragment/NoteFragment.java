package com.apolle.zhiyou.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.android.volley.VolleyError;
import com.apolle.zhiyou.Http.NotePadAction;
import com.apolle.zhiyou.Model.Note;
import com.apolle.zhiyou.Model.NotePad;
import com.apolle.zhiyou.R;
import com.apolle.zhiyou.activity.MainActivity;
import com.apolle.zhiyou.activity.SelectNodeActivity;
import com.apolle.zhiyou.activity.node.ReadNoteActivity;
import com.apolle.zhiyou.ui.NodeView;
import com.apolle.zhiyou.interactor.NetUrl;
import com.rey.material.widget.FloatingActionButton;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NoteFragment extends BaseFragment implements View.OnClickListener{
    private static final String  TAG_SHOW ="view_show";
    private static NoteFragment noteFragment;

    public NoteFragment() {
    }
   public static NoteFragment getNoteFragment(){
      if(null==noteFragment){
          noteFragment=new NoteFragment();
      }
       return noteFragment;
   }
    private View rootView;

    private RecyclerView breadcrumb;
    private ViewGroup notepadView;
    private  BreadCrumbAdapter breadCrumbAdapter;
    private ArrayList<String> tabs;
    private ArrayList<NotePad> notePads;
    private ArrayList<Note> notes;
    private FloatingActionButton addBtn,addNotepadBtn,addNoteBtn;
    private  AndroidTreeView treeView;
    private TreeNode rootTree;
    private NodeView nodeView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         rootView=inflater.inflate(R.layout.fragment_note, container, false);
         notepadView= (ViewGroup) rootView.findViewById(R.id.notepad);
        setToolBarTitle();
         rootTree=TreeNode.root();
         nodeView=new NodeView(getActivity());
         treeView=new AndroidTreeView(getActivity(),rootTree);
         renderTreeNode(0,rootTree);//页面加载第一次加载数据

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        tabs=new ArrayList<String>();
        tabs.add("root");
        System.out.println("数据"+tabs);
         breadcrumb= (RecyclerView) rootView.findViewById(R.id.breadcrumb);

        breadCrumbAdapter= new BreadCrumbAdapter(tabs);
        breadcrumb.setAdapter(breadCrumbAdapter);
        breadcrumb.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        breadcrumb.addItemDecoration(new BreadcrumbDecoration(getContext()));

        initViewClick();
        initViewItemClick();
        super.onActivityCreated(savedInstanceState);
    }

    private void renderTreeNode(int pid, final TreeNode parentNode){
        HashMap<String,String> params= NetUrl.initParams();
        params.put("pid",String.valueOf(pid));

        NotePadAction.padAndNotes(getActivity(),params,new NotePadAction.MultiRenderCallback() {
            @Override
            public void renderOnSuccess(HashMap<String, ArrayList<? extends Serializable>> content) {
                notePads= (ArrayList<NotePad>) content.get("pads");
                notes= (ArrayList<Note>) content.get("notes");

                nodeView.renderPad(parentNode,notePads);
                nodeView.renderNote(parentNode,notes);
                treeView.setDefaultAnimation(true);
                treeView.setUse2dScroll(true);
                treeView.setDefaultContainerStyle(com.unnamed.b.atv.R.style.TreeNodeStyle);
                View view=treeView.getView();
                notepadView.addView(view);
            }

            @Override
            public void renderOnFailed(VolleyError error) {
                System.out.println("服务错误"+error.getMessage());
            }
        });

        bindOnclickEvent();//为每个node绑定点击事件
    }
    private void setToolBarTitle(){
        MainActivity activity= (MainActivity) getActivity();
        com.rey.material.widget.TextView headTitle= (com.rey.material.widget.TextView) activity.findViewById(R.id.header_title);
        headTitle.setText(R.string.note);

    }
    /**
     *
     */
    private void bindOnclickEvent(){
        nodeView.setOnClickListener(new NodeView.onItemClickListener() {
            @Override
            public void onNoteClick(TreeNode node, Object value, ArrayList<Note> notes, int i) {
                    Note note= notes.get(i);
                     Bundle bundle=new Bundle();
                     bundle.putSerializable("note",note);
                     Intent intent=new Intent(getContext(), ReadNoteActivity.class);
                      intent.putExtras(bundle);
                     startActivity(intent);

            }
            @Override
            public void onNotePadClick(TreeNode node, Object value, ArrayList<NotePad> notePads, int i) {
                  NotePad notePad=notePads.get(i);
                  int pid=notePad.getNid();
                if(!node.isExpanded()){
                     List<TreeNode> childNodes=  node.getChildren();
                    if(childNodes.size()<1){//当节点下面没有数据时才去加载
                        renderTreeNode(pid,node);
                    }

                }


            }
        });
    }
    public void initViewClick(){

        addBtn= (FloatingActionButton) rootView.findViewById(R.id.addBtn);
        addNoteBtn=(FloatingActionButton)rootView.findViewById(R.id.addNote);
        addNotepadBtn=(FloatingActionButton)rootView.findViewById(R.id.addNotePad);
         Bundle tag=new Bundle();
        tag.putBoolean(TAG_SHOW,false);
        addBtn.setTag(tag);
        addBtn.setOnClickListener(this);
        addNoteBtn.setOnClickListener(this);
        addNotepadBtn.setOnClickListener(this);
    }
    private void initViewItemClick(){
        breadCrumbAdapter.setOnItemClickListener(new onItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder view, int position) {
                String tab=tabs.get(position);
                int removeCount=0;
                if(tabs.size()>1&&position>0){
                    for (int i=position+1;i<tabs.size();i++){
                        removeCount++;
                        tabs.remove(i);
                    }
                    breadCrumbAdapter.notifyItemRangeRemoved(position,removeCount);
                }

            }
        });


    }


    /**
     * breadcrumb adapter;
     */
    private class BreadCrumbAdapter extends RecyclerView.Adapter<BreadCrumbAdapter.BreadCrumbViewHolder>{
        private ArrayList<String> titles;
        private   onItemClickListener onItemClickListener;
        public BreadCrumbAdapter(ArrayList<String> titles) {
            this.titles=titles;
        }
        public void setOnItemClickListener(onItemClickListener onItemClickListener){
            this.onItemClickListener=onItemClickListener;
        }
        @Override
        public BreadCrumbViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1,null);
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            view.setLayoutParams(layoutParams);
            return new BreadCrumbViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final BreadCrumbViewHolder holder, final int position) {
            holder.textView.setText(titles.get(position));
            if(onItemClickListener!=null){
                holder.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(holder,position);
                    }
                });

            }
        }

        @Override
        public int getItemCount() {
            return titles.size();
        }
        public class BreadCrumbViewHolder extends RecyclerView.ViewHolder{
            private TextView textView;
            public BreadCrumbViewHolder(View itemView) {
                super(itemView);
                textView= (TextView) itemView.findViewById(android.R.id.text1);
            }
        }
    }
    public interface  onItemClickListener{
         void onItemClick(RecyclerView.ViewHolder view, int position);
    }



   private void initBreadcrumbData(){
       HashMap<String,String> params=new HashMap<String,String>();
       NotePadAction.mynotepad(getContext(),params, new NotePadAction.RenderCallback(){
           @Override
           public void renderOnSuccess(ArrayList<? extends Serializable> content) {
                    notePads.addAll((ArrayList<NotePad>)content);
           }

           @Override
           public void renderOnFailed(VolleyError error) {

           }
       });

   }
    public class BreadcrumbDecoration extends RecyclerView.ItemDecoration{
        private  int[] ATTRS=new int[]{android.R.attr.listDivider};
        private Drawable mDivider;

        public BreadcrumbDecoration(Context context){
            TypedArray array=context.getTheme().obtainStyledAttributes(ATTRS);
            mDivider=array.getDrawable(0);
            array.recycle();
        }
        @Override
        public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
            int top=parent.getPaddingTop();
            int bottom =parent.getHeight()-parent.getPaddingBottom();
            int childCount=parent.getChildCount();
            for (int i=0;i<childCount;i++){
             View childView=parent.getChildAt(i);
                RecyclerView.LayoutParams params=(RecyclerView.LayoutParams) childView.getLayoutParams();
                int left=childView.getRight()+params.rightMargin;
                int right=left+mDivider.getIntrinsicWidth();
                mDivider.setBounds(left,top,right,bottom);
                mDivider.draw(canvas);
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

         outRect.set(0,0,mDivider.getIntrinsicWidth(),0);
        }
    }
   private boolean isShow=false;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addBtn:


                if(!isShow){
                    addNoteBtn.setVisibility(View.VISIBLE);
                    addNotepadBtn.setVisibility(View.VISIBLE);
                     addNoteBtn.animate().setDuration(300).translationXBy(-100f).setInterpolator(new AccelerateInterpolator());
                    addNotepadBtn.animate().setDuration(300).translationYBy(-100f).setInterpolator(new AccelerateInterpolator());
                    isShow=true;
                }else{
                    addNoteBtn.animate().setDuration(300).translationXBy(100f).setInterpolator(new AccelerateInterpolator());
                    addNotepadBtn.animate().setDuration(300).translationYBy(100f).setInterpolator(new AccelerateInterpolator());
                    addNoteBtn.setVisibility(View.GONE);
                    addNotepadBtn.setVisibility(View.GONE);
                    isShow=false;
                }
                break;
            case R.id.addNote:
                Bundle bundle1=new Bundle();
                bundle1.putInt("operator",3);
                goActivity(SelectNodeActivity.class,bundle1);
                break;
            case R.id.addNotePad:
                   Bundle bundle2=new Bundle();
                   bundle2.putInt("operator",2);
                   goActivity(SelectNodeActivity.class,bundle2);
                break;
        }
    }

}

package com.apolle.zhiyou.mUtil;

import android.content.Context;
import android.content.Entity;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

/**
 * Created by huangtao on 2016/3/710:57.
 * modify by huangtao on 10:57
 */
public class MDbUtil {
    private  DbUtils dbUtils;
    private static MDbUtil mDbUtil;
    public MDbUtil(Context context){
        DbUtils.DaoConfig config=new DbUtils.DaoConfig( context );
        config.setDbName("zhiyou");
        config.setDbVersion( 1 );
         dbUtils = DbUtils.create( config );
    }

    public static MDbUtil newInstance(Context context) {
        if(null==mDbUtil){
            mDbUtil=new MDbUtil(context);
        }
        return mDbUtil;
    }
    public void save(Object entity){
        try{
            dbUtils.createTableIfNotExist(entity.getClass());
            dbUtils.save(entity);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void saveAll(List<? extends  Object> entitys){
        try{
            if(null!=entitys){
                dbUtils.createTableIfNotExist(entitys.get(0).getClass());
                dbUtils.saveAll(entitys);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public List<? extends  Object> fetchList(Class<?> entityType){
        return  fetchList(entityType,null,null,null,0,0,null);
    }

    public List<? extends  Object> fetchList(Class<?> entityType, WhereBuilder where){
      return  fetchList(entityType,where,null,null,0,0,null);
    }
    public List<? extends  Object> fetchList(Class<?> entityType, WhereBuilder where,String orderBy,String groupBy,int limit,int offset,String[] select){
        List<? extends Object> result=null;
        Selector selector=Selector.from(entityType);
        if(null!=where) selector.where( where);
        if(null!=orderBy) selector.orderBy(orderBy);
        if(null!=select) selector.select(select);
        if(null!=groupBy) selector.groupBy(groupBy);
        if(0!=limit) selector.limit(limit).offset(offset+0);
        try {
            result=(ArrayList<? extends Object>) dbUtils.findAll(selector);

        }catch (Exception e){
            e.printStackTrace();
        }
            return result;
    }

    /**
     * 添加全部没有存在数据中的值
     * @param entitys
     * @param column
     */
    public void saveNoInsert(List<? extends Object> entitys,String column){
        try{
             Class<?> tableName=entitys.get(0).getClass();
            dbUtils.createTableIfNotExist(tableName);
            for (Object obj:entitys){
                if(!hasEntity(obj,column)){
                    dbUtils.save(obj);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 根据列在数据库中找到
     * @param entity
     * @param column
     * @return
     */
    public boolean hasEntity(Object entity,String column){
            try {
                Class<?> tableName=entity.getClass();
              if(tableName!=null){
                 Field field = entity.getClass().getField(column);
                 Selector selector=Selector.from(tableName).where(column,"=",field.get(entity));
                 if(dbUtils.findFirst(selector)){
                     return true;
                  }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        return false;
    }
    public boolean update(Object entity){
        try {
             dbUtils.update(entity);
            return true;
        } catch (DbException e) {
            e.printStackTrace();
        }
        return false;
    }

}

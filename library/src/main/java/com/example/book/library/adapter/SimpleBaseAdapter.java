package com.example.book.library.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * BaseAdapter基类
 * 直接继承即可
 */
public abstract class SimpleBaseAdapter<T,E extends SimpleBaseAdapter.SimpleViewHolder> extends BaseAdapter {
    private Context mContext;
    private List<T> mEntities;
    private LayoutInflater mLayoutInflater;
    public SimpleBaseAdapter(Context context, List<T> entities) {
        mContext = context;
        mEntities = entities;
        mLayoutInflater=LayoutInflater.from(context);
    }
    
    public List<T> getData() {
        return mEntities;
    }
    
    /**
     * 初始数据
     *
     * @param entity
     */
    public void setData(List<T> entity) {
        mEntities = entity;
        notifyDataSetChanged();
    }
    
    /**
     * 加载用
     *
     * @param entity
     */
    public void addData(T entity) {
        if (mEntities != null) {
            mEntities.add(entity);
            notifyDataSetChanged();
        }
        
    }
    public void addData(List<T> entity) {
        if (mEntities != null) {
            mEntities.addAll(entity);
        } else {
            mEntities = entity;
        }
    }
    
    public void deleteData(int position) {
        if (position > 0 && position <= mEntities.size()) {
            mEntities.remove(position);
        }
        notifyDataSetChanged();
    }
    
    public void deleteData(T entity) {
        mEntities.remove(entity);
        notifyDataSetChanged();
    }
    
    public void deleteDataALL() {
        mEntities.clear();
        notifyDataSetChanged();
    }
    
 
    
    @Override
    public int getCount() {
        return (mEntities != null) ? mEntities.size() : 0;
    }
    
    @Override
    public T getItem(int position) {
        return (mEntities != null) ? mEntities.get(position) : null;
    }
    
    @Override
    public long getItemId(int position) {
        return position;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SimpleViewHolder simpleViewHolder=null;
        if (convertView==null){
            convertView=mLayoutInflater.inflate(setRootLayout(),parent,false);
            simpleViewHolder=onCreateViewHolder(convertView);
            convertView.setTag(simpleViewHolder);
        }else {
            simpleViewHolder= (SimpleViewHolder) convertView.getTag();
        }
        initViews(position,getItem(position), (E) simpleViewHolder);
        return convertView;
    }
    
    protected abstract  E onCreateViewHolder(View convertView);
    public abstract int setRootLayout();
    public abstract  void initViews(int position,T entity,E viewholder);
   
    
    public static class SimpleViewHolder{
        public  View mItemView;
    
        public SimpleViewHolder(View itemView) {
            mItemView = itemView;
        }
    }
}

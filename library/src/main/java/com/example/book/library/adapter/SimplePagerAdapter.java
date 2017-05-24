package com.example.book.library.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2016/3/11.
 */
public abstract class SimplePagerAdapter<E> extends PagerAdapter {

    public List<E> mEntities;
    public Context mContext;
    public LayoutInflater mLayoutInflater;
    private ViewPager mViewPager;
    private boolean mHasChangePos;
    
    public SimplePagerAdapter(List<E> entities, Context context, ViewPager viewPager) {
        mEntities = entities;
        mContext = context;
        mLayoutInflater=LayoutInflater.from(context);
        mViewPager=viewPager;
    }
    
    
    
    @Override
    public int getCount() {
        return mEntities.size()*100000;
    }
    public void setMiddle(){
        mViewPager.setCurrentItem(mEntities.size()*100000/2);
    }
    
    public  abstract int setLayouResID();
    
    public abstract  void initView(View itemView, ViewGroup container, int position, E entity);
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //这段代码会导致溢出
//        if (!mHasChangePos) {
//            mHasChangePos=true;
//            mViewPager.setCurrentItem(mEntities.size()*100000/2);
//        }
        View itemView=mLayoutInflater.inflate(setLayouResID(),container,false);
        initView(itemView,container,position,mEntities.get(position%mEntities.size()));
        container.addView(itemView);
        return itemView;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}

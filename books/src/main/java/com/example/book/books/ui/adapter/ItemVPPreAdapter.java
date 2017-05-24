package com.example.book.books.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Admin on 2017/5/22.
 */


public class ItemVPPreAdapter extends PagerAdapter {

    private Context mContext;
    private List<String> mEntities;

    public ItemVPPreAdapter(List<String> entities, Context context) {
        mContext=context;
        mEntities=entities;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView view = new ImageView(mContext);
        Glide.with(mContext).load(mEntities.get(position)).into(view);
        container.addView(view);
        return view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mEntities.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }
}

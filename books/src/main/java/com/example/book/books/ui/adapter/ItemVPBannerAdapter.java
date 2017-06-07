package com.example.book.books.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Admin on 2017/5/12.
 */

public class ItemVPBannerAdapter extends PagerAdapter implements View.OnClickListener {

    private Context mContext;
    private List<String> mEntities;

    public ItemVPBannerAdapter(List<String> entities, Context context) {
        mContext=context;
        mEntities=entities;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView view = new ImageView(mContext);
        Glide.with(mContext).load(mEntities.get(position)).into(view);
        view.setTag(position);
        view.setOnClickListener(this);
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

    private OnItemClickedListener mOnItemClickedListener;

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        mOnItemClickedListener = onItemClickedListener;
    }

    @Override
    public void onClick(View v) {
        mOnItemClickedListener.onItemClick((Integer) v.getTag());
    }

    public interface OnItemClickedListener{
        void onItemClick(int position);
    }
}

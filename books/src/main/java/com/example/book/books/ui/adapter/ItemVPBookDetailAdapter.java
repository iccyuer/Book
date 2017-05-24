package com.example.book.books.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.book.books.R;

import java.util.List;

import static org.xutils.common.util.DensityUtil.dip2px;

/**
 * Created by Admin on 2017/5/14.
 */


public class ItemVPBookDetailAdapter extends PagerAdapter {

    private Context mContext;
    private List<String> mEntities;
    public ImageView mArrowImage;
    public TextView mSlideText;


    public ItemVPBookDetailAdapter(List<String> entities, Context context) {
        mContext = context;
        mEntities = entities;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        if (position < mEntities.size()) {
            ImageView imageView = new ImageView(mContext);

            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip2px(300));
            imageView.setLayoutParams(lp);
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(mContext).load(mEntities.get(position)).into(imageView);

            container.addView(imageView);
            return imageView;
        } else {
            View hintView = LayoutInflater.from(container.getContext()).inflate(R.layout.more_view, container, false);

            mSlideText = (TextView) hintView.findViewById(R.id.tv_more_view);
            mArrowImage = (ImageView) hintView.findViewById(R.id.imgv_more_view);

            container.addView(hintView);
            return hintView;
        }

//        ImageView view = new ImageView(mContext);
//        Glide.with(mContext).load(mEntities.get(position)).into(view);
//        container.addView(view);
//        return view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mEntities.size()+1;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }
}
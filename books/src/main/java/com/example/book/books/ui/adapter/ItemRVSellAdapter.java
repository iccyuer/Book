package com.example.book.books.ui.adapter;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.book.books.R;
import com.example.book.books.entity.SellData;

import java.util.List;

/**
 * Created by Admin on 2017/5/31.
 */


public class ItemRVSellAdapter extends RecyclerView.Adapter<SellViewHolder> implements View.OnClickListener {
    private Context mContext;
    List<SellData> mEntities;

    public ItemRVSellAdapter(Context context, List<SellData> entities) {
        mContext = context;
        mEntities = entities;
    }

    public interface OnItemClickListener {
        void OnItemClick(int id);
    }

    @Override
    public SellViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_rv_selldata, parent, false);
        SellViewHolder typeViewHolder = new SellViewHolder(itemView);

        return typeViewHolder;
    }

    @Override
    public void onBindViewHolder(final SellViewHolder holder, int position) {
        SellData sellData = mEntities.get(position);
        Glide.with(mContext).load(sellData.getPic()).into(holder.mImgvBookItemRvSelldata);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        //屏幕宽度像素
        int screenWidth = displayMetrics.widthPixels;
        System.out.println("screenWidth = " + screenWidth);
//        Glide.with(mContext).load(s)
        holder.mTvNameItemRvSelldata.setText(sellData.getBookname());
        holder.mTvAmountItemRvSelldata.setText("x"+sellData.getCount());

        final int bgwidth = (int) ((mContext.getResources().getDisplayMetrics().density) * 150 + 0.5f);
        System.out.println("bgwidth = " + bgwidth);
        int i = (screenWidth - bgwidth) / mEntities.get(0).getCount();
        int zz = sellData.getCount() * i;
        float zzz = (float)(bgwidth + zz) / bgwidth;
        System.out.println("i = " + i+"    zz = " + zz+"   zzz = " + zzz);
        ValueAnimator valueAnimator=ValueAnimator.ofFloat(0,zzz);
        valueAnimator.setDuration(2000);
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                 float animatedValue = (float) animation.getAnimatedValue();
                holder.mImgvBgItemRvSelldata.setPivotX(0.2f);
                holder.mImgvBgItemRvSelldata.setScaleX(animatedValue);
                holder.mTvAmountItemRvSelldata.setTranslationX(animatedValue*bgwidth/2.2f);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mEntities.size();
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public void onClick(View v) {
        mOnItemClickListener.OnItemClick((Integer) v.getTag());
    }


}

class SellViewHolder extends RecyclerView.ViewHolder {
    public ImageView mImgvBookItemRvSelldata;
    public TextView mTvAmountItemRvSelldata;
    public TextView mTvNameItemRvSelldata;
    public ImageView mImgvBgItemRvSelldata;


    public SellViewHolder(View itemView) {
        super(itemView);
        mImgvBookItemRvSelldata = (ImageView) itemView.findViewById(R.id.imgv_book_item_rv_selldata);
        mTvAmountItemRvSelldata = (TextView) itemView.findViewById(R.id.tv_amount_item_rv_selldata);
        mTvNameItemRvSelldata = (TextView) itemView.findViewById(R.id.tv_name_item_rv_selldata);
        mImgvBgItemRvSelldata = (ImageView) itemView.findViewById(R.id.imgv_bg_item_rv_selldata);

    }
}

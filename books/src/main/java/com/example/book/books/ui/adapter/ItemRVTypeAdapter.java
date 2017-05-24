package com.example.book.books.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.book.books.R;
import com.example.book.books.entity.TypeEntity;

import java.util.List;

/**
 * Created by Admin on 2017/5/12.
 */

public class ItemRVTypeAdapter extends RecyclerView.Adapter<TypeViewHolder> implements View.OnClickListener {
    private Context mContext;
    List<TypeEntity> mEntities;

    public ItemRVTypeAdapter(Context context, List<TypeEntity> entities) {
        mContext = context;
        mEntities = entities;
    }

    public interface OnItemClickListener {
        void OnItemClick(String name);
    }

    @Override
    public TypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_rv_type, parent, false);
        TypeViewHolder typeViewHolder = new TypeViewHolder(itemView);
        return typeViewHolder;
    }

    @Override
    public void onBindViewHolder(TypeViewHolder holder, int position) {
        Glide.with(mContext).load(mEntities.get(position).getPath()).into(holder.mImageView);
//        System.out.println("holder = " + mEntities.get(position).getPath());
        holder.mTextView.setText(mEntities.get(position).getName());

        holder.mImageView.setTag(mEntities.get(position).getName());
        holder.mImageView.setOnClickListener(this);

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
        mOnItemClickListener.OnItemClick((String) v.getTag());
    }


}

class TypeViewHolder extends RecyclerView.ViewHolder {

    public TextView mTextView;
    public ImageView mImageView;

    public TypeViewHolder(View itemView) {
        super(itemView);
        mTextView = (TextView) itemView.findViewById(R.id.tv_item_type);
        mImageView = (ImageView) itemView.findViewById(R.id.imgv_item_type);
    }
}



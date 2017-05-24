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
import com.example.book.books.model.Carts;

import java.util.List;

/**
 * Created by Admin on 2017/5/19.
 */


public class ItemRVOrderBookAdapter extends RecyclerView.Adapter<OrderBookViewHolder> implements View.OnClickListener {
    private Context mContext;
    List<Carts> mEntities;

    public ItemRVOrderBookAdapter(Context context, List<Carts> entities) {
        mContext = context;
        mEntities = entities;
    }

    public interface OnItemClickListener {
        void OnItemClick(int id);
    }

    @Override
    public OrderBookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_rv_order_book, parent, false);
        OrderBookViewHolder typeViewHolder = new OrderBookViewHolder(itemView);

        return typeViewHolder;
    }

    @Override
    public void onBindViewHolder(OrderBookViewHolder holder, int position) {
        Glide.with(mContext).load(mEntities.get(position).getPic()).into(holder.mImgvBookItemRvOrderBook);
        holder.mTvAmountItemRvOrderBook.setText("x"+mEntities.get(position).getAmount());

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

class OrderBookViewHolder extends RecyclerView.ViewHolder {
    public ImageView mImgvBookItemRvOrderBook;
    public TextView mTvAmountItemRvOrderBook;

    public OrderBookViewHolder(View itemView) {
        super(itemView);
        mImgvBookItemRvOrderBook = (ImageView) itemView.findViewById(R.id.imgv_book_item_rv_order_book);
        mTvAmountItemRvOrderBook = (TextView) itemView.findViewById(R.id.tv_amount_item_rv_order_book);

    }
}
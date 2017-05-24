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
 * Created by Admin on 2017/5/18.
 */


public class ItemRVOrdersAdapter extends RecyclerView.Adapter<OrdersViewHolder>  {
    private Context mContext;
    List<Carts> mEntities;


    public ItemRVOrdersAdapter(Context context, List<Carts> entities) {
        mContext = context;
        mEntities = entities;
    }

    @Override
    public OrdersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_rv_orders, parent, false);
        OrdersViewHolder typeViewHolder = new OrdersViewHolder(itemView);
        return typeViewHolder;
    }

    @Override
    public void onBindViewHolder(OrdersViewHolder holder, int position) {
        Glide.with(mContext).load(mEntities.get(position).getPic()).into(holder.mImgvBookItemRvOrders);
        holder.mTvNameItemRvOrders.setText(mEntities.get(position).getName());
        holder.mTvPriceItemRvOrders.setText(mEntities.get(position).getPrice()+"");
        holder.mTvAmountItemRvOrders.setText("x "+mEntities.get(position).getAmount());
    }

    @Override
    public int getItemCount() {
        return mEntities.size();
    }

}

class OrdersViewHolder extends RecyclerView.ViewHolder {
    public ImageView mImgvBookItemRvOrders;
    public TextView mTvNameItemRvOrders;
    public TextView mTvPriceItemRvOrders;
    public TextView mTvAmountItemRvOrders;

    public OrdersViewHolder(View itemView) {
        super(itemView);
        mImgvBookItemRvOrders = (ImageView) itemView.findViewById(R.id.imgv_book_item_rv_orders);
        mTvNameItemRvOrders = (TextView) itemView.findViewById(R.id.tv_name_item_rv_orders);
        mTvPriceItemRvOrders = (TextView) itemView.findViewById(R.id.tv_price_item_rv_orders);
        mTvAmountItemRvOrders = (TextView) itemView.findViewById(R.id.tv_amount_item_rv_orders);


    }
}


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
import com.example.book.books.model.DeliveryDetails;

import java.util.List;

/**
 * Created by Admin on 2017/5/23.
 */


public class ItemRVDeliveryBookAdapter extends RecyclerView.Adapter<DeliverBookViewHolder> implements View.OnClickListener {
    private Context mContext;
    List<DeliveryDetails> mEntities;

    public ItemRVDeliveryBookAdapter(Context context, List<DeliveryDetails> entities) {
        mContext = context;
        mEntities = entities;
    }

    public interface OnItemClickListener {
        void OnItemClick(int id);
    }

    @Override
    public DeliverBookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_rv_delivery_book, parent, false);
        DeliverBookViewHolder typeViewHolder = new DeliverBookViewHolder(itemView);

        return typeViewHolder;
    }

    @Override
    public void onBindViewHolder(DeliverBookViewHolder holder, int position) {
        DeliveryDetails deliveryDetails = mEntities.get(position);
        Glide.with(mContext).load(deliveryDetails.getBookPic()).into(holder.mImgvBookItemRvDeliverBook);
        holder.mTvAmountItemRvDeliverBook.setText("x"+deliveryDetails.getDeliverynum());
        holder.mTvNameItemRvDeliverBook.setText(deliveryDetails.getBookname());
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

class DeliverBookViewHolder extends RecyclerView.ViewHolder {
    public ImageView mImgvBookItemRvDeliverBook;
    public TextView mTvAmountItemRvDeliverBook;
    public TextView mTvNameItemRvDeliverBook;

    public DeliverBookViewHolder(View itemView) {
        super(itemView);
        mImgvBookItemRvDeliverBook = (ImageView) itemView.findViewById(R.id.imgv_book_item_rv_deliver_book);
        mTvAmountItemRvDeliverBook = (TextView) itemView.findViewById(R.id.tv_amount_item_rv_deliver_book);
        mTvNameItemRvDeliverBook = (TextView) itemView.findViewById(R.id.tv_name_item_rv_deliver_book);

    }
}

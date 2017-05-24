package com.example.book.books.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.book.books.R;
import com.example.book.books.model.Books;

import java.util.List;

/**
 * Created by Admin on 2017/5/13.
 */


public class ItemRVBooklistAdapter extends RecyclerView.Adapter<BooklistViewHolder> implements View.OnClickListener {
    private Context mContext;
    List<Books> mEntities;

    public ItemRVBooklistAdapter(Context context, List<Books> entities) {
        mContext = context;
        mEntities = entities;
    }

    public interface OnItemClickListener {
         void OnItemClick(int id);
    }

    @Override
    public BooklistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_rv_booklist, parent, false);
        BooklistViewHolder typeViewHolder = new BooklistViewHolder(itemView);

        return typeViewHolder;
    }

    @Override
    public void onBindViewHolder(BooklistViewHolder holder, int position) {
        Glide.with(mContext).load(mEntities.get(position).getPic()).into(holder.mImgvItemRvBooklist);
        holder.mTvNameItemRvBooklist.setText(mEntities.get(position).getName());
        holder.mTvAuthorItemRvBooklist.setText(mEntities.get(position).getAuthor());
        holder.mTvPressItemRvBooklist.setText(mEntities.get(position).getPress());
        holder.mTvPriceItemRvBooklist.setText(mEntities.get(position).getPrice()+"");
        holder.mTvMarketpriceItemRvBooklist.setText(mEntities.get(position).getMarketPrice()+"");

        holder.mRlItemRvBooklist.setTag(mEntities.get(position).getBookid());
        holder.mRlItemRvBooklist.setOnClickListener(this);
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

class BooklistViewHolder extends RecyclerView.ViewHolder {

    public RelativeLayout mRlItemRvBooklist;
    public ImageView mImgvItemRvBooklist;
    public TextView mTvNameItemRvBooklist;
    public TextView mTvAuthorItemRvBooklist;
    public TextView mTvPressItemRvBooklist;
    public TextView mTvPriceItemRvBooklist;
    public TextView mTvMarketpriceItemRvBooklist;

    public BooklistViewHolder(View itemView) {
        super(itemView);
        mRlItemRvBooklist = (RelativeLayout) itemView.findViewById(R.id.rl_item_rv_booklist);
        mImgvItemRvBooklist = (ImageView) itemView.findViewById(R.id.imgv_item_rv_booklist);
        mTvNameItemRvBooklist = (TextView) itemView.findViewById(R.id.tv_name_item_rv_booklist);
        mTvAuthorItemRvBooklist = (TextView) itemView.findViewById(R.id.tv_author_item_rv_booklist);
        mTvPressItemRvBooklist = (TextView) itemView.findViewById(R.id.tv_press_item_rv_booklist);
        mTvPriceItemRvBooklist = (TextView) itemView.findViewById(R.id.tv_price_item_rv_booklist);
        mTvMarketpriceItemRvBooklist = (TextView) itemView.findViewById(R.id.tv_marketprice_item_rv_booklist);

    }
}




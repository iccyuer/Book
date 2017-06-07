package com.example.book.books.ui.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.book.books.R;
import com.example.book.books.base.SBaseApp;
import com.example.book.books.db.BooksDao;
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
        holder.mTvPriceItemRvBooklist.setText(mEntities.get(position).getPrice() + "");
        holder.mTvMarketpriceItemRvBooklist.setText(mEntities.get(position).getMarketPrice() + "");

        holder.mRlItemRvBooklist.setTag(mEntities.get(position).getBookid());
        holder.mRlItemRvBooklist.setOnClickListener(this);
        if (!SBaseApp.isAdmin) {
            holder.mTvDeleteItemRvBooklist.setVisibility(View.GONE);
        }
        holder.mTvDeleteItemRvBooklist.setTag(mEntities.get(position));
        holder.mTvDeleteItemRvBooklist.setOnClickListener(this);
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
        int id = v.getId();
        switch (id) {
            case R.id.rl_item_rv_booklist:
                mOnItemClickListener.OnItemClick((Integer) v.getTag());
                break;
            case R.id.tv_delete_item_rv_booklist:
                Books books = (Books) v.getTag();
                showDialog(books);
                break;
        }
    }

    private void showDialog(final Books books) {
        //弹个dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        // 构建者模式 构建链
        builder.setTitle("提示").setMessage("确定要删除吗").setCancelable(false)
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BooksDao.getBooksDao().deleteBoooksById(books.getBookid());
                        mEntities.remove(books);
                        notifyDataSetChanged();
                        Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create().show();
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
    public TextView mTvDeleteItemRvBooklist;


    public BooklistViewHolder(View itemView) {
        super(itemView);
        mRlItemRvBooklist = (RelativeLayout) itemView.findViewById(R.id.rl_item_rv_booklist);
        mImgvItemRvBooklist = (ImageView) itemView.findViewById(R.id.imgv_item_rv_booklist);
        mTvNameItemRvBooklist = (TextView) itemView.findViewById(R.id.tv_name_item_rv_booklist);
        mTvAuthorItemRvBooklist = (TextView) itemView.findViewById(R.id.tv_author_item_rv_booklist);
        mTvPressItemRvBooklist = (TextView) itemView.findViewById(R.id.tv_press_item_rv_booklist);
        mTvPriceItemRvBooklist = (TextView) itemView.findViewById(R.id.tv_price_item_rv_booklist);
        mTvMarketpriceItemRvBooklist = (TextView) itemView.findViewById(R.id.tv_marketprice_item_rv_booklist);
        mTvDeleteItemRvBooklist = (TextView) itemView.findViewById(R.id.tv_delete_item_rv_booklist);

    }
}




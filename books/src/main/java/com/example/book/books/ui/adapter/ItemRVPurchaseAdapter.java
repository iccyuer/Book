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
import com.example.book.books.model.Purchase;
import com.example.book.books.utils.DataFormatUtil;

import java.util.List;

/**
 * Created by Admin on 2017/5/22.
 */


public class ItemRVPurchaseAdapter extends RecyclerView.Adapter<PurchaseViewHolder> implements View.OnClickListener {
    private Context mContext;
    List<Purchase> mEntities;

    public ItemRVPurchaseAdapter(Context context, List<Purchase> entities) {
        mContext = context;
        mEntities = entities;
    }

    public interface OnItemClickListener {
        void OnItemClick(int id);
    }

    @Override
    public PurchaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_rv_purchaselist, parent, false);
        PurchaseViewHolder typeViewHolder = new PurchaseViewHolder(itemView);

        return typeViewHolder;
    }

    @Override
    public void onBindViewHolder(PurchaseViewHolder holder, int position) {
        Purchase purchase = mEntities.get(position);
        holder.mTvPurchaseidItemRvPurchaselist.setText("进货订单id:" + purchase.getPurchaseid());
        Glide.with(mContext).load(purchase.getBookPic()).into(holder.mImgvPurchasepicItemRvPurchaselist);
        holder.mTvPurchasetimeItemRvPurchaselist.setText("进货订单创建时间：" + DataFormatUtil.format(purchase.getPurchaseTime()));
        holder.mTvPurchasenameItemRvPurchaselist.setText(purchase.getBookName());

        if (purchase.getPurchaseStock() == 0 && purchase.getPurchaseAmount() != 0) {
            if (purchase.getPurchaseAmount() > 0) {
                holder.mTvPurchaseinfoItemRvPurchaselist.setText("添加库存" + purchase.getPurchaseAmount() + "本"
                        + "  现有库存" + purchase.getStockNow());
            } else {
                holder.mTvPurchaseinfoItemRvPurchaselist.setText("减少库存" + purchase.getPurchaseAmount() + "本"
                        + "  现有库存" + purchase.getStockNow());
            }
        } else if (purchase.getPurchaseStock() != 0 && purchase.getPurchaseAmount() == 0) {
            if (purchase.isNew()) {
                holder.mTvPurchaseinfoItemRvPurchaselist.setText("新书进货数量" + purchase.getPurchaseStock() + "本"
                        + "  现有库存" + purchase.getStockNow());
            } else {
                holder.mTvPurchaseinfoItemRvPurchaselist.setText("库存调整为" + purchase.getPurchaseStock() + "本"
                        + "  现有库存" + purchase.getStockNow());
            }
        }
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

class PurchaseViewHolder extends RecyclerView.ViewHolder {
    public TextView mTvPurchaseidItemRvPurchaselist;
    public ImageView mImgvPurchasepicItemRvPurchaselist;
    public TextView mTvPurchasetimeItemRvPurchaselist;
    public TextView mTvPurchaseinfoItemRvPurchaselist;
    public TextView mTvPurchasenameItemRvPurchaselist;


    public PurchaseViewHolder(View itemView) {
        super(itemView);
        mTvPurchaseidItemRvPurchaselist = (TextView) itemView.findViewById(R.id.tv_purchaseid_item_rv_purchaselist);
        mImgvPurchasepicItemRvPurchaselist = (ImageView) itemView.findViewById(R.id.imgv_purchasepic_item_rv_purchaselist);
        mTvPurchasetimeItemRvPurchaselist = (TextView) itemView.findViewById(R.id.tv_purchasetime_item_rv_purchaselist);
        mTvPurchaseinfoItemRvPurchaselist = (TextView) itemView.findViewById(R.id.tv_purchaseinfo_item_rv_purchaselist);
        mTvPurchasenameItemRvPurchaselist = (TextView) itemView.findViewById(R.id.tv_purchasename_item_rv_purchaselist);

    }
}

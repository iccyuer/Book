package com.example.book.books.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.book.books.R;
import com.example.book.books.db.DeliveryDetailsDao;
import com.example.book.books.model.Delivery;
import com.example.book.books.model.DeliveryDetails;
import com.example.book.books.utils.DataFormatUtil;

import java.util.List;

/**
 * Created by Admin on 2017/5/23.
 */


public class ItemRVDeliveryAdapter extends RecyclerView.Adapter<DeliveryViewHolder> implements View.OnClickListener {
    private Context mContext;
    List<Delivery> mEntities;
    private ItemRVDeliveryBookAdapter mMyAdapter;

    public ItemRVDeliveryAdapter(Context context, List<Delivery> entities) {
        mContext = context;
        mEntities = entities;
    }

    public interface OnItemClickListener {
        void OnItemClick(int id);
    }

    @Override
    public DeliveryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_deliverylist, parent, false);
        DeliveryViewHolder typeViewHolder = new DeliveryViewHolder(itemView);

        return typeViewHolder;
    }

    @Override
    public void onBindViewHolder(DeliveryViewHolder holder, int position) {
        Delivery delivery = mEntities.get(position);
        holder.mTvDeliveryidItemRvDeliverylist.setText("出货id:"+delivery.getDeliveryid()+"");
        holder.mTvDeliverytimeItemRvDeliverylist.setText("出货订单创建时间:"+DataFormatUtil.format(delivery.getDeliveryTime()));

        int deliveryid=delivery.getDeliveryid();

        List<DeliveryDetails> deliveryDetails = DeliveryDetailsDao.getDeliveryDetailsDao().getDeliveryDetails(deliveryid);

        if (deliveryDetails!=null) {
//            Logger.i(deliveryDetails.toString());
            holder.mRvDeliverbookItemOrderlist.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.HORIZONTAL, false));
            mMyAdapter = new ItemRVDeliveryBookAdapter(mContext,deliveryDetails);
            holder.mRvDeliverbookItemOrderlist.setAdapter(mMyAdapter);
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

class DeliveryViewHolder extends RecyclerView.ViewHolder {
    public TextView mTvDeliveryidItemRvDeliverylist;
    public TextView mTvDeliverytimeItemRvDeliverylist;
    public TextView mTvDeliveryinfoItemRvDeliverylist;
    public RecyclerView mRvDeliverbookItemOrderlist;



    public DeliveryViewHolder(View itemView) {
        super(itemView);
        mTvDeliveryidItemRvDeliverylist = (TextView) itemView.findViewById(R.id.tv_deliveryid_item_rv_deliverylist);
        mTvDeliverytimeItemRvDeliverylist = (TextView) itemView.findViewById(R.id.tv_deliverytime_item_rv_deliverylist);
        mTvDeliveryinfoItemRvDeliverylist = (TextView) itemView.findViewById(R.id.tv_deliveryinfo_item_rv_deliverylist);
        mRvDeliverbookItemOrderlist = (RecyclerView) itemView.findViewById(R.id.rv_deliverbook_item_orderlist);

    }
}
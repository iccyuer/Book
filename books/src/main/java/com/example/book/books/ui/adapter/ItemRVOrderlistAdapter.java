package com.example.book.books.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.book.books.R;
import com.example.book.books.base.SBaseApp;
import com.example.book.books.db.CartsDao;
import com.example.book.books.db.OrderDetailsDao;
import com.example.book.books.model.Carts;
import com.example.book.books.model.Orders;
import com.example.book.books.utils.DataFormatUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2017/5/19.
 */


public class ItemRVOrderlistAdapter extends RecyclerView.Adapter<OrderlistViewHolder> implements View.OnClickListener {
    private Context mContext;
    public List<Orders> mEntities;
    private ItemRVOrderBookAdapter mMyAdapter;

    public ItemRVOrderlistAdapter(Context context, List<Orders> entities) {
        mContext = context;
        mEntities = entities;
    }

    public interface OnItemClickListener {
        void OnItemClick(Orders orders);
    }

    @Override
    public OrderlistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_rv_orderlist, parent, false);
        OrderlistViewHolder typeViewHolder = new OrderlistViewHolder(itemView);

        return typeViewHolder;
    }

    @Override
    public void onBindViewHolder(OrderlistViewHolder holder, int position) {
        holder.mTvOrderidItemRvOrderlist.setText("订单号 ："+mEntities.get(position).getOrderid()+"");
        holder.mTvOrdertimeItemRvOrderlist.setText("下单时间 ："+DataFormatUtil.format(mEntities.get(position).getOrderTime()));
        holder.mTvOrderstateItemRvOrderlist.setText("订单状态："+mEntities.get(position).getOrderState());
        boolean deliver = mEntities.get(position).isDeliver();
        if (deliver) {
            holder.mTvIsdeliverItemRvOrderlist.setText("是否出货：是");
        }else{
            holder.mTvIsdeliverItemRvOrderlist.setText("是否出货：否");
        }
        if (SBaseApp.isAdmin) {
            if (mEntities.get(position).getOrderState().equals("交易成功")
                    &&mEntities.get(position).isDeliver()==false) {
                holder.mBtDeliverItemRvOrderlist.setVisibility(View.VISIBLE);
            }
        }else{
            holder.mBtDeliverItemRvOrderlist.setVisibility(View.GONE);
        }
        holder.mBtDeliverItemRvOrderlist.setTag(mEntities.get(position));
        holder.mBtDeliverItemRvOrderlist.setOnClickListener(this);

        int orderid=mEntities.get(position).getOrderid();

        List<Integer> cartIds = OrderDetailsDao.getOrderDetailsDao().getCartIds(orderid);

        holder.mTvOrderpriceItemRvOrderlist.setText("共"+cartIds.size()+"类书目 合计：￥ "+mEntities.get(position).getAllprice()+"");


        if (cartIds!=null) {
//            Logger.i(cartIds.toString());
            List<Carts> mCartses=new ArrayList<>() ;
            for (Integer cartId : cartIds) {
                Carts carts = CartsDao.getCartsDao().getgetCartsByCartIdAndUserId2(cartId, SBaseApp.onUserId);
                mCartses.add(carts);
            }
//            Logger.i(mCartses.toString());
            holder.mRvOrderbookItemOrderlist.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.HORIZONTAL, false));
            mMyAdapter = new ItemRVOrderBookAdapter(mContext, mCartses);
//        mRvCartsActivity.setItemViewCacheSize(5+0);
            holder.mRvOrderbookItemOrderlist.setAdapter(mMyAdapter);
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
        mOnItemClickListener.OnItemClick((Orders) v.getTag());
    }


}

class OrderlistViewHolder extends RecyclerView.ViewHolder {
    public LinearLayout mLlItemRvOrderlist;
    public TextView mTvOrderidItemRvOrderlist;
    public TextView mTvOrdertimeItemRvOrderlist;
    public TextView mTvOrderpriceItemRvOrderlist;
    public TextView mTvOrderstateItemRvOrderlist;
    public RecyclerView mRvOrderbookItemOrderlist;
    public TextView mTvIsdeliverItemRvOrderlist;
    public Button mBtDeliverItemRvOrderlist;



    public OrderlistViewHolder(View itemView) {
        super(itemView);
        mLlItemRvOrderlist = (LinearLayout) itemView.findViewById(R.id.ll_item_rv_orderlist);
        mTvOrderidItemRvOrderlist = (TextView) itemView.findViewById(R.id.tv_orderid_item_rv_orderlist);
        mTvOrdertimeItemRvOrderlist = (TextView) itemView.findViewById(R.id.tv_ordertime_item_rv_orderlist);
        mTvOrderpriceItemRvOrderlist = (TextView) itemView.findViewById(R.id.tv_orderprice_item_rv_orderlist);
        mTvOrderstateItemRvOrderlist = (TextView) itemView.findViewById(R.id.tv_orderstate_item_rv_orderlist);
        mRvOrderbookItemOrderlist = (RecyclerView) itemView.findViewById(R.id.rv_orderbook_item_orderlist);
        mTvIsdeliverItemRvOrderlist = (TextView) itemView.findViewById(R.id.tv_isdeliver_item_rv_orderlist);
        mBtDeliverItemRvOrderlist = (Button) itemView.findViewById(R.id.bt_deliver_item_rv_orderlist);

    }
}

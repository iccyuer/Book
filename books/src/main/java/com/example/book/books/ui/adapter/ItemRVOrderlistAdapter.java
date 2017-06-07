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
import com.example.book.books.db.UsersDao;
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
        void OnItemClickDeliver(Orders orders);

        void OnItemClickDoPay(Orders orders);

        void OnItemClickReceive(Orders orders);
        void OnItemClickCancel(Orders orders);
    }

    @Override
    public OrderlistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_rv_orderlist, parent, false);
        OrderlistViewHolder typeViewHolder = new OrderlistViewHolder(itemView);

        return typeViewHolder;
    }

    @Override
    public void onBindViewHolder(OrderlistViewHolder holder, int position) {
        Orders orders = mEntities.get(position);
//        Logger.i(orders.toString());
        holder.mTvOrderidItemRvOrderlist.setText("订单号 ：" + orders.getOrderid() + "");
        holder.mTvUsernameItemRvOrderlist.setText("会员名：" + UsersDao.getUsersDao().getUserByUserId(orders.getUserid()).getUserName());
        holder.mTvOrdertimeItemRvOrderlist.setText("下单时间 ：" + DataFormatUtil.format(orders.getOrderTime()));
        holder.mTvOrderstateItemRvOrderlist.setText("订单状态：" + orders.getOrderState());
        boolean deliver = orders.isDeliver();
        if (deliver) {
            holder.mTvIsdeliverItemRvOrderlist.setText("是否出货：是");
        } else {
            holder.mTvIsdeliverItemRvOrderlist.setText("是否出货：否");
        }
        if (orders.getOrderState().equals("订单取消")) {
            holder.mBtDeliverItemRvOrderlist.setVisibility(View.GONE);
            holder.mBtReceiveItemRvOrderlist.setVisibility(View.GONE);
            holder.mBtDopayItemRvOrderlist.setVisibility(View.GONE);
            holder.mBtCancelItemRvOrderlist.setVisibility(View.GONE);
        } else {
            if (SBaseApp.isAdmin) {
                holder.mBtDeliverItemRvOrderlist.setVisibility(View.GONE);
                if (orders.getOrderState().equals("交易成功")
                        && orders.isDeliver() == false) {
                    holder.mBtDeliverItemRvOrderlist.setVisibility(View.VISIBLE);
                }
                holder.mBtDopayItemRvOrderlist.setVisibility(View.GONE);
                holder.mBtReceiveItemRvOrderlist.setVisibility(View.GONE);
                holder.mBtReceiveItemRvOrderlist.setVisibility(View.GONE);
            } else {
                holder.mBtDeliverItemRvOrderlist.setVisibility(View.GONE);
                if (!orders.getOrderState().equals("交易成功")
                        && orders.isDeliver() == false) {
                    holder.mBtCancelItemRvOrderlist.setVisibility(View.VISIBLE);
                    holder.mBtDopayItemRvOrderlist.setVisibility(View.VISIBLE);
                    holder.mBtReceiveItemRvOrderlist.setVisibility(View.GONE);
                } else if (orders.getOrderState().equals("交易成功")
                        && orders.isDeliver() == true) {
                    holder.mBtCancelItemRvOrderlist.setVisibility(View.GONE);
                    holder.mBtDopayItemRvOrderlist.setVisibility(View.GONE);
                    holder.mBtReceiveItemRvOrderlist.setVisibility(View.VISIBLE);
                }
            }
        }

        holder.mBtDeliverItemRvOrderlist.setTag(orders);
        holder.mBtDeliverItemRvOrderlist.setOnClickListener(this);
        holder.mBtDopayItemRvOrderlist.setTag(orders);
        holder.mBtDopayItemRvOrderlist.setOnClickListener(this);
        holder.mBtReceiveItemRvOrderlist.setTag(orders);
        holder.mBtReceiveItemRvOrderlist.setOnClickListener(this);
        holder.mBtCancelItemRvOrderlist.setTag(orders);
        holder.mBtCancelItemRvOrderlist.setOnClickListener(this);

        int orderid = orders.getOrderid();

        List<Integer> cartIds = OrderDetailsDao.getOrderDetailsDao().getCartIds(orderid);

        holder.mTvOrderpriceItemRvOrderlist.setText("共" + cartIds.size() + "类书目 合计：￥ " + orders.getAllprice() + "");


        if (cartIds != null) {
//            Logger.i(cartIds.toString());
            List<Carts> mCartses = new ArrayList<>();
            for (Integer cartId : cartIds) {
                if (!SBaseApp.isAdmin) {
                    //用户查看得带上自己id!
                    Carts carts = CartsDao.getCartsDao().getgetCartsByCartIdAndUserId2(cartId, SBaseApp.onUserId);//这里userid有问题!!!!!????
                    mCartses.add(carts);
                } else {
                    //管理员的话就就查所有了!
                    Carts carts = CartsDao.getCartsDao().getCartsByCartId(cartId);
                    mCartses.add(carts);
                }
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
        int id = v.getId();
        switch (id) {
            case R.id.bt_deliver_item_rv_orderlist:
                mOnItemClickListener.OnItemClickDeliver((Orders) v.getTag());
                break;
            case R.id.bt_dopay_item_rv_orderlist:
                mOnItemClickListener.OnItemClickDoPay((Orders) v.getTag());
                break;
            case R.id.bt_receive_item_rv_orderlist:
                mOnItemClickListener.OnItemClickReceive((Orders) v.getTag());
                break;
            case R.id.bt_cancel_item_rv_orderlist:
                mOnItemClickListener.OnItemClickCancel((Orders) v.getTag());
                break;
        }

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
    public TextView mTvUsernameItemRvOrderlist;
    public Button mBtDopayItemRvOrderlist;
    public Button mBtReceiveItemRvOrderlist;
    public Button mBtCancelItemRvOrderlist;


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
        mTvUsernameItemRvOrderlist = (TextView) itemView.findViewById(R.id.tv_username_item_rv_orderlist);
        mBtDopayItemRvOrderlist = (Button) itemView.findViewById(R.id.bt_dopay_item_rv_orderlist);
        mBtReceiveItemRvOrderlist = (Button) itemView.findViewById(R.id.bt_receive_item_rv_orderlist);
        mBtCancelItemRvOrderlist = (Button) itemView.findViewById(R.id.bt_cancel_item_rv_orderlist);

    }
}

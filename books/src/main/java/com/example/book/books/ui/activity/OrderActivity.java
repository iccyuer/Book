package com.example.book.books.ui.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.book.books.R;
import com.example.book.books.base.SBaseActivity;
import com.example.book.books.base.SBaseApp;
import com.example.book.books.db.CartsDao;
import com.example.book.books.db.OrderDetailsDao;
import com.example.book.books.db.OrdersDao;
import com.example.book.books.model.Carts;
import com.example.book.books.model.OrderDetails;
import com.example.book.books.model.Orders;
import com.example.book.books.ui.adapter.ItemRVOrdersAdapter;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends SBaseActivity {

    private RecyclerView mRvOrderActivity;
    private ItemRVOrdersAdapter mMyAdapter;
    private TextView mTvAllpriceOrderActivity;
    private Button mBtSubmitOrderActivity;
    private float mAllprice;
    private ArrayList<Integer> mCartsids;
    private Orders mOrders;

    @Override
    public int setRootView() {
        return R.layout.activity_order;
    }

    @Override
    public void initView() {
        mRvOrderActivity = (RecyclerView) findViewById(R.id.rv_order_activity);
        mTvAllpriceOrderActivity = (TextView) findViewById(R.id.tv_allprice_order_activity);
        mBtSubmitOrderActivity = (Button) findViewById(R.id.bt_submit_order_activity);

        mBtSubmitOrderActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOrders = new Orders();
                mOrders.setUserid(SBaseApp.onUserId);
                mOrders.setAllprice(mAllprice);
                mOrders.setOrderTime(System.currentTimeMillis());
                OrdersDao.getOrdersDao().addOrders(mOrders);
                int orderid =  mOrders.getOrderid();
                System.out.println("orderid = " + orderid);

                for (Integer cartsid : mCartsids) {
                    OrderDetails orderDetails=new OrderDetails() ;
                    orderDetails.setUserid(SBaseApp.onUserId);
                    orderDetails.setOrderid(orderid);
                    orderDetails.setCartid(cartsid);
                    OrderDetailsDao.getOrderDetailsDao().addOrderDetails(orderDetails);
                }
                Intent intent=new Intent(OrderActivity.this,YLActivity.class);
                startActivityForResult(intent,110);
            }
        });
    }

    @Override
    public void initDatas() {
        Intent intent = getIntent();
        mCartsids = intent.getIntegerArrayListExtra("cartsids");
        mAllprice = intent.getFloatExtra("allprice", 0);
        mTvAllpriceOrderActivity.setText("总额（含运费） ￥ "+ mAllprice);

        List<Carts> cartses = CartsDao.getCartsDao().getCartsByCartsId(SBaseApp.onUserId, mCartsids);
        if (cartses!=null) {
            showRV(cartses);
        }
    }

    private void showRV(List<Carts> cartses) {
//        Logger.i(cartses.toString());
        mRvOrderActivity.setLayoutManager(new LinearLayoutManager(mActivitySelf, LinearLayout.VERTICAL, false));
        mMyAdapter = new ItemRVOrdersAdapter(mActivitySelf, cartses);
//        mRvCartsActivity.setItemViewCacheSize(5+0);
        mRvOrderActivity.setAdapter(mMyAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (110==requestCode) {
            String result = data.getStringExtra("result");
            Toast.makeText(mActivitySelf, result, Toast.LENGTH_SHORT).show();
            //只要订单生成了，无论支付成功、失败、取消，修改购物车商品属性（清空购物车）
            for (Integer cartsid : mCartsids) {
                Carts carts = CartsDao.getCartsDao().getgetCartsByCartIdAndUserId(cartsid, SBaseApp.onUserId);
                if (carts!=null) {
                    CartsDao.getCartsDao().updateCartsState(carts,false);
                }
            }
            if (result.equalsIgnoreCase("success")) {
                OrdersDao.getOrdersDao().updateOrdersState(mOrders,"交易成功");
            } else if (result.equalsIgnoreCase("fail")) {
                OrdersDao.getOrdersDao().updateOrdersState(mOrders,"交易失败");
            } else if (result.equalsIgnoreCase("cancel")) {
                OrdersDao.getOrdersDao().updateOrdersState(mOrders,"交易取消");
            }
            finish();
        }
    }
}

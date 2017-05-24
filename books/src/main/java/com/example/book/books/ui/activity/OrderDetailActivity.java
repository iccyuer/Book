package com.example.book.books.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.book.books.R;
import com.example.book.books.base.SBaseActivity;
import com.example.book.books.base.SBaseApp;
import com.example.book.books.db.BooksDao;
import com.example.book.books.db.CartsDao;
import com.example.book.books.db.DeliveryDao;
import com.example.book.books.db.DeliveryDetailsDao;
import com.example.book.books.db.OrderDetailsDao;
import com.example.book.books.db.OrdersDao;
import com.example.book.books.model.Books;
import com.example.book.books.model.Carts;
import com.example.book.books.model.Delivery;
import com.example.book.books.model.DeliveryDetails;
import com.example.book.books.model.Orders;
import com.example.book.books.ui.adapter.ItemRVOrderlistAdapter;
import com.orhanobut.logger.Logger;

import java.util.List;

public class OrderDetailActivity extends SBaseActivity {
    private RecyclerView mRvrvOrderlistActivity;
    private ItemRVOrderlistAdapter mMyAdapter;
    private TextView mTvNothingOrderlistActivity;


    @Override
    public int setRootView() {
        return R.layout.activity_order_detail;
    }

    @Override
    public void initView() {

        mRvrvOrderlistActivity = (RecyclerView) findViewById(R.id.rv_orderlist_activity);
        mTvNothingOrderlistActivity = (TextView) findViewById(R.id.tv_nothing_orderlist_activity);


    }

    @Override
    public void initDatas() {
        Intent intent = getIntent();
        int ordertype = intent.getIntExtra("ordertype", 0);
        System.out.println("ordertype = " + ordertype);
        List<Orders> orderses = null;
        //11为用户  22为未出货  33为所有
        if (ordertype == 11) {
            orderses = OrdersDao.getOrdersDao().getOrdersByUserId(SBaseApp.onUserId);
        } else if (ordertype == 22) {
            orderses = OrdersDao.getOrdersDao().getOrdersUn();
            Logger.i(orderses.toString());
        } else if (ordertype == 33) {
            orderses = OrdersDao.getOrdersDao().getOrders();
        }
        if (orderses != null) {
            System.out.println("OrderDetailActivity.initDatas");
            showRV(orderses);
            if (orderses.size()!=0) {
                mTvNothingOrderlistActivity.setVisibility(View.GONE);
            }
        }
    }

    private void showRV(List<Orders> orderses) {
//        Logger.i(cartses.toString());
        mRvrvOrderlistActivity.setLayoutManager(new LinearLayoutManager(mActivitySelf, LinearLayout.VERTICAL, false));
        mMyAdapter = new ItemRVOrderlistAdapter(mActivitySelf, orderses);
//        mRvCartsActivity.setItemViewCacheSize(5+0);
        mRvrvOrderlistActivity.setAdapter(mMyAdapter);

        if (SBaseApp.isAdmin) {
            mMyAdapter.setOnItemClickListener(new ItemRVOrderlistAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(final Orders orders) {
                    final int orderid = orders.getOrderid();
                    final List<Integer> cartIds = OrderDetailsDao.getOrderDetailsDao().getCartIds(orderid);

                    //弹个dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(mActivitySelf);
                    // 构建者模式 构建链
                    builder.setTitle("提示").setMessage("确定要出货吗").setCancelable(false)
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(mActivitySelf, "yes", Toast.LENGTH_SHORT).show();

                                    //出货①：添加出货订单--单次
                                    Delivery delivery=new Delivery() ;
                                    delivery.setDeliveryTime(System.currentTimeMillis());
                                    DeliveryDao.getDeliveryDao().addDelivery(delivery);
                                    int deliveryid = delivery.getDeliveryid();

                                    for (Integer cartId : cartIds) {
                                        System.out.println("cartId = " + cartId);
                                        Carts carts = CartsDao.getCartsDao().getCartsByCartId(cartId);
                                        int bookid = carts.getBookid();
                                        int amount = carts.getAmount();

                                        //出货②：从库存中扣除--循环扣除
                                        Books books = BooksDao.getBooksDao().getBooksById(bookid);
                                        BooksDao.getBooksDao().updateBooksStockBalance(books,-amount);

                                        //出货③：添加出货详单--循环添加
                                        DeliveryDetails deliveryDetails=new DeliveryDetails() ;
                                        deliveryDetails.setDeliveryid(deliveryid);
                                        deliveryDetails.setBookid(bookid);
                                        deliveryDetails.setDeliverynum(amount);
                                        deliveryDetails.setBookname(books.getName());
                                        deliveryDetails.setBookPic(books.getPic());
                                        DeliveryDetailsDao.getDeliveryDetailsDao().addDeliveryDetails(deliveryDetails);

                                        //出货④：修改用户订单信息，刷新界面
                                        OrdersDao.getOrdersDao().updateOrdersDeliver(orders,true);
                                        mMyAdapter.mEntities.clear();
                                        initDatas();
                                    }
                                }
                            })
                            .setNegativeButton("否", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(mActivitySelf, "no", Toast.LENGTH_SHORT).show();
                                }
                            }).create().show();

                }
            });
        }

    }
}

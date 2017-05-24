package com.example.book.books.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.example.book.books.R;
import com.example.book.books.base.SBaseActivity;
import com.example.book.books.db.DeliveryDao;
import com.example.book.books.model.Delivery;
import com.example.book.books.ui.adapter.ItemRVDeliveryAdapter;

import java.util.List;

public class DeliveryActivity extends SBaseActivity {
    private RecyclerView mRvDeliverlistActivity;
    private ItemRVDeliveryAdapter mMyAdapter;

    @Override
    public int setRootView() {
        return R.layout.activity_delivery;
    }

    @Override
    public void initView() {

        mRvDeliverlistActivity = (RecyclerView) findViewById(R.id.rv_deliverlist_activity);

    }

    @Override
    public void initDatas() {
        List<Delivery> allDelivery = DeliveryDao.getDeliveryDao().getAllDelivery();
        if (allDelivery!=null) {
            showRV(allDelivery);
        }
    }

    private void showRV(List<Delivery> deliveries) {
//        Logger.i(cartses.toString());
        mRvDeliverlistActivity.setLayoutManager(new LinearLayoutManager(mActivitySelf, LinearLayout.VERTICAL, false));
        mMyAdapter = new ItemRVDeliveryAdapter(mActivitySelf, deliveries);
//        mRvCartsActivity.setItemViewCacheSize(5+0);
        mRvDeliverlistActivity.setAdapter(mMyAdapter);

    }
}

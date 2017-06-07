package com.example.book.books.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.book.books.R;
import com.example.book.books.base.SBaseActivity;
import com.example.book.books.db.DeliveryDao;
import com.example.book.books.model.Delivery;
import com.example.book.books.ui.adapter.ItemRVDeliveryAdapter;

import java.util.List;

public class DeliveryActivity extends SBaseActivity {
    private RecyclerView mRvDeliverlistActivity;
    private ItemRVDeliveryAdapter mMyAdapter;
    private TextView mTvNodeliver;

    @Override
    public int setRootView() {
        return R.layout.activity_delivery;
    }

    @Override
    public void initView() {

        mRvDeliverlistActivity = (RecyclerView) findViewById(R.id.rv_deliverlist_activity);

        mTvNodeliver = (TextView) findViewById(R.id.tv_nodeliver);

    }

    @Override
    public void initDatas() {
        List<Delivery> allDelivery = DeliveryDao.getDeliveryDao().getAllDelivery();
        if (allDelivery!=null) {
            showRV(allDelivery);
            mTvNodeliver.setVisibility(View.GONE);
        }else{
            mTvNodeliver.setVisibility(View.VISIBLE);
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

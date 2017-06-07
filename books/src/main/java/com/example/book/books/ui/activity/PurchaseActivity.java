package com.example.book.books.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.book.books.R;
import com.example.book.books.base.SBaseActivity;
import com.example.book.books.db.PurchaseDao;
import com.example.book.books.model.Purchase;
import com.example.book.books.ui.adapter.ItemRVPurchaseAdapter;

import java.util.List;

public class PurchaseActivity extends SBaseActivity {
    private RecyclerView mRvPurchaselistActivity;
    private ItemRVPurchaseAdapter mMyAdapter;
    private TextView mTvNopurchase;

    @Override
    public int setRootView() {
        return R.layout.activity_purchase;
    }

    @Override
    public void initView() {
        mRvPurchaselistActivity = (RecyclerView) findViewById(R.id.rv_purchaselist_activity);

        mTvNopurchase = (TextView) findViewById(R.id.tv_nopurchase);

    }

    @Override
    public void initDatas() {
        List<Purchase> allPurchase = PurchaseDao.getPurchaseDao().getAllPurchase();
        if (allPurchase!=null) {
            showRV(allPurchase);
            mTvNopurchase.setVisibility(View.GONE);
        }else{
            mTvNopurchase.setVisibility(View.VISIBLE);
        }
    }

    private void showRV(List<Purchase> purchases) {
//        Logger.i(cartses.toString());
        mRvPurchaselistActivity.setLayoutManager(new LinearLayoutManager(mActivitySelf, LinearLayout.VERTICAL, false));
        mMyAdapter = new ItemRVPurchaseAdapter(mActivitySelf, purchases);
//        mRvCartsActivity.setItemViewCacheSize(5+0);
        mRvPurchaselistActivity.setAdapter(mMyAdapter);

    }
}

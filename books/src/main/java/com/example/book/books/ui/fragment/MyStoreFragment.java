package com.example.book.books.ui.fragment;


import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;

import com.example.book.books.R;
import com.example.book.books.base.SBaseFragment;
import com.example.book.books.ui.activity.OrderDetailActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyStoreFragment extends SBaseFragment implements View.OnClickListener {
    private Button mBtVieworderMystoreFragment;



    @Override
    public int setRootView() {
        return R.layout.fragment_mystore;
    }

    @Override
    public void initView() {
        mBtVieworderMystoreFragment = (Button) findViewById(R.id.bt_vieworder_mystore_fragment);

        mBtVieworderMystoreFragment.setOnClickListener(this);
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.bt_vieworder_mystore_fragment:
                //11为用户  22为未出货  33为所有
                gotoActivity(OrderDetailActivity.class,"ordertype",11);
                break;
        }
    }
}

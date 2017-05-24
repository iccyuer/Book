package com.example.book.books.ui.fragment;


import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;

import com.example.book.books.R;
import com.example.book.books.base.SBaseFragment;
import com.example.book.books.ui.activity.DeliveryActivity;
import com.example.book.books.ui.activity.OrderDetailActivity;
import com.example.book.books.ui.activity.PurchaseActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class AManageFragment extends SBaseFragment implements View.OnClickListener {
    private Button mBtPurchaseAmanageFrag;
    private Button mBtUndeliverAmanageFrag;
    private Button mBtAllorderAmanageFrag;
    private Button mBtDeliverAmanageFrag;



    @Override
    public int setRootView() {
        return R.layout.fragment_amanage;
    }

    @Override
    public void initView() {
        mBtPurchaseAmanageFrag = (Button) findViewById(R.id.bt_purchase_amanage_frag);
        mBtUndeliverAmanageFrag = (Button) findViewById(R.id.bt_unorder_amanage_frag);
        mBtAllorderAmanageFrag = (Button) findViewById(R.id.bt_allorder_amanage_frag);
        mBtDeliverAmanageFrag = (Button) findViewById(R.id.bt_deliver_amanage_frag);


        mBtPurchaseAmanageFrag.setOnClickListener(this);
        mBtUndeliverAmanageFrag.setOnClickListener(this);
        mBtAllorderAmanageFrag.setOnClickListener(this);
        mBtDeliverAmanageFrag.setOnClickListener(this);
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.bt_purchase_amanage_frag:
                gotoActivity(PurchaseActivity.class);
                break;
            case R.id.bt_unorder_amanage_frag:
                gotoActivity(OrderDetailActivity.class, "ordertype", 22);
                break;
            case R.id.bt_allorder_amanage_frag:
                gotoActivity(OrderDetailActivity.class, "ordertype", 33);
                break;
            case R.id.bt_deliver_amanage_frag:
                gotoActivity(DeliveryActivity.class);
                break;
        }
    }
}

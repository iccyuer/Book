package com.example.book.books.ui.fragment;


import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;

import com.example.book.books.R;
import com.example.book.books.base.SBaseFragment;
import com.example.book.books.ui.activity.SellDataActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class AMyFragment extends SBaseFragment implements View.OnClickListener {
    private Button mBtSelldataAmyFrag;


    @Override
    public int setRootView() {
        return R.layout.fragment_amy;
    }

    @Override
    public void initView() {
        mBtSelldataAmyFrag = (Button) findViewById(R.id.bt_selldata_amy_frag);

        mBtSelldataAmyFrag.setOnClickListener(this);
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.bt_selldata_amy_frag:
                gotoActivity(SellDataActivity.class);
                break;
        }
    }
}

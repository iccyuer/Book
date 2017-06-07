package com.example.book.books.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.book.books.R;

import java.text.SimpleDateFormat;

/**
 * Created by Admin on 2017/5/31.
 */

public class ShowSellDialog extends Dialog implements View.OnClickListener {
    private TextView mTvSellTitle;
    private TextView mTvSellNum;
    private TextView mTvSellPrice;
    private Button mBtSellCancel;

    private Context mContext;


    private OnConfirmListener mOnConfirmListener;

    public void setOnConfirmListener(OnConfirmListener onConfirmListener) {
        mOnConfirmListener = onConfirmListener;
    }

    public ShowSellDialog(Context context, long timeaa, long timebb, int i, float v) {
        super(context);
        mContext = context;
        setContentView(R.layout.dialog_showsell);

        mTvSellTitle = (TextView) findViewById(R.id.tv_sell_title);
        mTvSellNum = (TextView) findViewById(R.id.tv_sell_num);
        mTvSellPrice = (TextView) findViewById(R.id.tv_sell_price);
        mBtSellCancel = (Button) findViewById(R.id.bt_sell_cancel);

        if (timeaa==0) {
            mTvSellTitle.setText("所有时间"+"\r\n"+" 出货统计表");
        }else{
            mTvSellTitle.setText(getTime(timeaa)+"~"+getTime(timebb)+"\r\n"+" 出货统计表");
        }

        mTvSellNum.setText("出货总数目："+i+"册");
        mTvSellPrice.setText("总销售额："+v+"元");
        mBtSellCancel.setOnClickListener(this);
    }

    private String getTime(long time) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(time);
    }


    @Override
    public void onClick(View v) {
        cancel();
    }

    public interface OnConfirmListener {
        void onConfrim(int i);
    }
}

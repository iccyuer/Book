package com.example.book.books.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.example.book.books.R;
import com.example.book.books.base.SBaseActivity;
import com.example.book.books.db.BooksDao;
import com.example.book.books.db.CartsDao;
import com.example.book.books.db.OrderDetailsDao;
import com.example.book.books.model.Books;
import com.example.book.books.model.Carts;

import java.util.List;

public class ZFActivity extends SBaseActivity implements View.OnClickListener {
    private TextView mTvUppayZfActivity;
    private TextView mTvMypayZfActivity;
    private int mOrderid;


    @Override
    public int setRootView() {
        return R.layout.activity_zf;
    }

    @Override
    public void initView() {
        setTitleCenter("请选择支付方式");
        mTvUppayZfActivity = (TextView) findViewById(R.id.tv_uppay_zf_activity);
        mTvMypayZfActivity = (TextView) findViewById(R.id.tv_mypay_zf_activity);

        mTvMypayZfActivity.setOnClickListener(this);
        mTvUppayZfActivity.setOnClickListener(this);
    }

    @Override
    public void initDatas() {
        Intent intent = getIntent();
        mOrderid = intent.getIntExtra("orderid", 0);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_uppay_zf_activity:
                Intent intent = new Intent(ZFActivity.this, YLActivity.class);
                startActivityForResult(intent, 110);
                break;
            case R.id.tv_mypay_zf_activity:
                Intent intent1 = new Intent(ZFActivity.this, MyPayActivity.class);
                intent1.putExtra("orderid", mOrderid);
                startActivityForResult(intent1, 110);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (110 == requestCode && 120 == resultCode) {
            String result = data.getStringExtra("result");
            if (result.equalsIgnoreCase("success")) {
                Intent intent = new Intent();
                intent.putExtra("result", result);
                intent.putExtra("orderId", mOrderid);
                setResult(120, intent);

                //支付成功--扣除库存
                List<Integer> cartIds = OrderDetailsDao.getOrderDetailsDao().getCartIds(mOrderid);
                for (Integer cartId : cartIds) {
                    Carts carts = CartsDao.getCartsDao().getCartsByCartId(cartId);
                    Books books = BooksDao.getBooksDao().getBooksById(carts.getBookid());
                    BooksDao.getBooksDao().updateBooksStockBalance(books, -carts.getAmount());
                }

            } else if (result.equalsIgnoreCase("fail")) {
                Intent intent = new Intent();
                intent.putExtra("result", result);
                intent.putExtra("orderId", mOrderid);
                setResult(120, intent);
            } else if (result.equalsIgnoreCase("cancel")) {
                Intent intent = new Intent();
                intent.putExtra("result", result);
                intent.putExtra("orderId", mOrderid);
                setResult(120, intent);
            }
            finish();
        }
    }
}

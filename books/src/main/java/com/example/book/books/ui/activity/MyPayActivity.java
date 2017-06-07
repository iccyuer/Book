package com.example.book.books.ui.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.book.books.R;
import com.example.book.books.base.SBaseActivity;
import com.example.book.books.db.CartsDao;
import com.example.book.books.db.OrderDetailsDao;
import com.example.book.books.db.OrdersDao;
import com.example.book.books.model.Carts;
import com.example.book.books.model.Orders;
import com.example.book.books.ui.fragment.LoadingFragment;
import com.example.book.books.utils.DataFormatUtil;

public class MyPayActivity extends SBaseActivity implements View.OnClickListener {

    private boolean isOpen;
    private LinearLayout mLlOrderinfoDescribe;
    private LinearLayout mLlOrderinfoTime;
    private LinearLayout mLlOrderinfoId;
    private ImageView mImgvOrderinfoArrowDown;
    private ImageView mImgvOrderinfoArrowUp;
    private EditText mEdtMypayPayid;
    private EditText mEdtMypayPassword;
    private TextView mTvPaymentMypayActivity;
    private TextView mTvOrderpriceMypayActivity;
    private TextView mTvDescribeMypayActivity;
    private TextView mTvOrdertimeMypayActivity;
    private TextView mTvOrderidMypayActivity;

    LoadingFragment mLayoutFragLoading = new LoadingFragment();
    private static final int REMOVE_LOADING = 110;
    private static final int REMOVE_PAY_LOADING = 120;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REMOVE_LOADING:
                    removeFrag(mLayoutFragLoading);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//撤销全屏显示
//                    为了欢迎页不受影响，把FrameLayout父布局设置为白背景，欢迎页消失时，把布局隐藏了
//                    mLayoutFragWelcome.setVisibility(View.GONE);
                    break;
                case REMOVE_PAY_LOADING:
                    if ("188888888".equals(mPayid) && "123456".equals(mPaypwd)) {
                        Intent intent = new Intent();
                        intent.putExtra("result", "success");
                        setResult(120, intent);
                        Toast.makeText(mActivitySelf, "支付成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(mActivitySelf, "支付账号或密码错误", Toast.LENGTH_SHORT).show();
//                            return;
                    }
                    dismissLoadingDialog();
                    break;
            }

        }
    };
    private Dialog mDialog;
    private String mPayid;
    private String mPaypwd;

    @Override
    public int setRootView() {
        return R.layout.activity_my_pay;
    }

    @Override
    public void initView() {
        setTitleCenter("订单支付");
        setTitleLeftGone();
        mLlOrderinfoDescribe = (LinearLayout) findViewById(R.id.ll_orderinfo_describe);
        mLlOrderinfoTime = (LinearLayout) findViewById(R.id.ll_orderinfo_time);
        mLlOrderinfoId = (LinearLayout) findViewById(R.id.ll_orderinfo_id);
        mImgvOrderinfoArrowDown = (ImageView) findViewById(R.id.imgv_orderinfo_arrow_down);
        mImgvOrderinfoArrowUp = (ImageView) findViewById(R.id.imgv_orderinfo_arrow_up);
        mTvOrderpriceMypayActivity = (TextView) findViewById(R.id.tv_orderprice_mypay_activity);
        mTvDescribeMypayActivity = (TextView) findViewById(R.id.tv_describe_mypay_activity);
        mTvOrdertimeMypayActivity = (TextView) findViewById(R.id.tv_ordertime_mypay_activity);
        mTvOrderidMypayActivity = (TextView) findViewById(R.id.tv_orderid_mypay_activity);

        mEdtMypayPayid = (EditText) findViewById(R.id.edt_mypay_payid);
        mEdtMypayPassword = (EditText) findViewById(R.id.edt_mypay_password);
        mTvPaymentMypayActivity = (TextView) findViewById(R.id.tv_payment_mypay_activity);

        mTvPaymentMypayActivity.setOnClickListener(this);

        addFrag(R.id.layout_frag_loading, mLayoutFragLoading);
        mHandler.sendEmptyMessageDelayed(REMOVE_LOADING, 2000);
    }

    @Override
    public void initDatas() {
        Intent intent = getIntent();
        int orderid = intent.getIntExtra("orderid", 0);
        Orders orders = OrdersDao.getOrdersDao().getOrderById(orderid);
        mTvOrderpriceMypayActivity.setText(orders.getAllprice() + "元");
        int cartId = OrderDetailsDao.getOrderDetailsDao().getCartId(orderid);
        Carts carts = CartsDao.getCartsDao().getCartsByCartId(cartId);
        mTvDescribeMypayActivity.setText(carts.getName() + "...等书目");
        mTvOrdertimeMypayActivity.setText(DataFormatUtil.format(orders.getOrderTime()));
        mTvOrderidMypayActivity.setText(orderid + "");
    }

    public void openorclose(View view) {
        if (isOpen) {
            isOpen = false;
            mLlOrderinfoDescribe.setVisibility(View.GONE);
            mLlOrderinfoTime.setVisibility(View.GONE);
            mLlOrderinfoId.setVisibility(View.GONE);
            mImgvOrderinfoArrowDown.setVisibility(View.VISIBLE);
            mImgvOrderinfoArrowUp.setVisibility(View.GONE);
        } else {
            isOpen = true;
            mLlOrderinfoDescribe.setVisibility(View.VISIBLE);
            mLlOrderinfoTime.setVisibility(View.VISIBLE);
            mLlOrderinfoId.setVisibility(View.VISIBLE);
            mImgvOrderinfoArrowDown.setVisibility(View.GONE);
            mImgvOrderinfoArrowUp.setVisibility(View.VISIBLE);
        }
    }

    public void putpayid(View view) {
        mEdtMypayPayid.setText("188888888");
        mEdtMypayPassword.setText("123456");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_payment_mypay_activity:
                mPayid = mEdtMypayPayid.getText().toString().trim();
                mPaypwd = mEdtMypayPassword.getText().toString().trim();
                if ("".equals(mPayid) || "".equals(mPaypwd)) {
                    Toast.makeText(mActivitySelf, "不可为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                showLoadingDialog();
                mHandler.sendEmptyMessageDelayed(REMOVE_PAY_LOADING, 2000);
                break;
        }
    }

    private void dismissLoadingDialog() {
        mDialog.dismiss();
    }

    private void showLoadingDialog() {
        mDialog = new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.dialog_payloading);
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
    }

    @Override
    public void onBackPressed() {
        showBackDialog();
        if (false) {
            super.onBackPressed();
        }
    }

    private void showBackDialog() {
        //弹个dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 构建者模式 构建链
        builder.setTitle("提示").setMessage("订单还没有支付完成，确定要放弃支付订单吗?").setCancelable(false)
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.putExtra("result", "cancel");
                        setResult(120, intent);
                        finish();
                    }
                })
                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();
    }
}

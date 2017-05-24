package com.example.book.books.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.example.book.books.R;
import com.example.book.books.base.SBaseActivity;
import com.unionpay.UPPayAssistEx;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class YLActivity extends SBaseActivity implements Runnable,Handler.Callback{

    @Override
    public int setRootView() {
        return R.layout.activity_yl;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initDatas() {
        mContext = this;
        mHandler = new Handler(this);
        new Thread(this).start();

//        YLService retrofit = NetJsonUtil.getRetrofit(TN_URL_02, YLService.class);
//        Observable<String> stringObservable = retrofit.doPay();
//        stringObservable.observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<String>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(String tn) {
//                        Logger.e(tn);
//                        UPPayAssistEx.startPayByJAR(mActivitySelf, com.unionpay.uppay.PayActivity.class, null, null,
//                                tn, mMode);
//                    }
//                });


//        Call<String> stringCall = retrofit.doPay2();
//        stringCall.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//
//                if (response==null) {
//                    System.out.println("response = null" );
//                    return;
//                }
//                if (response.body()==null) {
//                    System.out.println("body = null" );
//                    return;
//                }
//                String s = response.body().toString();
//                System.out.println("s = " + s);
//
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Logger.i("错误");
//            }
//        });

    }


    private Context mContext = null;
    private int mGoodsIdx = 0;
    private Handler mHandler = null;


    /*****************************************************************
     * mMode参数解释： "00" - 启动银联正式环境 "01" - 连接银联测试环境
     *****************************************************************/
    private final String mMode = "01";
    private static final String TN_URL_01 = "http://101.231.204.84:8091/sim/getacptn";
    private static final String TN_URL_02 = "http://www.baidu.com";
    private static final String TN_URL_03 = "http://http://119.75.217.109/";


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*************************************************
         * 步骤3：处理银联手机支付控件返回的支付结果
         ************************************************/
        if (data == null) {
            return;
        }

        String msg = "";
        /*
         * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
         */
        String str = data.getExtras().getString("pay_result");
        if (str.equalsIgnoreCase("success")) {
            msg = "支付成功！";
        } else if (str.equalsIgnoreCase("fail")) {
            msg = "支付失败！";
        } else if (str.equalsIgnoreCase("cancel")) {
            msg = "用户取消了支付";
        }
        Intent intent=new Intent() ;
        intent.putExtra("result",str);
        setResult(110,intent);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("支付结果通知");
        builder.setMessage(msg);
        builder.setInverseBackgroundForced(true);
        // builder.setCustomTitle();
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.create().show();
    }

    @Override
    public void run() {
        String tn = null;
        InputStream is;
        try {

            String url = TN_URL_01;

            URL myURL = new URL(url);
            URLConnection ucon = myURL.openConnection();
            ucon.setConnectTimeout(120000);
            is = ucon.getInputStream();
            int i = -1;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((i = is.read()) != -1) {
                baos.write(i);
            }

            tn = baos.toString();
            is.close();
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Message msg = mHandler.obtainMessage();
        msg.obj = tn;
        mHandler.sendMessage(msg);
    }

    @Override
    public boolean handleMessage(Message msg) {
        String tn = "";
        if (msg.obj == null || ((String) msg.obj).length() == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("错误提示");
            builder.setMessage("网络连接失败,请重试!");
            builder.setNegativeButton("确定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.create().show();
        } else {
            tn = (String) msg.obj;
            /*************************************************
             * 步骤2：通过银联工具类启动支付插件
             ************************************************/
            UPPayAssistEx.startPayByJAR(mActivitySelf, com.unionpay.uppay.PayActivity.class, null, null,
                                tn, mMode);
        }

        return false;
    }
}
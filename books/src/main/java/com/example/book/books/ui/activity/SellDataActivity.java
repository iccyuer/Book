package com.example.book.books.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.book.books.R;
import com.example.book.books.base.SBaseActivity;
import com.example.book.books.db.BooksDao;
import com.example.book.books.db.DeliveryDao;
import com.example.book.books.db.DeliveryDetailsDao;
import com.example.book.books.db.SellDataDao;
import com.example.book.books.entity.SellData;
import com.example.book.books.model.Delivery;
import com.example.book.books.model.DeliveryDetails;
import com.example.book.books.ui.adapter.ItemRVSellAdapter;
import com.example.book.books.ui.dialog.ShowSellDialog;
import com.example.book.books.ui.views.TimeDayPickerView;
import com.example.book.books.ui.views.TimeMonthPickerView;
import com.example.book.books.ui.views.TimeYearPickerView;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class SellDataActivity extends SBaseActivity implements View.OnClickListener {
    private TimeYearPickerView mTimeYearPickerView;
    private TimeMonthPickerView mTimeMonthPickerView;
    private TimeDayPickerView mTimeDayPickerView;
    private RecyclerView mRvSelldataActivity;
    private Button mBtShowTimepickeraSelldataActivity;
    private Button mBtShowTimepickerbSelldataActivity;
    private TimeDayPickerView mTimeDayPickerView2;
    private long timeaa, timebb;
    private List<SellData> mAllSellData;
    private LinearLayout mLlEmptySelldata;


    @Override
    public int setRootView() {
        return R.layout.activity_sell_data;
    }

    @Override
    public void initView() {
        mBtShowTimepickeraSelldataActivity = (Button) findViewById(R.id.bt_show_timepickera_selldata_activity);
        mBtShowTimepickerbSelldataActivity = (Button) findViewById(R.id.bt_show_timepickerb_selldata_activity);

        mRvSelldataActivity = (RecyclerView) findViewById(R.id.rv_selldata_activity);

        mLlEmptySelldata = (LinearLayout) findViewById(R.id.ll_empty_selldata);


        mBtShowTimepickeraSelldataActivity.setOnClickListener(this);
        mBtShowTimepickerbSelldataActivity.setOnClickListener(this);

        mTimeDayPickerView = new TimeDayPickerView(this);
        mTimeDayPickerView2 = new TimeDayPickerView(this);

        mTimeDayPickerView.setOnPickerConfirm(new TimeYearPickerView.onPickerConfirm() {
            @Override
            public void onPickerConfirm(Date date) {
//                Logger.i(date.getTime()+"");
                String timea = mBtShowTimepickeraSelldataActivity.getText().toString().trim();
                String timeb = mBtShowTimepickerbSelldataActivity.getText().toString().trim();
//                Logger.i(timea+"   x  "+timeb);
                timeaa = date.getTime();
                showSellData(timea,timeb);
            }
        });
        mTimeDayPickerView2.setOnPickerConfirm(new TimeYearPickerView.onPickerConfirm() {
            @Override
            public void onPickerConfirm(Date date) {
//                Logger.i(date.getTime()+"");
                String timea = mBtShowTimepickeraSelldataActivity.getText().toString().trim();
                String timeb = mBtShowTimepickerbSelldataActivity.getText().toString().trim();
//                Logger.i(timea+"   x  "+timeb);
                timebb = date.getTime() + 86399000;
                showSellData(timea,timeb);
            }
        });
    }

    private void showSellData(String timea,String timeb){
        if (!"请选择开始日期".equals(timea) && !"请选择结束日期".equals(timeb)) {
            //同一一天+23.59.59
            if (timeaa == timebb) {
                timebb += 86399000;
            }
//                    Logger.i(DataFormatUtil.format(timeaa) + "     " + DataFormatUtil.format(timebb));
            List<Delivery> someDelivery = DeliveryDao.getDeliveryDao().getSomeDelivery(timeaa, timebb);

            if (someDelivery==null||someDelivery.size()==0) {
                mLlEmptySelldata.setVisibility(View.VISIBLE);
                mRvSelldataActivity.setVisibility(View.GONE);
                return;
            }else{
                mLlEmptySelldata.setVisibility(View.GONE);
                mRvSelldataActivity.setVisibility(View.VISIBLE);
            }

//                    Logger.i(someDelivery.toString());
//                    List<SellData> sellDatas = new ArrayList<>();
            SellDataDao.getSellDataDao().deleteAll();
            for (Delivery delivery : someDelivery) {
                List<DeliveryDetails> deliveryDetails = DeliveryDetailsDao.getDeliveryDetailsDao().getDeliveryDetails(delivery.getDeliveryid());
//                        Logger.i(deliveryDetails.toString());
                for (DeliveryDetails deliveryDetail : deliveryDetails) {
                    int bookid=deliveryDetail.getBookid();
                    boolean b = SellDataDao.getSellDataDao().checkExist(bookid);
                    if (b) {
                        SellData sellData = SellDataDao.getSellDataDao().getSellData(bookid);
                        int count = sellData.getCount();
                        count++;
                        sellData.setCount(count);
                        SellDataDao.getSellDataDao().updateNum(sellData);
                    }else{
                        SellData sellData = new SellData();
                        sellData.setBookid(deliveryDetail.getBookid());
                        sellData.setBookname(deliveryDetail.getBookname());
                        sellData.setCount(deliveryDetail.getDeliverynum());
                        sellData.setPic(deliveryDetail.getBookPic());
                        SellDataDao.getSellDataDao().addSellDate(sellData);
                    }
                }
            }
            mAllSellData = SellDataDao.getSellDataDao().getAllSellData();

            if (mAllSellData==null||mAllSellData.size()==0) {
                mLlEmptySelldata.setVisibility(View.VISIBLE);
                mRvSelldataActivity.setVisibility(View.GONE);
            }else{
                mLlEmptySelldata.setVisibility(View.GONE);
                mRvSelldataActivity.setVisibility(View.VISIBLE);
            }

//                    Logger.i(allSellData.toString());
            Collections.sort(mAllSellData);
//                    Logger.i(mAllSellData.toString());
            mRvSelldataActivity.setLayoutManager(new LinearLayoutManager(mActivitySelf, LinearLayout.VERTICAL, false));
            ItemRVSellAdapter itemRVSellAdapter = new ItemRVSellAdapter(SellDataActivity.this, mAllSellData);
            mRvSelldataActivity.setAdapter(itemRVSellAdapter);
//                    Logger.i(sellDatas.toString());
////                    List<SellData> sellDatas2 = new ArrayList<>();
//                    Set<Integer> ids=new TreeSet<Integer>() ;
//                    ids.add(0);
//                    for (int i = 0; i < sellDatas.size(); i++) {
//                        SellData sellData = sellDatas.get(i);
//                        for (int j = 0; j < i+1; j++) {
//                            Integer id = ids.;
//                            if (sellData.getBookid()==id) {
//                                int count = sellData.getCount();
//                                count++;
//                                sellData.setCount(count);
//                            }else{
//                                ids.add(sellData.getBookid());
//                            }
//                        }
//                    }


//                    List<SellData> sellDatas2 = new ArrayList<>();
//                    for (int i = 0; i < sellDatas.size(); i++) {
//                        SellData sellData = sellDatas.get(i);
//                        if (i==0) {
//                            sellDatas2.add(sellData);
//                        }else{
//                            for (int j = 0; j < sellDatas2.size(); j++) {
//                                SellData sellData1 = sellDatas2.get(j);
//                                if (sellData.getBookid()==sellData1.getBookid()) {
//                                    int count = sellData1.getCount();
//                                    count++;
//                                    sellData1.setCount(count);
//                                    sellDatas2.set(i,sellData1);
//                                }else{
//                                    sellDatas2.add(sellData);
//                                }
//                            }
//                        }
//                    }
//
        }
    }

    @Override
    public void initDatas() {
        //默认看看范围--所有
        timeaa=0;
        timebb=System.currentTimeMillis();
        mBtShowTimepickeraSelldataActivity.setText("..");
        mBtShowTimepickerbSelldataActivity.setText("今天");
        showSellData("","");
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.bt_show_timepickera_selldata_activity:
                mTimeDayPickerView.showDayPicker(v);
                break;
            case R.id.bt_show_timepickerb_selldata_activity:
                mTimeDayPickerView2.showDayPicker(v);
                break;
        }
    }

    public void tongji(View view){
        if (mAllSellData==null||mAllSellData.size()==0) {
            Toast.makeText(mActivitySelf, "所选时间无出货记录", Toast.LENGTH_SHORT).show();
            return;
        }
        System.out.println("num = " + countNum()+"  price"+ countPrice());
        showSellDataialog(timeaa,timebb, countNum(),countPrice());
    }

    private void showSellDataialog(long timeaa, long timebb, int i, float v) {
        ShowSellDialog showSellDialog=new ShowSellDialog(this,timeaa,timebb,i,v) ;
        showSellDialog.show();
    }

    private int countNum(){
        int i=0;
        for (SellData sellData : mAllSellData) {
            i+=sellData.getCount();
        }
        return i;
    }

    private float countPrice(){
        float a=0.0f;
        for (SellData sellData : mAllSellData) {
            a+=sellData.getCount()* BooksDao.getBooksDao().getBooksById(sellData.getBookid()).getPrice();
        }
        float vjian = (float) (Math.round((a) * 100)) / 100;
        return vjian;
    }
}

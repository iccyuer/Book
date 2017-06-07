package com.example.book.books.ui.views;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;

import com.bigkoo.pickerview.TimePickerView;
import com.orhanobut.logger.Logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Admin on 2017/5/30.
 */

public class TimeYearPickerView {
    private TimePickerView pvTime;
    private Context mContext;

    public TimeYearPickerView(Context context) {
        mContext = context;
        initTimePicker();
    }

    private void initTimePicker() {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();

        Calendar startDate = Calendar.getInstance();
        startDate.set(2013, 0, 23);

        Calendar endDate = Calendar.getInstance();
        endDate.set(2019, 11, 28);
        //香蕉选择器
        pvTime = new TimePickerView.Builder(mContext, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null

                /*btn_Time.setText(getTime(date));*/
                mOnPickerConfirm.onPickerConfirm(date);
                Logger.i(date.getTime()+"    "+getTime(date).length());
                Button btn = (Button) v;
                btn.setText(getTime(date));

            }
        })
                .setType(new boolean[]{true, false, false, false, false, false})
                .setLabel("", "", "", "", "", "") //设置空字符串以隐藏单位提示   hide label
                .setDividerColor(Color.DKGRAY)
                .setContentSize(20)
                .setDate(selectedDate)
                .setRangDate(startDate, selectedDate)
//                .setBackgroudId(ContextCompat.getColor(this,R.color.pickerview_timebtn_nor)) //设置显示时的外部背景颜色
//                .setDecorView(null)
                .build();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        return format.format(date);
    }

    public void showYearPicker(View view) {
        pvTime.show(view);
    }

    private onPickerConfirm mOnPickerConfirm;

    public void setOnPickerConfirm(onPickerConfirm onPickerConfirm) {
        mOnPickerConfirm = onPickerConfirm;
    }

    public interface onPickerConfirm{
        void onPickerConfirm(Date date);
    }
}

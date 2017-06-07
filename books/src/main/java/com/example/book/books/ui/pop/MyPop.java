package com.example.book.books.ui.pop;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.book.books.R;

public class MyPop extends PopupWindow implements View.OnClickListener {
    private Context mContext;
    private LayoutInflater mInflater;

    public MyPop(Context context) {
        super(context);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        //这里只有一个重载，传入一个view对象
        View view = mInflater.inflate(R.layout.pop, null, false);//设置pop的样式
        TextView tvBook = (TextView) view.findViewById(R.id.tv_book_pop);
        TextView tvAuthor = (TextView) view.findViewById(R.id.tv_author_pop);

        String book = tvBook.getText().toString().trim();
        String author = tvAuthor.getText().toString().trim();

        tvBook.setTag(book);
        tvAuthor.setTag(author);

        tvBook.setOnClickListener(this);
        tvAuthor.setOnClickListener(this);
        //设置显示样式
        this.setContentView(view);
        //设置大小
        this.setWidth(190);
        this.setHeight(240);
        //设置兼容性  兼容2.0+ 死的写法不用管
        this.setBackgroundDrawable(new BitmapDrawable());
        //设置焦点（写两个），为false时此window不响应事件（不响应点旁边关闭、点击back关闭）
        //如果写true，保证响应事件，可以退出
        this.setFocusable(true);
        this.setOutsideTouchable(true);

        //popupWindow本身就是一个window，设置窗体动画，就要获得窗体对象
//        this.setAnimationStyle(R.style.pop);//设置pop的动画样式，和窗体动画一样
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_book_pop:
                mOnClickListener.onClick((String) v.getTag());
                break;
            case R.id.tv_author_pop:
                mOnClickListener.onClick((String) v.getTag());
                break;
        }
        dismiss();
    }

    private OnClickListener mOnClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public interface OnClickListener{
        void onClick(String tx);
    }
}
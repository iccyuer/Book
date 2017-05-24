package com.example.book.books.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * Created by Admin on 2017/5/13.
 */

public class SlideGroup extends ScrollView {

    private LinearLayout mWapper;
    private ViewGroup mMenu;
    private ViewGroup mContent;
    private boolean once;
    private int mHeight;


    public SlideGroup(Context context) {
        this(context, null);
    }

    public SlideGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        if (!once)
        {
            mWapper = (LinearLayout) getChildAt(0);
            mMenu = (ViewGroup) mWapper.getChildAt(0);
            mContent = (ViewGroup) mWapper.getChildAt(1);
            mHeight = 300;
            once = true;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

//        System.out.println("l = " + l);
//        System.out.println("t = " + t);
//        System.out.println("oldl = " + oldl);
//        System.out.println("oldt = " + oldt);
        float scale = t * 1.0f / mHeight; // 1 ~ 0
//        System.out.println("height = " + mHeight);
        mMenu.setTranslationY(mHeight*scale*0.5f);


    }
}

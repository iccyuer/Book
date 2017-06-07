package com.example.book.library.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.book.library.R;
import com.example.book.library.control.ActivityControl;

import java.util.ArrayList;


/**
 * Created by Administractor on 2016/12/28.
 * * 1.模板模式封装Activity的使用流程
 * 2.组件间访问
 * 3.快捷操作fragment
 * <p/>
 * 4.快捷打吐司
 * 5.log
 * 6.dig
 * <p/>
 * 7.共性属性
 * 8.封装三部标题栏    左   中    右
 */

public abstract class BaseActivity extends FragmentActivity {
    //共性属性
    public FragmentManager mFragmentManager;
    public BaseActivity mActivitySelf;//方便获取activity对象
    public LayoutInflater mLayoutInflater;
    public TextView mTitleCenter, mTitleRight;
    public ImageView mTitleLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityControl.add(this);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        mFragmentManager = this.getSupportFragmentManager();//作兼容
        mLayoutInflater = this.getLayoutInflater();
        mActivitySelf = this;
        if (isNeedTitle()) {
            addTitle();
        } else {
            setContentView(setRootView());
        }
        initOthers();
        initView();
        initDatas();
    }

    public void initOthers() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityControl.remove(this);
    }

    private void addTitle() {
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

//        View titleView=mLayoutInflater.inflate(useOwnTitle(),linearLayout,false);
        View titleView = mLayoutInflater.inflate(BaseApp.mTitleLayoutID, linearLayout, false);
        View contentView = mLayoutInflater.inflate(setRootView(), linearLayout, false);

        mTitleCenter = (TextView) titleView.findViewById(R.id.title_center);
        mTitleLeft = (ImageView) titleView.findViewById(R.id.title_left);
        mTitleRight = (TextView) titleView.findViewById(R.id.title_right);

        mTitleCenter.setVisibility(View.INVISIBLE);
        if (mTitleRight != null) {
            mTitleRight.setVisibility(View.INVISIBLE);
        }

        mTitleLeft.setVisibility(View.INVISIBLE);
        linearLayout.addView(titleView);
        linearLayout.addView(contentView);
        setContentView(linearLayout);
    }

    public void setTitleCenter(String text) {
        setTitleCenter(text, null);
    }

    public void setTitleCenter(String text, View.OnClickListener onClickListener) {

        if (mTitleCenter != null) {
            mTitleCenter.setVisibility(View.VISIBLE);
            if (text != null) {
                mTitleCenter.setText(text);
            }
            if (onClickListener != null) {
                mTitleCenter.setOnClickListener(onClickListener);
            }
        }

    }

    public void setTitleRight(String text) {
        setTitleRight(text, null);
    }

    public void setTitleRight(String text, View.OnClickListener onClickListener) {
        if (mTitleRight != null) {
            mTitleRight.setVisibility(View.VISIBLE);
            if (text != null) {
                mTitleRight.setText(text);
            }
            if (onClickListener != null) {
                mTitleRight.setOnClickListener(onClickListener);
            }
        }
    }


    public void setTitleLeft(View.OnClickListener onClickListener) {
        if (mTitleLeft != null) {
            mTitleLeft.setVisibility(View.VISIBLE);
            if (onClickListener != null) {
                mTitleLeft.setOnClickListener(onClickListener);
            }
        }
    }

    public void setTitleRightGone(){
        mTitleRight.setVisibility(View.GONE);
    }

    public void setTitleLeftGone(){
        mTitleLeft.setVisibility(View.GONE);
    }

    public void setTitleLeftVISIBLE(){
        mTitleLeft.setVisibility(View.VISIBLE);
    }

    public boolean isNeedTitle() {
        return true;//默认需要标题，如果不需要可以选择覆写false
    }

    public abstract int setRootView();

//    public abstract int useOwnTitle();//返回title样式，这里在app做了统一样式

    public abstract void initView();

    public abstract void initDatas();

    //快捷跳转到activity
    public void gotoActivity(Class des) {
        Intent intent = new Intent(this, des);
        startActivity(intent);
    }

    public void gotoActivity(Class des, String key, Bundle bundle) {
        Intent intent = new Intent(this, des);
        intent.putExtra(key, bundle);
        startActivity(intent);
    }

    public void gotoActivity(Class des, String key, int data) {
        Intent intent = new Intent(this, des);
        intent.putExtra(key, data);
        startActivity(intent);
    }

    public void gotoActivity(Class des, String key, String data) {
        Intent intent = new Intent(this, des);
        intent.putExtra(key, data);
        startActivity(intent);
    }

    public void gotoActivity(Class des, String key1, String data1, String key2, String data2) {
        Intent intent = new Intent(this, des);
        intent.putExtra(key1, data1);
        intent.putExtra(key2, data2);
        startActivity(intent);
    }

    public void gotoActivity(Class des, String key1, ArrayList<Integer> datas, String key2, Float allprice) {
        Intent intent = new Intent(mActivitySelf, des);
        intent.putIntegerArrayListExtra(key1,datas);
        intent.putExtra(key2,allprice);
        startActivity(intent);
    }


    //快捷跳转到service
    public void gotoService(Class des) {
        Intent intent = new Intent(this, des);
        startService(intent);
    }

    public void gotoService(Class des, String key, Bundle bundle) {
        Intent intent = new Intent(this, des);
        intent.putExtra(key, bundle);
        startService(intent);
    }

    //快捷跳转到broad
    public void gotoBroad(Class des) {
        Intent intent = new Intent(this, des);
        sendBroadcast(intent);
    }

    public void gotoBroad(Class des, String key, Bundle bundle) {
        Intent intent = new Intent(this, des);
        intent.putExtra(key, bundle);
        sendBroadcast(intent);
    }

    //快捷操作Fragment
    public void addFrag(int resID, BaseFragment fragment) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(resID, fragment, fragment.mTag);
        transaction.commit();
    }

    public void removeFrag(BaseFragment fragment) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.remove(fragment);
        transaction.commit();
    }

    public void replaceFrag(int resID, BaseFragment fragment) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(resID, fragment, fragment.mTag);
        transaction.commit();
    }

    public void hideFrag(BaseFragment fragment) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.hide(fragment);
        transaction.commit();
    }

    public void showFrag(BaseFragment fragment) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.show(fragment);
        transaction.commit();
    }
    //快捷打toast
//    public void showToast(String msg){
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
//    }
    //快捷打log
//    public void e(String msg){
//        Logger.e(msg);
//    }


}

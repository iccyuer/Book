package com.example.book.books.ui.fragment;


import android.support.v4.app.Fragment;

import com.example.book.books.R;
import com.example.book.books.base.SBaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeFragment extends SBaseFragment {


    @Override
    public int setRootView() {
        return R.layout.fragment_welcome;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initDatas() {

    }

    @Override
    public boolean isNeedTitle() {
        return false;
    }
}

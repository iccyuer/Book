package com.example.book.books.base;

import com.example.book.library.base.BaseFragment;


/**
 * Created by Administractor on 2017/1/18.
 */

public abstract class SBaseFragment extends BaseFragment {

    @Override
    public boolean isNeedTitle() {
        return false;
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            initView();
//            initDatas();
//        }
//    }
}

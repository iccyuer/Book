package com.example.book.books.base;

import android.view.View;

import com.example.book.library.base.BaseActivity;


/**
 * Created by Administractor on 2017/1/18.
 */

public abstract class SBaseActivity extends BaseActivity {

    @Override
    public void initOthers() {
        if (isNeedTitle()) {
            setTitleLeft( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }
}

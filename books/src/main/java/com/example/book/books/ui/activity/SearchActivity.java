package com.example.book.books.ui.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.book.books.R;
import com.example.book.books.base.SBaseActivity;
import com.example.book.books.ui.pop.MyPop;

public class SearchActivity extends SBaseActivity implements View.OnClickListener {
    private ImageView mImgvBackSearch;
    private LinearLayout mLlSpinnerSearchActivity;
    private EditText mEdtSearchActivity;
    private TextView mTvSearchActivity;
    private TextView mTvSearchtypeSearchActivity;


    @Override
    public int setRootView() {
        return R.layout.activity_search;
    }

    @Override
    public void initView() {
        mImgvBackSearch = (ImageView) findViewById(R.id.imgv_back_search);
        mLlSpinnerSearchActivity = (LinearLayout) findViewById(R.id.ll_spinner_search_activity);
        mEdtSearchActivity = (EditText) findViewById(R.id.edt_search_activity);
        mTvSearchActivity = (TextView) findViewById(R.id.tv_search_activity);
        mTvSearchtypeSearchActivity = (TextView) findViewById(R.id.tv_searchtype_search_activity);


        mLlSpinnerSearchActivity.setOnClickListener(this);
        mImgvBackSearch.setOnClickListener(this);
        mTvSearchActivity.setOnClickListener(this);

    }

    @Override
    public void initDatas() {

    }

    @Override
    public boolean isNeedTitle() {
        return false;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ll_spinner_search_activity:
                showPop();
                break;
            case R.id.imgv_back_search:
                finish();
                break;
            case R.id.tv_search_activity:
                String searchType = mTvSearchtypeSearchActivity.getText().toString();
                String searchContent = mEdtSearchActivity.getText().toString().trim();
                String hint = mEdtSearchActivity.getHint().toString();
                if (searchContent.length()==0) {
                    gotoActivity(BookListActivity.class,"searchType",hint,"searchContent",hint);
                    return;
                }else{
                    gotoActivity(BookListActivity.class,"searchType",searchType,"searchContent",searchContent);
                }
                break;
        }
    }

    private void showPop() {
        MyPop pop = new MyPop(this);
        //在哪一个控件下面弹出   默认这个控件左下角，偏移量
        pop.showAsDropDown(mLlSpinnerSearchActivity, 0, 0);

        pop.setOnClickListener(new MyPop.OnClickListener() {
            @Override
            public void onClick(String tx) {
                mTvSearchtypeSearchActivity.setText(tx);
            }
        });
    }

    public void dosearch(View view){
        TextView textView= (TextView) view;
        String text = textView.getText().toString();
//        Toast.makeText(mActivitySelf, text, Toast.LENGTH_SHORT).show();
//        List<Books> bookses = BooksDao.getBooksDao().getBookBySearchName(text);
//        Logger.i(bookses.toString());
        gotoActivity(BookListActivity.class,"searchType",text,"searchContent",text);


    }
}

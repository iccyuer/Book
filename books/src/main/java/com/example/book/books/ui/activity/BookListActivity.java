package com.example.book.books.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.book.books.R;
import com.example.book.books.base.SBaseActivity;
import com.example.book.books.base.SBaseApp;
import com.example.book.books.db.BooksDao;
import com.example.book.books.model.Books;
import com.example.book.books.model.BooksType;
import com.example.book.books.ui.adapter.ItemRVBooklistAdapter;

import java.util.ArrayList;
import java.util.List;

public class BookListActivity extends SBaseActivity implements View.OnClickListener {
    private RecyclerView mRvBooklistActivity;
    private ItemRVBooklistAdapter mMyAdapter;
    private int mBooktype;
    private ImageView mImgvSearchEmpty;
    private SwipeRefreshLayout mRefreshlayout;


    @Override
    public int setRootView() {
        return R.layout.activity_book_list;
    }

    @Override
    public void initView() {
        mRvBooklistActivity = (RecyclerView) findViewById(R.id.rv_booklist_activity);
        mImgvSearchEmpty = (ImageView) findViewById(R.id.imgv_search_empty);
        mRefreshlayout = (SwipeRefreshLayout) findViewById(R.id.refreshlayout);
        mRefreshlayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        mRefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                // TODO Auto-generated method stub
                new Handler().postDelayed(new Runnable() {

                    @Override//在主线程中操作
                    public void run() {
                        //在这里进行网络请求发送新数据
                        initDatas();
                        //结束刷新
                        mRefreshlayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });

    }

    @Override
    public void initDatas() {

        if (SBaseApp.isAdmin) {
            setTitleRight("添加", this);
        }
        Intent intent = getIntent();
        mBooktype = intent.getIntExtra("booktype", 0);
        String searchType = intent.getStringExtra("searchType");
        String searchContent = intent.getStringExtra("searchContent");


        //按书的类型显示
        if (mBooktype != 0) {
            mImgvSearchEmpty.setVisibility(View.GONE);
            switch (mBooktype) {
                case BooksType.BOOKS_TYPE_NEW:
                    setTitleCenter("新书榜");
                    break;
                case BooksType.BOOKS_TYPE_HOT:
                    setTitleCenter("畅销榜");
                    break;
                case BooksType.BOOKS_TYPE_CHILD:
                    setTitleCenter("童书榜");
                    break;
            }
            final List<Books> allBooksByType = BooksDao.getBooksDao().getAllBooksByType(mBooktype);
            if (allBooksByType != null) {
                mRvBooklistActivity.setLayoutManager(new LinearLayoutManager(mActivitySelf, LinearLayout.VERTICAL, false));
                mMyAdapter = new ItemRVBooklistAdapter(mActivitySelf, allBooksByType);
                mRvBooklistActivity.setAdapter(mMyAdapter);
                mMyAdapter.setOnItemClickListener(new ItemRVBooklistAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(int id) {
                        for (Books books : allBooksByType) {
                            if (books.getBookid() == id) {
                                if (SBaseApp.isAdmin) {
                                    gotoActivity(ABookDetailActivity.class, "id", id);
                                    Toast.makeText(mActivitySelf, "id:" + id, Toast.LENGTH_SHORT).show();
                                } else {
                                    gotoActivity(BookDetailActivity.class, "id", id);
                                    Toast.makeText(mActivitySelf, "id:" + id, Toast.LENGTH_SHORT).show();
                                }
                                break;
                            }
                        }
                    }
                });
            }
        } else {
            setTitleRightGone();
            //按搜索结果显示
            setTitleCenter(searchContent);
            List<Books> bookses = new ArrayList<>();
//            Logger.i(searchType);
            if ("图书".equals(searchType)) {
                bookses = BooksDao.getBooksDao().getBookBySearchName(searchContent);
            } else if ("作者".equals(searchType)) {
                bookses = BooksDao.getBooksDao().getBookBySearchAuthor(searchContent);
            } else {
                bookses = BooksDao.getBooksDao().getBookBySearch(searchContent);
            }
            if (bookses.size() != 0) {
//                Logger.i(bookses.toString());
                mImgvSearchEmpty.setVisibility(View.GONE);
                mRvBooklistActivity.setLayoutManager(new LinearLayoutManager(mActivitySelf, LinearLayout.VERTICAL, false));
                mMyAdapter = new ItemRVBooklistAdapter(mActivitySelf, bookses);
                mRvBooklistActivity.setAdapter(mMyAdapter);
                final List<Books> finalBookses = bookses;
                mMyAdapter.setOnItemClickListener(new ItemRVBooklistAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(int id) {
                        for (Books books : finalBookses) {
                            if (books.getBookid() == id) {
                                if (SBaseApp.isAdmin) {
                                    gotoActivity(ABookDetailActivity.class, "id", id);
                                    Toast.makeText(mActivitySelf, "id:" + id, Toast.LENGTH_SHORT).show();
                                } else {
                                    gotoActivity(BookDetailActivity.class, "id", id);
                                    Toast.makeText(mActivitySelf, "id:" + id, Toast.LENGTH_SHORT).show();
                                }
                                break;
                            }
                        }
                    }
                });
            } else {
                mImgvSearchEmpty.setVisibility(View.VISIBLE);
            }

        }


    }

    @Override
    public void onClick(View v) {
        gotoActivity(AddBooksActivity.class, "booktype", mBooktype);
    }
}

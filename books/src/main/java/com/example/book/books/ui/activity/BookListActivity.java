package com.example.book.books.ui.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.book.books.R;
import com.example.book.books.base.SBaseActivity;
import com.example.book.books.base.SBaseApp;
import com.example.book.books.db.BooksDao;
import com.example.book.books.model.Books;
import com.example.book.books.model.BooksType;
import com.example.book.books.ui.adapter.ItemRVBooklistAdapter;

import java.util.List;

public class BookListActivity extends SBaseActivity implements View.OnClickListener {
    private RecyclerView mRvBooklistActivity;
    private ItemRVBooklistAdapter mMyAdapter;
    private int mBooktype;


    @Override
    public int setRootView() {
        return R.layout.activity_book_list;
    }

    @Override
    public void initView() {
        mRvBooklistActivity = (RecyclerView) findViewById(R.id.rv_booklist_activity);

    }

    @Override
    public void initDatas() {

        if (SBaseApp.isAdmin) {
            setTitleRight("添加",this);
        }
        Intent intent = getIntent();
        mBooktype = intent.getIntExtra("booktype", 0);

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

//        Logger.i(allBooksByType.toString());
        if (allBooksByType!=null) {
            mRvBooklistActivity.setLayoutManager(new LinearLayoutManager(mActivitySelf, LinearLayout.VERTICAL,false));
            mMyAdapter = new ItemRVBooklistAdapter(mActivitySelf, allBooksByType);
            mRvBooklistActivity.setAdapter(mMyAdapter);
            mMyAdapter.setOnItemClickListener(new ItemRVBooklistAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(int id) {
                    for (Books books : allBooksByType) {
                        if (books.getBookid()==id) {
                            if (SBaseApp.isAdmin) {
                                gotoActivity(ABookDetailActivity.class,"id",id);
                                Toast.makeText(mActivitySelf, "id:" + id, Toast.LENGTH_SHORT).show();
                            }else{
                                gotoActivity(BookDetailActivity.class,"id",id);
                                Toast.makeText(mActivitySelf, "id:" + id, Toast.LENGTH_SHORT).show();
                            }
                            break;
                        }
                    }
                }
            });
        }

    }

    @Override
    public void onClick(View v) {
        gotoActivity(AddBooksActivity.class,"booktype",mBooktype );
    }
}

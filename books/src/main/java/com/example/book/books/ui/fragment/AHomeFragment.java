package com.example.book.books.ui.fragment;


import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.book.books.R;
import com.example.book.books.base.SBaseFragment;
import com.example.book.books.entity.TypeEntity;
import com.example.book.books.model.BooksType;
import com.example.book.books.ui.activity.BookListActivity;
import com.example.book.books.ui.activity.SearchActivity;
import com.example.book.books.ui.adapter.ItemRVTypeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AHomeFragment extends SBaseFragment {
    private RelativeLayout mSearchButtonA;
    private RecyclerView mRvFragHomeA;

    private List<TypeEntity> mTypeEntities = new ArrayList<>();
    private ItemRVTypeAdapter mMyAdapter;

    @Override
    public int setRootView() {
        return R.layout.fragment_ahome;
    }

    @Override
    public void initView() {
        mSearchButtonA = (RelativeLayout) findViewById(R.id.search_button_a);
        mRvFragHomeA = (RecyclerView) findViewById(R.id.rv_frag_home_a);

        mSearchButtonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(SearchActivity.class);
            }
        });

    }

    @Override
    public void initDatas() {
        String pathType0 = "file:///android_asset/new.jpg";
        String pathType1 = "file:///android_asset/hot.jpg";
        String pathType2 = "file:///android_asset/child.jpg";

        TypeEntity typeEntity0 = new TypeEntity();
        typeEntity0.setName("新书榜");
        typeEntity0.setPath(pathType0);
        TypeEntity typeEntity1 = new TypeEntity();
        typeEntity1.setName("畅销榜");
        typeEntity1.setPath(pathType1);
        TypeEntity typeEntity2 = new TypeEntity();
        typeEntity2.setName("童书榜");
        typeEntity2.setPath(pathType2);

        mTypeEntities.add(typeEntity0);
        mTypeEntities.add(typeEntity1);
        mTypeEntities.add(typeEntity2);
        mTypeEntities.add(typeEntity0);
        mTypeEntities.add(typeEntity1);
        mTypeEntities.add(typeEntity2);
        mTypeEntities.add(typeEntity0);
        mTypeEntities.add(typeEntity1);
        mTypeEntities.add(typeEntity2);
        showRV();
    }

    private void showRV() {
        mRvFragHomeA.setLayoutManager(new StaggeredGridLayoutManager(3, GridLayoutManager.VERTICAL));
        mMyAdapter = new ItemRVTypeAdapter(mActivitySelf, mTypeEntities);
        mRvFragHomeA.setAdapter(mMyAdapter);
        mMyAdapter.setOnItemClickListener(new ItemRVTypeAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(String name) {
                if ("新书榜".equals(name)) {
                    Toast.makeText(mActivitySelf, "新书", Toast.LENGTH_SHORT).show();
                    gotoActivity(BookListActivity.class,"booktype", BooksType.BOOKS_TYPE_NEW);
                }if ("畅销榜".equals(name)) {
                    Toast.makeText(mActivitySelf, "畅销", Toast.LENGTH_SHORT).show();
                    gotoActivity(BookListActivity.class,"booktype", BooksType.BOOKS_TYPE_HOT);
                }if("童书榜".equals(name)){
                    Toast.makeText(mActivitySelf, "童书", Toast.LENGTH_SHORT).show();
                    gotoActivity(BookListActivity.class,"booktype", BooksType.BOOKS_TYPE_CHILD);
                }
            }
        });
    }

}

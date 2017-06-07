package com.example.book.books.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.example.book.books.R;
import com.example.book.books.base.SBaseActivity;
import com.example.book.books.db.UsersDao;
import com.example.book.books.model.Users;
import com.example.book.books.ui.adapter.ItemRVUserlistAdapter;

import java.util.List;

public class UserListActivity extends SBaseActivity {
    private RecyclerView mRvUserlistActivity;
    private ItemRVUserlistAdapter mMyAdapter;

    @Override
    public int setRootView() {
        return R.layout.activity_user_list;
    }

    @Override
    public void initView() {
        setTitleCenter("用户列表");
        mRvUserlistActivity = (RecyclerView) findViewById(R.id.rv_userlist_activity);
    }

    @Override
    public void initDatas() {
        List<Users> allUsers = UsersDao.getUsersDao().getAllUsers();
        if (allUsers!=null) {
            showRV(allUsers);
        }

    }
    private void showRV(List<Users> userses) {
//        Logger.i(cartses.toString());
        mRvUserlistActivity.setLayoutManager(new LinearLayoutManager(mActivitySelf, LinearLayout.VERTICAL, false));
        mMyAdapter = new ItemRVUserlistAdapter(mActivitySelf, userses);
//        mRvCartsActivity.setItemViewCacheSize(5+0);
        mRvUserlistActivity.setAdapter(mMyAdapter);
        mMyAdapter.setOnItemClickListener(new ItemRVUserlistAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int id) {
                gotoActivity(UsersInfoActivity.class,"userid", id);
            }
        });

    }
}

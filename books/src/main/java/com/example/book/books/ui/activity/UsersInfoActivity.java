package com.example.book.books.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.book.books.R;
import com.example.book.books.base.SBaseActivity;
import com.example.book.books.base.SBaseApp;
import com.example.book.books.db.UsersDao;
import com.example.book.books.model.Users;
import com.example.book.books.ui.views.CityPickerView;

public class UsersInfoActivity extends SBaseActivity implements View.OnClickListener {
    private TextView mTvUsernameUsersinfo;
    private TextView mTvUserpwdUsersinfo;
    private TextView mTvUserphoneUsersinfo;
    private TextView mTvUseraddressUsersinfo;
    private int mUserid;
    private Users mUsers;


    @Override
    public int setRootView() {
        return R.layout.activity_users_info;
    }

    @Override
    public void initView() {
        if (SBaseApp.isAdmin) {
            setTitleCenter("用户资料");
        }else{
            setTitleCenter("个人资料");
        }
        mTvUsernameUsersinfo = (TextView) findViewById(R.id.tv_username_usersinfo);
        mTvUserpwdUsersinfo = (TextView) findViewById(R.id.tv_userpwd_usersinfo);
        mTvUserphoneUsersinfo = (TextView) findViewById(R.id.tv_userphone_usersinfo);
        mTvUseraddressUsersinfo = (TextView) findViewById(R.id.tv_useraddress_usersinfo);

        mTvUsernameUsersinfo.setOnClickListener(this);
        mTvUserpwdUsersinfo.setOnClickListener(this);
        mTvUserphoneUsersinfo.setOnClickListener(this);
        mTvUseraddressUsersinfo.setOnClickListener(this);

    }

    @Override
    public void initDatas() {
        Intent intent = getIntent();
        mUserid = intent.getIntExtra("userid", 0);
        mUsers = UsersDao.getUsersDao().getUserByUserId(mUserid);
        mTvUsernameUsersinfo.setText(mUsers.getUserName());
        mTvUserpwdUsersinfo.setText("****");
        mTvUserphoneUsersinfo.setText(mUsers.getUserphone());
        mTvUseraddressUsersinfo.setText(mUsers.getUserAddress());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_username_usersinfo:
                Toast.makeText(mActivitySelf, "用户名不可以修改~", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_userpwd_usersinfo:
                if (SBaseApp.isAdmin) {
                    gotoActivity(AModifyPwdActivity.class,"userid", mUserid);
                }else{
                    //用户修改密码和管理员不同~
                    gotoActivity(ModifyPwdActivity.class,"userid", mUserid);
                }
                break;
            case R.id.tv_userphone_usersinfo:
                gotoActivity(ModifyPhoneActivity.class,"userid", mUserid);
                break;
            case R.id.tv_useraddress_usersinfo:
                CityPickerView cityPickerView=new CityPickerView(this) ;
                cityPickerView.showCityPicker();
                cityPickerView.setOnConfirmListener(new CityPickerView.OnConfirmListener() {
                    @Override
                    public void onConfirm(String tx) {
                        mTvUseraddressUsersinfo.setText(tx+"");
                        mUsers.setUserAddress(tx);
                        UsersDao.getUsersDao().updateUsersInfo(mUsers);
                    }
                });
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initDatas();
    }
}

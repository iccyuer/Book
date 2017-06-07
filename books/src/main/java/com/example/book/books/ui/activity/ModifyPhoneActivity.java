package com.example.book.books.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.book.books.R;
import com.example.book.books.base.SBaseActivity;
import com.example.book.books.db.UsersDao;
import com.example.book.books.model.Users;
import com.example.book.books.utils.PhoneFormatCheckUtils;

public class ModifyPhoneActivity extends SBaseActivity implements View.OnClickListener {
    private EditText mEdtUserphoneModify;
    private TextView mTvUserphoneError;
    private TextView mTvModifyPhone;
    private Users mUsers;


    @Override
    public int setRootView() {
        return R.layout.activity_modify_phone;
    }

    @Override
    public void initView() {
        setTitleCenter("修改联系电话");
        mEdtUserphoneModify = (EditText) findViewById(R.id.edt_userphone_modify);
        mTvUserphoneError = (TextView) findViewById(R.id.tv_userphone_error);
        mTvModifyPhone = (TextView) findViewById(R.id.tv_modify_phone);

        mTvModifyPhone.setOnClickListener(this);
    }

    @Override
    public void initDatas() {
        Intent intent = getIntent();
        int userid = intent.getIntExtra("userid", 0);
        mUsers = UsersDao.getUsersDao().getUserByUserId(userid);
    }

    @Override
    public void onClick(View v) {
        String userphone = mEdtUserphoneModify.getText().toString().trim();
        boolean b = PhoneFormatCheckUtils.isPhoneLegal(userphone);
        if (!b) {
            mTvUserphoneError.setVisibility(View.VISIBLE);
        }else{
            mTvUserphoneError.setVisibility(View.GONE);
        }
        int visibility = mTvUserphoneError.getVisibility();
        if (visibility==0) {
            Toast.makeText(mActivitySelf, "请规范填写", Toast.LENGTH_SHORT).show();
            return;
        }
        mUsers.setUserphone(userphone);
        UsersDao.getUsersDao().updateUsersInfo(mUsers);
        Toast.makeText(mActivitySelf, "修改成功", Toast.LENGTH_SHORT).show();
        finish();
    }
}

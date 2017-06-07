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

public class ModifyPwdActivity extends SBaseActivity implements View.OnFocusChangeListener, View.OnClickListener {
    private EditText mEdtUserpwdOldModify;
    private EditText mEdtUserpwdNewModify;
    private EditText mEdtUserpwdNew1Modify;
    private TextView mTvModifyPwd;
    private TextView mTvUserpwdError;
    private TextView mTvUserpwdnewDiff;
    private Users mUsers;


    @Override
    public int setRootView() {
        return R.layout.activity_modify_pwd;
    }

    @Override
    public void initView() {
        setTitleCenter("修改密码");
        mEdtUserpwdOldModify = (EditText) findViewById(R.id.edt_userpwd_old_modify);
        mEdtUserpwdNewModify = (EditText) findViewById(R.id.edt_userpwd_new_modify);
        mEdtUserpwdNew1Modify = (EditText) findViewById(R.id.edt_userpwd_new1_modify);
        mTvModifyPwd = (TextView) findViewById(R.id.tv_modify_pwd);
        mTvUserpwdError = (TextView) findViewById(R.id.tv_userpwd_error);
        mTvUserpwdnewDiff = (TextView) findViewById(R.id.tv_userpwdnew_diff);

        mEdtUserpwdNew1Modify.setOnFocusChangeListener(this);
        mEdtUserpwdOldModify.setOnFocusChangeListener(this);
        mEdtUserpwdNewModify.setOnFocusChangeListener(this);
        mTvModifyPwd.setOnClickListener(this);

    }

    @Override
    public void initDatas() {
        Intent intent = getIntent();
        int userid = intent.getIntExtra("userid", 0);
        mUsers = UsersDao.getUsersDao().getUserByUserId(userid);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        String oldpwd = mEdtUserpwdOldModify.getText().toString().trim();
        String newpwd = mEdtUserpwdNewModify.getText().toString().trim();
        String newpwd1 = mEdtUserpwdNew1Modify.getText().toString().trim();

        int id = v.getId();
        switch (id) {
            case R.id.edt_userpwd_old_modify:
                if (!hasFocus) {
                    if (oldpwd.length()!=0) {
                        boolean b = UsersDao.getUsersDao().loginInByNameAndPwd(mUsers.getUserName(), oldpwd);
                        if (!b) {
                            mTvUserpwdError.setVisibility(View.VISIBLE);
                        }else{
                            mTvUserpwdError.setVisibility(View.GONE);
                        }
                    }
                }
                break;
            case R.id.edt_userpwd_new_modify:
                if (!"".equals(newpwd1)) {
                    if (!hasFocus) {
                        if (!newpwd.equals(newpwd1)) {
                            mTvUserpwdnewDiff.setVisibility(View.VISIBLE);
                        } else {
                            mTvUserpwdnewDiff.setVisibility(View.GONE);
                        }
                    }
                }
                break;
            case R.id.edt_userpwd_new1_modify:
                if (!hasFocus) {
                    if (!newpwd.equals(newpwd1)) {
                        mTvUserpwdnewDiff.setVisibility(View.VISIBLE);
                    } else {
                        mTvUserpwdnewDiff.setVisibility(View.GONE);
                    }
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {

        String newpwd = mEdtUserpwdNewModify.getText().toString().trim();
        String newpwd1 = mEdtUserpwdNew1Modify.getText().toString().trim();
        if ("".equals(newpwd)||"".equals(newpwd1)) {
            Toast.makeText(mActivitySelf, "请填写完成再进行操作", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newpwd.equals(newpwd1)) {
            mTvUserpwdnewDiff.setVisibility(View.VISIBLE);
        } else {
            mTvUserpwdnewDiff.setVisibility(View.GONE);
        }
        int visibility = mTvUserpwdError.getVisibility();
        int visibility1 = mTvUserpwdnewDiff.getVisibility();
        if (visibility==0||visibility1==0) {
            Toast.makeText(mActivitySelf, "请规范填写", Toast.LENGTH_SHORT).show();
            return;
        }
        mUsers.setUserPassword(newpwd);
        UsersDao.getUsersDao().updateUsersInfo(mUsers);
        Toast.makeText(mActivitySelf, "修改成功", Toast.LENGTH_SHORT).show();
        finish();
    }
}

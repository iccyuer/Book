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

public class AModifyPwdActivity extends SBaseActivity implements View.OnFocusChangeListener, View.OnClickListener {
    private EditText mEdtUserpwdNewModifyA;
    private EditText mEdtUserpwdNew1ModifyA;
    private TextView mTvUserpwdnewDiffA;
    private TextView mTvModifyPwdA;
    private Users mUsers;

    @Override
    public int setRootView() {
        return R.layout.activity_amodify_pwd;
    }

    @Override
    public void initView() {
        setTitleCenter("修改密码");
        mEdtUserpwdNewModifyA = (EditText) findViewById(R.id.edt_userpwd_new_modify_a);
        mEdtUserpwdNew1ModifyA = (EditText) findViewById(R.id.edt_userpwd_new1_modify_a);
        mTvUserpwdnewDiffA = (TextView) findViewById(R.id.tv_userpwdnew_diff_a);
        mTvModifyPwdA = (TextView) findViewById(R.id.tv_modify_pwd_a);

        mEdtUserpwdNew1ModifyA.setOnFocusChangeListener(this);
        mEdtUserpwdNewModifyA.setOnFocusChangeListener(this);
        mTvModifyPwdA.setOnClickListener(this);
    }

    @Override
    public void initDatas() {
        Intent intent = getIntent();
        int userid = intent.getIntExtra("userid", 0);
        mUsers = UsersDao.getUsersDao().getUserByUserId(userid);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        String newpwd = mEdtUserpwdNewModifyA.getText().toString().trim();
        String newpwd1 = mEdtUserpwdNew1ModifyA.getText().toString().trim();

        int id = v.getId();
        switch (id) {
            case R.id.edt_userpwd_new_modify_a:
                if (!"".equals(newpwd1)) {
                    if (!hasFocus) {
                        if (!newpwd.equals(newpwd1)) {
                            mTvUserpwdnewDiffA.setVisibility(View.VISIBLE);
                        } else {
                            mTvUserpwdnewDiffA.setVisibility(View.GONE);
                        }
                    }
                }
                break;
            case R.id.edt_userpwd_new1_modify_a:
                if (!hasFocus) {
                    if (!newpwd.equals(newpwd1)) {
                        mTvUserpwdnewDiffA.setVisibility(View.VISIBLE);
                    } else {
                        mTvUserpwdnewDiffA.setVisibility(View.GONE);
                    }
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {

        String newpwd = mEdtUserpwdNewModifyA.getText().toString().trim();
        String newpwd1 = mEdtUserpwdNew1ModifyA.getText().toString().trim();
        if ("".equals(newpwd)||"".equals(newpwd1)) {
            Toast.makeText(mActivitySelf, "请填写完成再进行操作", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!newpwd.equals(newpwd1)) {
            mTvUserpwdnewDiffA.setVisibility(View.VISIBLE);
        } else {
            mTvUserpwdnewDiffA.setVisibility(View.GONE);
        }
        int visibility = mTvUserpwdnewDiffA.getVisibility();
        if (visibility==0) {
            Toast.makeText(mActivitySelf, "请规范填写", Toast.LENGTH_SHORT).show();
            return;
        }
        mUsers.setUserPassword(newpwd);
        UsersDao.getUsersDao().updateUsersInfo(mUsers);
        Toast.makeText(mActivitySelf, "修改成功", Toast.LENGTH_SHORT).show();
        finish();
    }
}

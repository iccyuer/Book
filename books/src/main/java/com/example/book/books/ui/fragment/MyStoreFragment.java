package com.example.book.books.ui.fragment;


import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.book.books.R;
import com.example.book.books.base.SBaseApp;
import com.example.book.books.base.SBaseFragment;
import com.example.book.books.db.UsersDao;
import com.example.book.books.model.Users;
import com.example.book.books.sp.LoginSP;
import com.example.book.books.ui.activity.LoginActivity;
import com.example.book.books.ui.activity.OrderDetailActivity;
import com.example.book.books.ui.activity.UsersInfoActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyStoreFragment extends SBaseFragment implements View.OnClickListener {
    private Button mBtVieworderMystoreFragment;
    private Button mBtLoginOrOut;
    private TextView mTvUsernameMystore;
    private LinearLayout mLlUserinfo;






    @Override
    public int setRootView() {
        return R.layout.fragment_mystore;
    }

    @Override
    public void initView() {
        mBtVieworderMystoreFragment = (Button) findViewById(R.id.bt_vieworder_mystore_fragment);
        mTvUsernameMystore = (TextView) findViewById(R.id.tv_username_mystore);
        mBtLoginOrOut = (Button) findViewById(R.id.bt_login_or_out);
        mLlUserinfo = (LinearLayout) findViewById(R.id.ll_userinfo);

        if (SBaseApp.onUserId!=0) {
            mBtLoginOrOut.setText("注销");
        }else{
            mBtLoginOrOut.setText("登录");
        }

        mBtVieworderMystoreFragment.setOnClickListener(this);
        mBtLoginOrOut.setOnClickListener(this);

        mLlUserinfo.setOnClickListener(this);

    }

    @Override
    public void initDatas() {
        Users users = UsersDao.getUsersDao().getUserByUserId(SBaseApp.onUserId);
        if (users!=null) {
            mTvUsernameMystore.setText(users.getUserName());
        }else{
            mTvUsernameMystore.setText("还没登录~");
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.bt_vieworder_mystore_fragment:
                if (SBaseApp.onUserId!=0) {
                    //11为用户  22为未出货  33为所有
                    gotoActivity(OrderDetailActivity.class,"ordertype",11);
                }else{
                    Toast.makeText(mActivitySelf, "请登录后再进行操作", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_login_or_out:
                if (SBaseApp.onUserId!=0) {
                    if (!"".equals(LoginSP.getUid())) {
                        LoginSP.saveUid("");
                        LoginSP.saveUserpwd("");
                    }
                    SBaseApp.onUserId=0;
                    mBtLoginOrOut.setText("登录");
                    mTvUsernameMystore.setText("还没登录~");
                }else{
                    gotoActivity(LoginActivity.class);
//                    mBtLoginOrOut.setText("注销");
                }
                break;
            case R.id.ll_userinfo:
                if (SBaseApp.onUserId==0) {
                    Toast.makeText(mActivitySelf, "请登录后再进行操作", Toast.LENGTH_SHORT).show();
                }else{
                    gotoActivity(UsersInfoActivity.class,"userid",SBaseApp.onUserId);
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
        initDatas();
    }
}

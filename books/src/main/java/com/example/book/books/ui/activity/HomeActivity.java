package com.example.book.books.ui.activity;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.book.books.R;
import com.example.book.books.base.SBaseActivity;
import com.example.book.books.base.SBaseApp;
import com.example.book.books.ui.dialog.CheckDialog;

public class HomeActivity extends SBaseActivity implements View.OnClickListener {
    private TextView mTvAdminHomeActivity;
    private TextView mTvUserHomeActivity;
    private CheckDialog mCheckDialog;

    @Override
    public int setRootView() {
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//全屏显示
        return R.layout.activity_home;
    }

    @Override
    public void initView() {

        mTvAdminHomeActivity = (TextView) findViewById(R.id.tv_admin_home_activity);
        mTvUserHomeActivity = (TextView) findViewById(R.id.tv_user_home_activity);

        mTvAdminHomeActivity.setOnClickListener(this);
        mTvUserHomeActivity.setOnClickListener(this);
        mCheckDialog = new CheckDialog(mActivitySelf);
        mCheckDialog.setOnConfirmListener(new CheckDialog.OnConfirmListener() {
            @Override
            public void onConfrim(String adminid, String adminpwd) {

                if (adminid.equals("")||adminpwd.equals("")) {
                    Toast.makeText(mActivitySelf, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (adminid.equals("admin")&&adminpwd.equals("123")) {
                    Toast.makeText(mActivitySelf, "验证成功", Toast.LENGTH_SHORT).show();
                    mCheckDialog.dismiss();
                    gotoActivity(AdminActivity.class);
                }else{
                    Toast.makeText(mActivitySelf, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_admin_home_activity:
//                showCheckDialog();
                SBaseApp.isAdmin=true;
                gotoActivity(AdminActivity.class);
                break;
            case R.id.tv_user_home_activity:
                gotoActivity(MainActivity.class);
                SBaseApp.isAdmin=false;
                break;
        }

    }

    private void showCheckDialog(){
        mCheckDialog.show();
    }

    @Override
    public boolean isNeedTitle() {
        return false;
    }
}

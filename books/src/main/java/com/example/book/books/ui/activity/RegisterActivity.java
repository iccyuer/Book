package com.example.book.books.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.book.books.R;
import com.example.book.books.base.SBaseActivity;
import com.example.book.books.db.UsersDao;
import com.example.book.books.model.Users;
import com.example.book.books.ui.views.CityPickerView;
import com.example.book.books.utils.PhoneFormatCheckUtils;

public class RegisterActivity extends SBaseActivity implements View.OnClickListener, View.OnFocusChangeListener {
    private EditText mEdtUsernameRegister;
    private EditText mEdtUserpwdRegister;
    private EditText mEdtUserpwd1Register;
    private EditText mEdtUserphoneRegister;
    private TextView mTvUseraddressRegister;
    private TextView mTvDoRegister;
    private TextView mTvResetRegister;
    private TextView mTvUsernameExist;
    private TextView mTvUserpwdDiff;
    private TextView mTvUserphoneErrorR;

    @Override
    public int setRootView() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        setTitleCenter("请填写注册信息");
        mEdtUsernameRegister = (EditText) findViewById(R.id.edt_username_register);
        mEdtUserpwdRegister = (EditText) findViewById(R.id.edt_userpwd_register);
        mEdtUserpwd1Register = (EditText) findViewById(R.id.edt_userpwd1_register);
        mEdtUserphoneRegister = (EditText) findViewById(R.id.edt_userphone_register);
        mTvUseraddressRegister = (TextView) findViewById(R.id.tv_useraddress_register);

        mTvUsernameExist = (TextView) findViewById(R.id.tv_username_exist);
        mTvUserpwdDiff = (TextView) findViewById(R.id.tv_userpwd_diff);
        mTvUserphoneErrorR = (TextView) findViewById(R.id.tv_userphone_error_r);

        mTvDoRegister = (TextView) findViewById(R.id.tv_do_register);
        mTvResetRegister = (TextView) findViewById(R.id.tv_reset_register);

        mTvUseraddressRegister.setOnClickListener(this);
        mTvDoRegister.setOnClickListener(this);
        mTvResetRegister.setOnClickListener(this);

        mEdtUsernameRegister.setOnFocusChangeListener(this);
        mEdtUserpwdRegister.setOnFocusChangeListener(this);
        mEdtUserpwd1Register.setOnFocusChangeListener(this);
        mTvUserphoneErrorR.setOnFocusChangeListener(this);
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_useraddress_register:
                CityPickerView cityPickerView=new CityPickerView(this) ;
                cityPickerView.showCityPicker();
                cityPickerView.setOnConfirmListener(new CityPickerView.OnConfirmListener() {
                    @Override
                    public void onConfirm(String tx) {
                        mTvUseraddressRegister.setText(tx);
                    }
                });
                break;
            case R.id.tv_reset_register:
                mEdtUsernameRegister.setText("");
                mEdtUserpwdRegister.setText("");
                mEdtUserpwd1Register.setText("");
                mEdtUserphoneRegister.setText("");
                mTvUseraddressRegister.setText("请选择");
                mTvUserpwdDiff.setVisibility(View.GONE);
                mTvUsernameExist.setVisibility(View.GONE);
                break;
            case R.id.tv_do_register:

                String username = mEdtUsernameRegister.getText().toString().trim();
                String userpwd = mEdtUserpwdRegister.getText().toString().trim();
                String userphone = mEdtUserphoneRegister.getText().toString().trim();
                String useraddress = mTvUseraddressRegister.getText().toString().trim();
                System.out.println("useraddress = " + useraddress);

                if (userphone.length()!=0) {
                    boolean b = PhoneFormatCheckUtils.isPhoneLegal(userphone);
                    if (!b) {
                        mTvUserphoneErrorR.setVisibility(View.VISIBLE);
                    }else{
                        mTvUserphoneErrorR.setVisibility(View.GONE);
                    }
                }
                Users users = new Users();
                users.setUserName(username);
                users.setUserPassword(userpwd);
                users.setUserphone(userphone);
                users.setUserAddress(useraddress);
                //返回值为0，visible；返回值为4，invisible；返回值为8，gone。
                int visibility = mTvUsernameExist.getVisibility();
                int visibility1 = mTvUserpwdDiff.getVisibility();
                int visibility2 = mTvUserphoneErrorR.getVisibility();
                if (visibility == 0 || visibility1 == 0||visibility2==0) {
                    Toast.makeText(mActivitySelf, "请规范注册", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ("".equals(username) || "".equals(userpwd) || "".equals(userphone)) {
                    Toast.makeText(mActivitySelf, "填写内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ("请选择>".equals(useraddress)) {
                    Toast.makeText(mActivitySelf, "请选择地址", Toast.LENGTH_SHORT).show();
                    return;
                }

                UsersDao.getUsersDao().registUsers(users);
                Toast.makeText(mActivitySelf, "注册成功", Toast.LENGTH_SHORT).show();

                //弹个dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(mActivitySelf);
                // 构建者模式 构建链
                builder.setTitle("提示").setMessage("注册成功").setCancelable(false)
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).create().show();

                //注册成功，自动登录-没有
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        String username = mEdtUsernameRegister.getText().toString().trim();
        String userpwd = mEdtUserpwdRegister.getText().toString().trim();
        String userpwd1 = mEdtUserpwd1Register.getText().toString().trim();
        String userphone = mEdtUserphoneRegister.getText().toString().trim();
        int id = v.getId();
        switch (id) {
            case R.id.edt_username_register:
                if (!hasFocus) {
                    boolean b = UsersDao.getUsersDao().checkUserByName(username);
                    if (b) {
                        mTvUsernameExist.setVisibility(View.VISIBLE);
                    } else {
                        mTvUsernameExist.setVisibility(View.GONE);
                    }
                }
                break;
            case R.id.edt_userpwd1_register:
                if (!hasFocus) {
                    if (!userpwd.equals(userpwd1)) {
                        mTvUserpwdDiff.setVisibility(View.VISIBLE);
                    } else {
                        mTvUserpwdDiff.setVisibility(View.GONE);
                    }
                }
                break;
            case R.id.edt_userpwd_register:
                if (!"".equals(userpwd1)) {
                    if (!hasFocus) {
                        if (!userpwd.equals(userpwd1)) {
                            mTvUserpwdDiff.setVisibility(View.VISIBLE);
                        } else {
                            mTvUserpwdDiff.setVisibility(View.GONE);
                        }
                    }
                }
                break;
            case R.id.tv_userphone_error_r:
                if (!hasFocus) {
                    if (userphone.length()!=0) {
                        boolean b = PhoneFormatCheckUtils.isPhoneLegal(userphone);
                        if (!b) {
                            mTvUserphoneErrorR.setVisibility(View.VISIBLE);
                        }else{
                            mTvUserphoneErrorR.setVisibility(View.GONE);
                        }
                    }
                }
                break;
        }
    }
}

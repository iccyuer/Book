package com.example.book.books.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.book.books.R;
import com.example.book.books.base.SBaseActivity;
import com.example.book.books.db.UsersDao;
import com.example.book.books.sp.LoginSP;
import com.example.book.library.util.UtilEdt;

import static com.example.book.books.base.SBaseApp.onUserId;

public class LoginActivity extends SBaseActivity {
    private EditText mEdtUsername;
    private EditText mEdtUserpwd;
    private CheckBox mCbPassShow;
    private CheckBox mCbPwdRecord;
    private Button mBtLogin;
    private View.OnClickListener mOnClickListener;


    @Override
    public int setRootView() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        setTitleCenter("登录");
        mEdtUsername = (EditText) findViewById(R.id.edt_username);
        mEdtUserpwd = (EditText) findViewById(R.id.edt_userpwd);
        mCbPassShow = (CheckBox) findViewById(R.id.cb_pass_show);
        mCbPwdRecord = (CheckBox) findViewById(R.id.cb_pwd_record);
        mBtLogin = (Button) findViewById(R.id.bt_login);


        mEdtUserpwd.setInputType(EditorInfo.TYPE_TEXT_VARIATION_PASSWORD | EditorInfo.TYPE_CLASS_TEXT);
        mEdtUsername.setText(LoginSP.getUid());
        mEdtUserpwd.setText(LoginSP.getUserpwd());
        if (!TextUtils.isEmpty(LoginSP.getUid())) {
            mCbPwdRecord.setChecked(true);
        }

        mCbPassShow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mEdtUserpwd.setInputType(EditorInfo.TYPE_CLASS_TEXT);
                } else {
                    mEdtUserpwd.setInputType(EditorInfo.TYPE_TEXT_VARIATION_PASSWORD | EditorInfo.TYPE_CLASS_TEXT);
                }
            }
        });
        //把做网络访问的模块抽取出来 分别注册监听和取消监听
        mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        };
        //初始的时候注册上登录监听
        regesiterBTLoginLis();
    }

    private void unRegesiterBTLoginLis() {
        mBtLogin.setOnClickListener(null);//解注册
    }

    private void regesiterBTLoginLis() {
        mBtLogin.setOnClickListener(mOnClickListener);//注册上
    }

    private void doLogin() {

        String uid = UtilEdt.getEdtText(mEdtUsername);
        String passwd = UtilEdt.getEdtText(mEdtUserpwd);

        if (TextUtils.isEmpty(uid)) {
            dyd(mEdtUsername);
            return;
        }
        if (TextUtils.isEmpty(passwd)) {
            dyd(mEdtUserpwd);
            return;
        }
        //发起登录后 解除注册
        unRegesiterBTLoginLis();

        if (mCbPwdRecord.isChecked()) {
            LoginSP.saveUid(uid);
            LoginSP.saveUserpwd(passwd);
        } else {
            LoginSP.saveUid("");
            LoginSP.saveUserpwd("");
        }

        boolean b = UsersDao.getUsersDao().loginInByNameAndPwd(uid, passwd);

        System.out.println("b = " + b);
        //登录失败
        if (b==false){
            regesiterBTLoginLis();
            LoginSP.saveUid("");
            LoginSP.saveUserpwd("");
            Toast.makeText(mActivitySelf, "用户名或密码错误", Toast.LENGTH_SHORT).show();
        }else{
            LoginSP.saveUid(uid);
            LoginSP.saveUserpwd(passwd);
            Toast.makeText(mActivitySelf, "登录成功", Toast.LENGTH_SHORT).show();
            onUserId =UsersDao.getUsersDao().getUserIdByName(LoginSP.getUid());
            finish();
        }

    }

    private void dyd(EditText edt) {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);//1秒之内都懂7次
        edt.startAnimation(shake);
    }

    @Override
    public void initDatas() {

    }
}

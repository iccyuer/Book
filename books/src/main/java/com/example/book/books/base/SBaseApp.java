package com.example.book.books.base;


import com.example.book.books.R;
import com.example.book.books.db.BooksDao;
import com.example.book.books.db.UsersDao;
import com.example.book.books.sp.DbInitSP;
import com.example.book.books.sp.LoginSP;
import com.example.book.library.base.BaseApp;

/**
 * Created by Administractor on 2017/1/18.
 */

public class SBaseApp extends BaseApp {

    public static int onUserId;

    public static boolean isAdmin=false;

    @Override
    public boolean isDebug() {
        return true;
    }

    @Override
    public void initOthers() {
//        ShareSDK.initSDK(this);

        if (false== DbInitSP.getInit()) {
            BooksDao.getBooksDao().init();
            UsersDao.getUsersDao().init();
            DbInitSP.saveInit(true);
        }
        if (!"".equals(LoginSP.getUid())) {
            onUserId =UsersDao.getUsersDao().getUserIdByName(LoginSP.getUid());
        }else{
            onUserId =0;
        }
//        Logger.i(onUserId+"");
    }

    @Override
    public int initTitleBar() {
        return R.layout.title_bar;
    }
}

package com.example.book.books.sp;


import com.example.book.books.base.SBaseApp;
import com.example.book.library.util.SharedPrefrencesUtil;

/**
 * Created by Administractor on 2017/1/18.
 */

public class LoginSP {
    public static void saveUid(String uid){
        SharedPrefrencesUtil.saveData(SBaseApp.mContextGlobal,"login","uid",uid);
    }

    public static String getUid() {
        return SharedPrefrencesUtil.getData(SBaseApp.mContextGlobal,"login","uid","");
    }

    public static void saveUserpwd(String passwd) {
        SharedPrefrencesUtil.saveData(SBaseApp.mContextGlobal, "login", "passwd", passwd);
    }

    public static String getUserpwd() {
        return SharedPrefrencesUtil.getData(SBaseApp.mContextGlobal, "login", "passwd", "");
    }
}

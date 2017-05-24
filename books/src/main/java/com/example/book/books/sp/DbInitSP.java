package com.example.book.books.sp;

import com.example.book.books.base.SBaseApp;
import com.example.book.library.util.SharedPrefrencesUtil;

/**
 * Created by Admin on 2017/5/13.
 */

public class DbInitSP {
    public static void saveInit(Boolean is){
        SharedPrefrencesUtil.saveData(SBaseApp.mContextGlobal,"dbinit","init",is);
    }

    public static Boolean getInit() {
        return SharedPrefrencesUtil.getData(SBaseApp.mContextGlobal,"dbinit","init",false);
    }

}

package com.example.book.books.db;

import org.xutils.DbManager;

/**
 * Created by Admin on 2017/5/13.
 */

public class BooksDaoConfig {
    private static DbManager.DaoConfig mBooksDaoConfig;
    public static DbManager.DaoConfig getBooksDaoConfig(){
        //优化效率--只有第一次才有必要同步
        if (mBooksDaoConfig==null) {
            //处理线程安全问题
            synchronized (BooksDaoConfig.class){
                if (mBooksDaoConfig==null) {
                    mBooksDaoConfig=new DbManager.DaoConfig() ;
                    mBooksDaoConfig.setDbName("booksDB");
                    mBooksDaoConfig.setDbVersion(1);
                }
            }
        }
        return mBooksDaoConfig;
    }
}

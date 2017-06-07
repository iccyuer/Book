package com.example.book.books.db;

import com.example.book.books.model.Purchase;

import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.List;

/**
 * Created by Admin on 2017/5/22.
 */

public class PurchaseDao {
    private PurchaseDao() {
    }

    private static PurchaseDao mPurchaseDao;

    public static PurchaseDao getPurchaseDao() {
        //优化效率--只有第一次才有必要同步
        if (mPurchaseDao == null) {
            //处理线程安全问题
            synchronized (PurchaseDao.class) {
                if (mPurchaseDao == null) {
                    mPurchaseDao = new PurchaseDao();
                }
            }
        }
        return mPurchaseDao;
    }


    public void addPurchase(Purchase purchase){
        try {
            x.getDb(BooksDaoConfig.getBooksDaoConfig()).save(purchase);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public List<Purchase> getAllPurchase(){
        try {
            return x.getDb(BooksDaoConfig.getBooksDaoConfig()).selector(Purchase.class)
                    .orderBy("purchaseid",true).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }
}

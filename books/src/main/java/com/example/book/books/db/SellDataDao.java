package com.example.book.books.db;

import com.example.book.books.entity.SellData;

import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.List;

/**
 * Created by Admin on 2017/5/31.
 */

public class SellDataDao {
    private SellDataDao() {
    }

    private static SellDataDao mSellDataDao;

    public static SellDataDao getSellDataDao() {
        //优化效率--只有第一次才有必要同步
        if (mSellDataDao == null) {
            //处理线程安全问题
            synchronized (SellDataDao.class) {
                if (mSellDataDao == null) {
                    mSellDataDao = new SellDataDao();
                }
            }
        }
        return mSellDataDao;
    }

    public void addSellDate(SellData sellData){
        try {
            x.getDb(BooksDaoConfig.getBooksDaoConfig()).saveBindingId(sellData);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public SellData getSellData(int bookid){
        try {
            return x.getDb(BooksDaoConfig.getBooksDaoConfig()).selector(SellData.class)
                    .where("bookid","=",bookid).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean checkExist(int bookid){
        try {
            SellData bookid1 = x.getDb(BooksDaoConfig.getBooksDaoConfig()).selector(SellData.class)
                    .where("bookid", "=", bookid).findFirst();
            if (bookid1!=null) {
                return true;
            }else{
                return false;
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updateNum(SellData sellData){
        try {
            x.getDb(BooksDaoConfig.getBooksDaoConfig()).update(sellData);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public List<SellData> getAllSellData(){
        try {
            return x.getDb(BooksDaoConfig.getBooksDaoConfig()).selector(SellData.class)
                    .findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteAll(){
        try {
            x.getDb(BooksDaoConfig.getBooksDaoConfig()).delete(SellData.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

}


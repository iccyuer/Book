package com.example.book.books.db;

import com.example.book.books.model.Delivery;

import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.List;

/**
 * Created by Admin on 2017/5/23.
 */

public class DeliveryDao {
    private  DeliveryDao(){}

    private static DeliveryDao mDeliveryDao;
    public static DeliveryDao getDeliveryDao(){
        //优化效率--只有第一次才有必要同步
        if (mDeliveryDao==null) {
            //处理线程安全问题
            synchronized (DeliveryDao.class){
                if (mDeliveryDao==null) {
                    mDeliveryDao=new DeliveryDao();
                }
            }
        }
        return mDeliveryDao;
    }

    public void addDelivery(Delivery delivery){
        try {
            x.getDb(BooksDaoConfig.getBooksDaoConfig()).saveBindingId(delivery);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public List<Delivery> getAllDelivery(){
        try {
            return x.getDb(BooksDaoConfig.getBooksDaoConfig()).selector(Delivery.class)
                    .findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

}

package com.example.book.books.db;

import com.example.book.books.model.DeliveryDetails;

import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.List;

import static org.xutils.x.getDb;

/**
 * Created by Admin on 2017/5/23.
 */

public class DeliveryDetailsDao {
    private  DeliveryDetailsDao(){}

    private static DeliveryDetailsDao mDeliveryDetailsDao;
    public static DeliveryDetailsDao getDeliveryDetailsDao(){
        //优化效率--只有第一次才有必要同步
        if (mDeliveryDetailsDao==null) {
            //处理线程安全问题
            synchronized (DeliveryDetailsDao.class){
                if (mDeliveryDetailsDao==null) {
                    mDeliveryDetailsDao=new DeliveryDetailsDao();
                }
            }
        }
        return mDeliveryDetailsDao;
    }

    public void addDeliveryDetails(DeliveryDetails deliveryDetails){
        try {
            getDb(BooksDaoConfig.getBooksDaoConfig()).saveBindingId(deliveryDetails);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

//    List<DeliveryDetails>
    public List<DeliveryDetails> getDeliveryDetails(int deliveryid){
        try {
            return x.getDb(BooksDaoConfig.getBooksDaoConfig()).selector(DeliveryDetails.class)
                    .where("deliveryid","=",deliveryid).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }
}

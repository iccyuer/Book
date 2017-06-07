package com.example.book.books.db;

import com.example.book.books.model.OrderDetails;

import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2017/5/18.
 */

public class OrderDetailsDao {
    private OrderDetailsDao() {
    }

    private static OrderDetailsDao mOrderDetailsDao;

    public static OrderDetailsDao getOrderDetailsDao() {
        //优化效率--只有第一次才有必要同步
        if (mOrderDetailsDao == null) {
            //处理线程安全问题
            synchronized (OrderDetailsDao.class) {
                if (mOrderDetailsDao == null) {
                    mOrderDetailsDao = new OrderDetailsDao();
                }
            }
        }
        return mOrderDetailsDao;
    }

    public void addOrderDetails(OrderDetails orderDetails){
        try {
            x.getDb(BooksDaoConfig.getBooksDaoConfig()).save(orderDetails);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取所有和orderid相关的
     */
    List<Integer> mCartIds=new ArrayList<>() ;
    public List<Integer> getCartIds(int orderid){
        try {
            List<OrderDetails> orderDetailses = x.getDb(BooksDaoConfig.getBooksDaoConfig()).selector(OrderDetails.class)
                    .where("orderid", "=", orderid).findAll();
            if (orderDetailses!=null) {
                mCartIds.clear();
                for (OrderDetails orderDetailse : orderDetailses) {
                    mCartIds.add(orderDetailse.getCartid());
                }
                return mCartIds;
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取单个
     * @return
     */
    public int getCartId(int orderid){
        try {
            OrderDetails details = x.getDb(BooksDaoConfig.getBooksDaoConfig()).selector(OrderDetails.class)
                    .where("orderid", "=", orderid).findFirst();
            if (details!=null) {
                return details.getCartid();
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

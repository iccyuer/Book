package com.example.book.books.db;

import com.example.book.books.model.Orders;

import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.List;

/**
 * Created by Admin on 2017/5/18.
 */

public class OrdersDao {
    private OrdersDao() {
    }

    private static OrdersDao mOrdersDao;

    public static OrdersDao getOrdersDao() {
        //优化效率--只有第一次才有必要同步
        if (mOrdersDao == null) {
            //处理线程安全问题
            synchronized (OrdersDao.class) {
                if (mOrdersDao == null) {
                    mOrdersDao = new OrdersDao();
                }
            }
        }
        return mOrdersDao;
    }

    public void addOrders(Orders orders){
        try {
            x.getDb(BooksDaoConfig.getBooksDaoConfig()).saveBindingId(orders);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public Orders getOrderById(int orderid){
        try {
            return x.getDb(BooksDaoConfig.getBooksDaoConfig()).selector(Orders.class)
                    .where("orderid","=",orderid).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 普通用户获取自己的订单
     * @param userid
     * @return
     */
    public List<Orders> getOrdersByUserId(int userid){
        try {
            List<Orders> orderses = x.getDb(BooksDaoConfig.getBooksDaoConfig()).selector(Orders.class)
                    .where("userid", "=", userid).findAll();
            return orderses;
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 管理员获取所有用户订单--所有状态
     * @return
     */
    public List<Orders> getOrders() {
        try {
            List<Orders> orderses = x.getDb(BooksDaoConfig.getBooksDaoConfig()).selector(Orders.class)
                    .findAll();
            return orderses;
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 管理员获取所有用户订单--未出货状态--且交易成功的
     *
     * @return
     */
    public List<Orders> getOrdersUn() {
        try {
            List<Orders> orderses = x.getDb(BooksDaoConfig.getBooksDaoConfig()).selector(Orders.class)
                    .where("isDeliver","=",false).and("orderState","=","交易成功").findAll();
            return orderses;
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 修改订单状态--交易成功--失败--取消
     * @param orders
     * @param newState
     */
    public void updateOrdersState(Orders orders,String newState){
        orders.setOrderState(newState);
        try {
            x.getDb(BooksDaoConfig.getBooksDaoConfig()).update(orders,"orderState");
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改订单出货状态--是否
     *
     * @param orders
     * @param newDeliver
     */
    public void updateOrdersDeliver(Orders orders, boolean newDeliver) {
        orders.setDeliver(newDeliver);
        try {
            x.getDb(BooksDaoConfig.getBooksDaoConfig()).update(orders, "isDeliver");
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

}

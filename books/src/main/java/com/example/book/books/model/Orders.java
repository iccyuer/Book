package com.example.book.books.model;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by Admin on 2017/5/16.
 */

@Table(name = "Orders")
public class Orders {

//    @Column(name = "id",isId = true)
//    private int id;

    @Column(name = "orderid",isId = true)
    private int orderid;

    @Column(name = "userid")
    private int userid;

    @Column(name = "allprice")
    private float allprice;

    @Column(name = "orderTime")
    private long orderTime;

    @Column(name = "orderState")
    private String orderState="";

    @Column(name = "isDeliver")
    private boolean isDeliver;

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public float getAllprice() {
        return allprice;
    }

    public void setAllprice(float allprice) {
        this.allprice = allprice;
    }

    public long getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(long orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public boolean isDeliver() {
        return isDeliver;
    }

    public void setDeliver(boolean deliver) {
        isDeliver = deliver;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "orderid=" + orderid +
                ", userid=" + userid +
                ", allprice=" + allprice +
                ", orderTime=" + orderTime +
                ", orderState='" + orderState + '\'' +
                ", isDeliver=" + isDeliver +
                '}';
    }
}

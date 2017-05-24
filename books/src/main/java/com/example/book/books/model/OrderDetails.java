package com.example.book.books.model;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by Admin on 2017/5/18.
 */
@Table(name = "OrderDetails")
public class OrderDetails {

    @Column(name = "orderDetailsid",isId = true)
    private int orderDetailsid;

    @Column(name = "orderid")
    private int orderid;

    @Column(name = "userid")
    private int userid;

    @Column(name = "cartid")
    private int cartid;

    public int getOrderDetailsid() {
        return orderDetailsid;
    }

    public void setOrderDetailsid(int orderDetailsid) {
        this.orderDetailsid = orderDetailsid;
    }

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

    public int getCartid() {
        return cartid;
    }

    public void setCartid(int cartid) {
        this.cartid = cartid;
    }
}

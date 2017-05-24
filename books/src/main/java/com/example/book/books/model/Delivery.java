package com.example.book.books.model;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by Admin on 2017/5/23.
 * 出货单--用户下单支付成功后，管理员确准后出货记录
 */

@Table(name = "Delivery")
public class Delivery {

    @Column(name = "deliveryid",isId = true)
    private int deliveryid;

    @Column(name = "deliveryTime")
    private long deliveryTime;

    public int getDeliveryid() {
        return deliveryid;
    }

    public void setDeliveryid(int deliveryid) {
        this.deliveryid = deliveryid;
    }

    public long getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(long deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
}

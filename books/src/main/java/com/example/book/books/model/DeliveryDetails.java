package com.example.book.books.model;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by Admin on 2017/5/23.
 */

//出货详单--当前出货单中具体出货书籍及数量
@Table(name = "DeliveryDetails")
public class DeliveryDetails {

    @Column(name = "deliveryDetailsid",isId = true)
    private int deliveryDetailsid;

    @Column(name = "deliveryid")
    private int deliveryid;

    @Column(name = "bookid")
    private int bookid;

    @Column(name = "bookname")
    private String bookname;

    @Column(name = "bookPic")
    private String bookPic;

    @Column(name = "deliverynum")
    private int deliverynum;

    public int getDeliveryDetailsid() {
        return deliveryDetailsid;
    }

    public void setDeliveryDetailsid(int deliveryDetailsid) {
        this.deliveryDetailsid = deliveryDetailsid;
    }

    public int getDeliveryid() {
        return deliveryid;
    }

    public void setDeliveryid(int deliveryid) {
        this.deliveryid = deliveryid;
    }

    public int getBookid() {
        return bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getBookPic() {
        return bookPic;
    }

    public void setBookPic(String bookPic) {
        this.bookPic = bookPic;
    }

    public int getDeliverynum() {
        return deliverynum;
    }

    public void setDeliverynum(int deliverynum) {
        this.deliverynum = deliverynum;
    }

    @Override
    public String toString() {
        return "DeliveryDetails{" +
                "deliveryDetailsid=" + deliveryDetailsid +
                ", deliveryid=" + deliveryid +
                ", bookid=" + bookid +
                ", bookname='" + bookname + '\'' +
                ", bookPic='" + bookPic + '\'' +
                ", deliverynum=" + deliverynum +
                '}';
    }
}

package com.example.book.books.model;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by Admin on 2017/5/14.
 */


@Table(name = "Carts")
public class Carts {
    @Column(name = "cartid", isId = true)
    private int cartid;

    @Column(name = "bookid")
    private int bookid;

    @Column(name = "userid")
    private int userid;

    @Column(name = "name")
    private String name;

    @Column(name = "marketPrice")
    private float marketPrice;

    @Column(name = "price")
    private float price;

    @Column(name = "pic")
    private String pic;

    @Column(name = "amount")
    private int amount;

    @Column(name = "checked")
    private boolean checked;

    @Column(name = "isCanShow")
    private boolean isCanShow=true;

    public int getCartid() {
        return cartid;
    }

    public void setCartid(int cartid) {
        this.cartid = cartid;
    }

    public int getBookid() {
        return bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(float marketPrice) {
        this.marketPrice = marketPrice;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isCanShow() {
        return isCanShow;
    }

    public void setCanShow(boolean canShow) {
        isCanShow = canShow;
    }

    @Override
    public String toString() {
        return "Carts{" +
                "cartid=" + cartid +
                ", bookid=" + bookid +
                ", userid=" + userid +
                ", name='" + name + '\'' +
                ", marketPrice=" + marketPrice +
                ", price=" + price +
                ", pic='" + pic + '\'' +
                ", amount=" + amount +
                ", checked=" + checked +
                ", isCanShow=" + isCanShow +
                '}';
    }
}

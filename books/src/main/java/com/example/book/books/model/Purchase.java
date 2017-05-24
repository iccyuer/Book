package com.example.book.books.model;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by Admin on 2017/5/22.
 * 进货单
 */
@Table(name = "Purchase")
public class Purchase {

    @Column(name = "purchaseid",isId = true)
    private int purchaseid;

    @Column(name = "bookid")
    private int bookid;

    @Column(name = "purchaseTime")
    private long purchaseTime;

    //增减库存量--增加了多少--减少了多少
    @Column(name = "purchaseAmount")
    private int purchaseAmount;

    //修改库存量--直接修改成多少--或是添加新品时的数量
    @Column(name = "purchaseStock")
    private int purchaseStock;

    @Column(name = "isNew")
    private boolean isNew;

    @Column(name = "bookPic")
    private String bookPic;

    @Column(name = "bookName")
    private String bookName;

    //现有库存量
    @Column(name = "stockNow")
    private int stockNow;

    public int getPurchaseid() {
        return purchaseid;
    }

    public void setPurchaseid(int purchaseid) {
        this.purchaseid = purchaseid;
    }

    public int getBookid() {
        return bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }

    public long getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(long purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public int getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(int purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    public int getPurchaseStock() {
        return purchaseStock;
    }

    public void setPurchaseStock(int purchaseStock) {
        this.purchaseStock = purchaseStock;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public String getBookPic() {
        return bookPic;
    }

    public void setBookPic(String bookPic) {
        this.bookPic = bookPic;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getStockNow() {
        return stockNow;
    }

    public void setStockNow(int stockNow) {
        this.stockNow = stockNow;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "purchaseid=" + purchaseid +
                ", bookid=" + bookid +
                ", purchaseTime=" + purchaseTime +
                ", purchaseAmount=" + purchaseAmount +
                ", purchaseStock=" + purchaseStock +
                ", isNew=" + isNew +
                ", bookPic='" + bookPic + '\'' +
                ", bookName='" + bookName + '\'' +
                ", stockNow=" + stockNow +
                '}';
    }
}

package com.example.book.books.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by Admin on 2017/5/31.
 */

@Table(name = "SellData")
public class SellData implements Comparable {

    @Column(name = "sellid",isId = true)
    private int sellid;

    @Column(name = "bookid")
    private int bookid;

    @Column(name = "bookname")
    private String bookname;

    @Column(name = "count")
    private int count;

    @Column(name = "pic")
    private String pic;

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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @Override
    public String toString() {
        return "SellData{" +
                "bookid=" + bookid +
                ", bookname='" + bookname + '\'' +
                ", count=" + count +
                '}';
    }

    @Override
    public int compareTo(Object o) {

        SellData sell=(SellData) o;
        if(count>sell.count){
            return -58;
        }
        if(count<sell.count){
            return -(count-sell.count);
        }
        return 0;

//        SellData sell = (SellData) o;
//        if (bookid > sell.bookid) {
//            return bookid - sell.bookid;
//        }
//        if (bookid < sell.bookid) {
//            return -1;
//        }
//        return 0;
    }
}

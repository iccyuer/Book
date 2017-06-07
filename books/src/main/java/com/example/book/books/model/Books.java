package com.example.book.books.model;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by Admin on 2017/5/13.
 */


@Table(name="Books")
public class Books {

    @Column(name = "bookid", isId = true)
    private int bookid;

    @Column(name = "name")
    private String name;

    @Column(name = "author")
    private String author;

    @Column(name = "intro")
    private String intro;

    @Column(name = "press")
    private String press;

    @Column(name = "marketPrice")
    private float marketPrice;

    @Column(name = "price")
    private float price;

    @Column(name = "type")
    private int type;

    @Column(name = "pic")
    private String pic;

    @Column(name = "stockBalance")
    private int stockBalance;

    @Column(name = "ISBN")
    private String ISBN;

    public int getBookid() {
        return bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getStockBalance() {
        return stockBalance;
    }

    public void setStockBalance(int stockBalance) {
        this.stockBalance = stockBalance;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    @Override
    public String toString() {
        return "Books{" +
                "bookid=" + bookid +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", intro='" + intro + '\'' +
                ", press='" + press + '\'' +
                ", marketPrice=" + marketPrice +
                ", price=" + price +
                ", type=" + type +
                ", pic='" + pic + '\'' +
                ", stockBalance=" + stockBalance +
                ", ISBN='" + ISBN + '\'' +
                '}';
    }
}

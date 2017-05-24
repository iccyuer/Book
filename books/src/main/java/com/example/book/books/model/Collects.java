package com.example.book.books.model;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by Admin on 2017/5/14.
 */
@Table(name = "Collects")
public class Collects {

    @Column(name = "collectId",isId = true)
    private int collectId;

    @Column(name = "userId")
    private int userId;

    @Column(name = "bookId")
    private int bookId;

    public int getCollectId() {
        return collectId;
    }

    public void setCollectId(int collectId) {
        this.collectId = collectId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}

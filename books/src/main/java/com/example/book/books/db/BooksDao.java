package com.example.book.books.db;

import com.example.book.books.Constant;
import com.example.book.books.model.Books;
import com.example.book.books.model.BooksType;

import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2017/5/13.
 */

public class BooksDao {

    private BooksDao() {
    }

    private static BooksDao mBooksDao;

    public static BooksDao getBooksDao() {
        //优化效率--只有第一次才有必要同步
        if (mBooksDao == null) {
            //处理线程安全问题
            synchronized (BooksDao.class) {
                if (mBooksDao == null) {
                    mBooksDao = new BooksDao();
                }
            }
        }
        return mBooksDao;
    }

    private List<Books> mBooksList = new ArrayList<>();


    /**
     * 初始化假数据
     */
    public void init() {
        Books books0 = new Books();
        books0.setName("愿你迷路到我身边");
        books0.setAuthor("蕊希");
        books0.setIntro("千万粉丝暖心直播蕊希首部感情励志作品集。" +
                "其实从未迷路，在遇见你之前，就把人生当作一场自由自在的漫步。");
        books0.setPress("百花洲文艺出版社");
        books0.setPrice((float) 27.50);
        books0.setMarketPrice((float) 39.80);
        books0.setPic(Constant.new_milu);
        books0.setStockBalance(100);
        books0.setType(BooksType.BOOKS_TYPE_NEW);

        Books books1 = new Books();
        books1.setName("半小时漫画中国史");
        books1.setAuthor("二混子");
        books1.setIntro("200万粉丝大号\"混子曰\"创始人二混子的革命性历史作品。" +
                "张泉灵鼎力推荐！看半小时漫画，同三千年历史，脉络务必清晰，看完就能倒背。");
        books1.setPress("江苏文艺出版社");
        books1.setPrice((float) 28.70);
        books1.setMarketPrice((float) 39.90);
        books1.setPic(Constant.new_manhua);
        books1.setStockBalance(100);
        books1.setType(BooksType.BOOKS_TYPE_NEW);

        Books books2 = new Books();
        books2.setName("婴幼儿睡眠全书");
        books2.setAuthor("小土大橙子");
        books2.setIntro("给宝宝的黄金睡眠 育儿专家张思莱作序并倾情推荐。");
        books2.setPress("北京师范大学出版社");
        books2.setPrice((float) 32.40);
        books2.setMarketPrice((float) 45.00);
        books2.setPic(Constant.new_sleep);
        books2.setStockBalance(100);
        books2.setType(BooksType.BOOKS_TYPE_NEW);

        Books books3 = new Books();
        books3.setName("时光有你，记忆成花");
        books3.setAuthor("顾西爵");
        books3.setIntro("(暖萌青春代言人顾西爵全新唯美治愈系力作。" +
                "你叫蔚迟，我叫莫离，你不迟到，我不离开，我们总会相遇。白马时光)");
        books3.setPress("百花洲文艺出版社");
        books3.setPrice((float) 22.70);
        books3.setMarketPrice((float) 32.80);
        books3.setPic(Constant.new_time);
        books3.setStockBalance(100);
        books3.setType(BooksType.BOOKS_TYPE_NEW);

        Books books4 = new Books();
        books4.setName("你好，法奈利");
        books4.setAuthor("杰西卡~诺尔");
        books4.setIntro("(献给世上所有的蒂芙阿尼·法奈利，美国超热销50万册，已售30国版权，" +
                "被国外出版社和媒体誉为2016年备受关注的小说。)");
        books4.setPress("百花洲文艺出版社");
        books4.setPrice((float) 19.50);
        books4.setMarketPrice((float) 39.80);
        books4.setPic(Constant.new_hello);
        books4.setStockBalance(100);
        books4.setType(BooksType.BOOKS_TYPE_NEW);


        mBooksList.add(books0);
        mBooksList.add(books1);
        mBooksList.add(books2);
        mBooksList.add(books3);
        mBooksList.add(books4);
        mBooksList.add(books4);
        mBooksList.add(books4);
        mBooksList.add(books4);
        mBooksList.add(books4);
        mBooksList.add(books4);
        addBooks(mBooksList);
    }


    public void addBooks(List<Books> bookses) {
        for (Books bookse : bookses) {
            try {
                x.getDb(BooksDaoConfig.getBooksDaoConfig()).save(bookse);
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
    }

    public void addBook(Books books) {
        try {
            x.getDb(BooksDaoConfig.getBooksDaoConfig()).saveBindingId(books);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public List<Books> getAllBooksByType(int type) {

        try {
            return x.getDb(BooksDaoConfig.getBooksDaoConfig()).selector(Books.class).
                    where("type", "=", type).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Books getBooksById(int id) {
        try {
            return x.getDb(BooksDaoConfig.getBooksDaoConfig()).selector(Books.class).
                    where("bookid", "=", id).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateBooksInfo(Books books) {
        try {
            x.getDb(BooksDaoConfig.getBooksDaoConfig()).update(books);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void updateBooksStockBalance(Books books, int num) {
        int stockBalance = books.getStockBalance();
        stockBalance += num;
        books.setStockBalance(stockBalance);
        try {
            x.getDb(BooksDaoConfig.getBooksDaoConfig()).update(books, "stockBalance");
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

}

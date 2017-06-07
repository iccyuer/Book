package com.example.book.books.db;

import com.example.book.books.Constant;
import com.example.book.books.model.Books;
import com.example.book.books.model.BooksType;

import org.xutils.db.sqlite.WhereBuilder;
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

        //-------------------------------------------------------------
        Books books5 = new Books();//id=6
        books5.setName("人工智能");
        books5.setAuthor("李开复、王咏刚");
        books5.setIntro("\"人工智能\"被写入2017年政府工作报告，智能革命时代先行者李开复解读" +
                "AI如何重塑个人、商业与社会的未来图谱");
        books5.setPress("文化发展出版社");
        books5.setPrice((float) 39.60);
        books5.setMarketPrice((float) 55.00);
        books5.setPic(Constant.zhineng);
        books5.setStockBalance(100);
        books5.setType(BooksType.BOOKS_TYPE_HOT);

        Books books6 = new Books();//id=7
        books6.setName("一个电商运营总监的自白");
        books6.setAuthor("金牛城");
        books6.setIntro("淘宝天猫、京东网站一线员工、运营、创业者争相传阅！前阿里运营大咖十年经验总结！" +
                "");
        books6.setPress("电子工业出版社");
        books6.setPrice((float) 51.30);
        books6.setMarketPrice((float) 59.00);
        books6.setPic(Constant.baibai);
        books6.setStockBalance(100);
        books6.setType(BooksType.BOOKS_TYPE_HOT);

        Books books7 = new Books();//id=8
        books7.setName("欢乐颂.典藏版（全3季）");
        books7.setAuthor("阿耐");
        books7.setIntro("刘涛、蒋欣、王凯、靳东主演电视剧原著小说，收纳电视剧全三季的所有精彩！" +
                "全新修订典藏！生活艰难，至少我们还拥有彼此。");
        books7.setPress("四川文艺出版社");
        books7.setPrice((float) 73.50);
        books7.setMarketPrice((float) 98.00);
        books7.setPic(Constant.huanlesong);
        books7.setStockBalance(100);
        books7.setType(BooksType.BOOKS_TYPE_HOT);

        Books books8 = new Books();//id 9
        books8.setName("你会不会喜欢我");
        books8.setAuthor("喃东尼");
        books8.setIntro("\"友谊的小船说翻就翻\"原作者喃东尼2017年力作！邓超、李晨、鹿晗微博配图转发！" +
                "");
        books8.setPress("湖南文艺出版社");
        books8.setPrice((float) 29.60);
        books8.setMarketPrice((float) 39.80);
        books8.setPic(Constant.like);
        books8.setStockBalance(100);
        books8.setType(BooksType.BOOKS_TYPE_HOT);


        //-------------------------------------------------------------
        Books books9 = new Books();
        books9.setName("大英儿童百科全书（全16册）");
        books9.setAuthor("不列颠百科出品");
        books9.setIntro("入选新闻出版总署向青少年推荐的100中图书，获评全国引进版优秀图书奖。" +
                "");
        books9.setPress("湖南少儿出版社");
        books9.setPrice((float) 344.90);
        books9.setMarketPrice((float) 448.00);
        books9.setPic(Constant.child_baike);
        books9.setStockBalance(100);
        books9.setType(BooksType.BOOKS_TYPE_CHILD);

        Books books10 = new Books();
        books10.setName("走进奇妙的虫子世界");
        books10.setAuthor("（以）尤瓦·左默");
        books10.setIntro("1年畅销16国，英国皇家艺术学院插画师清新无比的科普图画书。" +
                "中科院动物研究所昆虫专家刘晔审定、推荐--爱心树童书作品");
        books10.setPress("新星出版社");
        books10.setPrice((float) 76.90);
        books10.setMarketPrice((float) 88.00);
        books10.setPic(Constant.child_chongzi);
        books10.setStockBalance(100);
        books10.setType(BooksType.BOOKS_TYPE_CHILD);

        Books books11 = new Books();
        books11.setName("暖暖心绘本");
        books11.setAuthor("（伊朗）米拦弗特毕 文，（德）沃琪顿 绘，漪然 译");
        books11.setIntro("冰心儿童图书奖，为3-7岁处在性格形成关键期的孩子准备的一份心理自助礼物，" +
                "让孩子学会倾诉与聆听、感恩与知足、友善与互助、给予和分享、团队和写作...");
        books11.setPress("湖南少儿出版社");
        books11.setPrice((float) 144.90);
        books11.setMarketPrice((float) 188.00);
        books11.setPic(Constant.child_bear);
        books11.setStockBalance(100);
        books11.setType(BooksType.BOOKS_TYPE_CHILD);

        Books books12 = new Books();
        books12.setName("一闪一闪小银鱼（套装全3册）");
        books12.setAuthor("（以色列）保罗·候尔（Paul Kor）");
        books12.setIntro("献给喜欢和小朋友玩的小朋友，以及不喜欢和小朋友玩的小朋友。" +
                "学会在新环境里找到好朋友。");
        books12.setPress("北京联合出版公司");
        books12.setPrice((float) 85.70);
        books12.setMarketPrice((float) 119.80);
        books12.setPic(Constant.child_fish);
        books12.setStockBalance(100);
        books12.setType(BooksType.BOOKS_TYPE_CHILD);


        mBooksList.add(books0);
        mBooksList.add(books1);
        mBooksList.add(books2);
        mBooksList.add(books3);
        mBooksList.add(books4);
        mBooksList.add(books5);
        mBooksList.add(books6);
        mBooksList.add(books7);
        mBooksList.add(books8);
        mBooksList.add(books9);
        mBooksList.add(books10);
        mBooksList.add(books11);
        mBooksList.add(books12);
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

    public List<Books> getBookBySearchName(String bookname) {
        try {
            return x.getDb(BooksDaoConfig.getBooksDaoConfig()).selector(Books.class)
                    .where("name", "like", "%" + bookname + "%").findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Books> getBookBySearchAuthor(String author) {
        try {
            return x.getDb(BooksDaoConfig.getBooksDaoConfig()).selector(Books.class)
                    .where("author", "like", "%" + author + "%").findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Books> getBookBySearch(String some) {
        try {
            return x.getDb(BooksDaoConfig.getBooksDaoConfig()).selector(Books.class)
                    .where("author", "like", "%" + some + "%").or("name", "like", "%" + some + "%").findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteBoooksById(int bookid) {
        WhereBuilder whereBuilder = WhereBuilder.b("bookid", "=", bookid);
        try {
            x.getDb(BooksDaoConfig.getBooksDaoConfig()).delete(Books.class, whereBuilder);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public boolean checkISBN(String ISBN) {
        try {
            Books isbn = x.getDb(BooksDaoConfig.getBooksDaoConfig()).selector(Books.class)
                    .where("ISBN", "=", ISBN).findFirst();
            if (isbn != null) {
                return true;
            } else {
                return false;
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return false;
    }

}

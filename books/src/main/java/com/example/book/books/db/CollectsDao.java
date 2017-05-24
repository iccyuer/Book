package com.example.book.books.db;

import com.example.book.books.model.Collects;

import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2017/5/15.
 */

public class CollectsDao {
    private CollectsDao() {
    }

    private static CollectsDao mCollectsDao;

    public static CollectsDao getCollectsDao() {
        //优化效率--只有第一次才有必要同步
        if (mCollectsDao == null) {
            //处理线程安全问题
            synchronized (CollectsDao.class) {
                if (mCollectsDao == null) {
                    mCollectsDao = new CollectsDao();
                }
            }
        }
        return mCollectsDao;
    }

    public void addCollects(Collects collects){
        try {
            x.getDb(BooksDaoConfig.getBooksDaoConfig()).save(collects);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    List<Integer> mCollectsBookIds=new ArrayList<>() ;

    public List<Integer> getCollectsBookIds(int userid){
        try {
            List<Collects> collectses = x.getDb(BooksDaoConfig.getBooksDaoConfig()).selector(Collects.class)
                    .where("userId", "=", userid).findAll();
            if (collectses!=null) {
                mCollectsBookIds.clear();
                for (Collects collectse : collectses) {
                    mCollectsBookIds.add(collectse.getBookId());
                }
                return mCollectsBookIds;
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteCollectByBookId(int bookid){
        try {
            WhereBuilder whereBuilder=WhereBuilder.b("bookId","=",bookid);
            int delete = x.getDb(BooksDaoConfig.getBooksDaoConfig()).delete(Collects.class, whereBuilder);
            System.out.println("delete = " + delete);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return false;
    }
}

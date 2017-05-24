package com.example.book.books.db;

import com.example.book.books.model.Carts;

import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2017/5/14.
 */

public class CartsDao {

    private CartsDao() {
    }

    private static CartsDao mCartsDao;

    public static CartsDao getCartsDao() {
        //优化效率--只有第一次才有必要同步
        if (mCartsDao == null) {
            //处理线程安全问题
            synchronized (CartsDao.class) {
                if (mCartsDao == null) {
                    mCartsDao = new CartsDao();
                }
            }
        }
        return mCartsDao;
    }


    public void addCarts(Carts carts) {
        try {
            x.getDb(BooksDaoConfig.getBooksDaoConfig()).save(carts);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public List<Carts> getAllCartsByUserId(int id) {
        try {
            return x.getDb(BooksDaoConfig.getBooksDaoConfig()).selector(Carts.class).
                    where("userid", "=", id).and("isCanShow","=",true).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    List<Carts> mCartses = new ArrayList<>();

    public List<Carts> getCartsByCartsId(int userid, List<Integer> cartsId) {
        try {
            mCartses.clear();
            for (Integer integer : cartsId) {
                Carts carts = x.getDb(BooksDaoConfig.getBooksDaoConfig()).selector(Carts.class)
                        .where("userid", "=", userid).and("cartid", "=", integer).and("isCanShow","=",true).findFirst();
                if (carts!=null) {
                    mCartses.add(carts);
                }
            }
            return mCartses;
        } catch (DbException e) {
            e.printStackTrace();
        }
        return  null;
    }

    List<Integer> mCartsBookIds = new ArrayList<>();

    public List<Integer> getUsersCartsBookIds(int userid) {
        try {
            List<Carts> cartses = x.getDb(BooksDaoConfig.getBooksDaoConfig()).selector(Carts.class)
                    .where("userid", "=", userid).and("isCanShow","=",true).findAll();
            if (cartses != null) {
                mCartsBookIds.clear();
                for (Carts cartse : cartses) {
                    mCartsBookIds.add(cartse.getBookid());
                }
                return mCartsBookIds;
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Integer getCartsAllAmounts(int userid) {
        try {
            List<Carts> cartses = x.getDb(BooksDaoConfig.getBooksDaoConfig()).selector(Carts.class)
                    .where("userid", "=", userid).and("isCanShow","=",true).findAll();
            int allAmount = 0;
            if (cartses != null) {
                for (Carts cartse : cartses) {
                    allAmount += cartse.getAmount();
                }
                return allAmount;
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Carts getCartsByBookIdAndUserId(int bookid, int userid) {
        try {
            return x.getDb(BooksDaoConfig.getBooksDaoConfig()).selector(Carts.class).
                    where("bookid", "=", bookid).and("userid", "=", userid).and("isCanShow","=",true).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateCartsAmount(Carts carts, int newAmount) {
        carts.setAmount(newAmount);
        try {
            x.getDb(BooksDaoConfig.getBooksDaoConfig()).update(carts, "amount");
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public Carts getgetCartsByCartIdAndUserId(int cartid, int userid){
        try {
            return x.getDb(BooksDaoConfig.getBooksDaoConfig()).selector(Carts.class).
                    where("cartid", "=", cartid).and("userid", "=", userid).and("isCanShow","=",true).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 此方法和上面  用在不同地方 上面用在购物车显示  下满用在订单显示
     * @param cartid
     * @param userid
     * @return
     */
    public Carts getgetCartsByCartIdAndUserId2(int cartid, int userid) {
        try {
            return x.getDb(BooksDaoConfig.getBooksDaoConfig()).selector(Carts.class).
                    where("cartid", "=", cartid).and("userid", "=", userid).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 管理员获取
     * @param cartid
     * @return
     */
    public Carts getCartsByCartId(int cartid) {
        try {
            return x.getDb(BooksDaoConfig.getBooksDaoConfig()).selector(Carts.class).
                    where("cartid", "=", cartid).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void updateCartsState(Carts carts,boolean state){
        carts.setCanShow(state);
        try {
            x.getDb(BooksDaoConfig.getBooksDaoConfig()).update(carts,"isCanShow");
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}

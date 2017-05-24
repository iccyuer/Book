package com.example.book.books.db;

import com.example.book.books.model.Users;

import org.xutils.ex.DbException;
import org.xutils.x;

/**
 * Created by Admin on 2017/5/14.
 */

public class UsersDao {

    private  UsersDao(){}

    private static UsersDao mUsersDao;
    public static UsersDao getUsersDao(){
        //优化效率--只有第一次才有必要同步
        if (mUsersDao==null) {
            //处理线程安全问题
            synchronized (UsersDao.class){
                if (mUsersDao==null) {
                    mUsersDao=new UsersDao();
                }
            }
        }
        return mUsersDao;
    }

    //初始化测试用户
    public void init(){
        Users users=new Users() ;
        users.setUserName("temp");
        users.setUserPassword("temp");
        users.setUserSex("男");
        users.setUserphone("110");
        users.setUserAddress("河北省廊坊市三河市");

        registUsers(users);
    }

    public void registUsers(Users users){
        try {
            x.getDb(BooksDaoConfig.getBooksDaoConfig()).save(users);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public boolean checkUserByName(String name){
        try {
            Users users = x.getDb(BooksDaoConfig.getBooksDaoConfig()).selector(Users.class).
                    where("userName", "=", name).findFirst();
            if (users!=null) {
                return true;
            }else{
                return false;
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getUserIdByName(String name) {
        try {
            Users users = x.getDb(BooksDaoConfig.getBooksDaoConfig()).selector(Users.class).
                    where("userName", "=", name).findFirst();
            if (users != null) {
                return users.getUserid();
            } else {
                return 0;
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean loginInByNameAndPwd(String name,String pwd){
        try {
            Users users = x.getDb(BooksDaoConfig.getBooksDaoConfig()).selector(Users.class).
                    where("userName", "=", name).and("userPassword", "=", pwd).findFirst();
            if (users!=null) {
                return true;
            }else{
                return false;
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return false;
    }
}

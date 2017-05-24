package com.example.book.books.utils;

import java.text.SimpleDateFormat;

/**
 * Created by Admin on 2017/5/19.
 */

public class DataFormatUtil {
    public static String format(long currentTimeMillis){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy年 MM月 dd日 a HH:mm:ss");//自己写的模板解析时间date对象
        String str=sdf.format(currentTimeMillis);//返回字符串
        System.out.println(str);
        return str;
    }
}

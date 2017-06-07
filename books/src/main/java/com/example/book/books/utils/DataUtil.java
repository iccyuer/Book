package com.example.book.books.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Admin on 2017/5/31.
 */

public class DataUtil {


    public static long get(String time) throws ParseException {
        if (time.length() == 4) {//年
            if ("2017".equals(time)) {
                String timeN = "2018-01-11 00:00:00";
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = sdf1.parse(timeN);
                return date.getTime();
            } else if (time.length() == 7) {//月

            } else if (time.length() == 10) {//日

            }
        }
        return 0;
    }

    public static void gg(Date date) {

    }
}

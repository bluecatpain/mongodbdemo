package com.example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期转换工具类
 */
public class DateUtil {
    /**
     * Date To String
     */
    public static String dateToString(String pattern, Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return  simpleDateFormat.format(date);
    }
    /**
     * String To Date
     */
    public static Date StringToDate(String pattern, String date){
        Date d =null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            d= simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }

    public static void main(String[] args) {
       Date date= DateUtil.StringToDate("yyyy-MM-dd HH:mm:ss", "2020-11-19 10:23:59");
        System.out.println(date);//Thu Nov 19 10:23:59 CST 2020
        String str = DateUtil.dateToString("yyyy-MM-dd HH:mm:ss",date);
        System.out.println(str);//2020-11-19 10:23:59

    }
}

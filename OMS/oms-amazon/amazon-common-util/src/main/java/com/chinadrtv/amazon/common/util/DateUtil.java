package com.chinadrtv.amazon.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    /**
     * yyyy-MM-dd hh:mm:ss  输出格式: 2006-01-01 01:00:00 
     * yyyy-MM-dd HH:mm:ss  输出格式: 2006-01-01 13:00:00
     * yyyyMMddhhmmss       输出格式: 20060101000000  
     */

    public static Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }

    public static String getFormatedDate(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    public static String getFormatedDate(String pattern) {
        Date date = getCurrentDate();
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }
    /**
     * 获取传入的时间和域对应的值
     * 
     * @param date
     *            传入的时间
     * @param field
     *            所要获取值的域
     * @return
     */
    public static int obtainDateFieldValue(Date date, int field) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(field);
    }
    /**
     * 返回指定日期相应位移后的日期
     * 
     * @param date
     *            参考日期
     * @param field
     *            位移单位，见 {@link Calendar}
     * @param offset
     *            位移数量，正数表示之后的时间，负数表示之前的时间
     * @return 位移后的日期
     */
    public static Date offsetDate(Date date, int field, int offset){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(field, offset);
        return cal.getTime();
    }
    

    public static void main(String[] args) {
        Date date = offsetDate(new Date(),Calendar.DATE,-30);
        Date d = new Date();
        System.out.println(d.compareTo(date));
        System.out.println(getFormatedDate(date,"yyyy-MM-dd HH:mm:ss"));

    }
}

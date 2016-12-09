package com.chinadrtv.erp.sales.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-6-5
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class DateUtil {

	private static Long dayMilliseconds = 24 * 60 * 60 * 1000L;
    
    /**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

	/**
	 * 日期格式(yyyyMMdd)
	 */
	public static final SimpleDateFormat FORMAT_DATE_SIMPLE = new SimpleDateFormat("yyyyMMdd");

	/**
	 * 时间格式(HHmmss)
	 */
	public static final SimpleDateFormat FORMAT_TIME_SIMPLE = new SimpleDateFormat("HHmmss");

	/**
	 * 小时分钟格式(HHmm)
	 */
	public static final SimpleDateFormat FORMAT_HOUR_MINUTE = new SimpleDateFormat("HHmm");

	/**
	 * 时间格式(HH:mm:ss)
	 */
	public static final SimpleDateFormat FORMAT_TIME_COLON = new SimpleDateFormat("HH:mm:ss");

	/**
	 * 日期格式(yyyy-MM-dd)
	 */
	public static final SimpleDateFormat FORMAT_DATE_LINE = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 日期时间格式(yyyyMMddHHmmss)
	 */
	public static final SimpleDateFormat FORMAT_DATETIME_SIMPLE_FULL = new SimpleDateFormat("yyyyMMddHHmmss");

	/**
	 * 日期格式(yyyy-MM-dd HH:mm:ss)
	 */
	public static final SimpleDateFormat FORMAT_DATETIME_FULL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 日期格式:完整日期(yyyy-MM-dd HH:mm:ss,S)
	 */
	public static final SimpleDateFormat DATE_TIME_FORMAT_EXAC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,S");

    /**
     * 日期格式:完整日期(yyyy-MM-dd HH:mm:ss,S)
     */
    public static final SimpleDateFormat DATE_TIME_FORMAT_EXAC_SSS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	
	/**
	 * 日期格式:完整日期(yyyy-MM-dd 00:00:00)
	 */
	public static final SimpleDateFormat DATE_TIME_FORMAT_ZERO = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
	
	/**
	 * 日期格式:完整日期(yyyy-MM-dd 23:59:59)
	 */
	public static final SimpleDateFormat DATE_TIME_FORMAT_LAST = new SimpleDateFormat("yyyy-MM-dd 23:59:59");

	/**
	 * 日期格式:(yyyy)
	 */
	public static final SimpleDateFormat FORMAT_DATE_YEAR = new SimpleDateFormat("yyyy");

    private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>();

    /**
	 * 计算时间差
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static Long calcDiffJustDays(Date beginDate, Date endDate) {
		if (beginDate == null || endDate == null) {
			logger.error("Begin or End time can not be null");
			throw new RuntimeException("Begin or end time can not be null");
		}
		return (beginDate.getTime() - endDate.getTime()) / (dayMilliseconds);
	}
	
	/**
	 * 将字符串转成日期
	 * 
	 * @param value
	 *            日期字符串
	 * @param format
	 *            格式
	 * @return
	 */
	public static Date parseDate(String value, DateFormat format) {
		if (! StringUtils.isNotBlank(value))
			return null;
		try {
			return format.parse(value);
		} catch (ParseException e) {
			logger.error("Can not be parsed to date. ", e);
		}
		return null;
	}

	/**
	 * 将字符串转成日期
	 * 
	 * @param value
	 *            日期字符串
	 * @param pattern
	 *            格式字符串
	 * @return
	 */
	public static Date parseDate(String value, String pattern) {
		if (value == null) return null;
		try {
			return new SimpleDateFormat(pattern).parse(value);
		} catch (ParseException e) {
			logger.error("Can not be parsed to date. ", e);
		}
		return null;
	}
	
	/**
	 * 将字符串转成日期
	 * 
	 * @param value
	 *            日期字符串
	 * @param parsePatterns
	 *            格式字符串
	 * @return
	 */
	public static Date parseDate(String value, String... parsePatterns) {
		try {
			return org.apache.commons.lang.time.DateUtils.parseDate(value, parsePatterns);
		} catch (ParseException e) {
			logger.error("Can not be parsed to date. ", e);
		}
		return null;
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            格式
	 * @return
	 */
	public static String formatDate(Date date, DateFormat format) {
		if (date == null) return null;
		try {
			return format.format(date);
		} catch (Exception e) {
			logger.error("Can not be parsed to date. ", e);
		}
		return null;
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            格式
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		if (date == null) return null;
		try {
			return new SimpleDateFormat(pattern).format(date);
		} catch (Exception e) {
			logger.error("Can not be parsed to date. ", e);
		}
		return null;
	}

	/**
	 * Returns current date.
	 * 
	 * @return
	 */
	public static Date currentDate() {
		return new Date();
	}
	
	/**
	 * Returns the number of milliseconds since January 1, 1970, 00:00:00 GMT
	 * represented by current date.
	 * 
	 * @return
	 */
	public static long currentTime() {
		return currentDate().getTime();
	}

	/**
	 * 以默认(yyyy-MM-dd HH:mm:ss)格式得到当前时间字符串
	 * 
	 * @return
	 */
	public static String formatCurrentDate() {
		return FORMAT_DATETIME_FULL.format(new Date());
	}

	/**
	 * 以固定格式得到当前时间字符串
	 * 
	 * @param format
	 *            格式
	 * @return
	 */
	public static String formatCurrentDate(DateFormat format) {
		return format.format(new Date());
	}

	/**
	 * 以固定格式化字符串得到当前时间字符串
	 * 
	 * @param format
	 *            格式
	 * @return
	 */
	public static String formatCurrentDate(String pattern) {
		return new SimpleDateFormat(pattern).format(new Date());
	}

	/**
	 * 比较两个时间
	 * 
	 * @param source
	 * @param target
	 * @return 0: source=target, 1: source>target, -1: source<target
	 */
	public static int compareDate(Date source, Date target) {
		return source.compareTo(target);
	}

	/**
	 * 以同种格式比较两个时间字符串
	 * 
	 * @param source
	 * @param target
	 * @param format
	 * @return 0: source=target, 1: source>target, -1: source<target
	 */
	public static int compareDate(String source, String target, DateFormat format) {
		try {
			return format.parse(source).compareTo(format.parse(target));
		} catch (ParseException e) {
			throw new RuntimeException("Exception when compare two date string. ", e);
		}
	}

	/**
	 * 以同种格式比较两个时间字符串
	 * 
	 * @param source
	 * @param target
	 * @param pattern
	 * @return 0: source=target, 1: source>target, -1: source<target
	 */
	public static int compareDate(String source, String target, String pattern) {
		DateFormat formate = new SimpleDateFormat(pattern);
		try {
			return formate.parse(source).compareTo(formate.parse(target));
		} catch (ParseException e) {
			throw new RuntimeException("Exception when compare two date string. ", e);
		}
	}

	/**
	 * 以不同格式比较两个时间字符串
	 * 
	 * @param source
	 * @param sourceFormat
	 * @param source
	 * @param targetFormat
	 * @return 0: source=target, 1: source>target, -1: source<target
	 */
	public static int compareDate(String source, DateFormat sourceFormat,
			String target, DateFormat targetFormat) {
		try {
			return sourceFormat.parse(source).compareTo(targetFormat.parse(target));
		} catch (ParseException e) {
			throw new RuntimeException("Exception when compare two date string. ", e);
		}
	}

	/**
	 * 以不同格式比较两个时间字符串
	 * 
	 * @param source
	 * @param sourcePattern
	 * @param source
	 * @param targetPattern
	 * @return 0: source=target, 1: source>target, -1: source<target
	 */
	public static int compareDate(String source, String sourcePattern,
			String target, String targetPattern) {
		try {
			return new SimpleDateFormat(sourcePattern).parse(source).compareTo(
					new SimpleDateFormat(targetPattern).parse(target));
		} catch (ParseException e) {
			throw new RuntimeException("Exception when compare two date string. ", e);
		}
	}
	
	/**
     * <p>Checks if two date objects represent the same instant in time.</p>
     *
     * <p>This method compares the long millisecond time of the two objects.</p>
     * 
     * @param date1  the first date, not altered, not null
     * @param date2  the second date, not altered, not null
     * @return true if they represent the same millisecond instant
     * @throws IllegalArgumentException if either date is <code>null</code>
     * @since 2.1
     */
    public static boolean isSameInstant(Date date1, Date date2) {
        return org.apache.commons.lang.time.DateUtils.isSameInstant(date1, date2);
    }

    /**
     * <p>Checks if two calendar objects represent the same instant in time.</p>
     *
     * <p>This method compares the long millisecond time of the two objects.</p>
     * 
     * @param cal1  the first calendar, not altered, not null
     * @param cal2  the second calendar, not altered, not null
     * @return true if they represent the same millisecond instant
     * @throws IllegalArgumentException if either date is <code>null</code>
     * @since 2.1
     */
    public static boolean isSameInstant(Calendar cal1, Calendar cal2) {
        return org.apache.commons.lang.time.DateUtils.isSameInstant(cal1, cal2);
    }
    
    /**
     * <p>Checks if two date objects are on the same day ignoring time.</p>
     *
     * <p>28 Mar 2002 13:45 and 28 Mar 2002 06:01 would return true.
     * 28 Mar 2002 13:45 and 12 Mar 2002 13:45 would return false.
     * </p>
     * 
     * @param date1  the first date, not altered, not null
     * @param date2  the second date, not altered, not null
     * @return true if they represent the same day
     * @throws IllegalArgumentException if either date is <code>null</code>
     * @since 2.1
     */
    public static boolean isSameDay(Date date1, Date date2) {
        return org.apache.commons.lang.time.DateUtils.isSameDay(date1, date2);
    }

    /**
     * <p>Checks if two calendar objects are on the same day ignoring time.</p>
     *
     * <p>28 Mar 2002 13:45 and 28 Mar 2002 06:01 would return true.
     * 28 Mar 2002 13:45 and 12 Mar 2002 13:45 would return false.
     * </p>
     * 
     * @param cal1  the first calendar, not altered, not null
     * @param cal2  the second calendar, not altered, not null
     * @return true if they represent the same day
     * @throws IllegalArgumentException if either calendar is <code>null</code>
     * @since 2.1
     */
    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        return org.apache.commons.lang.time.DateUtils.isSameDay(cal1, cal2);
    }

	/**
	 * 比较时间和时间字符串是否相等
	 * 
	 * @param source
	 * @param target
	 * @param format
	 * @return
	 */
	public static boolean isEqual(Date source, String target, DateFormat format) {
		return StringUtils.equals(format.format(source), target);
	}

	/**
	 * 比较时间和时间字符串是否相等
	 * 
	 * @param source
	 * @param target
	 * @param pattern
	 * @return
	 */
	public static boolean isEqual(Date source, String target, String pattern) {
		return StringUtils.equals(new SimpleDateFormat(pattern).format(source), target);
	}
	
	/**
	 * 增加或减少天数
	 * 
	 * @param date
	 *            日期
	 * @param amount
	 *            递增量
	 * @return
	 */
	public static Date addDay(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, amount);
		return cal.getTime();
	}
	
	/**
	 * 增加或减少日历
	 * 
	 * @param date
	 *            日期
	 * @param calendarField
	 *            Calendar中的常量
	 * @param amount
	 *            递增量
	 * @return
	 */
	public static Date addCalendar(Date date, int calendarField, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.setTime(date);
		cal.add(calendarField, amount);
		return cal.getTime();
	}
    public static DateFormat getDateFormat(String date_format)
    {
        DateFormat df = threadLocal.get();
        if(df==null){
            df = new SimpleDateFormat(date_format);
            threadLocal.set(df);
        }
        return df;
    }

    public static Date parse(String strDate,String date_format) throws ParseException {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(date_format);
        return simpleDateFormat.parse(strDate);
    }

    public static String date2String(Date date, String format) {
        if (date == null) return null;
        try {
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat(format);
            return simpleDateFormat.format(date);
        } catch (Exception e) {
            logger.error("Can not be parsed to date. ", e);
        }
        return null;
    }
}

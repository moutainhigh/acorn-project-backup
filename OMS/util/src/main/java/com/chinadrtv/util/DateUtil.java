package com.chinadrtv.util;

import java.text.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Calendar;

import org.apache.commons.lang.StringUtils;

public class DateUtil {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(DateUtil.class);

	int year;

	int month;

	int day;

	int hour;

	int minute;

	int second;

	String tempMonth;

	GregorianCalendar gc;

	Calendar calendar = new GregorianCalendar();

	public DateUtil() {

	}

	/**
	 * 格式化日期显示格式
	 * 
	 * @param sdate
	 *            原始日期格式
	 * @param format
	 *            格式化后日期格式
	 * @return 格式化后的日期显示
	 */
	public static String dateFormat(String sdate, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		java.sql.Date date = java.sql.Date.valueOf(sdate);
		String dateString = formatter.format(date);
		return dateString;
	}

	/**
	 * 格式化日期显示格式yyyy-MM-dd
	 * 
	 * @param sdate
	 *            原始日期格式
	 * @return yyyy-MM-dd格式化后的日期显示
	 */
	public static String dateFormat(String sdate) {
		return dateFormat(sdate, "yyyy-MM-dd");
	}

	/**
	 * 取得当前日期和时间
	 */
	public void getCurrentDate() {
		year = calendar.get(GregorianCalendar.YEAR);
		month = (calendar.get(GregorianCalendar.MONTH) + 1);
		day = calendar.get(GregorianCalendar.DAY_OF_MONTH);
		gc = new GregorianCalendar(year, month, day);

		hour = calendar.get(GregorianCalendar.HOUR_OF_DAY);
		minute = (calendar.get(GregorianCalendar.MINUTE));
		second = (calendar.get(GregorianCalendar.SECOND));
	}

	/**
	 * 获得当前时间，返回yyyy-MM-dd hh:mm:ss
	 * 
	 * @return java.lang.String
	 */
	public String getDateTime() {
		getCurrentDate();
		return Integer.toString(this.year)
				+ "-"
				+ (Integer.toString(this.month).length() < 2 ? "0"
						+ Integer.toString(this.month) : Integer
						.toString(this.month))
				+ "-"
				+ (Integer.toString(this.day).length() < 2 ? "0"
						+ Integer.toString(this.day) : Integer
						.toString(this.day))
				+ " "
				+ (Integer.toString(this.hour).length() < 2 ? "0"
						+ Integer.toString(this.hour) : Integer
						.toString(this.hour))
				+ ":"
				+ (Integer.toString(this.minute).length() < 2 ? "0"
						+ Integer.toString(this.minute) : Integer
						.toString(this.minute))
				+ ":"
				+ (Integer.toString(this.second).length() < 2 ? "0"
						+ Integer.toString(this.second) : Integer
						.toString(this.second));
	}

	/**
	 * 得到每月的最后日期
	 * 
	 * @param theMonth月份
	 * @return int
	 */
	public int daysInMonth(int theMonth) {
		int[] daysInMonths = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		gc = new GregorianCalendar();
		daysInMonths[1] += gc.isLeapYear(year) ? 1 : 0;
		return daysInMonths[theMonth - 1];
	}

	/**
	 * ***输入yyyy/mm字符串，得到该月的最大日期
	 * 
	 * @param yearmonth
	 *            return int
	 */
	public int lastDayInMonth(String yMonth) {
		String thisDate = yMonth;
		// thisDate = dateFormat(thisDate,"yyyy-MM");
		month = Integer.parseInt(thisDate.substring(5, 7));
		year = Integer.parseInt(thisDate.substring(0, 4));
		gc = new GregorianCalendar(year, month, 1);
		int lastday = daysInMonth(month);
		return lastday;
	}

	/**
	 * 上月
	 * 
	 * @return int
	 */
	public int reduceMonth() {
		// 上月
		if (month == 1) {
			month = 12;
			year--;
		} else {
			month--;
		}
		return month;
	}

	/**
	 * 下月
	 * 
	 * @return int
	 */
	public int nextMonth() {
		// 下月
		if (month == 12) {
			month = 1;
			year++;
		} else {
			month++;
		}
		return month;
	}

	/**
	 * 已知年月yyyy-MM，获得下月yyyy-MM
	 * 
	 * @return int
	 */
	public String getNextMonth(String thisMonth) {
		// 下月
		int intYear = Integer.parseInt(thisMonth.substring(0, 4));
		int intMonth = Integer.parseInt(thisMonth.substring(thisMonth
				.indexOf("-") + 1));
		if (intMonth == 12) {
			intMonth = 1;
			intYear++;
		} else {
			intMonth++;
		}
		if (intMonth < 10)
			tempMonth = "0" + Integer.toString(intMonth);
		else
			tempMonth = Integer.toString(intMonth);
		return intYear + "-" + tempMonth;
	}

	/**
	 * 已知年月yyyy-MM，获得上月yyyy-MM
	 * 
	 * @return int
	 */
	public String getPrevMonth(String thisMonth) {
		// 下月
		int intYear = Integer.parseInt(thisMonth.substring(0, 4));
		int intMonth = Integer.parseInt(thisMonth.substring(thisMonth
				.indexOf("-") + 1));
		if (intMonth == 1) {
			intMonth = 12;
			intYear--;
		} else {
			intMonth--;
		}
		if (intMonth < 10)
			tempMonth = "0" + Integer.toString(intMonth);
		else
			tempMonth = Integer.toString(intMonth);
		return intYear + "-" + tempMonth;
	}

	/**
	 * 昨天
	 * 
	 * @return int
	 * 
	 */
	public int reduceDay() {
		// 昨天
		if (day == 1) {
			month = reduceMonth();
			day = daysInMonth(month);
		} else {
			day--;
		}
		return day;
	}

	/**
	 * 得到昨天的日期，返回格式如yyyy-mm-dd
	 * 
	 * @return java.lang.String
	 */
	public String getYestoday() {
		getCurrentDate();
		day = reduceDay();
		String mon = "";
		String day1 = "";
		if (day < 10)
			day1 = "0" + Integer.toString(day);
		else
			day1 = Integer.toString(day);
		;
		if (month < 10)
			mon = "0" + Integer.toString(month);
		else
			mon = Integer.toString(month);
		return Integer.toString(year) + "-" + mon + "-" + day1;
	}

	/**
	 * 得到去年 返回格式如yyyy
	 * 
	 * @return java.lang.String
	 */
	public String getLastYear() {
		getCurrentDate();
		year--;
		return Integer.toString(year);
	}

	/**
	 * 获得本月，返回格式如yyyy-mm
	 * 
	 * @return java.lang.String
	 */
	public String getCurMonth() {
		getCurrentDate();
		if (month < 10)
			tempMonth = "0" + Integer.toString(month);
		else
			tempMonth = Integer.toString(month);
		return Integer.toString(year) + "-" + tempMonth;
	}

	/**
	 * 获得今年，返回格式如yyyy
	 * 
	 * @return java.lang.String
	 */
	public String getCurYear() {
		getCurrentDate();
		return Integer.toString(year);
	}

	/**
	 * 返回上月的最大日期 格式为yyyy-mm-dd
	 * 
	 * @return java.lang.String
	 */
	public String getPriorMonthLastDay() {
		getCurrentDate();
		month = reduceMonth();
		day = daysInMonth(month);
		return dateFormat(
				Integer.toString(year) + "-" + Integer.toString(month) + "-"
						+ Integer.toString(day), "yyyy-MM-dd");
	}

	/**
	 * 返回上月的最小日期 格式为yyyy-mm-dd
	 * 
	 * @return java.lang.String
	 */
	public String getPriorMonthFirstDay() {
		getCurrentDate();
		month = reduceMonth();
		return dateFormat(
				Integer.toString(year) + "-" + Integer.toString(month) + "-01",
				"yyyy-MM-dd");
	}

	/**
	 * 返回本月的最大日期 格式为yyyy-mm-dd
	 * 
	 * @return java.lang.String
	 */
	public String getThisMonthLastDay() {
		getCurrentDate();
		day = daysInMonth(month);
		return dateFormat(
				Integer.toString(year) + "-" + Integer.toString(month) + "-"
						+ Integer.toString(day), "yyyy-MM-dd");
	}

	/**
	 * 返回某月的最小日期 格式为yyyy-mm-dd
	 * 
	 * @return java.lang.String
	 */
	public String getMonthFirstDay(String sdate) {
		return dateFormat(sdate.substring(0, 7) + "-01", "yyyy-MM-dd");
	}

	/**
	 * 返回指定日期在该星期内第几天
	 * 
	 * @return java.lang.int
	 */
	public int getWeekDay(Date d) {
		int a = 0;
		calendar.setTime(d);
		if (calendar.get(Calendar.DAY_OF_WEEK) == 2)
			a = 1;// 星期一
		else if (calendar.get(Calendar.DAY_OF_WEEK) == 3)
			a = 2;
		else if (calendar.get(Calendar.DAY_OF_WEEK) == 4)
			a = 3;
		else if (calendar.get(Calendar.DAY_OF_WEEK) == 5)
			a = 4;
		else if (calendar.get(Calendar.DAY_OF_WEEK) == 6)
			a = 5;
		else if (calendar.get(Calendar.DAY_OF_WEEK) == 7)
			a = 6;
		else if (calendar.get(Calendar.DAY_OF_WEEK) == 1)
			a = 7;
		return a;
	}

	/**
	 * 将字符串(yyyy-mm-dd)转换成日期
	 * 
	 * @param sdate
	 *            原始日期格式
	 * @param format
	 *            格式化后日期格式
	 * @return 格式化后的日期显示
	 */
	public static Date stringToDate(String sdate) {
		java.sql.Date dDate = java.sql.Date.valueOf(sdate);
		return dDate;
	}

	/*
	 * 将date转换成字符串yyyy-mm-dd
	 */
	public static String dateToString(Date dDate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(dDate);
	}

	public static String date2FormattedString(Date dateTime, String dateFormat)
			throws SQLException {
		SimpleDateFormat customDateFormat = new SimpleDateFormat(dateFormat);
		return customDateFormat.format(dateTime);
	}

	public static Timestamp string2SqlTimestamp(String dateTime,
			String dateFormat) throws SQLException {
		try {
			SimpleDateFormat customDateFormat = new SimpleDateFormat(dateFormat);
			return new Timestamp(customDateFormat.parse(dateTime).getTime());
		} catch (ParseException e) {
			throw new SQLException(
					"string2SqlTimestamp: Input date format not correct! "
							+ e.getMessage());
		}
	}

	public static Date string2Date(String dateTime, String dateFormat)
			throws SQLException {
		try {
			SimpleDateFormat customDateFormat = new SimpleDateFormat(dateFormat);
			return customDateFormat.parse(dateTime);
		} catch (ParseException e) {
			throw new SQLException(
					"string2SqlTimestamp: Input date format not correct! "
							+ e.getMessage());
		}
	}

	/**
	 * 为一个日期加上指定天数
	 * 
	 * @param aDate
	 *            yyyyMMdd格式字串
	 * @param amount
	 *            天数
	 * @return
	 */
	public static final String addDays(Date aDate, int amount) {
		try {
			return dateToString(addTime(aDate, Calendar.DAY_OF_YEAR, amount));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 为一个日期加上指定天数
	 * 
	 * @param sDate
	 *            yyyyMMdd格式字串
	 * @param amount
	 *            天数
	 * @return
	 * @throws java.text.ParseException
	 */
	public static final String addDays(String sDate, int amount) throws ParseException {
		SimpleDateFormat customDateFormat = null;
		if(StringUtils.isNotBlank(sDate) && sDate.length() > 10) {
			customDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		} else {
			customDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		}
		Date aDate = customDateFormat.parse(sDate);
		return addDays(aDate, amount);
	}

	public static final Date addDays2Date(Date aDate, int amount) {
		try{
			return addTime(aDate, Calendar.DAY_OF_YEAR, amount);
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static final Date addTime(Date aDate, int timeType, int amount) {
		if (aDate == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(aDate);
		cal.add(timeType, amount);
		return cal.getTime();
	}
	
	
	/**
	 * 
	 * 获取两个日期之间的天数,
	 * 
	 * @param sdt 开始日期
	 * @param edt 结束日期
	 * @param format 日期的格式,默认格式为:yyyy-MM-dd HH:mm:ss,当format为null时取默认值
	 * @return
	 * @author haoleitao
	 * @date 2013-3-28 下午8:19:59
	 */
	public static final long getDateDeviations(String sdt, String edt,
			String format) {
		if (format == null)
			format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat formatt = new SimpleDateFormat(format);
		Date sdate = null, edate = null;
		try {
			sdate = formatt.parse(sdt);
			edate = formatt.parse(edt);
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		calendar1.setTime(sdate);
		calendar2.setTime(edate);
		long milliseconds1 = calendar1.getTimeInMillis();
		long milliseconds2 = calendar2.getTimeInMillis();
		long diff = milliseconds2 - milliseconds1;
//		 long diffSeconds = diff / 1000;
//		 long diffMinutes = diff / (60 * 1000);
//		 long diffHours = diff / (60 * 60 * 1000);
		long diffDays = diff / (24 * 60 * 60 * 1000);
		return diffDays;
	}
	
	/**
	 * getEndOfDate
	 * @param endDate 2013-3-28 下午8:19:59
	 * @return 2013-3-29
	 * Date
	 * @throws java.sql.SQLException
	 * @throws java.text.ParseException
	 * @exception 
	 * @author yangfei
	 * @since  1.0.0
	 */
	public static Date getEndOfDate(Date endDate) throws ParseException {
		String endDateString = dateToString(endDate);
		SimpleDateFormat customDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		endDate = customDateFormat.parse(endDateString);
		endDate = addDays2Date(endDate, 1);
		return endDate;
	}
	
	/**
	 * checkDateRange 判断是否超过最大天数限制
	 * @param startDateString 2013-3-28 8:19:59
	 * @param endDateString   2013-3-28 18:19:59
	 * @param maxRange 最大天数
	 * @return
	 * @throws java.text.ParseException
	 * boolean
	 * @exception 
	 * @since  1.0.0
	 */
	public static boolean checkDateRange(String startDateString, String endDateString, long maxRange) throws ParseException {
		SimpleDateFormat customDateFormat = null;
		if(StringUtils.isNotBlank(startDateString) && startDateString.length() > 10) {
			customDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		} else {
			customDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		}

		boolean isExceed = true;
		Date endDate, startDate = null;
		long diff = 0;
		if(StringUtils.isNotBlank(startDateString) && StringUtils.isBlank(endDateString)) {
			startDate = customDateFormat.parse(startDateString);
			diff = System.currentTimeMillis() - startDate.getTime();
		} else if(StringUtils.isNotBlank(startDateString) && StringUtils.isNotBlank(endDateString)) {
			startDate = customDateFormat.parse(startDateString);
			endDate = customDateFormat.parse(endDateString);
			diff = endDate.getTime() - startDate.getTime();
		} else if(StringUtils.isBlank(startDateString) && StringUtils.isNotBlank(endDateString)) {
			endDate = customDateFormat.parse(endDateString);
			diff = endDate.getTime() - System.currentTimeMillis();
		}
		if(diff < maxRange) {
			isExceed = false;
		}
		return isExceed;
	}
	
	public static String  getCurDT(){
		SimpleDateFormat customDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return customDateFormat.format(new Date()).split(" ")[0];
	}
	
	public static String getCurTM(){
		SimpleDateFormat customDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return customDateFormat.format(new Date()).split(" ")[1];
	}
	
	
	public static void main(String[] args) throws ParseException {
		long maxTimeRange = 180 * 24 * 3600 * 1000L;
		String sDate1 = "2013-3-28 18:19:59", eDate1 = "2013-4-28 18:19:59";
		String sDate2 = "2013-3-28 18:19:59", eDate2 = null;
		String sDate3 = null, eDate3 = "2013-8-28 18:19:59";
		String sDate4 = "2013-3-28 18:19:59", eDate4 = "2014-4-28 18:19:59";
		String sDate5 = "2012-3-28 18:19:59", eDate5 = null;
		String sDate6 = null, eDate6 = "2013-12-28 18:19:59";
		if (checkDateRange(sDate1, eDate1, maxTimeRange)) {
			System.out.println("1 time out of range");
		} else {
			System.out.println("1 In range");
		}
		if (checkDateRange(sDate2, eDate2, maxTimeRange)) {
			System.out.println("2 time out of range");
		} else {
			System.out.println("2 In range");
		}
		if (checkDateRange(sDate3, eDate3, maxTimeRange)) {
			System.out.println("3 time out of range");
		} else {
			System.out.println("3 In range");
		}
		if (checkDateRange(sDate4, eDate4, maxTimeRange)) {
			System.out.println("4 time out of range");
		} else {
			System.out.println("4 In range");
		}
		if (checkDateRange(sDate5, eDate5, maxTimeRange)) {
			System.out.println("5 time out of range");
		} else {
			System.out.println("5 In range");
		}
		if (checkDateRange(sDate6, eDate6, maxTimeRange)) {
			System.out.println("6 time out of range");
		} else {
			System.out.println("6 In range");
		}
		
		System.out.println(DateUtil.getCurDT());
		System.out.println(DateUtil.getCurTM());
	}
}

package com.chinadrtv.erp.marketing.util;

import java.io.Serializable;

/**
 * Qurze job 设置bean
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-1-25 下午2:13:49 
 *
 */
public class JobCronSet implements Serializable {
	
	private String jobName;

	private String group;

	private String topic;// 主题

	private String message;// 提醒内容
	
	private String receiverNum;
	
	private String receiverName;
	
	private String receiverType;
	
	private String timeStr;//定时时间

	private String startDate;// 开始日期

	private String endDate;// 结束日期
	// private String startTime;//开始时间
	// private String endTime;//结束时间

	private boolean allDay;// 是否全天事件

	private String frequency = "0";// 重复频率

	//按小时
	
	private String everyHourNum;// 每everyDayNum天
	
	// 按天
	private boolean everyDay;// 每*天

	private String everyDayNum;// 每everyDayNum天

	// 按周
	private String weekNum;// 重复间隔为weekNum周后的

	private boolean sunday;// 星期日

	private boolean monday;// 星期一

	private boolean tuesday;// 星期二

	private boolean wednesday;// 星期三

	private boolean thursday;// 星期四

	private boolean friday;// 星期五

	private boolean saturday;// 星期六

	// 按月
	private String everyMonth = "1";// 每*个月

	private String everyMonthNum;// 每everyMonthNum月

	private String everyMonthNumDay;// 每几个月的第everyMonthNumDay天

	private String everyMonthNum2;// 每everyMonthNum2月

	private String theDay;// 每*个月的第theDay

	private String theWeekday;// 每*个月的第theDay的周几theWeekday

	// 按年
	private String everyYear = "1";// 每年

	private String theMonthOfYear;// 每年的theMonthOfYear月

	private String theDayOfMonth;// 每年几月的第theDayOfMonth天

	private String theMonthOfYear2;// 每年的theMonthOfYear月

	private String theDayOfYear;// 每年的第theDayOfYear星期几

	private String theWeekdayOfYear;// 每年的第theDayOfYear星期theWeekdayOfYear

	// 范围
	private String dateOfStart;

	private String never = "2";

	private String endDay;

	// 方式
	private boolean menhu = true;

	private boolean sms;

	private boolean email;

	private String flag = "";// 0year;1day

	private int owner = 0;
	
	
	/**
	 * @return the owner
	 */
	public int getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(int owner) {
		this.owner = owner;
	}

	/**
	 * @return the receiverNum
	 */
	public String getReceiverNum() {
		return receiverNum;
	}

	/**
	 * @param receiverNum the receiverNum to set
	 */
	public void setReceiverNum(String receiverNum) {
		this.receiverNum = receiverNum;
	}

	/**
	 * @return the receiverName
	 */
	public String getReceiverName() {
		return receiverName;
	}

	/**
	 * @param receiverName the receiverName to set
	 */
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	/**
	 * @return the timeStr
	 */
	public String getTimeStr() {
		return timeStr;
	}

	/**
	 * @param timeStr the timeStr to set
	 */
	public void setTimeStr(String timeStr) {
		this.timeStr = timeStr;
	}

	/**
	 * @return the flag
	 */
	public String getFlag() {
		return flag;
	}

	/**
	 * @param flag
	 *            the flag to set
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * @return the group
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * @param group
	 *            the group to set
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 * @return the jobName
	 */
	public String getJobName() {
		return jobName;
	}

	/**
	 * @param jobName
	 *            the jobName to set
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	/**
	 * @return the email
	 */
	public boolean isEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(boolean email) {
		this.email = email;
	}

	/**
	 * @return the menhu
	 */
	public boolean isMenhu() {
		return menhu;
	}

	/**
	 * @param menhu
	 *            the menhu to set
	 */
	public void setMenhu(boolean menhu) {
		this.menhu = menhu;
	}

	/**
	 * @return the sms
	 */
	public boolean isSms() {
		return sms;
	}

	/**
	 * @param sms
	 *            the sms to set
	 */
	public void setSms(boolean sms) {
		this.sms = sms;
	}

	/**
	 * @return the allDay
	 */
	public boolean isAllDay() {
		return allDay;
	}

	/**
	 * @param allDay
	 *            the allDay to set
	 */
	public void setAllDay(boolean allDay) {
		this.allDay = allDay;
	}

	/**
	 * @return the dateOfStart
	 */
	public String getDateOfStart() {
		return dateOfStart;
	}

	/**
	 * @param dateOfStart
	 *            the dateOfStart to set
	 */
	public void setDateOfStart(String dateOfStart) {
		this.dateOfStart = dateOfStart;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the endDay
	 */
	public String getEndDay() {
		return endDay;
	}

	/**
	 * @param endDay
	 *            the endDay to set
	 */
	public void setEndDay(String endDay) {
		this.endDay = endDay;
	}

	// /**
	// * @return the endDayOfFlag
	// */
	// public boolean isEndDayOfFlag() {
	// return endDayOfFlag;
	// }
	// /**
	// * @param endDayOfFlag the endDayOfFlag to set
	// */
	// public void setEndDayOfFlag(boolean endDayOfFlag) {
	// this.endDayOfFlag = endDayOfFlag;
	// }
	// /**
	// * @return the endTime
	// */
	// public String getEndTime() {
	// return endTime;
	// }
	// /**
	// * @param endTime the endTime to set
	// */
	// public void setEndTime(String endTime) {
	// this.endTime = endTime;
	// }
	/**
	 * @return the everyDay
	 */
	public boolean isEveryDay() {
		return everyDay;
	}

	/**
	 * @param everyDay
	 *            the everyDay to set
	 */
	public void setEveryDay(boolean everyDay) {
		this.everyDay = everyDay;
	}

	/**
	 * @return the everyDayNum
	 */
	public String getEveryDayNum() {
		return everyDayNum;
	}

	/**
	 * @param everyDayNum
	 *            the everyDayNum to set
	 */
	public void setEveryDayNum(String everyDayNum) {
		this.everyDayNum = everyDayNum;
	}

	/**
	 * @return the everyMonthNum
	 */
	public String getEveryMonthNum() {
		return everyMonthNum;
	}

	/**
	 * @param everyMonthNum
	 *            the everyMonthNum to set
	 */
	public void setEveryMonthNum(String everyMonthNum) {
		this.everyMonthNum = everyMonthNum;
	}

	/**
	 * @return the everyMonthNum2
	 */
	public String getEveryMonthNum2() {
		return everyMonthNum2;
	}

	/**
	 * @param everyMonthNum2
	 *            the everyMonthNum2 to set
	 */
	public void setEveryMonthNum2(String everyMonthNum2) {
		this.everyMonthNum2 = everyMonthNum2;
	}

	/**
	 * @return the everyMonthNumDay
	 */
	public String getEveryMonthNumDay() {
		return everyMonthNumDay;
	}

	/**
	 * @param everyMonthNumDay
	 *            the everyMonthNumDay to set
	 */
	public void setEveryMonthNumDay(String everyMonthNumDay) {
		this.everyMonthNumDay = everyMonthNumDay;
	}

	// /**
	// * @return the everyMonth2
	// */
	// public boolean isEveryMonth2() {
	// return everyMonth2;
	// }
	// /**
	// * @param everyMonth2 the everyMonth2 to set
	// */
	// public void setEveryMonth2(boolean everyMonth2) {
	// this.everyMonth2 = everyMonth2;
	// }
	// /**
	// * @return the everyYear2
	// */
	// public boolean isEveryYear2() {
	// return everyYear2;
	// }
	// /**
	// * @param everyYear2 the everyYear2 to set
	// */
	// public void setEveryYear2(boolean everyYear2) {
	// this.everyYear2 = everyYear2;
	// }

	
	/**
	 * @return the frequency
	 */
	public String getFrequency() {
		return frequency;
	}

	/**
	 * @param frequency
	 *            the frequency to set
	 */
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	/**
	 * @return the friday
	 */
	public boolean isFriday() {
		return friday;
	}

	/**
	 * @param friday
	 *            the friday to set
	 */
	public void setFriday(boolean friday) {
		this.friday = friday;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the monday
	 */
	public boolean isMonday() {
		return monday;
	}

	/**
	 * @param monday
	 *            the monday to set
	 */
	public void setMonday(boolean monday) {
		this.monday = monday;
	}

	/**
	 * @return the never
	 */
	public String getNever() {
		return never;
	}

	/**
	 * @param never
	 *            the never to set
	 */
	public void setNever(String never) {
		this.never = never;
	}

	/**
	 * @return the saturday
	 */
	public boolean isSaturday() {
		return saturday;
	}

	/**
	 * @param saturday
	 *            the saturday to set
	 */
	public void setSaturday(boolean saturday) {
		this.saturday = saturday;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	// /**
	// * @return the startTime
	// */
	// public String getStartTime() {
	// return startTime;
	// }
	// /**
	// * @param startTime the startTime to set
	// */
	// public void setStartTime(String startTime) {
	// this.startTime = startTime;
	// }
	/**
	 * @return the sunday
	 */
	public boolean isSunday() {
		return sunday;
	}

	/**
	 * @param sunday
	 *            the sunday to set
	 */
	public void setSunday(boolean sunday) {
		this.sunday = sunday;
	}

	/**
	 * @return the theDay
	 */
	public String getTheDay() {
		return theDay;
	}

	/**
	 * @param theDay
	 *            the theDay to set
	 */
	public void setTheDay(String theDay) {
		this.theDay = theDay;
	}

	/**
	 * @return the theDayOfMonth
	 */
	public String getTheDayOfMonth() {
		return theDayOfMonth;
	}

	/**
	 * @param theDayOfMonth
	 *            the theDayOfMonth to set
	 */
	public void setTheDayOfMonth(String theDayOfMonth) {
		this.theDayOfMonth = theDayOfMonth;
	}

	/**
	 * @return the theDayOfYear
	 */
	public String getTheDayOfYear() {
		return theDayOfYear;
	}

	/**
	 * @param theDayOfYear
	 *            the theDayOfYear to set
	 */
	public void setTheDayOfYear(String theDayOfYear) {
		this.theDayOfYear = theDayOfYear;
	}

	/**
	 * @return the theMonthOfYear
	 */
	public String getTheMonthOfYear() {
		return theMonthOfYear;
	}

	/**
	 * @param theMonthOfYear
	 *            the theMonthOfYear to set
	 */
	public void setTheMonthOfYear(String theMonthOfYear) {
		this.theMonthOfYear = theMonthOfYear;
	}

	/**
	 * @return the theMonthOfYear2
	 */
	public String getTheMonthOfYear2() {
		return theMonthOfYear2;
	}

	/**
	 * @param theMonthOfYear2
	 *            the theMonthOfYear2 to set
	 */
	public void setTheMonthOfYear2(String theMonthOfYear2) {
		this.theMonthOfYear2 = theMonthOfYear2;
	}

	/**
	 * @return the theWeekday
	 */
	public String getTheWeekday() {
		return theWeekday;
	}

	/**
	 * @param theWeekday
	 *            the theWeekday to set
	 */
	public void setTheWeekday(String theWeekday) {
		this.theWeekday = theWeekday;
	}

	/**
	 * @return the theWeekdayOfYear
	 */
	public String getTheWeekdayOfYear() {
		return theWeekdayOfYear;
	}

	/**
	 * @param theWeekdayOfYear
	 *            the theWeekdayOfYear to set
	 */
	public void setTheWeekdayOfYear(String theWeekdayOfYear) {
		this.theWeekdayOfYear = theWeekdayOfYear;
	}

	/**
	 * @return the thursday
	 */
	public boolean isThursday() {
		return thursday;
	}

	/**
	 * @param thursday
	 *            the thursday to set
	 */
	public void setThursday(boolean thursday) {
		this.thursday = thursday;
	}

	/**
	 * @return the topic
	 */
	public String getTopic() {
		return topic;
	}

	/**
	 * @param topic
	 *            the topic to set
	 */
	public void setTopic(String topic) {
		this.topic = topic;
	}

	/**
	 * @return the tuesday
	 */
	public boolean isTuesday() {
		return tuesday;
	}

	/**
	 * @param tuesday
	 *            the tuesday to set
	 */
	public void setTuesday(boolean tuesday) {
		this.tuesday = tuesday;
	}

	/**
	 * @return the wednesday
	 */
	public boolean isWednesday() {
		return wednesday;
	}

	/**
	 * @param wednesday
	 *            the wednesday to set
	 */
	public void setWednesday(boolean wednesday) {
		this.wednesday = wednesday;
	}

	/**
	 * @return the weekNum
	 */
	public String getWeekNum() {
		return weekNum;
	}

	/**
	 * @param weekNum
	 *            the weekNum to set
	 */
	public void setWeekNum(String weekNum) {
		this.weekNum = weekNum;
	}

	/**
	 * @return the everyMonth
	 */
	public String getEveryMonth() {
		return everyMonth;
	}

	/**
	 * @param everyMonth
	 *            the everyMonth to set
	 */
	public void setEveryMonth(String everyMonth) {
		this.everyMonth = everyMonth;
	}

	/**
	 * @return the everyYear
	 */
	public String getEveryYear() {
		return everyYear;
	}

	/**
	 * @param everyYear
	 *            the everyYear to set
	 */
	public void setEveryYear(String everyYear) {
		this.everyYear = everyYear;
	}

	/**
	 * @return the receiverType
	 */
	public String getReceiverType() {
		return receiverType;
	}

	/**
	 * @param receiverType the receiverType to set
	 */
	public void setReceiverType(String receiverType) {
		this.receiverType = receiverType;
	}

	/**
	 * @return the everyHourNum
	 */
	public String getEveryHourNum() {
		return everyHourNum;
	}

	/**
	 * @param everyHourNum the everyHourNum to set
	 */
	public void setEveryHourNum(String everyHourNum) {
		this.everyHourNum = everyHourNum;
	}


}

// $Id: CronParser.java,v 1.1 2008/11/03 05:43:33 zhai Exp $
package com.chinadrtv.erp.marketing.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.chinadrtv.erp.util.DateUtil;



/**
 * @author zhaizy
 */
public class CronParser {
	
	public static String getReceiveCron(String interval,String type){
		String fieldSecond = "*";
		String fieldMinute = "*";
		String fieldHour = "*";
		String fieldDay = "*";
		String fieldMonth = "*";
		String fieldWeek = "?";
		String fieldYear = "";
		StringBuffer sb = new StringBuffer();
		
		if(type.equals("0")){
			fieldSecond = "0/"+interval;
		}else if(type.equals("1")){
			fieldMinute = "0/"+interval;
		}
			
			
		
		return sb.append(fieldSecond).append(" ").
		          append(fieldMinute).append(" ").
		          append(fieldHour).append(" ").
		          append(fieldDay).append(" ").
		          append(fieldMonth).append(" ").
		          append(fieldWeek).append(" ").
		          append(fieldYear).toString();
	}
		
	public static String getSimpleCronExpress(String singleTime) throws Exception{
		String fieldSecond = "";
		String fieldMinute = "";
		String fieldHour = "";
		String fieldDay = "";
		String fieldMonth = "";
		String fieldWeek = "";
		String fieldYear = "";
		StringBuffer sb = new StringBuffer();
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				Calendar calendar = Calendar.getInstance();
				try{
					calendar.setTime(df.parse(singleTime));
				}catch(ParseException e){
					calendar.setTime(df2.parse(singleTime));
				}
				fieldSecond = String.valueOf(calendar.get(Calendar.SECOND));
				fieldMinute = String.valueOf(calendar.get(Calendar.MINUTE));
				fieldHour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
				fieldDay = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
				fieldMonth = String.valueOf(calendar.get(Calendar.MONTH) + 1);
				fieldWeek = "?";
				fieldYear = String.valueOf(calendar.get(Calendar.YEAR));
		return sb.append(fieldSecond).append(" ").
		          append(fieldMinute).append(" ").
		          append(fieldHour).append(" ").
		          append(fieldDay).append(" ").
		          append(fieldMonth).append(" ").
		          append(fieldWeek).append(" ").
		          append(fieldYear).toString();
	}
	
	public static String getCronExpress(JobCronSet jobCronSet) throws Exception{
		String fieldSecond = "0";
		String fieldMinute = "";
		String fieldHour = "";
		String fieldDay = "";
		String fieldMonth = "";
		String fieldWeek = "";
		String fieldYear = "";
		StringBuffer sb = new StringBuffer();
		if (jobCronSet != null){
			//周期不重复
			if (jobCronSet.getFrequency().equals("0")){
				
				String cronExpress = getSimpleCronExpress(DateUtil.date2FormattedString(new Date(), "yyyy-MM-dd HH:mm"));
				return cronExpress;
				
			}else if(jobCronSet.getFrequency().equals("5")){
				return getSimpleCronExpress(jobCronSet.getTimeStr());
			}else{
				String startDate = jobCronSet.getDateOfStart()+":00";
				String endDate = jobCronSet.getEndDay()+":00";
				String [] minuteAndHour = startDate.substring(11).split(":");
				String [] startDateArray = startDate.substring(0,10).split("-");
				String [] endDateArray = endDate.substring(0,10).split("-");
				//minute and hour
				if (jobCronSet.isAllDay()){
					fieldMinute = "0";
					fieldHour = "0";
				}else{
					fieldHour = minuteAndHour[0];
					fieldMinute = minuteAndHour[1];
				}
				
				if(jobCronSet.getFrequency().equals("1")){//按天
				//day
					fieldDay=Integer.parseInt(startDateArray[2])+"/"+jobCronSet.getEveryDayNum();
					fieldMonth = "*";
					fieldWeek = "?";
					
				}else if(jobCronSet.getFrequency().equals("2")){//按周
					fieldDay="?";
					fieldMonth = "*";
					
					StringBuffer week = new StringBuffer("");
					if(jobCronSet.isSaturday()){
						week.append("SAT,");
					}
					if(jobCronSet.isMonday()){
						week.append("MON,");
					}
					if(jobCronSet.isWednesday()){
						week.append("WED,");
					}
					if(jobCronSet.isThursday()){
						week.append("THU,");
					}
					if(jobCronSet.isTuesday()){
						week.append("TUE,");
					}
					if(jobCronSet.isFriday()){
						week.append("FRI,");
					}
					if(jobCronSet.isSunday()){
						week.append("SUN,");
					}
					
					fieldWeek = week.toString();
					fieldWeek = fieldWeek.substring(0,fieldWeek.length()-1);
					fieldWeek = fieldWeek+"/"+jobCronSet.getWeekNum();
				}else if(jobCronSet.getFrequency().equals("3")){//按月
					if(jobCronSet.getEveryMonth().equals("1")){
						fieldMonth = Integer.parseInt(startDateArray[1])+"/"+jobCronSet.getEveryMonthNum();
						fieldDay=jobCronSet.getEveryMonthNumDay();
						fieldWeek="?";
					}else{
						fieldMonth = Integer.parseInt(startDateArray[1])+"/"+jobCronSet.getEveryMonthNum2();
						fieldDay="?";
						if(!jobCronSet.getTheDay().equals("5")){
							fieldWeek=jobCronSet.getTheWeekday()+"#"+jobCronSet.getTheDay();
						}else{
							fieldWeek=jobCronSet.getTheWeekday()+"L";
						}
					}
					
				}else if(jobCronSet.getFrequency().equals("4")){//按年
					
					if(jobCronSet.getEveryYear().equals("1")){
						fieldDay=jobCronSet.getTheDayOfMonth();
						fieldMonth = jobCronSet.getTheMonthOfYear();
						fieldWeek="?";
					}else{
						fieldDay="?";
						fieldMonth = jobCronSet.getTheMonthOfYear2();
						if(!jobCronSet.getTheDay().equals("5")){
							fieldWeek=jobCronSet.getTheWeekdayOfYear()+"#"+jobCronSet.getTheDayOfYear();
						}else{
							fieldWeek=jobCronSet.getTheWeekdayOfYear()+"L";
						}
					}
					
				}else if(jobCronSet.getFrequency().equals("6")){//按小时
				//day
					fieldHour="*/"+jobCronSet.getEveryHourNum();
					fieldDay="*";
					fieldMonth = "*";
					fieldWeek = "?";
					
				}else if(jobCronSet.getFrequency().equals("7")){//按分钟
				//day
					fieldMinute = "0/"+jobCronSet.getReceiverNum();
					fieldHour="*";
					fieldDay="*";
					fieldMonth = "*";
					fieldWeek = "?";
					
				}
				
			}
		}
		 sb.append(fieldSecond).append(" ").
		          append(fieldMinute).append(" ").
		          append(fieldHour).append(" ").
		          append(fieldDay).append(" ").
		          append(fieldMonth).append(" ").
		          append(fieldWeek).append(" ").
		          append(fieldYear);
		 return sb.toString();
	}

}

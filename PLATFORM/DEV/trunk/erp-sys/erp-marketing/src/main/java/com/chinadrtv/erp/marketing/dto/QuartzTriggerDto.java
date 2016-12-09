/*
 * @(#)QuartzTriggerDto.java 1.0 2014-2-28上午10:19:31
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.dto;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2014-2-28 上午10:19:31
 * 
 */
public class QuartzTriggerDto {

	private String triggerName;
	private String triggerGroup;
	private String jobName;
	private String jobGroup;
	private String isVolatile;
	private String description;
	private String nextFireTime;
	private String prevFireTime;
	private Long priority;
	private String triggerState;
	private String triggerType;
	private String startTime;
	private String endTime;
	private String calendarName;
	private Long misfireInstr;

	/**
	 * @return the triggerName
	 */
	public String getTriggerName() {
		return triggerName;
	}

	/**
	 * @param triggerName
	 *            the triggerName to set
	 */
	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}

	/**
	 * @return the triggerGroup
	 */
	public String getTriggerGroup() {
		return triggerGroup;
	}

	/**
	 * @param triggerGroup
	 *            the triggerGroup to set
	 */
	public void setTriggerGroup(String triggerGroup) {
		this.triggerGroup = triggerGroup;
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
	 * @return the jobGroup
	 */
	public String getJobGroup() {
		return jobGroup;
	}

	/**
	 * @param jobGroup
	 *            the jobGroup to set
	 */
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	/**
	 * @return the isVolatile
	 */
	public String getIsVolatile() {
		return isVolatile;
	}

	/**
	 * @param isVolatile
	 *            the isVolatile to set
	 */
	public void setIsVolatile(String isVolatile) {
		this.isVolatile = isVolatile;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the priority
	 */
	public Long getPriority() {
		return priority;
	}

	/**
	 * @param priority
	 *            the priority to set
	 */
	public void setPriority(Long priority) {
		this.priority = priority;
	}

	/**
	 * @return the triggerState
	 */
	public String getTriggerState() {
		return triggerState;
	}

	/**
	 * @param triggerState
	 *            the triggerState to set
	 */
	public void setTriggerState(String triggerState) {
		this.triggerState = triggerState;
	}

	/**
	 * @return the triggerType
	 */
	public String getTriggerType() {
		return triggerType;
	}

	/**
	 * @param triggerType
	 *            the triggerType to set
	 */
	public void setTriggerType(String triggerType) {
		this.triggerType = triggerType;
	}

	/**
	 * @return the calendarName
	 */
	public String getCalendarName() {
		return calendarName;
	}

	/**
	 * @param calendarName
	 *            the calendarName to set
	 */
	public void setCalendarName(String calendarName) {
		this.calendarName = calendarName;
	}

	/**
	 * @return the misfireInstr
	 */
	public Long getMisfireInstr() {
		return misfireInstr;
	}

	/**
	 * @param misfireInstr
	 *            the misfireInstr to set
	 */
	public void setMisfireInstr(Long misfireInstr) {
		this.misfireInstr = misfireInstr;
	}

	/**
	 * @return the nextFireTime
	 */
	public String getNextFireTime() {
		return nextFireTime;
	}

	/**
	 * @param nextFireTime
	 *            the nextFireTime to set
	 */
	public void setNextFireTime(String nextFireTime) {
		this.nextFireTime = nextFireTime;
	}

	/**
	 * @return the prevFireTime
	 */
	public String getPrevFireTime() {
		return prevFireTime;
	}

	/**
	 * @param prevFireTime
	 *            the prevFireTime to set
	 */
	public void setPrevFireTime(String prevFireTime) {
		this.prevFireTime = prevFireTime;
	}

	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}

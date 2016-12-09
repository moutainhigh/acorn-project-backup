/*
 * @(#)DateScope.java 1.0 2013-2-19上午10:49:42
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-2-19 上午10:49:42
 * 
 */
@XStreamAlias("dateScope")
public class DateScope implements Serializable {
	@XStreamAlias("startTime")
	private String startTime;
	@XStreamAlias("endTime")
	private String endTime;

	public List<TimeScope> times = new ArrayList<TimeScope>();

	public DateScope() {

	}

	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            the startTime to set
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
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the timeScopes
	 */
	public List<TimeScope> getTimeScopes() {
		return times;
	}

	/**
	 * @param timeScopes
	 *            the timeScopes to set
	 */
	public void setTimeScopes(List<TimeScope> times) {
		this.times = times;
	}

}

/*
 * @(#)TimeScope.java 1.0 2013-2-19上午10:53:18
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.dto;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-2-19 上午10:53:18
 * 
 */
@XStreamAlias("timeScope")
public class TimeScope implements Serializable {
	@XStreamAlias("time")
	private String time;
	@XStreamAlias("iops")
	private String iops;

	public TimeScope() {
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @return the iops
	 */
	public String getIops() {
		return iops;
	}

	/**
	 * @param iops
	 *            the iops to set
	 */
	public void setIops(String iops) {
		this.iops = iops;
	}

}

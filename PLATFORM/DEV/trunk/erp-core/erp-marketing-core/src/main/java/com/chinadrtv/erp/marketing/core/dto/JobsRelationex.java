/*
 * @(#)JobsRelationex.java 1.0 2013-5-15下午2:06:08
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.dto;

/**
 * <dl>
 * <dt><b>Title:查询取数规则</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * none</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-5-15 下午2:06:08
 * 
 */
public class JobsRelationex {

	private String jobId;
	private Integer top;// 最多可以取多少条
	private Integer days;// 天数，几天内最多取top条
	private Integer usableNum;// 可取数量
	private Integer usedNum;// 当日已取数量
	private Integer currentNum;

	/**
	 * @return the jobId
	 */
	public String getJobId() {
		return jobId;
	}

	/**
	 * @param jobId
	 *            the jobId to set
	 */
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	/**
	 * @return the top
	 */
	public Integer getTop() {
		return top;
	}

	/**
	 * @param top
	 *            the top to set
	 */
	public void setTop(Integer top) {
		this.top = top;
	}

	/**
	 * @return the days
	 */
	public Integer getDays() {
		return days;
	}

	/**
	 * @param days
	 *            the days to set
	 */
	public void setDays(Integer days) {
		this.days = days;
	}

	/**
	 * @return the usableNum
	 */
	public Integer getUsableNum() {
		
		if(this.top!=null && this.usedNum!=null){
			return top - usedNum;
		}
		return usableNum;
	}

	/**
	 * @param usableNum
	 *            the usableNum to set
	 */
	public void setUsableNum(Integer usableNum) {
		this.usableNum = usableNum;
	}

	/**
	 * @return the usedNum
	 */
	public Integer getUsedNum() {
		return usedNum;
	}

	/**
	 * @param usedNum
	 *            the usedNum to set
	 */
	public void setUsedNum(Integer usedNum) {
		this.usedNum = usedNum;
	}

	/**
	 * @return the currentNum
	 */
	public Integer getCurrentNum() {
		return currentNum;
	}

	/**
	 * @param currentNum the currentNum to set
	 */
	public void setCurrentNum(Integer currentNum) {
		this.currentNum = currentNum;
	}

}

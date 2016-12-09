/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.marketing.core.dto;

import java.io.Serializable;
import java.util.List;

import com.google.code.ssm.api.CacheKeyMethod;

/**
 * 2013-5-3 下午5:57:50
 * 
 * @version 1.0.0
 * @author yangfei
 * 
 */
public class CampaignTaskDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 任务创建者
	 * required, mustn't be null, 只能查自己
	 */
	private String userID;
	
	/**
	 * 任务所有者
	 */
	private String owner;

	/**
	 * 营销计划名称，等同展示中的任务名称（批次任务）
	 */
	private String campaignName;

	/**
	 * 营销计划名称，等同展示中的任务编号
	 */
	private String campaignID;

	/**
	 * 任务类型
	 */
	private String taskType;
	
	/**
	 * 任务来源
	 */
	private Integer taskSourceType;

	/**
	 * 
	 */
	private String customerID;

	/**
	 * 
	 */
	private String customerName;

	/**
	 * 
	 */
	private String startDate;

	/**
	 * 
	 */
	private String endDate;
	
	/**
	 * 实例ID
	 */
	private String instId;

	/**
	 * 任务状态
	 */
	private String status;
	
	private List<String> statuses;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	@CacheKeyMethod
	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	public String getCampaignID() {
		return campaignID;
	}

	public void setCampaignID(String campaignID) {
		this.campaignID = campaignID;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	
	public Integer getTaskSourceType() {
		return taskSourceType;
	}

	public void setTaskSourceType(Integer taskSourceType) {
		this.taskSourceType = taskSourceType;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getInstId() {
		return instId;
	}

	public void setInstId(String instId) {
		this.instId = instId;
	}

	public List<String> getStatuses() {
		return statuses;
	}

	public void setStatuses(List<String> statuses) {
		this.statuses = statuses;
	}

	@Override
	public String toString() {
		return "MarketingTaskDto [userID=" + userID + ", campaignName="
				+ campaignName + ", campaignID=" + campaignID + ", taskType="
				+ taskType + ", customerID=" + customerID + ", customerName="
				+ customerName + ", startDate=" + startDate + ", endDate="
				+ endDate + ", status=" + status + "]";
	}

}

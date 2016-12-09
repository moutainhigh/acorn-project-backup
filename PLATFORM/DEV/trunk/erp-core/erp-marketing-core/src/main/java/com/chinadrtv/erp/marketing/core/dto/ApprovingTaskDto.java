/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.marketing.core.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 2013-5-6 上午10:05:02
 * @version 1.0.0
 * @author yangfei
 *
 */
public class ApprovingTaskDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String taskType;
	
	private List<String> taskTypes;
	
	private String contactID;
	
	private String contactName;
	
	private String orderID;
	
	private String appliedUserID;
	
	private String orderCreatedUserID;
	
	private String startDate;
	
	private String endDate;
	
	private String taskStatus;
	
	//支持多状态查询
	private List<String> taskStatuses = new ArrayList<String>();
	
	/**
	 * 座席或者主管所属部门，必须字段
	 */
	private String department;
	
	private String workGrp;

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getContactID() {
		return contactID;
	}

	public void setContactID(String contactID) {
		this.contactID = contactID;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getAppliedUserID() {
		return appliedUserID;
	}

	public void setAppliedUserID(String appliedUserID) {
		this.appliedUserID = appliedUserID;
	}

	public String getOrderCreatedUserID() {
		return orderCreatedUserID;
	}

	public void setOrderCreatedUserID(String orderCreatedUserID) {
		this.orderCreatedUserID = orderCreatedUserID;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getWorkGrp() {
		return workGrp;
	}

	public void setWorkGrp(String workGrp) {
		this.workGrp = workGrp;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public List<String> getTaskTypes() {
		return taskTypes;
	}

	public void setTaskTypes(List<String> taskTypes) {
		this.taskTypes = taskTypes;
	}

	public List<String> getTaskStatuses() {
		return taskStatuses;
	}

	public void setTaskStatuses(List<String> taskStatuses) {
		this.taskStatuses = taskStatuses;
	}
}

/*
 * @(#)CustomerMonitor.java 1.0 2014-2-26下午2:05:15
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.model.marketing;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2014-2-26 下午2:05:15
 * 
 */
@Table(name = "CUSTOMER_MONITOR", schema = "ACOAPP_MARKETING")
@Entity
public class CustomerMonitor {
	private Long id;
	private String groupCode;
	private String groupName;
	private String batchCode;
	private String sqlContent;
	private String status;
	private String exception;
	private String sqlCount;
	private String customerRecovery;
	private String costTimes;

	/**
	 * @return the id
	 */
	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "generator", sequenceName = "ACOAPP_MARKETING.SEQ_CUSTOMER_MONITOR", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the groupCode
	 */
	@Column(name = "GROUP_CODE")
	public String getGroupCode() {
		return groupCode;
	}

	/**
	 * @param groupCode
	 *            the groupCode to set
	 */
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	/**
	 * @return the groupName
	 */
	@Column(name = "GROUP_NAME")
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName
	 *            the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return the batchCode
	 */
	@Column(name = "BATCH_CODE")
	public String getBatchCode() {
		return batchCode;
	}

	/**
	 * @param batchCode
	 *            the batchCode to set
	 */
	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}

	/**
	 * @return the sqlContent
	 */
	@Column(name = "SQL_CONTENT")
	public String getSqlContent() {
		return sqlContent;
	}

	/**
	 * @param sqlContent
	 *            the sqlContent to set
	 */
	public void setSqlContent(String sqlContent) {
		this.sqlContent = sqlContent;
	}

	/**
	 * @return the status
	 */
	@Column(name = "STATUS")
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the exception
	 */
	@Column(name = "EXCEPTION")
	public String getException() {
		return exception;
	}

	/**
	 * @param exception
	 *            the exception to set
	 */
	public void setException(String exception) {
		this.exception = exception;
	}

	/**
	 * @return the sqlCount
	 */
	@Column(name = "SQL_COUNT")
	public String getSqlCount() {
		return sqlCount;
	}

	/**
	 * @param sqlCount
	 *            the sqlCount to set
	 */
	public void setSqlCount(String sqlCount) {
		this.sqlCount = sqlCount;
	}

	/**
	 * @return the customerRecovery
	 */
	@Column(name = "CUSTOMER_RECOVERY")
	public String getCustomerRecovery() {
		return customerRecovery;
	}

	/**
	 * @param customerRecovery
	 *            the customerRecovery to set
	 */
	public void setCustomerRecovery(String customerRecovery) {
		this.customerRecovery = customerRecovery;
	}

	/**
	 * @return the costTimes
	 */
	@Column(name = "COST_TIMES")
	public String getCostTimes() {
		return costTimes;
	}

	/**
	 * @param costTimes
	 *            the costTimes to set
	 */
	public void setCostTimes(String costTimes) {
		this.costTimes = costTimes;
	}

}

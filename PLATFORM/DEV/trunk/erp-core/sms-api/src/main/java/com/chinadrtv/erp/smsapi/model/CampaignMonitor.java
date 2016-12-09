/*
 * @(#)CampaignMonitor.java 1.0 2013-7-8下午2:30:42
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.model;

import java.util.Date;

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
 * @since 2013-7-8 下午2:30:42
 * 
 */
@Entity
@Table(name = "CAMPAIGN_MONITOR", schema = "ACOAPP_MARKETING")
public class CampaignMonitor {

	public CampaignMonitor() {
	}

	private Long id;
	private Long campaignId;
	private String deparment;
	private String type;
	private String minuCount;
	private String errorCode;
	private String errorMessage;
	private String status;
	private Date createTime;
	private String createUser;
	private Date updateTime;
	private String updateUser;
	private Long smsCount;
	private String smsContent;
	private Long receiveCount;

	/**
	 * @return the id
	 */
	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "generator", sequenceName = "	SEQ_CAMPAIGN_MONITOR", allocationSize = 1)
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
	 * @return the campaign
	 */
	@Column(name = "campaign_id")
	public Long getCampaignId() {
		return campaignId;
	}

	/**
	 * @param campaign
	 *            the campaign to set
	 */
	public void setCampaignId(Long campaignId) {
		this.campaignId = campaignId;
	}

	/**
	 * @return the deparment
	 */
	@Column(name = "DEPARMENT")
	public String getDeparment() {
		return deparment;
	}

	/**
	 * @param deparment
	 *            the deparment to set
	 */
	public void setDeparment(String deparment) {
		this.deparment = deparment;
	}

	/**
	 * @return the type
	 */
	@Column(name = "TYPE")
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the minuCount
	 */
	@Column(name = "MINUCOUNT")
	public String getMinuCount() {
		return minuCount;
	}

	/**
	 * @param minuCount
	 *            the minuCount to set
	 */
	public void setMinuCount(String minuCount) {
		this.minuCount = minuCount;
	}

	/**
	 * @return the errorCode
	 */
	@Column(name = "ERROR_CODE")
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode
	 *            the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorMessage
	 */
	@Column(name = "ERROR_MESSAGE")
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage
	 *            the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
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
	 * @return the createTime
	 */
	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the createUser
	 */
	@Column(name = "CREATE_USER")
	public String getCreateUser() {
		return createUser;
	}

	/**
	 * @param createUser
	 *            the createUser to set
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	/**
	 * @return the updateTime
	 */
	@Column(name = "UPDATE_TIME")
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime
	 *            the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the updateUser
	 */
	@Column(name = "UPDATE_USER")
	public String getUpdateUser() {
		return updateUser;
	}

	/**
	 * @param updateUser
	 *            the updateUser to set
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	/**
	 * @return the smsCount
	 */
	@Column(name = "SMS_COUNT")
	public Long getSmsCount() {
		return smsCount;
	}

	/**
	 * @param smsCount
	 *            the smsCount to set
	 */
	public void setSmsCount(Long smsCount) {
		this.smsCount = smsCount;
	}

	/**
	 * @return the smsContent
	 */
	@Column(name = "SMS_CONTENT")
	public String getSmsContent() {
		return smsContent;
	}

	/**
	 * @param smsContent
	 *            the smsContent to set
	 */
	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	/**
	 * @return the receiveCount
	 */
	@Column(name = "RECEIVE_COUNT")
	public Long getReceiveCount() {
		return receiveCount;
	}

	/**
	 * @param receiveCount
	 *            the receiveCount to set
	 */
	public void setReceiveCount(Long receiveCount) {
		this.receiveCount = receiveCount;
	}

}

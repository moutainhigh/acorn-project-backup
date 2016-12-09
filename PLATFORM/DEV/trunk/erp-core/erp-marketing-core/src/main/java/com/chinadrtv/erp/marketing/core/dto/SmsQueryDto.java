/*
 * @(#)SmsQueryDto.java 1.0 2013-7-4下午2:27:19
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.dto;

import java.util.Date;

import com.chinadrtv.erp.model.marketing.Lead;

/**
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
 * @author andrew
 * @version 1.0
 * @since 2013-7-4 下午2:27:19 
 * 
 */
public class SmsQueryDto {
	private Long id;
	private String createUser;
	private Date createDate;
	private String status;
	private Date reserveDate;
	private Long leadId;
	private String resultCode;
	private String resultDescriptiong;
	private String comments;
	private String updateUser;
	private Date updateDate;
	private Long timeLength;
	private String appResponseCode;
	private String interActionType;
	private String appResponseStatus;
	private String appContent;
	private Lead lead;
	private String operation;
	private String contactId;
	private String templateTheme;
	private String orderIds;
	private String mobile;
	private String smsName;
	private String smsErrorCode;
	private String mobileUnMask;
	private String uuid;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getReserveDate() {
		return reserveDate;
	}
	public void setReserveDate(Date reserveDate) {
		this.reserveDate = reserveDate;
	}
	public Long getLeadId() {
		return leadId;
	}
	public void setLeadId(Long leadId) {
		this.leadId = leadId;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultDescriptiong() {
		return resultDescriptiong;
	}
	public void setResultDescriptiong(String resultDescriptiong) {
		this.resultDescriptiong = resultDescriptiong;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Long getTimeLength() {
		return timeLength;
	}
	public void setTimeLength(Long timeLength) {
		this.timeLength = timeLength;
	}
	public String getAppResponseCode() {
		return appResponseCode;
	}
	public void setAppResponseCode(String appResponseCode) {
		this.appResponseCode = appResponseCode;
	}
	public String getInterActionType() {
		return interActionType;
	}
	public void setInterActionType(String interActionType) {
		this.interActionType = interActionType;
	}
	public String getAppResponseStatus() {
		return appResponseStatus;
	}
	public void setAppResponseStatus(String appResponseStatus) {
		this.appResponseStatus = appResponseStatus;
	}
	public String getAppContent() {
		return appContent;
	}
	public void setAppContent(String appContent) {
		this.appContent = appContent;
	}
	public Lead getLead() {
		return lead;
	}
	public void setLead(Lead lead) {
		this.lead = lead;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getContactId() {
		return contactId;
	}
	public void setContactId(String contactId) {
		this.contactId = contactId;
	}
	public String getTemplateTheme() {
		return templateTheme;
	}
	public void setTemplateTheme(String templateTheme) {
		this.templateTheme = templateTheme;
	}
	public String getOrderIds() {
		return orderIds;
	}
	public void setOrderIds(String orderIds) {
		this.orderIds = orderIds;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getSmsName() {
		return smsName;
	}
	public void setSmsName(String smsName) {
		this.smsName = smsName;
	}
	public String getSmsErrorCode() {
		return smsErrorCode;
	}
	public void setSmsErrorCode(String smsErrorCode) {
		this.smsErrorCode = smsErrorCode;
	}
	public String getMobileUnMask() {
		return mobileUnMask;
	}
	public void setMobileUnMask(String mobileUnMask) {
		this.mobileUnMask = mobileUnMask;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}

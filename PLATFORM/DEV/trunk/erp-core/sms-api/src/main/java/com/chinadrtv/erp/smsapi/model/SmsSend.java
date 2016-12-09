/*
 * @(#)SmsSend.java 1.0 2013-2-18下午6:31:19
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.model;

import java.io.Serializable;
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
 * @since 2013-2-18 下午6:31:19
 * 
 */
@Table(name = "SMS_SEND", schema = "ACOAPP_MARKETING")
@Entity
public class SmsSend implements Serializable {
	private Long id;
	private String uuid;
	private String batchId;
	private String department;
	private String tomobile;
	private String smsContent;
	private Date timestamps;
	private String sendFilename;
	private Long recordcount;
	private String isreply;
	private String realtime;
	private String signiture;
	private Long priority;
	private String type;
	private Date createtime;
	private String creator;
	private String errorInfo;
	private String errorCode;
	private String ftpStatus;
	private Date starttime;
	private Date endtime;
	private String timescope;
	private String source;
	private String sendStatus;
	private String groupCode;
	private String groupName;
	private String smsName;
	private String smsstopStatus = "0";
	private String templateId;
	private String allowChannel;
	private String blackListFilter;
	private String mainNum;
	private String TemplateTheme;
	private String orderType;

	private String campaignId;

	private String dynamicTemplate;

	private Long receiveCount;

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SMS_SEND")
	@SequenceGenerator(name = "SEQ_SMS_SEND", sequenceName = "ACOAPP_MARKETING.SEQ_SMS_SEND", allocationSize = 1)
	@Column(name = "ID")
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
	 * @return the uuid
	 */
	@Column(name = "UUID")
	public String getUuid() {
		return uuid;
	}

	/**
	 * @param uuid
	 *            the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**
	 * @return the batchId
	 */
	@Column(name = "BATCHID")
	public String getBatchId() {
		return batchId;
	}

	/**
	 * @param batchId
	 *            the batchId to set
	 */
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	/**
	 * @return the department
	 */
	@Column(name = "DEPARTMENT")
	public String getDepartment() {
		return department;
	}

	/**
	 * @param department
	 *            the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	/**
	 * @return the tomobile
	 */
	@Column(name = "TOMOBILE")
	public String getTomobile() {
		return tomobile;
	}

	/**
	 * @param tomobile
	 *            the tomobile to set
	 */
	public void setTomobile(String tomobile) {
		this.tomobile = tomobile;
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
	 * @return the timestamps
	 */
	@Column(name = "TIMESTAMPS")
	public Date getTimestamps() {
		return timestamps;
	}

	/**
	 * @param timestamps
	 *            the timestamps to set
	 */
	public void setTimestamps(Date timestamps) {
		this.timestamps = timestamps;
	}

	/**
	 * @return the sendFilename
	 */
	@Column(name = "SEND_FILENAME")
	public String getSendFilename() {
		return sendFilename;
	}

	/**
	 * @param sendFilename
	 *            the sendFilename to set
	 */
	public void setSendFilename(String sendFilename) {
		this.sendFilename = sendFilename;
	}

	/**
	 * @return the recordcount
	 */
	@Column(name = "RECORDCOUNT")
	public Long getRecordcount() {
		return recordcount;
	}

	/**
	 * @param recordcount
	 *            the recordcount to set
	 */
	public void setRecordcount(Long recordcount) {
		this.recordcount = recordcount;
	}

	/**
	 * @return the isreply
	 */
	@Column(name = "ISREPLY")
	public String getIsreply() {
		return isreply;
	}

	/**
	 * @param isreply
	 *            the isreply to set
	 */
	public void setIsreply(String isreply) {
		this.isreply = isreply;
	}

	/**
	 * @return the realtime
	 */
	@Column(name = "REALTIME")
	public String getRealtime() {
		return realtime;
	}

	/**
	 * @param realtime
	 *            the realtime to set
	 */
	public void setRealtime(String realtime) {
		this.realtime = realtime;
	}

	/**
	 * @return the signiture
	 */
	@Column(name = "SIGNITURE")
	public String getSigniture() {
		return signiture;
	}

	/**
	 * @param signiture
	 *            the signiture to set
	 */
	public void setSigniture(String signiture) {
		this.signiture = signiture;
	}

	/**
	 * @return the priority
	 */
	@Column(name = "PRIORITY")
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
	 * @return the createtime
	 */
	@Column(name = "CREATEDATE")
	public Date getCreatetime() {
		return createtime;
	}

	/**
	 * @param createtime
	 *            the createtime to set
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	/**
	 * @return the creator
	 */
	@Column(name = "CREATEOR")
	public String getCreator() {
		return creator;
	}

	/**
	 * @param creator
	 *            the creator to set
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}

	/**
	 * @return the errorInfo
	 */
	@Column(name = "ERROR_INFO")
	public String getErrorInfo() {
		return errorInfo;
	}

	/**
	 * @param errorInfo
	 *            the errorInfo to set
	 */
	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
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
	 * @return the ftpStatus
	 */
	@Column(name = "FTP_STATUS")
	public String getFtpStatus() {
		return ftpStatus;
	}

	/**
	 * @param ftpStatus
	 *            the ftpStatus to set
	 */
	public void setFtpStatus(String ftpStatus) {
		this.ftpStatus = ftpStatus;
	}

	/**
	 * @return the starttime
	 */
	@Column(name = "STARTTIME")
	public Date getStarttime() {
		return starttime;
	}

	/**
	 * @param starttime
	 *            the starttime to set
	 */
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	/**
	 * @return the endtime
	 */
	@Column(name = "ENDTIME")
	public Date getEndtime() {
		return endtime;
	}

	/**
	 * @param endtime
	 *            the endtime to set
	 */
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	/**
	 * @return the timescope
	 */
	@Column(name = "TIMESCOPE")
	public String getTimescope() {
		return timescope;
	}

	/**
	 * @param timescope
	 *            the timescope to set
	 */
	public void setTimescope(String timescope) {
		this.timescope = timescope;
	}

	/**
	 * @return the source
	 */
	@Column(name = "SOURCE")
	public String getSource() {
		return source;
	}

	/**
	 * @param source
	 *            the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @return the sendStatus
	 */
	@Column(name = "SEND_STATUS")
	public String getSendStatus() {
		return sendStatus;
	}

	/**
	 * @param sendStatus
	 *            the sendStatus to set
	 */
	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
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
	 * @return the smsName
	 */
	@Column(name = "SMS_NAME")
	public String getSmsName() {
		return smsName;
	}

	/**
	 * @param smsName
	 *            the smsName to set
	 */
	public void setSmsName(String smsName) {
		this.smsName = smsName;
	}

	/**
	 * @return the smsstopStatus
	 */
	@Column(name = "SMSSTOP_STATUS")
	public String getSmsstopStatus() {
		return smsstopStatus;
	}

	/**
	 * @param smsstopStatus
	 *            the smsstopStatus to set
	 */
	public void setSmsstopStatus(String smsstopStatus) {
		this.smsstopStatus = smsstopStatus;
	}

	/**
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 */
	public SmsSend() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the templateId
	 */
	@Column(name = "TEMPLATE_ID")
	public String getTemplateId() {
		return templateId;
	}

	/**
	 * @param templateId
	 *            the templateId to set
	 */
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	/**
	 * @return the allowChannel
	 */
	@Column(name = "ALLOWCHANNEL")
	public String getAllowChannel() {
		return allowChannel;
	}

	/**
	 * @param allowChannel
	 *            the allowChannel to set
	 */
	public void setAllowChannel(String allowChannel) {
		this.allowChannel = allowChannel;
	}

	/**
	 * @return the blackListFilter
	 */
	@Column(name = "BLACKLIST_FILTER")
	public String getBlackListFilter() {
		return blackListFilter;
	}

	/**
	 * @param blackListFilter
	 *            the blackListFilter to set
	 */
	public void setBlackListFilter(String blackListFilter) {
		this.blackListFilter = blackListFilter;
	}

	/**
	 * @return the mainNum
	 */
	@Column(name = "MAIN_NUM")
	public String getMainNum() {
		return mainNum;
	}

	/**
	 * @param mainNum
	 *            the mainNum to set
	 */
	public void setMainNum(String mainNum) {
		this.mainNum = mainNum;
	}

	/**
	 * @return the templateTheme
	 */
	@Column(name = "TEMPLATE_THEME")
	public String getTemplateTheme() {
		return TemplateTheme;
	}

	/**
	 * @param templateTheme
	 *            the templateTheme to set
	 */
	public void setTemplateTheme(String templateTheme) {
		TemplateTheme = templateTheme;
	}

	/**
	 * @return the orderType
	 */
	@Column(name = "ORDER_TYPE")
	public String getOrderType() {
		return orderType;
	}

	/**
	 * @param orderType
	 *            the orderType to set
	 */
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	/**
	 * @return the campaignId
	 */
	@Column(name = "CAMPAIGN_ID")
	public String getCampaignId() {
		return campaignId;
	}

	/**
	 * @param campaignId
	 *            the campaignId to set
	 */
	public void setCampaignId(String campaignId) {
		this.campaignId = campaignId;
	}

	/**
	 * @return the dynamicTemplate
	 */
	@Column(name = "DYNAMIC_TEMPLATE")
	public String getDynamicTemplate() {
		return dynamicTemplate;
	}

	/**
	 * @param dynamicTemplate
	 *            the dynamicTemplate to set
	 */
	public void setDynamicTemplate(String dynamicTemplate) {
		this.dynamicTemplate = dynamicTemplate;
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

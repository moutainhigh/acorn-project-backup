/*
 * @(#)SmsDetail.java 1.0 2013-2-18下午6:38:38
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
 * @since 2013-2-18 下午6:38:38
 * 
 */
@Table(name = "SMS_SEND_DETAIL", schema = "ACOAPP_MARKETING")
@Entity
public class SmsSendDetail implements Serializable {

	private Long id;
	private String mobile;
	private String connectId;
	private String content;
	private String uuid;
	private Date sendtime;
	private String batchId;
	private String receiveStatus = "10";
	private String feedbackStatus = "10";
	private Date receiveStatusTime;
	private Date feedbackStatusTime;
	private Date createtime;
	private String creator;
	private String source;
	private String channel;
	private String smsStopStatus = "10";
	private String smsStopStatusTime;
	private String smsStopStatusDes;
	private String receiveStatusDes;
	private String feedbackStatusDes;
	private Date lastModifyDate;
	private String smsType;
	private Long campaignId;

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SMS_DETAIL")
	@SequenceGenerator(name = "SEQ_SMS_DETAIL", sequenceName = "ACOAPP_MARKETING.SEQ_SMS_DETAIL", allocationSize = 1)
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
	 * @return the mobile
	 */
	@Column(name = "MOBILE")
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile
	 *            the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the connectId
	 */
	@Column(name = "CONNECTID")
	public String getConnectId() {
		return connectId;
	}

	/**
	 * @param connectId
	 *            the connectId to set
	 */
	public void setConnectId(String connectId) {
		this.connectId = connectId;
	}

	/**
	 * @return the content
	 */
	@Column(name = "CONTENT")
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
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
	 * @return the sendtime
	 */
	@Column(name = "SENDTIME")
	public Date getSendtime() {
		return sendtime;
	}

	/**
	 * @param sendtime
	 *            the sendtime to set
	 */
	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
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
	 * @return the receiveStatus
	 */
	@Column(name = "RECEIVE_STATUS", nullable = false, columnDefinition = "VARCHAR2(100) default '10'")
	public String getReceiveStatus() {
		return receiveStatus;
	}

	/**
	 * @param receiveStatus
	 *            the receiveStatus to set
	 */
	public void setReceiveStatus(String receiveStatus) {
		this.receiveStatus = receiveStatus;
	}

	/**
	 * @return the feedbackStatus
	 */
	@Column(name = "FEEDBACK_STATUS", nullable = false, columnDefinition = "VARCHAR2(100) default '10'")
	public String getFeedbackStatus() {
		return feedbackStatus;
	}

	/**
	 * @param feedbackStatus
	 *            the feedbackStatus to set
	 */
	public void setFeedbackStatus(String feedbackStatus) {
		this.feedbackStatus = feedbackStatus;
	}

	/**
	 * @return the receiveStatusTime
	 */
	@Column(name = "RECEIVE_STATUS_TIME")
	public Date getReceiveStatusTime() {
		return receiveStatusTime;
	}

	/**
	 * @param receiveStatusTime
	 *            the receiveStatusTime to set
	 */
	public void setReceiveStatusTime(Date receiveStatusTime) {
		this.receiveStatusTime = receiveStatusTime;
	}

	/**
	 * @return the feedbackStatusTime
	 */
	@Column(name = "FEEDBACK_STATUS_TIME")
	public Date getFeedbackStatusTime() {
		return feedbackStatusTime;
	}

	/**
	 * @param feedbackStatusTime
	 *            the feedbackStatusTime to set
	 */
	public void setFeedbackStatusTime(Date feedbackStatusTime) {
		this.feedbackStatusTime = feedbackStatusTime;
	}

	/**
	 * @return the createtime
	 */
	@Column(name = "CREATETIME")
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
	@Column(name = "CREATOR")
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
	 * @return the channel
	 */
	@Column(name = "CHANNEL")
	public String getChannel() {
		return channel;
	}

	/**
	 * @param channel
	 *            the channel to set
	 */
	public void setChannel(String channel) {
		this.channel = channel;
	}

	/**
	 * @return the smsStopStatus
	 */
	@Column(name = "SMSSTOP_STATUS", nullable = false, columnDefinition = "VARCHAR2(100) default '10'")
	public String getSmsStopStatus() {
		return smsStopStatus;
	}

	/**
	 * @param smsStopStatus
	 *            the smsStopStatus to set
	 */
	public void setSmsStopStatus(String smsStopStatus) {
		this.smsStopStatus = smsStopStatus;
	}

	/**
	 * @return the smsStopStatusTime
	 */
	@Column(name = "SMSSTOP_STATUS_TIME")
	public String getSmsStopStatusTime() {
		return smsStopStatusTime;
	}

	/**
	 * @param smsStopStatusTime
	 *            the smsStopStatusTime to set
	 */
	public void setSmsStopStatusTime(String smsStopStatusTime) {
		this.smsStopStatusTime = smsStopStatusTime;
	}

	/**
	 * @return the smsStopStatusDes
	 */
	@Column(name = "SMSSTOP_STATUS_DES")
	public String getSmsStopStatusDes() {
		return smsStopStatusDes;
	}

	/**
	 * @param smsStopStatusDes
	 *            the smsStopStatusDes to set
	 */
	public void setSmsStopStatusDes(String smsStopStatusDes) {
		this.smsStopStatusDes = smsStopStatusDes;
	}

	/**
	 * @return the receiveStatusDes
	 */
	@Column(name = "RECEIVE_STATUS_DES")
	public String getReceiveStatusDes() {
		return receiveStatusDes;
	}

	/**
	 * @param receiveStatusDes
	 *            the receiveStatusDes to set
	 */
	public void setReceiveStatusDes(String receiveStatusDes) {
		this.receiveStatusDes = receiveStatusDes;
	}

	/**
	 * @return the feedbackStatusDes
	 */
	@Column(name = "FEEDBACK_STATUS_DES")
	public String getFeedbackStatusDes() {
		return feedbackStatusDes;
	}

	/**
	 * @param feedbackStatusDes
	 *            the feedbackStatusDes to set
	 */
	public void setFeedbackStatusDes(String feedbackStatusDes) {
		this.feedbackStatusDes = feedbackStatusDes;
	}

	/**
	 * @return the lastModifyDate
	 */
	@Column(name = "LAST_MODIFY_DATE")
	public Date getLastModifyDate() {
		return lastModifyDate;
	}

	/**
	 * @param lastModifyDate
	 *            the lastModifyDate to set
	 */
	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}

	/**
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 */
	public SmsSendDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the smsType
	 */
	@Column(name = "SMS_TYPE")
	public String getSmsType() {
		return smsType;
	}

	/**
	 * @param smsType
	 *            the smsType to set
	 */
	public void setSmsType(String smsType) {
		this.smsType = smsType;
	}

	/**
	 * @return the campaignId
	 */
	@Column(name = "CAMPAIGN_ID")
	public Long getCampaignId() {
		return campaignId;
	}

	/**
	 * @param campaignId
	 *            the campaignId to set
	 */
	public void setCampaignId(Long campaignId) {
		this.campaignId = campaignId;
	}

}

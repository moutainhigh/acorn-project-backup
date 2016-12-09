/*
 * @(#)SmsSendDetailDto.java 1.0 2013-4-3下午2:48:56
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.dto;

import java.util.Date;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-4-3 下午2:48:56
 * 
 */
public class SmsSendDetailDto {
	private Long id;
	private String mobile;
	private String connectId;
	private String content;
	private String uuid;
	private Date sendtime;
	private String batchId;
	private String receiveStatus;
	private String feedbackStatus;
	private String startreceiveStatusTime;
	private String endreceiveStatusTime;
	private String startfeedBackStatusTime;
	private String endfeedBackStatusTime;
	private String createtime;
	private String creator;
	private String source;
	private String channel;
	private String smsStopStatus;
	private String smsStopStatusTime;
	private String smsStopStatusDes;
	private String receiveStatusDes;
	private String feedbackStatusDes;
	private Date lastModifyDate;
	private String feedBackStatusTime;
	private String receiveStatusTime;
	private String smsStatus;
	private String smsTimes;
	
	private Long campaignId;

	public SmsSendDetailDto() {

	}

	/**
	 * @return the id
	 */
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
	 * @return the startreceiveStatusTime
	 */
	public String getStartreceiveStatusTime() {
		return startreceiveStatusTime;
	}

	/**
	 * @param startreceiveStatusTime
	 *            the startreceiveStatusTime to set
	 */
	public void setStartreceiveStatusTime(String startreceiveStatusTime) {
		this.startreceiveStatusTime = startreceiveStatusTime;
	}

	/**
	 * @return the endreceiveStatusTime
	 */
	public String getEndreceiveStatusTime() {
		return endreceiveStatusTime;
	}

	/**
	 * @param endreceiveStatusTime
	 *            the endreceiveStatusTime to set
	 */
	public void setEndreceiveStatusTime(String endreceiveStatusTime) {
		this.endreceiveStatusTime = endreceiveStatusTime;
	}

	/**
	 * @return the startfeedBackStatusTime
	 */
	public String getStartfeedBackStatusTime() {
		return startfeedBackStatusTime;
	}

	/**
	 * @param startfeedBackStatusTime
	 *            the startfeedBackStatusTime to set
	 */
	public void setStartfeedBackStatusTime(String startfeedBackStatusTime) {
		this.startfeedBackStatusTime = startfeedBackStatusTime;
	}

	/**
	 * @return the endfeedBackStatusTime
	 */
	public String getEndfeedBackStatusTime() {
		return endfeedBackStatusTime;
	}

	/**
	 * @param endfeedBackStatusTime
	 *            the endfeedBackStatusTime to set
	 */
	public void setEndfeedBackStatusTime(String endfeedBackStatusTime) {
		this.endfeedBackStatusTime = endfeedBackStatusTime;
	}

	/**
	 * @return the createtime
	 */
	public String getCreatetime() {
		return createtime;
	}

	/**
	 * @param createtime
	 *            the createtime to set
	 */
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	/**
	 * @return the creator
	 */
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
	 * @return the feedBackStatusTime
	 */
	public String getFeedBackStatusTime() {
		return feedBackStatusTime;
	}

	/**
	 * @param feedBackStatusTime
	 *            the feedBackStatusTime to set
	 */
	public void setFeedBackStatusTime(String feedBackStatusTime) {
		this.feedBackStatusTime = feedBackStatusTime;
	}

	/**
	 * @return the receiveStatusTime
	 */
	public String getReceiveStatusTime() {
		return receiveStatusTime;
	}

	/**
	 * @param receiveStatusTime
	 *            the receiveStatusTime to set
	 */
	public void setReceiveStatusTime(String receiveStatusTime) {
		this.receiveStatusTime = receiveStatusTime;
	}

	/**
	 * @return the smsStatus
	 */
	public String getSmsStatus() {
		return smsStatus;
	}

	/**
	 * @param smsStatus
	 *            the smsStatus to set
	 */
	public void setSmsStatus(String smsStatus) {
		this.smsStatus = smsStatus;
	}

	/**
	 * @return the smsTimes
	 */
	public String getSmsTimes() {
		return smsTimes;
	}

	/**
	 * @param smsTimes
	 *            the smsTimes to set
	 */
	public void setSmsTimes(String smsTimes) {
		this.smsTimes = smsTimes;
	}

	/**
	 * @return the campaignId
	 */
	//@Column(name = "campaignId")
	public Long getCampaignId() {
		return campaignId;
	}

	/**
	 * @param campaignId the campaignId to set
	 */
	public void setCampaignId(Long campaignId) {
		this.campaignId = campaignId;
	}

}

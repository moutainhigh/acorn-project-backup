/*
 * @(#)WxOcsCalllist.java 1.0 2013-12-16下午1:20:35
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
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
 * @since 2013-12-16 下午1:20:35
 * 
 */
@Table(name = "WX_OCS_CALLLIST", schema = "ACOAPP_GENE_URS3")
@Entity
public class WxOcsCalllist {
	private Integer recordId;
	private String contactInfo;
	private String contactInfoType;
	private Integer recordType;
	private Integer recordStatus;
	private Integer callResult;
	private Integer attempt;
	private Integer dialSchedTime;
	private Integer callTime;
	private Integer dailyFrom;
	private Integer dailyTill;
	private Integer tzDbid;
	private Integer campaignId;
	private Integer agentId;
	private Integer groupId;
	private Integer appId;
	private String treaments;
	private Integer mediaRef;
	private String emailSubject;
	private Integer emailTemplateId;
	private Integer switchId;
	private Integer chainId;
	private Integer chainN;
	private Integer batch;

	/**
	 * @return the recordId
	 */
	@Id
	@SequenceGenerator(name = "SEQ_WX_OCS_CALLLIST", sequenceName = "ACOAPP_GENE_URS3.SEQ_WX_OCS_CALLLIST")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WX_OCS_CALLLIST")
	@Column(name = "RECORD_ID")
	public Integer getRecordId() {
		return recordId;
	}

	/**
	 * @param recordId
	 *            the recordId to set
	 */
	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	/**
	 * @return the contactInfo
	 */
	@Column(name = "CONTACT_INFO")
	public String getContactInfo() {
		return contactInfo;
	}

	/**
	 * @param contactInfo
	 *            the contactInfo to set
	 */
	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}

	/**
	 * @return the contactInfoType
	 */
	@Column(name = "CONTACT_INFO_TYPE")
	public String getContactInfoType() {
		return contactInfoType;
	}

	/**
	 * @param contactInfoType
	 *            the contactInfoType to set
	 */
	public void setContactInfoType(String contactInfoType) {
		this.contactInfoType = contactInfoType;
	}

	/**
	 * @return the recordType
	 */
	@Column(name = "RECORD_TYPE")
	public Integer getRecordType() {
		return recordType;
	}

	/**
	 * @param recordType
	 *            the recordType to set
	 */
	public void setRecordType(Integer recordType) {
		this.recordType = recordType;
	}

	/**
	 * @return the recordStatus
	 */
	@Column(name = "RECORD_STATUS")
	public Integer getRecordStatus() {
		return recordStatus;
	}

	/**
	 * @param recordStatus
	 *            the recordStatus to set
	 */
	public void setRecordStatus(Integer recordStatus) {
		this.recordStatus = recordStatus;
	}

	/**
	 * @return the callResult
	 */
	@Column(name = "CALL_RESULT")
	public Integer getCallResult() {
		return callResult;
	}

	/**
	 * @param callResult
	 *            the callResult to set
	 */
	public void setCallResult(Integer callResult) {
		this.callResult = callResult;
	}

	/**
	 * @return the attempt
	 */
	@Column(name = "ATTEMPT")
	public Integer getAttempt() {
		return attempt;
	}

	/**
	 * @param attempt
	 *            the attempt to set
	 */
	public void setAttempt(Integer attempt) {
		this.attempt = attempt;
	}

	/**
	 * @return the dialSchedTime
	 */
	@Column(name = "DIAL_SCHED_TIME")
	public Integer getDialSchedTime() {
		return dialSchedTime;
	}

	/**
	 * @param dialSchedTime
	 *            the dialSchedTime to set
	 */
	public void setDialSchedTime(Integer dialSchedTime) {
		this.dialSchedTime = dialSchedTime;
	}

	/**
	 * @return the callTime
	 */
	@Column(name = "CALL_TIME")
	public Integer getCallTime() {
		return callTime;
	}

	/**
	 * @param callTime
	 *            the callTime to set
	 */
	public void setCallTime(Integer callTime) {
		this.callTime = callTime;
	}

	/**
	 * @return the dailyFrom
	 */
	@Column(name = "DAILY_FROM")
	public Integer getDailyFrom() {
		return dailyFrom;
	}

	/**
	 * @param dailyFrom
	 *            the dailyFrom to set
	 */
	public void setDailyFrom(Integer dailyFrom) {
		this.dailyFrom = dailyFrom;
	}

	/**
	 * @return the dailyTill
	 */
	@Column(name = "DAILY_TILL")
	public Integer getDailyTill() {
		return dailyTill;
	}

	/**
	 * @param dailyTill
	 *            the dailyTill to set
	 */
	public void setDailyTill(Integer dailyTill) {
		this.dailyTill = dailyTill;
	}

	/**
	 * @return the tzDbid
	 */
	@Column(name = "TZ_DBID")
	public Integer getTzDbid() {
		return tzDbid;
	}

	/**
	 * @param tzDbid
	 *            the tzDbid to set
	 */
	public void setTzDbid(Integer tzDbid) {
		this.tzDbid = tzDbid;
	}

	/**
	 * @return the campaignId
	 */
	@Column(name = "CAMPAIGN_ID")
	public Integer getCampaignId() {
		return campaignId;
	}

	/**
	 * @param campaignId
	 *            the campaignId to set
	 */
	public void setCampaignId(Integer campaignId) {
		this.campaignId = campaignId;
	}

	/**
	 * @return the agentId
	 */
	@Column(name = "AGENT_ID")
	public Integer getAgentId() {
		return agentId;
	}

	/**
	 * @param agentId
	 *            the agentId to set
	 */
	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

	/**
	 * @return the groupId
	 */
	@Column(name = "GROUP_ID")
	public Integer getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId
	 *            the groupId to set
	 */
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the appId
	 */
	@Column(name = "APP_ID")
	public Integer getAppId() {
		return appId;
	}

	/**
	 * @param appId
	 *            the appId to set
	 */
	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	/**
	 * @return the treaments
	 */
	@Column(name = "TREATMENTS")
	public String getTreaments() {
		return treaments;
	}

	/**
	 * @param treaments
	 *            the treaments to set
	 */
	public void setTreaments(String treaments) {
		this.treaments = treaments;
	}

	/**
	 * @return the mediaRef
	 */
	@Column(name = "MEDIA_REF")
	public Integer getMediaRef() {
		return mediaRef;
	}

	/**
	 * @param mediaRef
	 *            the mediaRef to set
	 */
	public void setMediaRef(Integer mediaRef) {
		this.mediaRef = mediaRef;
	}

	/**
	 * @return the emailSubject
	 */
	@Column(name = "EMAIL_SUBJECT")
	public String getEmailSubject() {
		return emailSubject;
	}

	/**
	 * @param emailSubject
	 *            the emailSubject to set
	 */
	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	/**
	 * @return the emailTemplateId
	 */
	@Column(name = "EMAIL_TEMPLATE_ID")
	public Integer getEmailTemplateId() {
		return emailTemplateId;
	}

	/**
	 * @param emailTemplateId
	 *            the emailTemplateId to set
	 */
	public void setEmailTemplateId(Integer emailTemplateId) {
		this.emailTemplateId = emailTemplateId;
	}

	/**
	 * @return the switchId
	 */
	@Column(name = "SWITCH_ID")
	public Integer getSwitchId() {
		return switchId;
	}

	/**
	 * @param switchId
	 *            the switchId to set
	 */
	public void setSwitchId(Integer switchId) {
		this.switchId = switchId;
	}

	/**
	 * @return the chainId
	 */
	@Column(name = "CHAIN_ID")
	public Integer getChainId() {
		return chainId;
	}

	/**
	 * @param chainId
	 *            the chainId to set
	 */
	public void setChainId(Integer chainId) {
		this.chainId = chainId;
	}

	/**
	 * @return the chainN
	 */
	@Column(name = "CHAIN_N")
	public Integer getChainN() {
		return chainN;
	}

	/**
	 * @param chainN
	 *            the chainN to set
	 */
	public void setChainN(Integer chainN) {
		this.chainN = chainN;
	}

	/**
	 * @return the batch
	 */
	@Column(name = "BATCH")
	public Integer getBatch() {
		return batch;
	}

	/**
	 * @param batch
	 *            the batch to set
	 */
	public void setBatch(Integer batch) {
		this.batch = batch;
	}

}

/*
 * @(#)CampaignReceiver.java 1.0 2013-4-17上午10:13:58
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.model.marketing;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-4-17 上午10:13:58 营销目标客户
 */
@Table(name = "CAMPAIGN_RECEIVER", schema = "ACOAPP_MARKETING")
@Entity
public class CampaignReceiver implements Serializable {
	private Long id;
	private String customerId;
	private Long campaignId;
	private String batchCode;
	private Date createDate;
	private String createUser;
	private String groupCode;
	private String contactInfo;
	private String statisInfo;
	private String jobid;
	private String queueid;
	private Long priority;
	private Date updateDate;
	private String updateUser;
	private String dataSource;
	private String assignGroup;
	private String assignGroupUser;
	private Date assignGroupTime;
	private String assignAgent;
	private String assignUser;
	private Date assignTime;
	private Long leadId;
	private String status;
	private Long bpmInstId;

	public CampaignReceiver() {
		super();
	}

	/**
	 * @return the id
	 */
	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "generator", sequenceName = "ACOAPP_MARKETING.SEQ_CAMPAIGN_RECEIVER")
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
	 * @return the customerId
	 */
	@Column(name = "CUSTOMER_ID")
	public String getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId
	 *            the customerId to set
	 */
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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
	 * @return the createDate
	 */
	@Column(name = "CREATE_DATE")
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate
	 *            the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
	 * @return the statisInfo
	 */
	@Column(name = "STATIS_INFO")
	public String getStatisInfo() {
		return statisInfo;
	}

	/**
	 * @param statisInfo
	 *            the statisInfo to set
	 */
	public void setStatisInfo(String statisInfo) {
		this.statisInfo = statisInfo;
	}

	/**
	 * @return the jobid
	 */
	@Column(name = "JOBID")
	public String getJobid() {
		return jobid;
	}

	/**
	 * @param jobid
	 *            the jobid to set
	 */
	public void setJobid(String jobid) {
		this.jobid = jobid;
	}

	/**
	 * @return the queueid
	 */
	@Column(name = "QUEUEID")
	public String getQueueid() {
		return queueid;
	}

	/**
	 * @param queueid
	 *            the queueid to set
	 */
	public void setQueueid(String queueid) {
		this.queueid = queueid;
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

	@Column(name = "UPDATE_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Column(name = "UPDATE_USER", length = 50)
	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	@Column(name = "DATA_SOURCE", length = 50)
	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	@Column(name = "ASSIGN_GROUP", length = 50)
	public String getAssignGroup() {
		return assignGroup;
	}

	public void setAssignGroup(String assignGroup) {
		this.assignGroup = assignGroup;
	}

	@Column(name = "ASSIGN_GROUP_USER", length = 50)
	public String getAssignGroupUser() {
		return assignGroupUser;
	}

	public void setAssignGroupUser(String assignGroupUser) {
		this.assignGroupUser = assignGroupUser;
	}

	@Column(name = "ASSIGN_GROUP_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getAssignGroupTime() {
		return assignGroupTime;
	}

	public void setAssignGroupTime(Date assignGroupTime) {
		this.assignGroupTime = assignGroupTime;
	}

	@Column(name = "ASSIGN_AGENT", length = 50)
	public String getAssignAgent() {
		return assignAgent;
	}

	public void setAssignAgent(String assignAgent) {
		this.assignAgent = assignAgent;
	}

	@Column(name = "ASSIGN_USER", length = 50)
	public String getAssignUser() {
		return assignUser;
	}

	public void setAssignUser(String assignUser) {
		this.assignUser = assignUser;
	}

	@Column(name = "ASSIGN_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getAssignTime() {
		return assignTime;
	}

	public void setAssignTime(Date assignTime) {
		this.assignTime = assignTime;
	}

	@Column(name = "LEAD_ID")
	public Long getLeadId() {
		return leadId;
	}

	public void setLeadId(Long leadId) {
		this.leadId = leadId;
	}

	@Column(name = "STATUS", length = 50)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "BPM_INST_ID")
	public Long getBpmInstId() {
		return bpmInstId;
	}

	public void setBpmInstId(Long bpmInstId) {
		this.bpmInstId = bpmInstId;
	}

}

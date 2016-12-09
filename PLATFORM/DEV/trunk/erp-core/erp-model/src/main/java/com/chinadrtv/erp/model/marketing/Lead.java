/*
 * @(#)Lead.java 1.0 2013-4-17上午10:57:40
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
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-4-17 上午10:57:40 销售线索
 */
@Table(name = "LEAD", schema = "ACOAPP_MARKETING")
@Entity
public class Lead implements Serializable {

	private Long id;
	private Long campaignId;
	private String batchCode;
	private Date createDate;
	private Date beginDate;
	private Date endDate;
	private String createUser;
	private String groupCode;
	private String contactInfo;
	private String statisInfo;
	private String jobid;
	private String queueid;
	private Long priority;
	private Long status;
	private String dnis;
	private String owner;
	private String userGroup;
	private String statusReason;
	private Long sourceId;
	private String objectType;
	private String updateUser;
	private Date updateDate;
	private String potentialContactId;
	private String contactId;
	private String ani;
	private Long callDirection;
	private Long lastOrderId;
	private Long ispotential;
	private Campaign campaign;
	private Long previousLeadId;
	
	/**
	 * 如果为取数任务，创建任务时，为后面能继续取数,
	 * 需要将pdCustomerId与任务关联
	 */
	@Transient
	private String pdCustomerId;
	
	/**
	 * 回访任务参数。回访任务的预约时间。创建回访任务时,
	 * 如果设置了预约时间，需要以此时间为回访任务的结束时间
	 */
	@Transient
	private Date appointDate;
	
	/**
	 * 标识是否为回访任务
	 */
	private boolean isCallbackTask;
	
	/**
	 * 回访任务参数。当前任务的结束时间。创建回访任务时,
	 * 如果appointDate为空，需要以此时间为回访任务的结束时间
	 */
	private Date currentTaskEndDate;
	
	/**
	 * 是否为outbound创建线索
	 */
	private boolean isOutbound = true;

    /**
     * 任务备注
     */
    private  String taskRemark;

    /**
     * 任务来源
     */
    private long taskSourcType;
    
    private long taskSourcType2 = -1;

	private long taskSourcType3 = -1;

	public Lead() {
		super();

	}

	/**
	 * @return the id
	 */
	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "generator", sequenceName = "ACOAPP_MARKETING.SEQ_LEAD", allocationSize = 1)
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
	
	@Column(name = "BEGIN_DATE")
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	@Column(name = "END_DATE")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	/**
	 * @return the status
	 */
	@Column(name = "STATUS")
	public Long getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Long status) {
		this.status = status;
	}

	/**
	 * @return the dnis
	 */
	@Column(name = "DNIS")
	public String getDnis() {
		return dnis;
	}

	/**
	 * @param dnis
	 *            the dnis to set
	 */
	public void setDnis(String dnis) {
		this.dnis = dnis;
	}

	/**
	 * @return the owner
	 */
	@Column(name = "OWNER")
	public String getOwner() {
		return owner;
	}

	/**
	 * @param owner
	 *            the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * @return the userGroup
	 */
	@Column(name = "USER_GROUP")
	public String getUserGroup() {
		return userGroup;
	}

	/**
	 * @param userGroup
	 *            the userGroup to set
	 */
	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}

	/**
	 * @return the statusReason
	 */
	@Column(name = "STATUS_REASON")
	public String getStatusReason() {
		return statusReason;
	}

	/**
	 * @param statusReason
	 *            the statusReason to set
	 */
	public void setStatusReason(String statusReason) {
		this.statusReason = statusReason;
	}

	/**
	 * @return the sourceId
	 */
	@Column(name = "SOURCE_ID")
	public Long getSourceId() {
		return sourceId;
	}

	/**
	 * @param sourceId
	 *            the sourceId to set
	 */
	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}

	/**
	 * @return the objectType
	 */
	@Column(name = "OBJECT_TYPE")
	public String getObjectType() {
		return objectType;
	}

	/**
	 * @param objectType
	 *            the objectType to set
	 */
	public void setObjectType(String objectType) {
		this.objectType = objectType;
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
	 * @return the updateDate
	 */
	@Column(name = "UPDATE_DATE")
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate
	 *            the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the potentialContactId
	 */
	@Column(name = "POTENTIAL_CONTACT_ID")
	public String getPotentialContactId() {
		return potentialContactId;
	}

	/**
	 * @param potentialContactId
	 *            the potentialContactId to set
	 */
	public void setPotentialContactId(String potentialContactId) {
		this.potentialContactId = potentialContactId;
	}

	/**
	 * @return the contactId
	 */
	@Column(name = "CONTACT_ID")
	public String getContactId() {
		return contactId;
	}

	/**
	 * @param contactId
	 *            the contactId to set
	 */
	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	/**
	 * @return the ani
	 */
	@Column(name = "ANI")
	public String getAni() {
		return ani;
	}

	/**
	 * @param ani
	 *            the ani to set
	 */
	public void setAni(String ani) {
		this.ani = ani;
	}

	/**
	 * @return the callDirection
	 */
	@Column(name = "CALL_DIRECTION")
	public Long getCallDirection() {
		return callDirection;
	}

	/**
	 * @param callDirection
	 *            the callDirection to set
	 */
	public void setCallDirection(Long callDirection) {
		this.callDirection = callDirection;
	}

	/**
	 * @return the lastOrderId
	 */
	@Column(name = "LAST_ORDER_ID")
	public Long getLastOrderId() {
		return lastOrderId;
	}

	/**
	 * @param lastOrderId
	 *            the lastOrderId to set
	 */
	public void setLastOrderId(Long lastOrderId) {
		this.lastOrderId = lastOrderId;
	}
	/**
	 * 父线索（转化线索）
	 * getPreviousLeadId
	 * @return 
	 * Long
	 * @exception 
	 * @since  1.0.0
	 */
	@Column(name = "PREVIOUS_LEAD_ID")
    public Long getPreviousLeadId() {
		return previousLeadId;
	}

	public void setPreviousLeadId(Long previousLeadId) {
		this.previousLeadId = previousLeadId;
	}

	/**
	 * @return the ispotential
	 */
	@Column(name = "IS_POTENTIAL")
	public Long getIspotential() {
		return ispotential;
	}

	/**
	 * @param ispotential
	 *            the ispotential to set
	 */
	public void setIspotential(Long ispotential) {
		this.ispotential = ispotential;
	}

	@Transient
	public String getPdCustomerId() {
		return pdCustomerId;
	}

	public void setPdCustomerId(String pdCustomerId) {
		this.pdCustomerId = pdCustomerId;
	}
	
	@Transient
	public Date getAppointDate() {
		return appointDate;
	}

	public void setAppointDate(Date appointDate) {
		this.appointDate = appointDate;
	}

	@Transient
	public boolean isCallbackTask() {
		return isCallbackTask;
	}

	public void setCallbackTask(boolean isCallbackTask) {
		this.isCallbackTask = isCallbackTask;
	}
	
	@Transient
	public boolean isOutbound() {
		return isOutbound;
	}

	public void setOutbound(boolean isOutbound) {
		this.isOutbound = isOutbound;
	}

    @Transient
    public String getTaskRemark() {
        return taskRemark;
    }

    public void setTaskRemark(String taskRemark) {
        this.taskRemark = taskRemark;
    }

    @Transient
    public long getTaskSourcType() {
        return taskSourcType;
    }

    public void setTaskSourcType(long taskSourcType) {
        this.taskSourcType = taskSourcType;
    }
    
    @Transient
    public long getTaskSourcType2() {
		return taskSourcType2;
	}

	public void setTaskSourcType2(long taskSourcType2) {
		this.taskSourcType2 = taskSourcType2;
	}

    @Transient
	public long getTaskSourcType3() {
		return taskSourcType3;
	}

	public void setTaskSourcType3(long taskSourcType3) {
		this.taskSourcType3 = taskSourcType3;
	}

	@Transient
	public Date getCurrentTaskEndDate() {
		return currentTaskEndDate;
	}

	public void setCurrentTaskEndDate(Date currentTaskEndDate) {
		this.currentTaskEndDate = currentTaskEndDate;
	}

	/**
	 * @return the campaign
	 */
	@OneToOne
	@JoinColumn(name = "CAMPAIGN_ID", referencedColumnName = "id", insertable = false, updatable = false)
	public Campaign getCampaign() {
		return campaign;
	}

	/**
	 * @param campaign
	 *            the campaign to set
	 */
	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}

}

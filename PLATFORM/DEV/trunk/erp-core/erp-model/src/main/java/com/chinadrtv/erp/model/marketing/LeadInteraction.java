/*
 * @(#)Lead_Interaction.java 1.0 2013-4-17上午11:07:22
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.model.marketing;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-4-17 上午11:07:22 销售线索交互记录
 */
@Table(name = "LEAD_INTERACTION", schema = "ACOAPP_MARKETING")
@Entity
public class LeadInteraction implements Serializable {

	private Long id;
	private String createUser;
	private Date createDate;
	private Date beginDate;
	private Date endDate;
	private String status;
	private Date reserveDate;
	private Long leadId;
	private String resultCode;
	private Long priority;
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
	private String ani;
	private String dnis;
	private Long allocateCount;
    private String callId;
    private Date ctiStartDate;
    private Date ctiEndDate;
    private Integer reson;
    private Long isValid;
    private String bpmInstId;
    private String groupCode;
    private String acdGroup;
	private Set<LeadInteractionOrder> leadInteractionOrders = new HashSet<LeadInteractionOrder>();
    private String callResult;
    private String callType;
    private String deptId;

	public LeadInteraction() {

	}

	/**
	 * @return the id
	 */
	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "generator", sequenceName = "ACOAPP_MARKETING.SEQ_LEAD_INTERACTION", allocationSize = 1)
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
	 * @return the reserveDate
	 */
	@Column(name = "RESERVE_DATE")
	public Date getReserveDate() {
		return reserveDate;
	}

	/**
	 * @param reserveDate
	 *            the reserveDate to set
	 */
	public void setReserveDate(Date reserveDate) {
		this.reserveDate = reserveDate;
	}

	/**
	 * @return the leadId
	 */
	@Column(name = "LEAD_ID")
	public Long getLeadId() {
		return leadId;
	}

	/**
	 * @param leadId
	 *            the leadId to set
	 */
	public void setLeadId(Long leadId) {
		this.leadId = leadId;
	}

	/**
	 * @return the resultCode
	 */
	@Column(name = "RESULT_CODE")
	public String getResultCode() {
		return resultCode;
	}

	/**
	 * @param resultCode
	 *            the resultCode to set
	 */
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	/**
	 * @return the resultDescriptiong
	 */
	@Column(name = "RESULT_DESCRIPTION")
	public String getResultDescriptiong() {
		return resultDescriptiong;
	}

	/**
	 * @param resultDescriptiong
	 *            the resultDescriptiong to set
	 */
	public void setResultDescriptiong(String resultDescriptiong) {
		this.resultDescriptiong = resultDescriptiong;
	}

	@Column(name = "RESULT_PRIORITY")
	public Long getPriority() {
		return priority;
	}

	public void setPriority(Long priority) {
		this.priority = priority;
	}

	/**
	 * @return the comments
	 */
	@Column(name = "COMMENTS")
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments
	 *            the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
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
	 * @return the timeLength
	 */
	@Column(name = "TIME_LENGTH")
	public Long getTimeLength() {
		return timeLength;
	}

	/**
	 * @param timeLength
	 *            the timeLength to set
	 */
	public void setTimeLength(Long timeLength) {
		this.timeLength = timeLength;
	}

	/**
	 * @return the appResponseCode
	 */
	@Column(name = "APP_RESPONSE_CODE")
	public String getAppResponseCode() {
		return appResponseCode;
	}

	/**
	 * @param appResponseCode
	 *            the appResponseCode to set
	 */
	public void setAppResponseCode(String appResponseCode) {
		this.appResponseCode = appResponseCode;
	}

	/**
	 * @return the interActionType
	 */
	@Column(name = "INTERACTION_TYPE")
	public String getInterActionType() {
		return interActionType;
	}

	/**
	 * @param interActionType
	 *            the interActionType to set
	 */
	public void setInterActionType(String interActionType) {
		this.interActionType = interActionType;
	}

	/**
	 * @return the appResponseStatus
	 */
	@Column(name = "APP_RESPONSE_STATUS")
	public String getAppResponseStatus() {
		return appResponseStatus;
	}

	/**
	 * @param appResponseStatus
	 *            the appResponseStatus to set
	 */
	public void setAppResponseStatus(String appResponseStatus) {
		this.appResponseStatus = appResponseStatus;
	}

	/**
	 * @return the appContent
	 */
	@Column(name = "APP_CONTENT")
	public String getAppContent() {
		return appContent;
	}

	/**
	 * @param appContent
	 *            the appContent to set
	 */
	public void setAppContent(String appContent) {
		this.appContent = appContent;
	}

	/**
	 * @return the lead
	 */
	@OneToOne
	@JoinColumn(name = "LEAD_ID", referencedColumnName = "id", insertable = false, updatable = false)
	public Lead getLead() {
		return lead;
	}

	/**
	 * @param lead
	 *            the lead to set
	 */
	public void setLead(Lead lead) {
		this.lead = lead;
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
	 * @return the operation
	 */
	@Column(name = "OPERATION")
	public String getOperation() {
		return operation;
	}

	/**
	 * @param operation
	 *            the operation to set
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}

	@Column(name = "CONTACT_ID")
	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}
	
	@Column(name = "ANI")
	public String getAni() {
		return ani;
	}

	public void setAni(String ani) {
		this.ani = ani;
	}

	@Column(name = "DNIS")
	public String getDnis() {
		return dnis;
	}

	public void setDnis(String dnis) {
		this.dnis = dnis;
	}

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval =true, fetch = FetchType.LAZY, mappedBy = "leadInteraction")
	@JsonIgnore
	public Set<LeadInteractionOrder> getLeadInteractionOrders() {
		return leadInteractionOrders;
	}

	public void setLeadInteractionOrders(Set<LeadInteractionOrder> leadInteractionOrders) {
		this.leadInteractionOrders = leadInteractionOrders;
	}

	@Column(name = "ALLOCATED_NUMBER")
	public Long getAllocateCount() {
		return allocateCount;
	}

	public void setAllocateCount(Long allocateCount) {
		this.allocateCount = allocateCount;
	}
    @Column(name = "CALL_ID")
    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }

	/**
	 * @return the ctiStartDate
	 */
	@Column(name = "cti_Start_Date")
	public Date getCtiStartDate() {
		return ctiStartDate;
	}

	/**
	 * @param ctiStartDate the ctiStartDate to set
	 */
	public void setCtiStartDate(Date ctiStartDate) {
		this.ctiStartDate = ctiStartDate;
	}

	/**
	 * @return the ctiEndDate
	 */
	@Column(name = "cti_End_Date")
	public Date getCtiEndDate() {
		return ctiEndDate;
	}

	/**
	 * @param ctiEndDate the ctiEndDate to set
	 */
	public void setCtiEndDate(Date ctiEndDate) {
		this.ctiEndDate = ctiEndDate;
	}

    @Column(name = "RESON")
    public Integer getReson() {
        return reson;
    }

    public void setReson(Integer reson) {
        this.reson = reson;
    }

    @Column(name = "IS_VALID")
	public Long getIsValid() {
		return isValid;
	}

	public void setIsValid(Long isValid) {
		this.isValid = isValid;
	}

    @Column(name = "BPMINSTID")
	public String getBpmInstId() {
		return bpmInstId;
	}

	public void setBpmInstId(String bpmInstId) {
		this.bpmInstId = bpmInstId;
	}

    @Column(name = "GROUP_CODE")
	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

    @Column(name = "ACDGROUP")
    public String getAcdGroup() {
        return acdGroup;
    }

    public void setAcdGroup(String acdGroup) {
        this.acdGroup = acdGroup;
    }

    @Column(name = "CALL_TYPE")
    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    @Column(name = "CALL_RESULT")
    public String getCallResult() {
        return callResult;
    }

    public void setCallResult(String callResult) {
        this.callResult = callResult;
    }

    @Column(name = "DEPT_ID")
    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }
}



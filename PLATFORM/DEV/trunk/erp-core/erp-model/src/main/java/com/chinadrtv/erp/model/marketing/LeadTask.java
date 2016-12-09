/*
 * @(#)LeadTask.java 1.0 2013-5-9上午10:07:39
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.model.marketing;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.chinadrtv.erp.model.Contact;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-5-9 上午10:07:39
 * 
 */
@Table(name = "LEAD_TASK", schema = "ACOAPP_MARKETING")
@Entity
public class LeadTask implements Serializable {

	private Long id;
	private Long leadId;
	private String bpmInstanceId;
	private String bpmTaskId;
	private String userid;
	private String contactId;
	private String status;
	private String careateUser;
	private Date createDate;
	private String updateUser;
	private Date updateDate;
	private String remark;
	private String pdCustomerId;
	private Date appointDate;
    private Long sourceType;
    private Long sourceType2;
    private Long sourceType3;
    
    private Long isDone;
    
    private Long leadTypeId;
    
	private Long isPotential;

	private Contact contact;
	private Lead Lead;

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_LEAD_TASK")
	@SequenceGenerator(name = "SEQ_LEAD_TASK", sequenceName = "ACOAPP_MARKETING.SEQ_LEAD_TASK", allocationSize = 1)
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
	 * @return the bpmInstanceId
	 */
	@Column(name = "BPM_INSTANCE_ID")
	public String getBpmInstanceId() {
		return bpmInstanceId;
	}

	/**
	 * @param bpmInstanceId
	 *            the bpmInstanceId to set
	 */
	public void setBpmInstanceId(String bpmInstanceId) {
		this.bpmInstanceId = bpmInstanceId;
	}

	/**
	 * @return the userid
	 */
	@Column(name = "USERID")
	public String getUserid() {
		return userid;
	}

	/**
	 * @param userid
	 *            the userid to set
	 */
	public void setUserid(String userid) {
		this.userid = userid;
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
	 * @return the careateUser
	 */
	@Column(name = "CREATE_USER")
	public String getCareateUser() {
		return careateUser;
	}

	/**
	 * @param careateUser
	 *            the careateUser to set
	 */
	public void setCareateUser(String careateUser) {
		this.careateUser = careateUser;
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

	@Column(name = "BPM_COMMENT")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "PD_CUSTOMER_ID")
	public String getPdCustomerId() {
		return pdCustomerId;
	}

	public void setPdCustomerId(String pdCustomerId) {
		this.pdCustomerId = pdCustomerId;
	}

	@Column(name = "START_DATE")
	public Date getAppointDate() {
		return appointDate;
	}

	public void setAppointDate(Date appointDate) {
		this.appointDate = appointDate;
	}

	public LeadTask() {
		super();
	}

	/**
	 * @return the lead
	 */
	@ManyToOne
	@JoinColumn(name = "LEAD_ID", referencedColumnName = "id", insertable = false, updatable = false)
	public Lead getLead() {
		return Lead;
	}

	/**
	 * @param lead
	 *            the lead to set
	 */
	public void setLead(Lead lead) {
		Lead = lead;
	}

	/**
	 * @return the bpmTaskId
	 */
	@Column(name = "BPM_TASK_ID")
	public String getBpmTaskId() {
		return bpmTaskId;
	}

	/**
	 * @param bpmTaskId
	 *            the bpmTaskId to set
	 */
	public void setBpmTaskId(String bpmTaskId) {
		this.bpmTaskId = bpmTaskId;
	}

    @Column(name = "SOURCE_TYPE")
    public Long getSourceType() {
        return sourceType;
    }

    public void setSourceType(Long sourceType) {
        this.sourceType = sourceType;
    }
    
    @Column(name = "SOURCE_TYPE2")
    public Long getSourceType2() {
		return sourceType2;
	}

	public void setSourceType2(Long sourceType2) {
		this.sourceType2 = sourceType2;
	}

    @Column(name = "SOURCE_TYPE3")
	public Long getSourceType3() {
		return sourceType3;
	}

	public void setSourceType3(Long sourceType3) {
		this.sourceType3 = sourceType3;
	}

	@Column(name = "IS_DONE")
    public Long getIsDone() {
		return isDone;
	}

	public void setIsDone(Long isDone) {
		this.isDone = isDone;
	}

	@Column(name = "LEAD_TYPE_ID")
	public Long getLeadTypeId() {
		return leadTypeId;
	}

	public void setLeadTypeId(Long leadTypeId) {
		this.leadTypeId = leadTypeId;
	}

	@Column(name = "IS_POTENTIAL")
	public Long getIsPotential() {
		return isPotential;
	}

	public void setIsPotential(Long isPotential) {
		this.isPotential = isPotential;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CONTACT_ID", referencedColumnName = "CONTACTID", insertable = false, updatable = false)
	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

}

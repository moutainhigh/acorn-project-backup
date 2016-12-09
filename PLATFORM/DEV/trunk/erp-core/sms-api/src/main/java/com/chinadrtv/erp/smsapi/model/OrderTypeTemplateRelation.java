/*
 * @(#)OrderTypeTemplateRelation.java 1.0 2013-5-24下午2:14:52
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-5-24 下午2:14:52
 * 
 */
@Entity
@Table(name = "ORDERTYPE_TEMPLATE_RELATION", schema = "ACOAPP_MARKETING")
public class OrderTypeTemplateRelation implements Serializable {

	private Long id;
	private Long templateId;
	private String createUser;
	private Date createDate;
	private String updateUser;
	private Date updateDate;
	private String departmentId;
	private String status;
	private String orderType;
	private String delayFlag;
	private String delayTime;
	private SmsTemplate smsTemplate;

	public OrderTypeTemplateRelation() {

	}

	/**
	 * @return the id
	 */
	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "generator", sequenceName = "	SEQ_ORDERTYPE_TEMPLATE", allocationSize = 1)
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
	 * @return the templateId
	 */
	@Column(name = "TEMPLATE_ID")
	public Long getTemplateId() {
		return templateId;
	}

	/**
	 * @param templateId
	 *            the templateId to set
	 */
	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
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
	 * @return the departmentId
	 */
	@Column(name = "DEPARMENT_ID")
	public String getDepartmentId() {
		return departmentId;
	}

	/**
	 * @param departmentId
	 *            the departmentId to set
	 */
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
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
	 * @return the delayFlag
	 */
	@Column(name = "DELAY_FLAG")
	public String getDelayFlag() {
		return delayFlag;
	}

	/**
	 * @param delayFlag
	 *            the delayFlag to set
	 */
	public void setDelayFlag(String delayFlag) {
		this.delayFlag = delayFlag;
	}

	/**
	 * @return the delayTime
	 */
	@Column(name = "DELAY_TIME")
	public String getDelayTime() {
		return delayTime;
	}

	/**
	 * @param delayTime
	 *            the delayTime to set
	 */
	public void setDelayTime(String delayTime) {
		this.delayTime = delayTime;
	}

	/**
	 * @return the smsTemplate
	 */
	@ManyToOne
	@JoinColumn(name = "TEMPLATE_ID", referencedColumnName = "id", insertable = false, updatable = false)
	public SmsTemplate getSmsTemplate() {
		return smsTemplate;
	}

	/**
	 * @param smsTemplate
	 *            the smsTemplate to set
	 */
	public void setSmsTemplate(SmsTemplate smsTemplate) {
		this.smsTemplate = smsTemplate;
	}

}

/*
 * @(#)RecommendConfDto.java 1.0 2013-3-8上午11:00:55
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * none</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-3-8 上午11:00:55
 * 
 */
public class RecommendConfDto {

	private String department;
	private String groupId;
	private String customerLevel;
	private String pdCustomerId;
	private String contactId;
	private Date validStart;
	private Date validEnd;
	private String userId;
	private String agentLevel;
	private String businessKey;
	private List<ProductObject> productIds;

	private String isFinish;

	/**
	 * @return the department
	 */
	// @Column(name = "department")
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
	 * @return the groupId
	 */
	// @Column(name = "groupId")
	public String getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId
	 *            the groupId to set
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the customerLevel
	 */
	// @Column(name = "customerLevel")
	public String getCustomerLevel() {
		return customerLevel;
	}

	/**
	 * @param customerLevel
	 *            the customerLevel to set
	 */
	public void setCustomerLevel(String customerLevel) {
		this.customerLevel = customerLevel;
	}

	/**
	 * @return the validStart
	 */
	// @Column(name = "validStart")
	public Date getValidStart() {
		return validStart;
	}

	/**
	 * @param validStart
	 *            the validStart to set
	 */
	public void setValidStart(Date validStart) {
		this.validStart = validStart;
	}

	/**
	 * @return the validEnd
	 */
	// @Column(name = "validEnd")
	public Date getValidEnd() {
		return validEnd;
	}

	/**
	 * @param validEnd
	 *            the validEnd to set
	 */
	public void setValidEnd(Date validEnd) {
		this.validEnd = validEnd;
	}

	/**
	 * @return the userId
	 */
	// @Column(name = "userId")
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the agentLevel
	 */
	// @Column(name = "agentLevel")
	public String getAgentLevel() {
		return agentLevel;
	}

	/**
	 * @param agentLevel
	 *            the agentLevel to set
	 */
	public void setAgentLevel(String agentLevel) {
		this.agentLevel = agentLevel;
	}

	/**
	 * @return the contactId
	 */
	// @Column(name = "contactId")
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
	 * @return the businessKey
	 */
	// @Column(name = "businessKey")
	public String getBusinessKey() {
		return businessKey;
	}

	/**
	 * @param businessKey
	 *            the businessKey to set
	 */
	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	/**
	 * @return the productId
	 */
	// @Column(name = "productId")
	public List<ProductObject> getProductIds() {
		return productIds;
	}

	/**
	 * @param productId
	 *            the productId to set
	 */
	public void setProductIds(List<ProductObject> productIds) {
		this.productIds = productIds;
	}

	/**
	 * @return the isFinish
	 */
	// @Column(name = "isFinish")
	public String getIsFinish() {
		return isFinish;
	}

	/**
	 * @param isFinish
	 *            the isFinish to set
	 */
	public void setIsFinish(String isFinish) {
		this.isFinish = isFinish;
	}

	/**
	 * @return the pdCustomerId
	 */
	// @Column(name = "pdCustomerId")
	public String getPdCustomerId() {
		return pdCustomerId;
	}

	/**
	 * @param pdCustomerId
	 *            the pdCustomerId to set
	 */
	public void setPdCustomerId(String pdCustomerId) {
		this.pdCustomerId = pdCustomerId;
	}

}

/*
 * @(#)DiscourseDto.java 1.0 2013-4-8上午9:40:23
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.dto;

import java.util.Date;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-4-8 上午9:40:23
 * 
 */
public class DiscourseDto {

	private Long id;
	private String discourseName;
	private String createTime;
	private String creator;
	private String status;
	private String productCode;
	private String productName;
	private Date modifyTime;
	private String modifyer;
	private String discourseHtmlUrl;
	private String discourseHtmlContent;
	private String startTime;
	private String endTime;
	private String department;

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
	 * @return the discourseName
	 */
	public String getDiscourseName() {
		return discourseName;
	}

	/**
	 * @param discourseName
	 *            the discourseName to set
	 */
	public void setDiscourseName(String discourseName) {
		this.discourseName = discourseName;
	}

	/**
	 * @return the createTime
	 */
	public String getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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
	 * @return the status
	 */
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
	 * @return the productCode
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * @param productCode
	 *            the productCode to set
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName
	 *            the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the modifyTime
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * @param modifyTime
	 *            the modifyTime to set
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * @return the modifyer
	 */
	public String getModifyer() {
		return modifyer;
	}

	/**
	 * @param modifyer
	 *            the modifyer to set
	 */
	public void setModifyer(String modifyer) {
		this.modifyer = modifyer;
	}

	/**
	 * @return the discourseHtmlUrl
	 */
	public String getDiscourseHtmlUrl() {
		return discourseHtmlUrl;
	}

	/**
	 * @param discourseHtmlUrl
	 *            the discourseHtmlUrl to set
	 */
	public void setDiscourseHtmlUrl(String discourseHtmlUrl) {
		this.discourseHtmlUrl = discourseHtmlUrl;
	}

	/**
	 * @return the discourseHtmlContent
	 */
	public String getDiscourseHtmlContent() {
		return discourseHtmlContent;
	}

	/**
	 * @param discourseHtmlContent
	 *            the discourseHtmlContent to set
	 */
	public void setDiscourseHtmlContent(String discourseHtmlContent) {
		this.discourseHtmlContent = discourseHtmlContent;
	}

	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public DiscourseDto() {
		super();
	}

	/**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * @param department the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}
}

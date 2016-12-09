/*
 * @(#)Discourse.java 1.0 2013-4-7下午6:15:15
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
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Type;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-4-7 下午6:15:15
 * 
 */
@javax.persistence.Table(name = "DISCOURSE_TEMPLATE", schema = "ACOAPP_MARKETING")
@Entity
public class Discourse implements Serializable {

	private Long id;
	private String discourseName;
	private Date createTime;
	private String creator;
	private String status;
	private String productCode;
	private String productName;
	private Date modifyTime;
	private String modifyer;
	private String discourseHtmlUrl;
	private String discourseHtmlContent;
	private String department;
	private String departmentCode;

	public Discourse() {
		super();
	}

	/**
	 * @return the id
	 */
	@Id
	@javax.persistence.Column(name = "ID")
	@SequenceGenerator(name = "generator", sequenceName = "ACOAPP_MARKETING.SEQ_DISCOURSE", allocationSize = 1)
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
	 * @return the discourseName
	 */
	@javax.persistence.Column(name = "DISCOURSE_NAME")
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
	@javax.persistence.Column(name = "CREATETIME")
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the creator
	 */
	@javax.persistence.Column(name = "CREATOR")
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
	@javax.persistence.Column(name = "STATUS")
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
	@javax.persistence.Column(name = "PRODUCT_CODE")
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
	@javax.persistence.Column(name = "PRODUCT_NAME")
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
	@javax.persistence.Column(name = "MODIFYTIME")
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
	@javax.persistence.Column(name = "MODIFYER")
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
	@javax.persistence.Column(name = "DISCOURSE_HTML_URL")
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
	@Lob
	@Type(type = "text")
	@Column(name = "DISCOURSE_HTML_CONTENT", columnDefinition = "CLOB")
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
	 * @return the department
	 */
	@javax.persistence.Column(name = "DEPARTMENT")
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
	 * @return the departmentCode
	 */
	@javax.persistence.Column(name = "DEPARTMENT_CODE")
	public String getDepartmentCode() {
		return departmentCode;
	}

	/**
	 * @param departmentCode
	 *            the departmentCode to set
	 */
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

}

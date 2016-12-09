/*
 * @(#)KnowledgeArticle.java 1.0 2013-11-6上午10:53:07
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.knowledge.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-11-6 上午10:53:07
 * 
 */
@Entity
@Table(name = "KNOWLEDGE_ARTICLE", schema = "ACOAPP_MARKETING")
public class KnowledgeArticle implements Serializable {

	private Long id;
	private String title;
	private Long categoryId;
	private String content;
	private String department;
	private String groupId;
	private String path;
	private String status;
	private String productCode;
	private String createUser;
	private Date createDate;
	private String updateUser;
	private Date updateDate;
	private String departType;
	private String shortPinyin;
	private String discouse;
	private String type;
	private String productName;
	private String discoursePath;
	private KnowledgeCategory knowledgeCategory;
	private String discourseHtml;
	private String contentHtml;
	private String relationshipIds;

	/**
	 * @return the id
	 */
	@Id
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
	 * @return the title
	 */
	@Column(name = "TITLE")
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the categoryId
	 */
	@Column(name = "CATEGORY_ID")
	public Long getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId
	 *            the categoryId to set
	 */
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * @return the content
	 */
	@Column(name = "CONTENT")
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
	 * @return the department
	 */
	@Column(name = "DEPARTMENT")
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
	@Column(name = "GROUP_ID")
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
	 * @return the path
	 */
	@Column(name = "PATH")
	public String getPath() {
		return path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path) {
		this.path = path;
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
	 * @return the productCode
	 */
	@Column(name = "PRODUCT_CODE")
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
	 * @return the departType
	 */
	@Column(name = "DEPART_TYPE")
	public String getDepartType() {
		return departType;
	}

	/**
	 * @param departType
	 *            the departType to set
	 */
	public void setDepartType(String departType) {
		this.departType = departType;
	}

	/**
	 * @return the shortPinyin
	 */
	@Column(name = "SHORT_PINYIN")
	public String getShortPinyin() {
		return shortPinyin;
	}

	/**
	 * @param shortPinyin
	 *            the shortPinyin to set
	 */
	public void setShortPinyin(String shortPinyin) {
		this.shortPinyin = shortPinyin;
	}

	/**
	 * @return the discouse
	 */
	@Column(name = "DISCOURSE")
	public String getDiscouse() {
		return discouse;
	}

	/**
	 * @param discouse
	 *            the discouse to set
	 */
	public void setDiscouse(String discouse) {
		this.discouse = discouse;
	}

	/**
	 * @return the type
	 */
	@Column(name = "TYPE")
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the productName
	 */
	@Column(name = "PRODUCT_NAME")
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
	 * @return the discoursePath
	 */
	@Column(name = "DISCOURSE_PATH")
	public String getDiscoursePath() {
		return discoursePath;
	}

	/**
	 * @param discoursePath
	 *            the discoursePath to set
	 */
	public void setDiscoursePath(String discoursePath) {
		this.discoursePath = discoursePath;
	}

	/**
	 * @return the knowledgeCategory
	 */
	@ManyToOne
	@JoinColumn(name = "CATEGORY_ID", referencedColumnName = "id", insertable = false, updatable = false)
	public KnowledgeCategory getKnowledgeCategory() {
		return knowledgeCategory;
	}

	/**
	 * @param knowledgeCategory
	 *            the knowledgeCategory to set
	 */
	public void setKnowledgeCategory(KnowledgeCategory knowledgeCategory) {
		this.knowledgeCategory = knowledgeCategory;
	}

	/**
	 * @return the discourseHtml
	 */
	@Transient
	public String getDiscourseHtml() {
		return discourseHtml;
	}

	/**
	 * @param discourseHtml
	 *            the discourseHtml to set
	 */
	public void setDiscourseHtml(String discourseHtml) {
		this.discourseHtml = discourseHtml;
	}

	/**
	 * @return the contentHtml
	 */
	@Transient
	public String getContentHtml() {
		return contentHtml;
	}

	/**
	 * @param contentHtml
	 *            the contentHtml to set
	 */
	public void setContentHtml(String contentHtml) {
		this.contentHtml = contentHtml;
	}

	/**
	 * @return the relationshipIds
	 */
	@Column(name = "RELATIONSHIP_IDS")
	public String getRelationshipIds() {
		return relationshipIds;
	}

	/**
	 * @param relationshipIds
	 *            the relationshipIds to set
	 */
	public void setRelationshipIds(String relationshipIds) {
		this.relationshipIds = relationshipIds;
	}

}

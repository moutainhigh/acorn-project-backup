/*
 * @(#)KnowledgeFavoriteArticle.java 1.0 2013-12-3下午1:59:31
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.knowledge.model;

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
 * @since 2013-12-3 下午1:59:31
 * 
 */
@Entity
@Table(name = "KNOWLEDGE_FAVORITE_ARTICLE", schema = "ACOAPP_MARKETING")
public class KnowledgeFavoriteArticle implements Serializable {

	private Long id;
	private Long categoryId;
	private Long articleId;
	private String createUser;
	private Date createDate;
	private String type;
	private String status;
	private String deptType;
	private KnowledgeArticle knowledgeArticle;

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FAVORITE_ARTICLE_SEQ_GEN")
	@SequenceGenerator(name = "FAVORITE_ARTICLE_SEQ_GEN", sequenceName = "ACOAPP_MARKETING.SEQ_KNOWLEDGE_FAVORITE_ARTICLE", allocationSize = 1)
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
	 * @return the articleId
	 */
	@Column(name = "ARTICLE_ID")
	public Long getArticleId() {
		return articleId;
	}

	/**
	 * @param articleId
	 *            the articleId to set
	 */
	public void setArticleId(Long articleId) {
		this.articleId = articleId;
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
	 * @return the knowledgeArticle
	 */
	@ManyToOne
	@JoinColumn(name = "ARTICLE_ID", referencedColumnName = "id", insertable = false, updatable = false)
	public KnowledgeArticle getKnowledgeArticle() {
		return knowledgeArticle;
	}

	/**
	 * @param knowledgeArticle
	 *            the knowledgeArticle to set
	 */
	public void setKnowledgeArticle(KnowledgeArticle knowledgeArticle) {
		this.knowledgeArticle = knowledgeArticle;
	}

	/**
	 * @return the deptType
	 */
	@Column(name = "DEPART_TYPE")
	public String getDeptType() {
		return deptType;
	}

	/**
	 * @param deptType
	 *            the deptType to set
	 */
	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}

}

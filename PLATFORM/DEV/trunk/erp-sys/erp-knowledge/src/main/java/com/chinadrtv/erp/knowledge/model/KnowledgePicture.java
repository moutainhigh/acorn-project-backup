/*
 * @(#)KnowledgePicture.java 1.0 2013-11-8下午2:12:54
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-11-8 下午2:12:54
 * 
 */
@Entity
@Table(name = "KNOWLEDGE_PICTURE", schema = "ACOAPP_MARKETING")
public class KnowledgePicture implements Serializable {

	private Long id;
	private Long articleId;
	private String pictureName;
	private String url;
	private String createUser;
	private Date createDate;
	private String updateUser;
	private Date updateDate;
	private String pictureType;

	/**
	 * @return the id
	 */
	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "generator", sequenceName = "ACOAPP_MARKETING.SEQ_KNOWLEDGE_PICTURE", allocationSize = 1)
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
	 * @return the pictureName
	 */
	@Column(name = "PICTURE_NAME")
	public String getPictureName() {
		return pictureName;
	}

	/**
	 * @param pictureName
	 *            the pictureName to set
	 */
	public void setPictureName(String pictureName) {
		this.pictureName = pictureName;
	}

	/**
	 * @return the url
	 */
	@Column(name = "URL")
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
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
	 * @return the pictureType
	 */
	@Column(name = "PICTURE_TYPE")
	public String getPictureType() {
		return pictureType;
	}

	/**
	 * @param pictureType
	 *            the pictureType to set
	 */

	public void setPictureType(String pictureType) {
		this.pictureType = pictureType;
	}

}

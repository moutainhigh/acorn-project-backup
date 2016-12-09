/*
 * @(#)CampaginTypeParams.java 1.0 2013-4-17上午10:39:54
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-4-17 上午10:39:54
 * 
 */
@Table(name = "CAMPAIGN_TYPE_PARAMS", schema = "ACOAPP_MARKETING")
@Entity
public class CampaignTypeParams implements Serializable {

	private String id;
	private String name;
	private String type;
	private String createUser;
	private Date createDate;
	private String updateUser;
	private Date updateDate;
	private Long campaignTypeId;
	private String code;

	/**
	 * @return the id
	 */
	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "generator", sequenceName = "ACOAPP_MARKETING.SEQ_CAMPAIGN_TYPE_PARAMS")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the addUser
	 */
	@Column(name = "CREATE_USER")
	public String getCreateUser() {
		return createUser;
	}

	/**
	 * @param addUser
	 *            the addUser to set
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	/**
	 * @return the addDate
	 */
	@Column(name = "Create_DATE")
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param addDate
	 *            the addDate to set
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
	 * @return the campaignTypeId
	 */
	@Column(name = "CAMPAIGN_TYPE_ID")
	public Long getCampaignTypeId() {
		return campaignTypeId;
	}

	/**
	 * @param campaignTypeId
	 *            the campaignTypeId to set
	 */
	public void setCampaignTypeId(Long campaignTypeId) {
		this.campaignTypeId = campaignTypeId;
	}

	/**
	 * @return the code
	 */
	@Column(name = "CODE")
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

}

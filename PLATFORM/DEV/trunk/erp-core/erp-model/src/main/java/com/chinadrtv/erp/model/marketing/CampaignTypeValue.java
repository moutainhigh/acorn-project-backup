/*
 * @(#)CampaignTypeValue.java 1.0 2013-4-17上午10:49:16
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
 * @since 2013-4-17 上午10:49:16
 * 
 */
@Table(name = "CAMPAIGN_TYPE_VALUE", schema = "ACOAPP_MARKETING")
@Entity
public class CampaignTypeValue implements Serializable {

	/**
	 * ID CHAR(18) not null, VALUE VARCHAR2(50), CAMPAIGN_TYPE_PARAMS_ID
	 * CHAR(18), CAMPAIGN_ID NUMBER(19), ADD_USER VARCHAR2(50), ADD_DATE DATE,
	 * UPDATE_USER DATE, UPDATE_DATE DATE, CODE VARCHAR2(50)
	 */

	private Long id;
	private String value;
	private String campaignTypeParamsId;
	private Long campaignId;
	private String createUser;
	private Date createDate;
	private String updateUser;
	private Date updateDate;
	private String code;

	public CampaignTypeValue() {

	}

	/**
	 * @return the id
	 */
	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "generator", sequenceName = "ACOAPP_MARKETING.SEQ_CAMPAIGN_TYPE_VALUE")
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
	 * @return the value
	 */
	@Column(name = "VALUE")
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the campaignTypeParamsId
	 */
	@Column(name = "CAMPAIGN_TYPE_PARAMS_ID")
	public String getCampaignTypeParamsId() {
		return campaignTypeParamsId;
	}

	/**
	 * @param campaignTypeParamsId
	 *            the campaignTypeParamsId to set
	 */
	public void setCampaignTypeParamsId(String campaignTypeParamsId) {
		this.campaignTypeParamsId = campaignTypeParamsId;
	}

	/**
	 * @return the campaignId
	 */
	@Column(name = "CAMPAIGN_ID")
	public Long getCampaignId() {
		return campaignId;
	}

	/**
	 * @param campaignId
	 *            the campaignId to set
	 */
	public void setCampaignId(Long campaignId) {
		this.campaignId = campaignId;
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
	@Column(name = "CREATE_DATE")
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
	 * @return the code
	 */
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

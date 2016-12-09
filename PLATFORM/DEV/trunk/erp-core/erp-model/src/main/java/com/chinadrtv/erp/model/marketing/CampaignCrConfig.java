/*
 * @(#)CampaignCrConfig.java 1.0 2013-9-9上午10:20:32
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.model.marketing;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-9-9 上午10:20:32
 * 
 */
@Table(name = "CAMPAIGN_CR_CONFIG", schema = "ACOAPP_MARKETING")
@Entity
public class CampaignCrConfig implements Serializable {

	private Long id;
	private String userId;
	private Long marketingType;
	private String marketingDepartment;
	private String department;

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
	 * @return the userId
	 */
	@Column(name = "USER_ID")
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
	 * @return the marketingType
	 */
	@Column(name = "MARKETING_TYPE")
	public Long getMarketingType() {
		return marketingType;
	}

	/**
	 * @param marketingType
	 *            the marketingType to set
	 */
	public void setMarketingType(Long marketingType) {
		this.marketingType = marketingType;
	}

	/**
	 * @return the marketingDepartment
	 */
	@Column(name = "MARKETING_DEPARTMENT")
	public String getMarketingDepartment() {
		return marketingDepartment;
	}

	/**
	 * @param marketingDepartment
	 *            the marketingDepartment to set
	 */
	public void setMarketingDepartment(String marketingDepartment) {
		this.marketingDepartment = marketingDepartment;
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

}

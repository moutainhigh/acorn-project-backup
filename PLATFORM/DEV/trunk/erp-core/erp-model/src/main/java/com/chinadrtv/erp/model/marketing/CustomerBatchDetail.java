/*
 * @(#)CustomerSqlSource.java 1.0 2013-1-21下午2:09:48
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.model.marketing;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * 客户群管理——批次内容</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-1-21 下午2:09:48
 * 
 */
@Table(name = "CUSTOMER_DETAIL", schema = "ACOAPP_MARKETING")
@Entity
public class CustomerBatchDetail {

	private Long id;
	private String batchCode;
	private String customerId;
	private Date crtDate;
	private String groupCode;
	private String contactInfo;
	private String statisInfo;

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CUSTOMER_BATCH_DETAIL")
	@SequenceGenerator(name = "SEQ_CUSTOMER_BATCH_DETAIL", sequenceName = "ACOAPP_MARKETING.SEQ_CUSTOMER_BATCH_DETAIL")
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

	@Column(name = "BATCH_CODE")
	public String getBatchCode() {
		return batchCode;
	}

	/**
	 * @param batchCode
	 *            the batchCode to set
	 */
	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}

	/**
	 * @return the groupCode
	 */
	@Column(name = "GROUP_CODE")
	public String getGroupCode() {
		return groupCode;
	}

	/**
	 * @param groupCode
	 *            the groupCode to set
	 */
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	/**
	 * @return the customerId
	 */
	@Column(name = "CUSTOMER_ID")
	public String getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId
	 *            the customerId to set
	 */
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	/**
	 * @return the crtDate
	 */
	@Column(name = "CRT_DATE")
	public Date getCrtDate() {
		return crtDate;
	}

	/**
	 * @param crtDate
	 *            the crtDate to set
	 */
	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}

	/**
	 * @return the contactInfo
	 */
	@Column(name = "CONTACT_INFO")
	public String getContactInfo() {
		return contactInfo;
	}

	/**
	 * @param contactInfo
	 *            the contactInfo to set
	 */
	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}

	/**
	 * @return the statisInfo
	 */
	@Column(name = "STATIS_INFO")
	public String getStatisInfo() {
		return statisInfo;
	}

	/**
	 * @param statisInfo
	 *            the statisInfo to set
	 */
	public void setStatisInfo(String statisInfo) {
		this.statisInfo = statisInfo;
	}

	/*
	 * (非 Javadoc) <p>Title: hashCode</p> <p>Description: </p>
	 * 
	 * @return
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((batchCode == null) ? 0 : batchCode.hashCode());
		result = prime * result
				+ ((contactInfo == null) ? 0 : contactInfo.hashCode());
		result = prime * result + ((crtDate == null) ? 0 : crtDate.hashCode());
		result = prime * result
				+ ((customerId == null) ? 0 : customerId.hashCode());
		result = prime * result
				+ ((groupCode == null) ? 0 : groupCode.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((statisInfo == null) ? 0 : statisInfo.hashCode());
		return result;
	}

	/*
	 * (非 Javadoc) <p>Title: equals</p> <p>Description: </p>
	 * 
	 * @param obj
	 * 
	 * @return
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerBatchDetail other = (CustomerBatchDetail) obj;
		if (batchCode == null) {
			if (other.batchCode != null)
				return false;
		} else if (!batchCode.equals(other.batchCode))
			return false;
		if (contactInfo == null) {
			if (other.contactInfo != null)
				return false;
		} else if (!contactInfo.equals(other.contactInfo))
			return false;
		if (crtDate == null) {
			if (other.crtDate != null)
				return false;
		} else if (!crtDate.equals(other.crtDate))
			return false;
		if (customerId == null) {
			if (other.customerId != null)
				return false;
		} else if (!customerId.equals(other.customerId))
			return false;
		if (groupCode == null) {
			if (other.groupCode != null)
				return false;
		} else if (!groupCode.equals(other.groupCode))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (statisInfo == null) {
			if (other.statisInfo != null)
				return false;
		} else if (!statisInfo.equals(other.statisInfo))
			return false;
		return true;
	}

}

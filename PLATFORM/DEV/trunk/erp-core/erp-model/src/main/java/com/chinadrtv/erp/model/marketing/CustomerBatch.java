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
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * 客户群管理——批次</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-1-21 下午2:09:48
 * 
 */
@Table(name = "CUSTOMER_BATCH", schema = "ACOAPP_MARKETING")
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class CustomerBatch {

	private String batchCode;
	private String type;
	private Date genDate;
	private Long count;
	private Long available;// AVAILABLE;
	private String groupCode;

	/**
	 * @return the batchCode
	 */

	/**
	 * @return the id
	 */
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "assigned")
	@GeneratedValue(generator = "idGenerator")
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
	 * @return the genDate
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	@Column(name = "GEN_DATE")
	public Date getGenDate() {
		return genDate;
	}

	/**
	 * @param genDate
	 *            the genDate to set
	 */
	public void setGenDate(Date genDate) {
		this.genDate = genDate;
	}

	/**
	 * @return the count
	 */
	@Column(name = "COUNT")
	public Long getCount() {
		return count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(Long count) {
		this.count = count;
	}

	/**
	 * @return the available
	 */
	@Column(name = "AVAILABLE")
	public Long getAvailable() {
		return available;
	}

	/**
	 * @param available
	 *            the available to set
	 */
	public void setAvailable(Long available) {
		this.available = available;
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
				+ ((available == null) ? 0 : available.hashCode());
		result = prime * result
				+ ((batchCode == null) ? 0 : batchCode.hashCode());
		result = prime * result + ((count == null) ? 0 : count.hashCode());
		result = prime * result + ((genDate == null) ? 0 : genDate.hashCode());
		result = prime * result
				+ ((groupCode == null) ? 0 : groupCode.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		CustomerBatch other = (CustomerBatch) obj;
		if (available == null) {
			if (other.available != null)
				return false;
		} else if (!available.equals(other.available))
			return false;
		if (batchCode == null) {
			if (other.batchCode != null)
				return false;
		} else if (!batchCode.equals(other.batchCode))
			return false;
		if (count == null) {
			if (other.count != null)
				return false;
		} else if (!count.equals(other.count))
			return false;
		if (genDate == null) {
			if (other.genDate != null)
				return false;
		} else if (!genDate.equals(other.genDate))
			return false;
		if (groupCode == null) {
			if (other.groupCode != null)
				return false;
		} else if (!groupCode.equals(other.groupCode))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

}

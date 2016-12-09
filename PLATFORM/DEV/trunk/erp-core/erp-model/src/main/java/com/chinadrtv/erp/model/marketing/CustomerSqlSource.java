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
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;

/**
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * 客户群管理——sql数据源历史表</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-1-21 下午2:09:48
 * 
 */
@Table(name = "CUSTOMER_SQLSOUCE", schema = "ACOAPP_MARKETING")
@Entity
public class CustomerSqlSource {

	private Long id;
	private String sqlContent;
	private Date crtDate;
	private String crtUser;
	// private String groupCode;

	@JsonBackReference
	private CustomerGroup group;

	/**
	 * @return the id
	 */
	@Id
	@SequenceGenerator(name = "SEQ_CUSTOMER_SQLSOUCE", sequenceName = "ACOAPP_MARKETING.SEQ_CUSTOMER_SQLSOUCE")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CUSTOMER_SQLSOUCE")
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
	 * @return the sqlContent
	 */
	@Column(name = "SQL_CONTENT")
	public String getSqlContent() {
		return sqlContent;
	}

	/**
	 * @param sqlContent
	 *            the sqlContent to set
	 */
	public void setSqlContent(String sqlContent) {
		this.sqlContent = sqlContent;
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
	 * @return the crtUser
	 */
	@Column(name = "CRT_USER")
	public String getCrtUser() {
		return crtUser;
	}

	/**
	 * @param crtUser
	 *            the crtUser to set
	 */
	public void setCrtUser(String crtUser) {
		this.crtUser = crtUser;
	}

	/**
	 * @return the group
	 */
	@OneToOne
	@JoinColumn(name = "GROUP_CODE", insertable = true, updatable = true)
	public CustomerGroup getGroup() {
		return group;
	}

	/**
	 * @param group
	 *            the group to set
	 */
	public void setGroup(CustomerGroup group) {
		this.group = group;
	}

	// /**
	// * @return the groupCode
	// */
	// @Column(name="GROUP_CODE")
	// public String getGroupCode() {
	// return groupCode;
	// }
	// /**
	// * @param groupCode the groupCode to set
	// */
	// public void setGroupCode(String groupCode) {
	// this.groupCode = groupCode;
	// }
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
		result = prime * result + ((crtDate == null) ? 0 : crtDate.hashCode());
		result = prime * result + ((crtUser == null) ? 0 : crtUser.hashCode());
		// result = prime * result
		// + ((groupCode == null) ? 0 : groupCode.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((sqlContent == null) ? 0 : sqlContent.hashCode());
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
		CustomerSqlSource other = (CustomerSqlSource) obj;
		if (crtDate == null) {
			if (other.crtDate != null)
				return false;
		} else if (!crtDate.equals(other.crtDate))
			return false;
		if (crtUser == null) {
			if (other.crtUser != null)
				return false;
		} else if (!crtUser.equals(other.crtUser))
			return false;
		// if (groupCode == null) {
		// if (other.groupCode != null)
		// return false;
		// } else if (!groupCode.equals(other.groupCode))
		// return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (sqlContent == null) {
			if (other.sqlContent != null)
				return false;
		} else if (!sqlContent.equals(other.sqlContent))
			return false;
		return true;
	}

}

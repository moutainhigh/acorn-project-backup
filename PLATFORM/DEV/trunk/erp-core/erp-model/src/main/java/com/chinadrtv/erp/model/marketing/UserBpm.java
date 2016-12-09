/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
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
 * 2013-5-24 下午3:41:20
 * @version 1.0.0
 * @author yangfei
 *
 */
@Table(name = "USER_BPM", schema = "ACOAPP_MARKETING")
@Entity
public class UserBpm implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String busiType;
	private String contactID;
	private String orderID;
	private String status;
	private String department;
	private String workGrp;
	private String createUser;
	private Date createDate;
	private Long createUserPriority;

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USER_BPM")
	@SequenceGenerator(name = "SEQ_USER_BPM", sequenceName = "ACOAPP_MARKETING.SEQ_USER_BPM", allocationSize = 1)
	@Column(name = "ID")
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "BUSI_TYPE")
	public String getBusiType() {
		return busiType;
	}

	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}

	@Column(name = "CONTACT_ID")
	public String getContactID() {
		return contactID;
	}

	public void setContactID(String contactID) {
		this.contactID = contactID;
	}

	@Column(name = "ORDER_ID")
	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	@Column(name = "STATUS")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "DEPARTMENT")
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	@Column(name = "WORK_GROUP")
	public String getWorkGrp() {
		return workGrp;
	}

	public void setWorkGrp(String workGrp) {
		this.workGrp = workGrp;
	}

	@Column(name = "CREATE_USER")
	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	@Column(name = "CREATE_DATE")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "CREATE_USER_PRIORITY")
	public Long getCreateUserPriority() {
		return createUserPriority;
	}

	public void setCreateUserPriority(Long createUserPriority) {
		this.createUserPriority = createUserPriority;
	}

}

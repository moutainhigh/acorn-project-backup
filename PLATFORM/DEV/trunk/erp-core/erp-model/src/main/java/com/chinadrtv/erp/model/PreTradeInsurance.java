package com.chinadrtv.erp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

/**
* 前置保险商品
* User: 郭永宏
* Date: 2013-06-07
* 橡果国际-系统集成部
* Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
*/
@Entity
@Table(name = "PRE_TRADE_INSURANCE", schema = "ACOAPP_OMS")
public class PreTradeInsurance implements java.io.Serializable {

	private Long id;
	private String outSkuId;
	private String isActive;
	private String createUser;
	private Date createDate;
	private String updateUser;
	private Date updateDate;

	// Constructors

	/** default constructor */
	public PreTradeInsurance() {
	}

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRE_TRADE_INSURANCE_SEQ")
    @SequenceGenerator(name = "PRE_TRADE_INSURANCE_SEQ", sequenceName = "PRE_TRADE_INSURANCE_SEQ")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "OUT_SKU_ID", length = 20)
	public String getOutSkuId() {
		return this.outSkuId;
	}

	public void setOutSkuId(String accountCode) {
		this.outSkuId = accountCode;
	}

	@Column(name = "ISACTIVE")
	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String accountName) {
		this.isActive = accountName;
	}

	@Column(name = "CREATE_USER", length = 50)
	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DATE", length = 7)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "UPDATE_USER", length = 50)
	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATE_DATE", length = 7)
	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}
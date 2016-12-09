package com.chinadrtv.erp.model.trade;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.chinadrtv.erp.model.Company;
import com.chinadrtv.erp.model.Warehouse;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 半拒收确认表头
 * User: 王健
 * Date: 2013-03-28
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Entity
@Table(name = "REFUND_HEAD", schema = "ACOAPP_OMS")
public class RefundHead implements java.io.Serializable {

	private static final long serialVersionUID = -4206873188776606771L;
	private Long id;
	private Warehouse warehouse;
	private Company company;
	private String frfundType;
	private String createUser;
	private Date createDate;
	private String isToWms;
	private Date toWmsDate;


	/** default constructor */
	public RefundHead() {
	}

	public RefundHead(Long id, Warehouse warehouse, Company company, String frfundType, String createUser,
			Date createDate, String isToWms, Date toWmsDate) {
		super();
		this.id = id;
		this.warehouse = warehouse;
		this.company = company;
		this.frfundType = frfundType;
		this.createUser = createUser;
		this.createDate = createDate;
		this.isToWms = isToWms;
		this.toWmsDate = toWmsDate;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false, scale = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REFUND_SEQ")
    @SequenceGenerator(name = "REFUND_SEQ", sequenceName = "ACOAPP_OMS.REFUND_SEQ")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name="WAREHOUSE")
	public Warehouse getWarehouse() {
		return this.warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	@Column(name = "FRFUND_TYPE", length = 20)
	public String getFrfundType() {
		return this.frfundType;
	}

	public void setFrfundType(String frfundType) {
		this.frfundType = frfundType;
	}

	@Column(name = "CREATE_USER", length = 20)
	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_DATE", length = 7)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TO_WMS_DATE", length = 7)
	public Date getToWmsDate() {
		return this.toWmsDate;
	}

	public void setToWmsDate(Date toWmsDate) {
		this.toWmsDate = toWmsDate;
	}

	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "COMPANY_ID")
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Column(name = "IS_TO_WMS", length=1)
	public String getIsToWms() {
		return isToWms;
	}

	public void setIsToWms(String isToWms) {
		this.isToWms = isToWms;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((createDate == null) ? 0 : createDate.hashCode());
		result = prime * result + ((createUser == null) ? 0 : createUser.hashCode());
		result = prime * result + ((frfundType == null) ? 0 : frfundType.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((isToWms == null) ? 0 : isToWms.hashCode());
		result = prime * result + ((toWmsDate == null) ? 0 : toWmsDate.hashCode());
		result = prime * result + ((warehouse == null) ? 0 : warehouse.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RefundHead other = (RefundHead) obj;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (createDate == null) {
			if (other.createDate != null)
				return false;
		} else if (!createDate.equals(other.createDate))
			return false;
		if (createUser == null) {
			if (other.createUser != null)
				return false;
		} else if (!createUser.equals(other.createUser))
			return false;
		if (frfundType == null) {
			if (other.frfundType != null)
				return false;
		} else if (!frfundType.equals(other.frfundType))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isToWms == null) {
			if (other.isToWms != null)
				return false;
		} else if (!isToWms.equals(other.isToWms))
			return false;
		if (toWmsDate == null) {
			if (other.toWmsDate != null)
				return false;
		} else if (!toWmsDate.equals(other.toWmsDate))
			return false;
		if (warehouse == null) {
			if (other.warehouse != null)
				return false;
		} else if (!warehouse.equals(other.warehouse))
			return false;
		return true;
	}

}
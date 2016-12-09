package com.chinadrtv.erp.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 支付水单
 * User: 王健
 * Date: 2013-04-08
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Entity
@Table(name = "COMPANY_PAYMENT", schema = "ACOAPP_OMS")
public class CompanyPayment implements java.io.Serializable {

	private static final long serialVersionUID = 4373769019103863248L;
	private Long id;
	private CompanyContract companyContract;
	private String paymentCode;
	private BigDecimal amount;
	private BigDecimal settledAmount;
	private String isSettled;
	private String companyAccountCode;
	private String cpompanyAccountName;
	private Date paymentDate;
	private String createUser;
	private Date createDate;
	private String updateUser;
	private Date updateDate;

	// Constructors

	/** default constructor */
	public CompanyPayment() {
	}

	/** full constructor */
	public CompanyPayment(String paymentCode,
			BigDecimal amount, BigDecimal settledAmount, String isSettled,
			String companyAccountCode, String cpompanyAccountName,
			Date paymentDate, String createUser, Date createDate,
			String updateUser, Date updateDate) {
		this.paymentCode = paymentCode;
		this.amount = amount;
		this.settledAmount = settledAmount;
		this.isSettled = isSettled;
		this.companyAccountCode = companyAccountCode;
		this.cpompanyAccountName = cpompanyAccountName;
		this.paymentDate = paymentDate;
		this.createUser = createUser;
		this.createDate = createDate;
		this.updateUser = updateUser;
		this.updateDate = updateDate;
	}

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMPANY_PAYMENT_SEQ")
    @SequenceGenerator(name = "COMPANY_PAYMENT_SEQ", sequenceName = "ACOAPP_OMS.COMPANY_PAYMENT_SEQ")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "PAYMENT_CODE", length = 20)
	public String getPaymentCode() {
		return this.paymentCode;
	}

	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}

	@Column(name = "AMOUNT", precision = 15, scale = 4)
	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Column(name = "SETTLED_AMOUNT", precision = 15, scale = 4)
	public BigDecimal getSettledAmount() {
		return this.settledAmount;
	}

	public void setSettledAmount(BigDecimal settledAmount) {
		this.settledAmount = settledAmount;
	}

	@Column(name = "IS_SETTLED", length = 1)
	public String getIsSettled() {
		return this.isSettled;
	}

	public void setIsSettled(String isSettled) {
		this.isSettled = isSettled;
	}

	@Column(name = "COMPANY_ACCOUNT_CODE", length = 20)
	public String getCompanyAccountCode() {
		return this.companyAccountCode;
	}

	public void setCompanyAccountCode(String companyAccountCode) {
		this.companyAccountCode = companyAccountCode;
	}

	@Column(name = "CPOMPANY_ACCOUNT_NAME", length = 20)
	public String getCpompanyAccountName() {
		return this.cpompanyAccountName;
	}

	public void setCpompanyAccountName(String cpompanyAccountName) {
		this.cpompanyAccountName = cpompanyAccountName;
	}

	@Column(name = "PAYMENT_DATE", length = 7)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
	public Date getPaymentDate() {
		return this.paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	@Column(name = "CREATE_USER", length = 20)
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

	@Column(name = "UPDATE_USER", length = 20)
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

	@OneToOne(cascade=CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name="COMPANY_CONTRACT_ID")
	public CompanyContract getCompanyContract() {
		return companyContract;
	}

	public void setCompanyContract(CompanyContract companyContract) {
		this.companyContract = companyContract;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((companyAccountCode == null) ? 0 : companyAccountCode.hashCode());
		result = prime * result + ((companyContract == null) ? 0 : companyContract.hashCode());
		result = prime * result + ((cpompanyAccountName == null) ? 0 : cpompanyAccountName.hashCode());
		result = prime * result + ((createDate == null) ? 0 : createDate.hashCode());
		result = prime * result + ((createUser == null) ? 0 : createUser.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((isSettled == null) ? 0 : isSettled.hashCode());
		result = prime * result + ((paymentCode == null) ? 0 : paymentCode.hashCode());
		result = prime * result + ((paymentDate == null) ? 0 : paymentDate.hashCode());
		result = prime * result + ((settledAmount == null) ? 0 : settledAmount.hashCode());
		result = prime * result + ((updateDate == null) ? 0 : updateDate.hashCode());
		result = prime * result + ((updateUser == null) ? 0 : updateUser.hashCode());
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
		CompanyPayment other = (CompanyPayment) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (companyAccountCode == null) {
			if (other.companyAccountCode != null)
				return false;
		} else if (!companyAccountCode.equals(other.companyAccountCode))
			return false;
		if (companyContract == null) {
			if (other.companyContract != null)
				return false;
		} else if (!companyContract.equals(other.companyContract))
			return false;
		if (cpompanyAccountName == null) {
			if (other.cpompanyAccountName != null)
				return false;
		} else if (!cpompanyAccountName.equals(other.cpompanyAccountName))
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isSettled == null) {
			if (other.isSettled != null)
				return false;
		} else if (!isSettled.equals(other.isSettled))
			return false;
		if (paymentCode == null) {
			if (other.paymentCode != null)
				return false;
		} else if (!paymentCode.equals(other.paymentCode))
			return false;
		if (paymentDate == null) {
			if (other.paymentDate != null)
				return false;
		} else if (!paymentDate.equals(other.paymentDate))
			return false;
		if (settledAmount == null) {
			if (other.settledAmount != null)
				return false;
		} else if (!settledAmount.equals(other.settledAmount))
			return false;
		if (updateDate == null) {
			if (other.updateDate != null)
				return false;
		} else if (!updateDate.equals(other.updateDate))
			return false;
		if (updateUser == null) {
			if (other.updateUser != null)
				return false;
		} else if (!updateUser.equals(other.updateUser))
			return false;
		return true;
	}

}
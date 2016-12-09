package com.chinadrtv.erp.report.entity;

import javax.persistence.Column;

import com.chinadrtv.erp.report.core.orm.entity.IdEntity;

//@Entity
//@Table(name="COMPANY_ACCOUNT", schema="ACOAPP_OMS")
public class CompanyAccount extends IdEntity<CompanyAccount> {
	
	private static final long serialVersionUID = -4604163480333229659L;

	@Column(name="ACCOUNT_CODE")
	private String accountCode;

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

}
package com.chinadrtv.erp.report.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.chinadrtv.erp.report.core.orm.entity.BaseEntity;

//@Entity
//@Table(name="DOCUMENT", schema="ACOAPP_KNOWLEDGE")
public class Document extends BaseEntity<Document> {

	private static final long serialVersionUID = 7705495593843844424L;

	@Id
	@Column(name="DOCUMENTID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	
	@ManyToOne
    @JoinColumn(name="orderid", nullable=true, updatable=false)
	private CompanyAccount companyAccount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public CompanyAccount getCompanyAccount() {
		return companyAccount;
	}

	public void setCompanyAccount(CompanyAccount companyAccount) {
		this.companyAccount = companyAccount;
	}

}
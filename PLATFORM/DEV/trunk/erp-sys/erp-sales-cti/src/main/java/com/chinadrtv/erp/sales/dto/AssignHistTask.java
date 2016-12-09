/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.sales.dto;

import java.util.Date;

/**
 * 用来展现取数后的提示信息
 * 2013-6-6 下午2:18:13
 * @version 1.0.0
 * @author yangfei
 *
 */
public class AssignHistTask {
	private String contactId;
	
	private String instId;
	
	private String leadId;
	
	private String campaignId;
	
	private String pdCustomerId;
	
	private String isPotential;
	
	private Date startDate;
	
	private Date endDate;
	
	private String tip;
	
	private boolean isSuc = true;

	
	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	public String getInstId() {
		return instId;
	}

	public void setInstId(String instId) {
		this.instId = instId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public boolean getIsSuc() {
		return isSuc;
	}

	public void setSuc(boolean isSuc) {
		this.isSuc = isSuc;
	}

	public String getLeadId() {
		return leadId;
	}

	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}

	public String getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(String campaignId) {
		this.campaignId = campaignId;
	}

	public String getPdCustomerId() {
		return pdCustomerId;
	}

	public void setPdCustomerId(String pdCustomerId) {
		this.pdCustomerId = pdCustomerId;
	}

	public String getIsPotential() {
		return isPotential;
	}

	public void setIsPotential(String isPotential) {
		this.isPotential = isPotential;
	}
	
}

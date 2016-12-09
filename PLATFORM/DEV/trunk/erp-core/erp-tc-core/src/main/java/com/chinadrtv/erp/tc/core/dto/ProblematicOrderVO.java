/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.tc.core.dto;

import java.util.Date;

/**
 * 2013-7-25 上午11:19:52
 * @version 1.0.0
 * @author yangfei
 *
 */
public class ProblematicOrderVO {
	private String problemId;
	private String orderid;
	private Date crdate;
	private String status;//redundancy
	private String statusDsc;
	private double mailprice;
	private double totalprice;
	private String crusr;
	private String contactId;
	private String contactName;
	private Date sentDate;
	private Date problemDate;
	private String problemUser;
	private String problemtype;//redundancy
	private String problemTypeDsc;
	private String problemDsc;
	private String problemStatus;//redundancy
	private String problemStatusDsc;
	
	private String campaignId;
	
	public String getProblemId() {
		return problemId;
	}
	public void setProblemId(String problemId) {
		this.problemId = problemId;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public Date getCrdate() {
		return crdate;
	}
	public void setCrdate(Date crdate) {
		this.crdate = crdate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusDsc() {
		return statusDsc;
	}
	public void setStatusDsc(String statusDsc) {
		this.statusDsc = statusDsc;
	}
	public double getMailprice() {
		return mailprice;
	}
	public void setMailprice(double mailprice) {
		this.mailprice = mailprice;
	}
	public double getTotalprice() {
		return totalprice;
	}
	public void setTotalprice(double totalprice) {
		this.totalprice = totalprice;
	}
	public String getCrusr() {
		return crusr;
	}
	public void setCrusr(String crusr) {
		this.crusr = crusr;
	}
	public String getContactId() {
		return contactId;
	}
	public void setContactId(String contactId) {
		this.contactId = contactId;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public Date getSentDate() {
		return sentDate;
	}
	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}
	public Date getProblemDate() {
		return problemDate;
	}
	public void setProblemDate(Date problemDate) {
		this.problemDate = problemDate;
	}
	public String getProblemUser() {
		return problemUser;
	}
	public void setProblemUser(String problemUser) {
		this.problemUser = problemUser;
	}
	public String getProblemtype() {
		return problemtype;
	}
	public void setProblemtype(String problemtype) {
		this.problemtype = problemtype;
	}
	public String getProblemTypeDsc() {
		return problemTypeDsc;
	}
	public void setProblemTypeDsc(String problemTypeDsc) {
		this.problemTypeDsc = problemTypeDsc;
	}
	public String getProblemDsc() {
		return problemDsc;
	}
	public void setProblemDsc(String problemDsc) {
		this.problemDsc = problemDsc;
	}
	public String getProblemStatus() {
		return problemStatus;
	}
	public void setProblemStatus(String problemStatus) {
		this.problemStatus = problemStatus;
	}
	public String getProblemStatusDsc() {
		return problemStatusDsc;
	}
	public void setProblemStatusDsc(String problemStatusDsc) {
		this.problemStatusDsc = problemStatusDsc;
	}
	public String getCampaignId() {
		return campaignId;
	}
	public void setCampaignId(String campaignId) {
		this.campaignId = campaignId;
	}
	
}

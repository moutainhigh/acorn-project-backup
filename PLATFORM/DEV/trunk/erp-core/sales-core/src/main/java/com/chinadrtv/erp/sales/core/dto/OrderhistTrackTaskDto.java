/*
 * @(#)OrderhistTrackTaskDto.java 1.0 2013年12月25日上午10:03:59
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.sales.core.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.chinadrtv.erp.model.agent.OrderhistTrackTask;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2013年12月25日 上午10:03:59 
 * 
 */
public class OrderhistTrackTaskDto extends OrderhistTrackTask {

	private static final long serialVersionUID = -7096607053531895247L;

	private Date beginDate;
	private Date endDate;
	private String orderStatus;
	private String contactId;
	private String mailId;
	private String contactName;
	private BigDecimal totalprice;
	private String crusr;
	private Date crdt;
	private Date rfoutdat;
	private String trackRemark;
	private List<String> createUserList = new ArrayList<String>();
	
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getContactId() {
		return contactId;
	}
	public void setContactId(String contactId) {
		this.contactId = contactId;
	}
	public String getMailId() {
		return mailId;
	}
	public void setMailId(String mailId) {
		this.mailId = mailId;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public BigDecimal getTotalprice() {
		return totalprice;
	}
	public void setTotalprice(BigDecimal totalprice) {
		this.totalprice = totalprice;
	}
	public String getCrusr() {
		return crusr;
	}
	public void setCrusr(String crusr) {
		this.crusr = crusr;
	}
	public Date getCrdt() {
		return crdt;
	}
	public void setCrdt(Date crdt) {
		this.crdt = crdt;
	}
	public Date getRfoutdat() {
		return rfoutdat;
	}
	public void setRfoutdat(Date rfoutdat) {
		this.rfoutdat = rfoutdat;
	}
	public String getTrackRemark() {
		return trackRemark;
	}
	public void setTrackRemark(String trackRemark) {
		this.trackRemark = trackRemark;
	}
	public List<String> getCreateUserList() {
		return createUserList;
	}
	public void setCreateUserList(List<String> createUserList) {
		this.createUserList = createUserList;
	}
	
}

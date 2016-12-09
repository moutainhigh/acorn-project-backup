/*
 * @(#)UrgentOrderDto.java 1.0 2013-7-12下午3:26:17
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.dto;

import java.math.BigDecimal;
import java.util.Date;

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
 * @since 2013-7-12 下午3:26:17 
 * 
 */
public class UrgentOrderDto {

	private Long tuid;
	private String orderid;
	private String apppsn;
	private Date appdate;
	private String appdsc;
	private String chkpsn;
	private Date chkdate;
	private String chkreason;
	private String class_;
	private String applicationreason;
	private String endpsn;
	private Date enddate;
	private String contactId;
	private String orderStatus;
	//申请状态
	private Integer appStatus;
	private String mailId;
	private String contactName;
	private Date beginCrDate;
	private Date endCrDate;
	//订单创建日期
	private Date crdt;
	//订单出库日期
	private Date rfoutdt;
	//订单总金额
	private BigDecimal totalprice;
	//订单创建人
	private String crusr;
	
	private String receiverName;
	private String receiverPhone;
	private String parentOrderId;
	private String provinceid;
	private Integer cityid;
	private Integer countyid;
	private Integer areaid;
	private String paytype;
	private String itemCode;
	private Date beginRfoutdt;
	private Date endRfoutdt;
	private Boolean couldReApply;
	private String agentId;
	
	public String getContactId() {
		return contactId;
	}
	public void setContactId(String contactId) {
		this.contactId = contactId;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public Date getBeginCrDate() {
		return beginCrDate;
	}
	public void setBeginCrDate(Date beginCrDate) {
		this.beginCrDate = beginCrDate;
	}
	public Date getEndCrDate() {
		return endCrDate;
	}
	public void setEndCrDate(Date endCrDate) {
		this.endCrDate = endCrDate;
	}
	public Date getCrdt() {
		return crdt;
	}
	public void setCrdt(Date crdt) {
		this.crdt = crdt;
	}
	public Date getRfoutdt() {
		return rfoutdt;
	}
	public void setRfoutdt(Date rfoutdt) {
		this.rfoutdt = rfoutdt;
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
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverPhone() {
		return receiverPhone;
	}
	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}
	public String getParentOrderId() {
		return parentOrderId;
	}
	public void setParentOrderId(String parentOrderId) {
		this.parentOrderId = parentOrderId;
	}
	public String getPaytype() {
		return paytype;
	}
	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public Date getBeginRfoutdt() {
		return beginRfoutdt;
	}
	public void setBeginRfoutdt(Date beginRfoutdt) {
		this.beginRfoutdt = beginRfoutdt;
	}
	public Date getEndRfoutdt() {
		return endRfoutdt;
	}
	public void setEndRfoutdt(Date endRfoutdt) {
		this.endRfoutdt = endRfoutdt;
	}
	public Integer getAppStatus() {
		return appStatus;
	}
	public void setAppStatus(Integer appStatus) {
		this.appStatus = appStatus;
	}
	public String getProvinceid() {
		return provinceid;
	}
	public void setProvinceid(String provinceid) {
		this.provinceid = provinceid;
	}
	public Integer getCityid() {
		return cityid;
	}
	public void setCityid(Integer cityid) {
		this.cityid = cityid;
	}
	public Integer getCountyid() {
		return countyid;
	}
	public void setCountyid(Integer countyid) {
		this.countyid = countyid;
	}
	public Integer getAreaid() {
		return areaid;
	}
	public void setAreaid(Integer areaid) {
		this.areaid = areaid;
	}
	public Long getTuid() {
		return tuid;
	}
	public void setTuid(Long tuid) {
		this.tuid = tuid;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getApppsn() {
		return apppsn;
	}
	public void setApppsn(String apppsn) {
		this.apppsn = apppsn;
	}
	public Date getAppdate() {
		return appdate;
	}
	public void setAppdate(Date appdate) {
		this.appdate = appdate;
	}
	public String getAppdsc() {
		return appdsc;
	}
	public void setAppdsc(String appdsc) {
		this.appdsc = appdsc;
	}
	public String getChkpsn() {
		return chkpsn;
	}
	public void setChkpsn(String chkpsn) {
		this.chkpsn = chkpsn;
	}
	public Date getChkdate() {
		return chkdate;
	}
	public void setChkdate(Date chkdate) {
		this.chkdate = chkdate;
	}
	public String getChkreason() {
		return chkreason;
	}
	public void setChkreason(String chkreason) {
		this.chkreason = chkreason;
	}
	public String getClass_() {
		return class_;
	}
	public void setClass_(String class_) {
		this.class_ = class_;
	}
	public String getApplicationreason() {
		return applicationreason;
	}
	public void setApplicationreason(String applicationreason) {
		this.applicationreason = applicationreason;
	}
	public String getEndpsn() {
		return endpsn;
	}
	public void setEndpsn(String endpsn) {
		this.endpsn = endpsn;
	}
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	public Boolean getCouldReApply() {
		return couldReApply;
	}
	public void setCouldReApply(Boolean couldReApply) {
		this.couldReApply = couldReApply;
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public String getMailId() {
		return mailId;
	}
	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

}

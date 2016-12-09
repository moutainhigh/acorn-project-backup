/*
 * @(#)Coupon.java 1.0 2013-8-12下午3:21:37
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-8-12 下午3:21:37
 * 
 */

public class CouponCrDto implements Serializable {

	private Long id;
	private String couponId;
	private String couponValue;
	private String crusr;
	private Date crdt;
	private Date startdt;
	private String contactId;
	private String status;
	private String type;
	private String deliverStatus;
	private Date deliverDt;
	private Long campaignId;
	private String smsBatchid;
	private String smsUuid;
	private String phone;
	private Date enddt;
	private String moneyUse;
	private String templateId;
	private String departmentId;
	private String source;
	private String orderId;
	private String useType;
	private String orderMoney;
	private String startdtTimes;
	private String endTimes;
	private Long couponConfigId;
	private String useDepartment;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the couponId
	 */
	public String getCouponId() {
		return couponId;
	}

	/**
	 * @param couponId
	 *            the couponId to set
	 */
	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	/**
	 * @return the couponPrice
	 */
	public String getCouponValue() {
		return couponValue;
	}

	/**
	 * @param couponPrice
	 *            the couponPrice to set
	 */
	public void setCouponValue(String couponValue) {
		this.couponValue = couponValue;
	}

	/**
	 * @return the crusr
	 */
	public String getCrusr() {
		return crusr;
	}

	/**
	 * @param crusr
	 *            the crusr to set
	 */
	public void setCrusr(String crusr) {
		this.crusr = crusr;
	}

	/**
	 * @return the crdt
	 */
	public Date getCrdt() {
		return crdt;
	}

	/**
	 * @param crdt
	 *            the crdt to set
	 */
	public void setCrdt(Date crdt) {
		this.crdt = crdt;
	}

	/**
	 * @return the startdt
	 */
	public Date getStartdt() {
		return startdt;
	}

	/**
	 * @param startdt
	 *            the startdt to set
	 */
	public void setStartdt(Date startdt) {
		this.startdt = startdt;
	}

	/**
	 * @return the contactId
	 */
	public String getContactId() {
		return contactId;
	}

	/**
	 * @param contactId
	 *            the contactId to set
	 */
	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the deliverStatus
	 */
	public String getDeliverStatus() {
		return deliverStatus;
	}

	/**
	 * @param deliverStatus
	 *            the deliverStatus to set
	 */
	public void setDeliverStatus(String deliverStatus) {
		this.deliverStatus = deliverStatus;
	}

	/**
	 * @return the deliverDt
	 */
	public Date getDeliverDt() {
		return deliverDt;
	}

	/**
	 * @param deliverDt
	 *            the deliverDt to set
	 */
	public void setDeliverDt(Date deliverDt) {
		this.deliverDt = deliverDt;
	}

	/**
	 * @return the campaignId
	 */
	public Long getCampaignId() {
		return campaignId;
	}

	/**
	 * @param campaignId
	 *            the campaignId to set
	 */
	public void setCampaignId(Long campaignId) {
		this.campaignId = campaignId;
	}

	/**
	 * @return the smsBatchid
	 */
	public String getSmsBatchid() {
		return smsBatchid;
	}

	/**
	 * @param smsBatchid
	 *            the smsBatchid to set
	 */
	public void setSmsBatchid(String smsBatchid) {
		this.smsBatchid = smsBatchid;
	}

	/**
	 * @return the smsUuid
	 */
	public String getSmsUuid() {
		return smsUuid;
	}

	/**
	 * @param smsUuid
	 *            the smsUuid to set
	 */
	public void setSmsUuid(String smsUuid) {
		this.smsUuid = smsUuid;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the enddt
	 */
	public Date getEnddt() {
		return enddt;
	}

	/**
	 * @param enddt
	 *            the enddt to set
	 */
	public void setEnddt(Date enddt) {
		this.enddt = enddt;
	}

	/**
	 * @return the moneyUse
	 */
	public String getMoneyUse() {
		return moneyUse;
	}

	/**
	 * @param moneyUse
	 *            the moneyUse to set
	 */
	public void setMoneyUse(String moneyUse) {
		this.moneyUse = moneyUse;
	}

	/**
	 * @return the templateId
	 */
	public String getTemplateId() {
		return templateId;
	}

	/**
	 * @param templateId
	 *            the templateId to set
	 */
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	/**
	 * @return the departmentId
	 */
	public String getDepartmentId() {
		return departmentId;
	}

	/**
	 * @param departmentId
	 *            the departmentId to set
	 */
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source
	 *            the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId
	 *            the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the useType
	 */
	public String getUseType() {
		return useType;
	}

	/**
	 * @param useType
	 *            the useType to set
	 */
	public void setUseType(String useType) {
		this.useType = useType;
	}

	/**
	 * @return the orderMoney
	 */
	public String getOrderMoney() {
		return orderMoney;
	}

	/**
	 * @param orderMoney
	 *            the orderMoney to set
	 */
	public void setOrderMoney(String orderMoney) {
		this.orderMoney = orderMoney;
	}

	/**
	 * @return the startdtTimes
	 */
	public String getStartdtTimes() {
		return startdtTimes;
	}

	/**
	 * @param startdtTimes
	 *            the startdtTimes to set
	 */
	public void setStartdtTimes(String startdtTimes) {
		this.startdtTimes = startdtTimes;
	}

	/**
	 * @return the endTimes
	 */
	public String getEndTimes() {
		return endTimes;
	}

	/**
	 * @param endTimes
	 *            the endTimes to set
	 */
	public void setEndTimes(String endTimes) {
		this.endTimes = endTimes;
	}

	/**
	 * @return the couponConfigId
	 */
	public Long getCouponConfigId() {
		return couponConfigId;
	}

	/**
	 * @param couponConfigId
	 *            the couponConfigId to set
	 */
	public void setCouponConfigId(Long couponConfigId) {
		this.couponConfigId = couponConfigId;
	}

	/**
	 * @return the useDepartment
	 */
	public String getUseDepartment() {
		return useDepartment;
	}

	/**
	 * @param useDepartment
	 *            the useDepartment to set
	 */
	public void setUseDepartment(String useDepartment) {
		this.useDepartment = useDepartment;
	}

}

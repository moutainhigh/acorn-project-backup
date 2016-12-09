/*
 * @(#)Coupon.java 1.0 2013-8-12下午3:21:37
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.model.marketing;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-8-12 下午3:21:37
 * 
 */
@Entity
@Table(name = "COUPON_CR", schema = "IAGENT")
public class CouponCr implements Serializable {

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
	private String usedDepartment;

	/**
	 * @return the id
	 */
	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "generator", sequenceName = "IAGENT.SEQ_COUPON_CR", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
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
	@Column(name = "COUPON_ID")
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
	@Column(name = "COUPON_VALUE")
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
	@Column(name = "CRUSR")
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
	@Column(name = "CRDT")
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
	@Column(name = "STARTDT")
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
	@Column(name = "CONTACT_ID")
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
	@Column(name = "STATUS")
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
	@Column(name = "TYPE")
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
	@Column(name = "DELIVER_STATUS")
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
	@Column(name = "DELIVERDT")
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
	@Column(name = "CAMPAIGN_ID")
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
	@Column(name = "SMS_BATCHID")
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
	@Column(name = "SMS_UUID")
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
	@Column(name = "PHONE")
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
	@Column(name = "ENDDT")
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
	@Column(name = "USE_MONEY")
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
	 * @return the usedDepartment
	 */
	@Column(name = "USE_DEPARTMENT")
	public String getUsedDepartment() {
		return usedDepartment;
	}

	/**
	 * @param usedDepartment
	 *            the usedDepartment to set
	 */
	public void setUsedDepartment(String usedDepartment) {
		this.usedDepartment = usedDepartment;
	}

}

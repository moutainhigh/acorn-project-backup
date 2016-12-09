/*
 * @(#)CouponUse.java 1.0 2013-8-14上午10:26:06
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
 * @since 2013-8-14 上午10:26:06
 * 
 */
@Entity
@Table(name = "COUPON_USE", schema = "IAGENT")
public class CouponUse implements Serializable {

	private Long id;
	private String orderId;
	private String contactId;
	private String phone;
	private String couponValue;
	private String type;
	private String crusr;
	private Date crdt;
	private String mdusr;
	private Date mddt;
	private String couponType;
	private Long campaignId;
	private String smsBatchid;
	private String smsUuid;
	private String couponId;
	private String useContactId;
	private String usePhone;

	/**
	 * @return the id
	 */
	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "generator", sequenceName = "IAGENT.SEQ_COUPON_USE", allocationSize = 1)
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
	 * @return the orderId
	 */
	@Column(name = "ORDER_ID")
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
	 * @return the couponValue
	 */
	@Column(name = "COUPON_VALUE")
	public String getCouponValue() {
		return couponValue;
	}

	/**
	 * @param couponValue
	 *            the couponValue to set
	 */
	public void setCouponValue(String couponValue) {
		this.couponValue = couponValue;
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
	 * @return the mdusr
	 */
	@Column(name = "MDUSR")
	public String getMdusr() {
		return mdusr;
	}

	/**
	 * @param mdusr
	 *            the mdusr to set
	 */
	public void setMdusr(String mdusr) {
		this.mdusr = mdusr;
	}

	/**
	 * @return the mddt
	 */
	@Column(name = "MDDT")
	public Date getMddt() {
		return mddt;
	}

	/**
	 * @param mddt
	 *            the mddt to set
	 */
	public void setMddt(Date mddt) {
		this.mddt = mddt;
	}

	/**
	 * @return the couponType
	 */
	@Column(name = "COUPON_TYPE")
	public String getCouponType() {
		return couponType;
	}

	/**
	 * @param couponType
	 *            the couponType to set
	 */
	public void setCouponType(String couponType) {
		this.couponType = couponType;
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
	 * @return the useContactId
	 */
	@Column(name = "USE_CONTACT_ID")
	public String getUseContactId() {
		return useContactId;
	}

	/**
	 * @param useContactId
	 *            the useContactId to set
	 */
	public void setUseContactId(String useContactId) {
		this.useContactId = useContactId;
	}

	/**
	 * @return the usePhone
	 */
	@Column(name = "USE_PHONE")
	public String getUsePhone() {
		return usePhone;
	}

	/**
	 * @param usePhone
	 *            the usePhone to set
	 */
	public void setUsePhone(String usePhone) {
		this.usePhone = usePhone;
	}

}

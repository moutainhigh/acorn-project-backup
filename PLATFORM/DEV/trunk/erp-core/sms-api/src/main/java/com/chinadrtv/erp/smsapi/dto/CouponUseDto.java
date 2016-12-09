/*
 * @(#)CouponUse.java 1.0 2013-8-14上午10:26:06
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
 * @since 2013-8-14 上午10:26:06
 * 
 */

public class CouponUseDto implements Serializable {

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
	 * @return the couponValue
	 */
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
	 * @return the mdusr
	 */
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

}

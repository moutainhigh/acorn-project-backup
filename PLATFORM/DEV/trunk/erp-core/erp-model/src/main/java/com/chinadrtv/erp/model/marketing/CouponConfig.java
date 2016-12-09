/*
 * @(#)CouponConfig.java 1.0 2013-8-23下午2:57:08
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

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-8-23 下午2:57:08
 * 
 */
@Entity
@Table(name = "COUPON_CONFIG", schema = "IAGENT")
public class CouponConfig implements Serializable {

	private Long id;
	private String couponType;
	private String useDeparment;
	private String crusr;
	private Date crdt;
	private String mdusr;
	private Date mddt;
	private String useMoney;
	private String couponValue;
	private String status;
	private Date stardt;
	private Date enddt;
	private String smsSendType;
	private String productType;
	private String templateName;
	private String templateContent;
	private Long campaignId;
	private String couponConfigName;

	/**
	 * @return the id
	 */
	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "generator", sequenceName = "IAGENT.SEQ_COUPON_CONFIG", allocationSize = 1)
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
	 * @return the useDeparment
	 */
	@Column(name = "USE_DEPARTMENT")
	public String getUseDeparment() {
		return useDeparment;
	}

	/**
	 * @param useDeparment
	 *            the useDeparment to set
	 */
	public void setUseDeparment(String useDeparment) {
		this.useDeparment = useDeparment;
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
	 * @return the useMoney
	 */
	@Column(name = "USE_MONEY")
	public String getUseMoney() {
		return useMoney;
	}

	/**
	 * @param useMoney
	 *            the useMoney to set
	 */
	public void setUseMoney(String useMoney) {
		this.useMoney = useMoney;
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
	 * @return the stardt
	 */
	@JsonFormat(pattern = "yyyy-MM-dd ", timezone = "GMT+08:00")
	@Column(name = "STARTDT")
	public Date getStardt() {
		return stardt;
	}

	/**
	 * @param stardt
	 *            the stardt to set
	 */
	public void setStardt(Date stardt) {
		this.stardt = stardt;
	}

	/**
	 * @return the enddt
	 */
	@JsonFormat(pattern = "yyyy-MM-dd ", timezone = "GMT+08:00")
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
	 * @return the smsSendType
	 */
	@Column(name = "SMS_SEND_TYPE")
	public String getSmsSendType() {
		return smsSendType;
	}

	/**
	 * @param smsSendType
	 *            the smsSendType to set
	 */
	public void setSmsSendType(String smsSendType) {
		this.smsSendType = smsSendType;
	}

	/**
	 * @return the productType
	 */
	@Column(name = "PRODUCT_TYPE")
	public String getProductType() {
		return productType;
	}

	/**
	 * @param productType
	 *            the productType to set
	 */
	public void setProductType(String productType) {
		this.productType = productType;
	}

	/**
	 * @return the templateId
	 */
	@Column(name = "TEMPLATE_NAME")
	public String getTemplateName() {
		return templateName;
	}

	/**
	 * @param templateId
	 *            the templateId to set
	 */
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	/**
	 * @return the templateContent
	 */
	@Column(name = "TEMPLATE_CONTENT")
	public String getTemplateContent() {
		return templateContent;
	}

	/**
	 * @param templateContent
	 *            the templateContent to set
	 */
	public void setTemplateContent(String templateContent) {
		this.templateContent = templateContent;
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
	 * @return the couponConfigName
	 */
	@Column(name = "COUPON_CONFIG_NAME")
	public String getCouponConfigName() {
		return couponConfigName;
	}

	/**
	 * @param couponConfigName
	 *            the couponConfigName to set
	 */
	public void setCouponConfigName(String couponConfigName) {
		this.couponConfigName = couponConfigName;
	}

}

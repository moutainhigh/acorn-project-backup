/*
 * @(#)SmsAnswer.java 1.0 2013-1-18下午1:09:24
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * none</dd>
 * </dl>
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-1-18 下午1:09:24
 * 
 */
@javax.persistence.Table(name = "SMS_BLACKLIST", schema = "ACOAPP_MARKETING")
@Entity
public class SmsBlackList {

	/**
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 */
	public SmsBlackList() {
		super();
		// TODO Auto-generated constructor stub
	}

	private Long id;

	@Id
	@javax.persistence.Column(name = "ID")
	@SequenceGenerator(name = "generator", sequenceName = "ACOAPP_MARKETING.SEQ_SMS_BLACKLIST", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private String phoneNum;

	@javax.persistence.Column(name = "USER_PHONE_NUM")
	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	private Date addTime;

	@javax.persistence.Column(name = "ADD_TIMES")
	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	private String creator;

	/**
	 * @return the creator
	 */
	@javax.persistence.Column(name = "CREATOR")
	public String getCreator() {
		return creator;
	}

	/**
	 * @param creator
	 *            the creator to set
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}

}

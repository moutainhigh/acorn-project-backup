/*
 * @(#)SmsAnswer.java 1.0 2013-2-18下午6:27:49
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.model;

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
 * @since 2013-2-18 下午6:27:49
 * 
 */
@Table(name = "SMS_ANSWER", schema = "ACOAPP_MARKETING")
@Entity
public class SmsAnswer implements Serializable {

	private Long id;
	private String mobile;
	private Date receiveTime;
	private String receiveContent;
	private String receiveChannel;
	private String smsChildId;
	private Date createtime;
	private String creator;
	private String state;

	/**
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 */
	public SmsAnswer() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SMS_ANSWER")
	@SequenceGenerator(name = "SEQ_SMS_ANSWER", sequenceName = "ACOAPP_MARKETING.SEQ_SMS_ANSWER", allocationSize = 1)
	@Column(name = "ID")
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
	 * @return the mobile
	 */
	@Column(name = "MOBILE")
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile
	 *            the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the receiveTime
	 */
	@Column(name = "RECEIVE_TIME")
	public Date getReceiveTime() {
		return receiveTime;
	}

	/**
	 * @param receiveTime
	 *            the receiveTime to set
	 */
	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	/**
	 * @return the receiveContent
	 */
	@Column(name = "RECEIVE_CONTENT")
	public String getReceiveContent() {
		return receiveContent;
	}

	/**
	 * @param receiveContent
	 *            the receiveContent to set
	 */
	public void setReceiveContent(String receiveContent) {
		this.receiveContent = receiveContent;
	}

	/**
	 * @return the receiveChannel
	 */
	@Column(name = "RECEIVE_CHANNEL")
	public String getReceiveChannel() {
		return receiveChannel;
	}

	/**
	 * @param receiveChannel
	 *            the receiveChannel to set
	 */
	public void setReceiveChannel(String receiveChannel) {
		this.receiveChannel = receiveChannel;
	}

	/**
	 * @return the smsChildId
	 */
	@Column(name = "SMS_CHILD_ID")
	public String getSmsChildId() {
		return smsChildId;
	}

	/**
	 * @param smsChildId
	 *            the smsChildId to set
	 */
	public void setSmsChildId(String smsChildId) {
		this.smsChildId = smsChildId;
	}

	/**
	 * @return the createtime
	 */
	@Column(name = "CREATETIME")
	public Date getCreatetime() {
		return createtime;
	}

	/**
	 * @param createtime
	 *            the createtime to set
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	/**
	 * @return the creator
	 */
	@Column(name = "CREATOR")
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

	/**
	 * @return the state
	 */
	@Column(name = "STATE")
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
}

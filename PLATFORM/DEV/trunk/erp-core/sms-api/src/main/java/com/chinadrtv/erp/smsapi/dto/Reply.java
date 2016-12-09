/*
 * @(#)Reply.java 1.0 2013-2-22下午5:23:37
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.dto;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-2-22 下午5:23:37
 * 
 */
@XStreamAlias("reply")
public class Reply implements Serializable {
	@XStreamAlias("mobile")
	private String mobile;
	@XStreamAlias("receivTime")
	private String receivTime;
	@XStreamAlias("content")
	private String content;
	@XStreamAlias("receiveChannel")
	private String receiveChannel;
	@XStreamAlias("subCode")
	private String subCode;

	public Reply() {

	}

	/**
	 * @return the mobile
	 */
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
	 * @return the receivTime
	 */
	public String getReceivTime() {
		return receivTime;
	}

	/**
	 * @param receivTime
	 *            the receivTime to set
	 */
	public void setReceivTime(String receivTime) {
		this.receivTime = receivTime;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the receiveChannel
	 */
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
	 * @return the subCode
	 */
	public String getSubCode() {
		return subCode;
	}

	/**
	 * @param subCode
	 *            the subCode to set
	 */
	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}

}

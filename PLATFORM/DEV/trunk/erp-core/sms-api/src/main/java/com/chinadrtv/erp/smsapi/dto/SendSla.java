/*
 * @(#)SendSla.java 1.0 2013-2-19上午10:47:18
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.dto;

import java.io.Serializable;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-2-19 上午10:47:18
 * 
 */
@XStreamAlias("sla")
public class SendSla implements Serializable {
	@XStreamAlias("type")
	private String type;
	private List<Channel> channels;
	@XStreamAlias("allowChangeChannel")
	private String allowChangeChannel;
	private DateScope dateScope;
	@XStreamAlias("priority")
	private String priority;
	@XStreamAlias("isReply")
	private String isReply;
	@XStreamAlias("realTime")
	private String realTime;
	@XStreamAlias("signiture")
	private String signiture;

	public SendSla() {

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
	 * @return the allowChangeChannel
	 */
	public String getAllowChangeChannel() {
		return allowChangeChannel;
	}

	/**
	 * @param allowChangeChannel
	 *            the allowChangeChannel to set
	 */
	public void setAllowChangeChannel(String allowChangeChannel) {
		this.allowChangeChannel = allowChangeChannel;
	}

	/**
	 * @return the dateScope
	 */
	public DateScope getDateScope() {
		return dateScope;
	}

	/**
	 * @param dateScope
	 *            the dateScope to set
	 */
	public void setDateScope(DateScope dateScope) {
		this.dateScope = dateScope;
	}

	/**
	 * @return the priority
	 */
	public String getPriority() {
		return priority;
	}

	/**
	 * @param priority
	 *            the priority to set
	 */
	public void setPriority(String priority) {
		this.priority = priority;
	}

	/**
	 * @return the isReply
	 */
	public String getIsReply() {
		return isReply;
	}

	/**
	 * @param isReply
	 *            the isReply to set
	 */
	public void setIsReply(String isReply) {
		this.isReply = isReply;
	}

	/**
	 * @return the realTime
	 */
	public String getRealTime() {
		return realTime;
	}

	/**
	 * @param realTime
	 *            the realTime to set
	 */
	public void setRealTime(String realTime) {
		this.realTime = realTime;
	}

	/**
	 * @return the signiture
	 */
	public String getSigniture() {
		return signiture;
	}

	/**
	 * @param signiture
	 *            the signiture to set
	 */
	public void setSigniture(String signiture) {
		this.signiture = signiture;
	}

	/**
	 * @return the channels
	 */
	public List<Channel> getChannels() {
		return channels;
	}

	/**
	 * @param channels
	 *            the channels to set
	 */
	public void setChannels(List<Channel> channels) {
		this.channels = channels;
	}

}

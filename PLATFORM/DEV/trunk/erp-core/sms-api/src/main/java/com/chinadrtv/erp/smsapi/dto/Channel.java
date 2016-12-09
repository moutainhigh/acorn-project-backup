/*
 * @(#)Channel.java 1.0 2013-2-19下午2:19:55
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.dto;

import java.io.Serializable;

import com.chinadrtv.erp.smsapi.util.ClConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-2-19 下午2:19:55
 * 
 */
@XStreamAlias("channel")
@XStreamConverter(ClConverter.class)
public class Channel implements Serializable {
	@XStreamAlias("type")
	private String type;
	private String channel;

	/**
	 * @return the channel
	 */
	public String getChannel() {
		return channel;
	}

	/**
	 * @param channel
	 *            the channel to set
	 */
	public void setChannel(String channel) {
		this.channel = channel;
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
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param channel
	 */
	public Channel(String channel) {
		super();
		this.channel = channel;
	}

	public Channel() {
		super();
	}

}

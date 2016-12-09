/*
 * @(#)SingleSendResponse.java 1.0 2013-2-22上午10:15:07
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.dto;

import java.io.ObjectInputStream;
import java.io.Serializable;
import java.io.StringReader;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-2-22 上午10:15:07
 * 
 */
@XStreamAlias("sendResponse")
public class SingleSendResponse implements Serializable {
	@XStreamAlias("uuid")
	private String uuid;
	@XStreamAlias("channel")
	private String channel;
	@XStreamAlias("timestamp")
	private String timestamp;
	@XStreamAlias("type")
	private String type;
	@XStreamAlias("status")
	private String status;
	@XStreamAlias("errorCode")
	private String errorCode;
	@XStreamAlias("errorMsg")
	private String errorMsg;

	public SingleSendResponse() {

	}

	/**
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * @param uuid
	 *            the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

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
	 * @return the timestamp
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp
	 *            the timestamp to set
	 */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
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
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode
	 *            the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorMsg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * @param errorMsg
	 *            the errorMsg to set
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	/**
	 * @Description: TODO
	 * @param args
	 * @return void
	 * @throws
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SingleSendResponse sendResponse = new SingleSendResponse();
		// sendResponse.setChannel("");
		// sendResponse.setErrorCode("");
		// sendResponse.setErrorMsg("");
		// sendResponse.setStatus("");
		// sendResponse.setTimestamp("");
		// sendResponse.setType("");
		// sendResponse.setUuid(1l);
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("sendResponse", SingleSendResponse.class);
		// xstream.autodetectAnnotations(true);
		ObjectInputStream in = null;
		// System.out.println(xstream.toXML(sendResponse));

		String s = "<?xml version='1.0' encoding='UTF-8'?>"
				+ "<sendResponse> "
				+ "<uuid>20130131130904000000119</uuid>  <channel>10655746</channel> <timestamp>YYYY-MM-DD HH:MM:SS</timestamp> <type>2</type> "
				+ "<status>0</status><errorCode>S00001</errorCode> <errorMsg>接收号码不符合规范</errorMsg></sendResponse>";
		StringReader reader = new StringReader(s);
		try {
			System.out.println((SingleSendResponse) xstream.fromXML(s));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

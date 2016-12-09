/*
 * @(#)Message.java 1.0 2013-2-19下午1:56:40
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.dto;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-2-19 下午1:56:40
 * 
 */
@XStreamAlias("message")
public class Message implements Serializable {
	@XStreamAlias("uuid")
	private String uuid;
	@XStreamAlias("timestamp")
	private String timestamp;
	@XStreamAlias("batchId")
	private String batchId;
	@XStreamAlias("slotId")
	private String slotId;
	@XStreamAlias("department")
	private String department;
	@XStreamAlias("source")
	private String source;
	@XStreamAlias("to")
	private String to;
	@XStreamAlias("connectId")
	private String connectId;
	@XStreamAlias("creator")
	private String creator;
	private Content content;

	public Message() {

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
	 * @return the batchId
	 */
	public String getBatchId() {
		return batchId;
	}

	/**
	 * @param batchId
	 *            the batchId to set
	 */
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	/**
	 * @return the slotId
	 */
	public String getSlotId() {
		return slotId;
	}

	/**
	 * @param slotId
	 *            the slotId to set
	 */
	public void setSlotId(String slotId) {
		this.slotId = slotId;
	}

	/**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * @param department
	 *            the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source
	 *            the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}

	/**
	 * @param to
	 *            the to to set
	 */
	public void setTo(String to) {
		this.to = to;
	}

	/**
	 * @return the connectId
	 */
	public String getConnectId() {
		return connectId;
	}

	/**
	 * @param connectId
	 *            the connectId to set
	 */
	public void setConnectId(String connectId) {
		this.connectId = connectId;
	}

	/**
	 * @return the creator
	 */
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
	 * @return the content
	 */
	public Content getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(Content content) {
		this.content = content;
	}

	public static void main(String[] args) {
		// SmsSendStatusServiceImpl sendStatusServiceImpl = new
		// SmsSendStatusServiceImpl();
		// sendStatusServiceImpl.oracleSqlLoad("d:\\123.csv", "1");
		String cmd = "sqlldr userid=crmmarketing/crm.2013@testdb control='d:/123.ctl' log='d:/input.log'";
		try {
			Process ldr = Runtime.getRuntime().exec(cmd);
			InputStream stderr = ldr.getInputStream();
			InputStreamReader isr = new InputStreamReader(stderr);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null)
				System.out.println("*** " + line);
			stderr.close();
			isr.close();
			br.close();
			try {
				ldr.waitFor();
			} catch (Exception e) {
				System.out.println("process function:loader wait for != 0");
			}
		} catch (Exception ex) {
			System.out.println("process function:loader execute exception"
					+ ex.toString());
		}
	}

}

/*
 * @(#)SingleSendRequest.java 1.0 2013-2-21上午9:37:10
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
 * @since 2013-2-21 上午9:37:10
 * 
 */
@XStreamAlias("sendRequest")
public class SendRequest implements Serializable {

	private Message message;
	@XStreamAlias("uuid")
	private String uuid;
	@XStreamAlias("timestamp")
	private String timestamp;
	@XStreamAlias("campaignId")
	private String campaignId;
	@XStreamAlias("batchId")
	private String batchId;
	@XStreamAlias("recordCount")
	private String recordCount;
	@XStreamAlias("slotId")
	private String slotId;
	@XStreamAlias("department")
	private String department;
	@XStreamAlias("file")
	private String file;
	@XStreamAlias("template")
	private String template;

	private SendSla sla;
	@XStreamAlias("secureHash")
	private String secureHash;
	@XStreamAlias("type")
	private String type;
	@XStreamAlias("minuCount")
	private String minuCount;

	public SendRequest() {

	}

	/**
	 * @return the message
	 */
	public Message getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(Message message) {
		this.message = message;
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
	 * @return the recordCount
	 */
	public String getRecordCount() {
		return recordCount;
	}

	/**
	 * @param recordCount
	 *            the recordCount to set
	 */
	public void setRecordCount(String recordCount) {
		this.recordCount = recordCount;
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
	 * @return the file
	 */
	public String getFile() {
		return file;
	}

	/**
	 * @param file
	 *            the file to set
	 */
	public void setFile(String file) {
		this.file = file;
	}

	/**
	 * @return the template
	 */
	public String getTemplate() {
		return template;
	}

	/**
	 * @param template
	 *            the template to set
	 */
	public void setTemplate(String template) {
		this.template = template;
	}

	/**
	 * @return the sla
	 */
	public SendSla getSla() {
		return sla;
	}

	/**
	 * @param sla
	 *            the sla to set
	 */
	public void setSla(SendSla sla) {
		this.sla = sla;
	}

	/**
	 * @return the secureHash
	 */
	public String getSecureHash() {
		return secureHash;
	}

	/**
	 * @param secureHash
	 *            the secureHash to set
	 */
	public void setSecureHash(String secureHash) {
		this.secureHash = secureHash;
	}

	public static void main(String[] args) {
		// SendRequest sendRequest = new SendRequest();
		// sendRequest.setCampaignId("123331");
		// sendRequest.setMinuCount("1000");
		// sendRequest.setTimestamp("" + new Date().getTime());
		// sendRequest.setType("1");
		// sendRequest.setDepartment("10000");
		// // 设置message 节点
		// Message message = new Message();
		// message.setBatchId("100001");
		// message.setDepartment("10000");
		// message.setSlotId("-1");
		// message.setTimestamp("2013-03-19 15:23:00");
		// message.setUuid("100001");
		// message.setTo("13918389281");
		// Content content = new Content();
		// content.setTemplate("{t1}你好{t2橡果国际}");
		// Variables variable = new Variables();
		// variable.setName("t1");
		// variable.setValue("111");
		// Variables variable2 = new Variables();
		// variable2.setName("t2");
		// variable2.setValue("222");
		// List<Variables> list = new ArrayList<Variables>();
		// list.add(variable);
		// list.add(variable2);
		// content.setVariables(list);
		// message.setContent(content);
		// sendRequest.setMessage(message);
		// // 设置sla 节点
		// SendSla sla = new SendSla();
		// sla.setType("");
		// DateScope dateScope = new DateScope();
		// dateScope.setEndTime("2013-03-19 16:23:00");
		// dateScope.setStartTime("2013-03-19 15:26:00");
		//
		// // dateScope.setTimeScopes(list2);
		// sla.setAllowChangeChannel("");
		// sla.setDateScope(dateScope);
		// sla.setPriority("Y");
		// sla.setIsReply("Y");
		// sla.setRealTime("Y");
		// sla.setSigniture("橡果国际");
		// sendRequest.setSla(sla);
		// sendRequest.setSecureHash("");
		// XStream xstream = new XStream(new DomDriver());
		// xstream.autodetectAnnotations(true);
		//
		// System.out.println(xstream.toXML(sendRequest));

	}

	/**
	 * @return the campaignId
	 */
	public String getCampaignId() {
		return campaignId;
	}

	/**
	 * @param campaignId
	 *            the campaignId to set
	 */
	public void setCampaignId(String campaignId) {
		this.campaignId = campaignId;
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
	 * @return the minuCount
	 */
	public String getMinuCount() {
		return minuCount;
	}

	/**
	 * @param minuCount
	 *            the minuCount to set
	 */
	public void setMinuCount(String minuCount) {
		this.minuCount = minuCount;
	}
}

/*
 * @(#)FeedBack.java 1.0 2013-2-22下午2:35:36
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
 * @since 2013-2-22 下午2:35:36
 * 
 */
@XStreamAlias("feedBack")
public class FeedBack implements Serializable {
	@XStreamAlias("file")
	private String file;
	@XStreamAlias("batchId")
	private String batchId;
	@XStreamAlias("recordCount")
	private String recordCount;
	@XStreamAlias("type")
	private String type;
	@XStreamAlias("timestamp")
	private String timestamp;

	public FeedBack() {

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

}

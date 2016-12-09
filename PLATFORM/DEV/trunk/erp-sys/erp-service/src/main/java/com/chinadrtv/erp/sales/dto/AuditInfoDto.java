/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.sales.dto;

/**
 * 2013-6-20 上午10:56:01
 * @version 1.0.0
 * @author yangfei
 *
 */
public class AuditInfoDto {
	private String instId;
	private String comment;
	private String status;
	private String busiType;
	private String id;
	private String appliedUser;
	
	public String getInstId() {
		return instId;
	}
	public void setInstId(String instId) {
		this.instId = instId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBusiType() {
		return busiType;
	}
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAppliedUser() {
		return appliedUser;
	}
	public void setAppliedUser(String appliedUser) {
		this.appliedUser = appliedUser;
	}
}

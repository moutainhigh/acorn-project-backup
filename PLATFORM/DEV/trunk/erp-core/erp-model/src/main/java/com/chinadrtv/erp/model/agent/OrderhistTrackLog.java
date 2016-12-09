/*
 * @(#)OrderhistTraceTask.java 1.0 2013年12月24日下午3:08:13
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.model.agent;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2013年12月24日 下午3:08:13 
 * 
 */
@javax.persistence.Table(name = "ORDERHIST_TRACK_LOG", schema = "IAGENT")
@Entity
public class OrderhistTrackLog implements Serializable{

	private static final long serialVersionUID = 5997372398699333461L;

	private Long id;
	private String orderId;
	private String ownerUser;
	private String assignUser;
	private Date assignTime;
	private String trackUser;
	private Date trackTime;
	private Long taskId;
	

	@Id
    @javax.persistence.Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ")
    @SequenceGenerator(name = "SEQ", sequenceName = "IAGENT.SEQ_ORDERHIST_TRACK_LOG")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "ORDER_ID", length = 16)
    @NotNull()
	public String getOrderId() {
		return orderId;
	}
	
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	@Column(name = "ASSIGN_USER", length = 16)
	public String getAssignUser() {
		return assignUser;
	}
	
	public void setAssignUser(String assignUser) {
		this.assignUser = assignUser;
	}
	
	@Column(name = "ASSIGN_TIME")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
	public Date getAssignTime() {
		return assignTime;
	}
	
	public void setAssignTime(Date assignTime) {
		this.assignTime = assignTime;
	}
	
	@Column(name = "TRACK_USER", length = 16)
	public String getTrackUser() {
		return trackUser;
	}
	
	public void setTrackUser(String trackUser) {
		this.trackUser = trackUser;
	}
	
	@Column(name = "TRACK_TIME")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
	public Date getTrackTime() {
		return trackTime;
	}
	
	public void setTrackTime(Date trackTime) {
		this.trackTime = trackTime;
	}

	@Column(name = "OWNER_USER", length = 16)
	public String getOwnerUser() {
		return ownerUser;
	}

	public void setOwnerUser(String ownerUser) {
		this.ownerUser = ownerUser;
	}

	@Column(name = "TASK_ID")
	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
}

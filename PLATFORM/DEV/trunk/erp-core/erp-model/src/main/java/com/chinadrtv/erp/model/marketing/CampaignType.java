/*
 * @(#)CampaignType.java 1.0 2013-4-17涓婂崍10:28:46
 *
 * 姗℃灉鍥介檯-erp寮�彂缁�
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.model.marketing;

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
 * @since 2013-4-17 涓婂崍10:28:46 钀ラ攢绫诲瀷
 */
@Table(name = "CAMPAIGN_TYPE", schema = "ACOAPP_MARKETING")
@Entity
public class CampaignType implements Serializable {

	private Long id;
	private String name;
	private String taskId;
	private String addUser;
	private Date addDate;
	private String updateUser;
	private Date updateDate;

	private String pageUrl;
	private String isVisible;

	private String isSchedule;
	
	public CampaignType() {
		super();
	}

	/**
	 * @return the id
	 */
	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "generator", sequenceName = "ACOAPP_MARKETING.SEQ_CAMPAIGN_TYPE")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
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
	 * @return the name
	 */
	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the taskId
	 */
	@Column(name = "TASK_ID")
	public String getTaskId() {
		return taskId;
	}

	/**
	 * @param taskId
	 *            the taskId to set
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	/**
	 * @return the addUser
	 */
	@Column(name = "ADD_USER")
	public String getAddUser() {
		return addUser;
	}

	/**
	 * @param addUser
	 *            the addUser to set
	 */
	public void setAddUser(String addUser) {
		this.addUser = addUser;
	}

	/**
	 * @return the addDate
	 */
	@Column(name = "ADD_DATE")
	public Date getAddDate() {
		return addDate;
	}

	/**
	 * @param addDate
	 *            the addDate to set
	 */
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	/**
	 * @return the updateUser
	 */
	@Column(name = "UPDATE_USER")
	public String getUpdateUser() {
		return updateUser;
	}

	/**
	 * @param updateUser
	 *            the updateUser to set
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	/**
	 * @return the updateDate
	 */
	@Column(name = "UPDATE_DATE")
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate
	 *            the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the pageUrl
	 */
	@Column(name = "PAGE_URL")
	public String getPageUrl() {
		return pageUrl;
	}

	/**
	 * @param pageUrl
	 *            the pageUrl to set
	 */
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	/**
	 * @return the isVisible
	 */
	@Column(name = "IS_VISIBLE")
	public String getIsVisible() {
		return isVisible;
	}

	/**
	 * @param isVisible the isVisible to set
	 */
	public void setIsVisible(String isVisible) {
		this.isVisible = isVisible;
	}

	/**
	 * @return the isSchedule
	 */
	@Column(name = "is_Schedule")
	public String getIsSchedule() {
		return isSchedule;
	}

	/**
	 * @param isSchedule the isSchedule to set
	 */
	public void setIsSchedule(String isSchedule) {
		this.isSchedule = isSchedule;
	}

}

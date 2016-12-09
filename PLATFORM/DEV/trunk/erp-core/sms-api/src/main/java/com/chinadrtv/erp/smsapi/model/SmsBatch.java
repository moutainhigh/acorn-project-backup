/*
 * @(#)SmsBatch.java 1.0 2013-2-18下午6:16:36
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.model;

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
 * @since 2013-2-18 下午6:16:36
 * 
 */
@Table(name = "SMS_BATCH", schema = "ACOAPP_MARKETING")
@Entity
public class SmsBatch implements Serializable {

	private Long id;
	private String batchId;
	private String uuid;
	private String department;
	private String tombile;
	private Date createtime;
	private String creator;
	private Date timestamps;
	private String connectId;
	private String param1;
	private String param2;
	private String param3;
	private String param4;
	private String param5;
	private String param6;
	private String param7;
	private String param8;
	private String param9;
	private String param10;

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SMS_BATCH")
	@SequenceGenerator(name = "SEQ_SMS_BATCH", sequenceName = "ACOAPP_MARKETING.SEQ_SMS_BATCH", allocationSize = 1)
	@Column(name = "ID")
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
	 * @return the batchId
	 */
	@Column(name = "BATCHID")
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
	 * @return the uuid
	 */
	@Column(name = "UUID")
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
	 * @return the department
	 */
	@Column(name = "DEPARTMENT")
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
	 * @return the tombile
	 */
	@Column(name = "TOMOBILE")
	public String getTombile() {
		return tombile;
	}

	/**
	 * @param tombile
	 *            the tombile to set
	 */
	public void setTombile(String tombile) {
		this.tombile = tombile;
	}

	/**
	 * @return the createtime
	 */
	@Column(name = "CREATETIME")
	public Date getCreatetime() {
		return createtime;
	}

	/**
	 * @param createtime
	 *            the createtime to set
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	/**
	 * @return the creator
	 */
	@Column(name = "CREATOR")
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
	 * @return the timestamps
	 */
	@Column(name = "TIMESTAMPS")
	public Date getTimestamps() {
		return timestamps;
	}

	/**
	 * @param timestamps
	 *            the timestamps to set
	 */
	public void setTimestamps(Date timestamps) {
		this.timestamps = timestamps;
	}

	/**
	 * @return the connectId
	 */
	@Column(name = "CONNECTID")
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
	 * @return the param1
	 */
	@Column(name = "PARAM1")
	public String getParam1() {
		return param1;
	}

	/**
	 * @param param1
	 *            the param1 to set
	 */
	public void setParam1(String param1) {
		this.param1 = param1;
	}

	/**
	 * @return the param2
	 */
	@Column(name = "PARAM2")
	public String getParam2() {
		return param2;
	}

	/**
	 * @param param2
	 *            the param2 to set
	 */
	public void setParam2(String param2) {
		this.param2 = param2;
	}

	/**
	 * @return the param3
	 */
	@Column(name = "PARAM3")
	public String getParam3() {
		return param3;
	}

	/**
	 * @param param3
	 *            the param3 to set
	 */
	public void setParam3(String param3) {
		this.param3 = param3;
	}

	/**
	 * @return the param4
	 */
	@Column(name = "PARAM4")
	public String getParam4() {
		return param4;
	}

	/**
	 * @param param4
	 *            the param4 to set
	 */
	public void setParam4(String param4) {
		this.param4 = param4;
	}

	/**
	 * @return the param5
	 */
	@Column(name = "PARAM5")
	public String getParam5() {
		return param5;
	}

	/**
	 * @param param5
	 *            the param5 to set
	 */
	public void setParam5(String param5) {
		this.param5 = param5;
	}

	/**
	 * @return the param6
	 */
	@Column(name = "PARAM6")
	public String getParam6() {
		return param6;
	}

	/**
	 * @param param6
	 *            the param6 to set
	 */
	public void setParam6(String param6) {
		this.param6 = param6;
	}

	/**
	 * @return the param7
	 */
	@Column(name = "PARAM7")
	public String getParam7() {
		return param7;
	}

	/**
	 * @param param7
	 *            the param7 to set
	 */
	public void setParam7(String param7) {
		this.param7 = param7;
	}

	/**
	 * @return the param8
	 */
	@Column(name = "PARAM8")
	public String getParam8() {
		return param8;
	}

	/**
	 * @param param8
	 *            the param8 to set
	 */
	public void setParam8(String param8) {
		this.param8 = param8;
	}

	/**
	 * @return the param9
	 */
	@Column(name = "PARAM9")
	public String getParam9() {
		return param9;
	}

	/**
	 * @param param9
	 *            the param9 to set
	 */
	public void setParam9(String param9) {
		this.param9 = param9;
	}

	/**
	 * @return the param10
	 */
	@Column(name = "PARAM10")
	public String getParam10() {
		return param10;
	}

	/**
	 * @param param10
	 *            the param10 to set
	 */
	public void setParam10(String param10) {
		this.param10 = param10;
	}

	/**
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 */
	public SmsBatch() {
		super();
		// TODO Auto-generated constructor stub
	}

}

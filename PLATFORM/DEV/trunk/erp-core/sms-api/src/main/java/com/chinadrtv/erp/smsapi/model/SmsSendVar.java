/*
 * @(#)CustomerSqlSource.java 1.0 2013-1-21下午2:09:48
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * 短信发送管理_静态发送变量</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-1-21 下午2:09:48
 * 
 */
@Table(name = "SMS_SEND_VAR", schema = "ACOAPP_MARKETING")
@Entity
public class SmsSendVar {

	private Long id;
	private String batch_id;
	private String var_name;
	private String var_value;
	private String create_user;
	private Date create_date;

	/**
	 * @return the id
	 */
	@Id
	@SequenceGenerator(name = "SEQ_SMS_SEND_VAR", sequenceName = "ACOAPP_MARKETING.SEQ_SMS_SEND_VAR", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SMS_SEND_VAR")
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
	 * @return the batch_id
	 */
	@Column(name = "batch_id")
	public String getBatch_id() {
		return batch_id;
	}

	/**
	 * @param batch_id
	 *            the batch_id to set
	 */
	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}

	/**
	 * @return the var_name
	 */
	@Column(name = "var_name")
	public String getVar_name() {
		return var_name;
	}

	/**
	 * @param var_name
	 *            the var_name to set
	 */
	public void setVar_name(String var_name) {
		this.var_name = var_name;
	}

	/**
	 * @return the var_value
	 */
	@Column(name = "var_value")
	public String getVar_value() {
		return var_value;
	}

	/**
	 * @param var_value
	 *            the var_value to set
	 */
	public void setVar_value(String var_value) {
		this.var_value = var_value;
	}

	/**
	 * @return the create_user
	 */
	@Column(name = "create_user")
	public String getCreate_user() {
		return create_user;
	}

	/**
	 * @param create_user
	 *            the create_user to set
	 */
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}

	/**
	 * @return the create_date
	 */
	@Column(name = "create_date")
	public Date getCreate_date() {
		return create_date;
	}

	/**
	 * @param create_date
	 *            the create_date to set
	 */
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	/*
	 * (非 Javadoc) <p>Title: hashCode</p> <p>Description: </p>
	 * 
	 * @return
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((batch_id == null) ? 0 : batch_id.hashCode());
		result = prime * result
				+ ((create_date == null) ? 0 : create_date.hashCode());
		result = prime * result
				+ ((create_user == null) ? 0 : create_user.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((var_name == null) ? 0 : var_name.hashCode());
		result = prime * result
				+ ((var_value == null) ? 0 : var_value.hashCode());
		return result;
	}

	/*
	 * (非 Javadoc) <p>Title: equals</p> <p>Description: </p>
	 * 
	 * @param obj
	 * 
	 * @return
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SmsSendVar other = (SmsSendVar) obj;
		if (batch_id == null) {
			if (other.batch_id != null)
				return false;
		} else if (!batch_id.equals(other.batch_id))
			return false;
		if (create_date == null) {
			if (other.create_date != null)
				return false;
		} else if (!create_date.equals(other.create_date))
			return false;
		if (create_user == null) {
			if (other.create_user != null)
				return false;
		} else if (!create_user.equals(other.create_user))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (var_name == null) {
			if (other.var_name != null)
				return false;
		} else if (!var_name.equals(other.var_name))
			return false;
		if (var_value == null) {
			if (other.var_value != null)
				return false;
		} else if (!var_value.equals(other.var_value))
			return false;
		return true;
	}

}

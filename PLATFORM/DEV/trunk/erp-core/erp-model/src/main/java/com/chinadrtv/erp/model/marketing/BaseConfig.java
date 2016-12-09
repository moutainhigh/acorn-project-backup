/*
 * @(#)CustomerSqlSource.java 1.0 2013-1-21下午2:09:48
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.model.marketing;

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
 * 基本参数配置表</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-1-21 下午2:09:48
 * 
 */
@Table(name = "BASE_CONFIG", schema = "ACOAPP_MARKETING")
@Entity
public class BaseConfig {

	private Long id;
	private String code;//
	private String name;//
	private String paraName;//
	private String paraValue;//
	private Integer postion;//

	/**
	 * @return the id
	 */
	@Id
	@SequenceGenerator(name = "SEQ_BASE_CONFIG", sequenceName = "ACOAPP_MARKETING.SEQ_BASE_CONFIG")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BASE_CONFIG")
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
	 * @return the code
	 */
	@Column(name = "CODE")
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
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
	 * @return the paraName
	 */
	@Column(name = "PARA_NAME")
	public String getParaName() {
		return paraName;
	}

	/**
	 * @param paraName
	 *            the paraName to set
	 */
	public void setParaName(String paraName) {
		this.paraName = paraName;
	}

	/**
	 * @return the paraValue
	 */
	@Column(name = "PARA_VALUE")
	public String getParaValue() {
		return paraValue;
	}

	/**
	 * @param paraValue
	 *            the paraValue to set
	 */
	public void setParaValue(String paraValue) {
		this.paraValue = paraValue;
	}

	/**
	 * @return the postion
	 */
	@Column(name = "POSTION")
	public Integer getPostion() {
		return postion;
	}

	/**
	 * @param postion
	 *            the postion to set
	 */
	public void setPostion(Integer postion) {
		this.postion = postion;
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
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((paraName == null) ? 0 : paraName.hashCode());
		result = prime * result
				+ ((paraValue == null) ? 0 : paraValue.hashCode());
		result = prime * result + ((postion == null) ? 0 : postion.hashCode());
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
		BaseConfig other = (BaseConfig) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (paraName == null) {
			if (other.paraName != null)
				return false;
		} else if (!paraName.equals(other.paraName))
			return false;
		if (paraValue == null) {
			if (other.paraValue != null)
				return false;
		} else if (!paraValue.equals(other.paraValue))
			return false;
		if (postion == null) {
			if (other.postion != null)
				return false;
		} else if (!postion.equals(other.postion))
			return false;
		return true;
	}

}

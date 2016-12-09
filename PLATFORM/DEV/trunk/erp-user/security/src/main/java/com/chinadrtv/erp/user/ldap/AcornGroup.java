/*
 * @(#)AcornGroup.java 1.0 2013-8-6上午11:01:37
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.user.ldap;

/**
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * none</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-8-6 上午11:01:37
 * 
 */
public class AcornGroup {

	private String name;
	private String acornAreaCode;
	private String acornGroupType;
	private String description;
	private String departmentNumber;

	

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the acornAreaCode
	 */
	public String getAcornAreaCode() {
		return acornAreaCode;
	}

	/**
	 * @param acornAreaCode
	 *            the acornAreaCode to set
	 */
	public void setAcornAreaCode(String acornAreaCode) {
		this.acornAreaCode = acornAreaCode;
	}

	/**
	 * @return the acornGroupType
	 */
	public String getAcornGroupType() {
		return acornGroupType;
	}

	/**
	 * @param acornGroupType
	 *            the acornGroupType to set
	 */
	public void setAcornGroupType(String acornGroupType) {
		this.acornGroupType = acornGroupType;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the departmentNumber
	 */
	//@Column(name = "departmentNumber")
	public String getDepartmentNumber() {
		return departmentNumber;
	}

	/**
	 * @param departmentNumber the departmentNumber to set
	 */
	public void setDepartmentNumber(String departmentNumber) {
		this.departmentNumber = departmentNumber;
	}

}

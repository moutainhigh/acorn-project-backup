/*
 * @(#)DepartmentInfo.java 1.0 2013-8-2下午2:30:00
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.uam.sync.model;

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
 * @since 2013-8-2 下午2:30:00
 * 
 */
public class DepartmentInfo {

	private String id;
	private String name;
	private String areaCode;
	private String code;
	private String dn;
    private String acornAreaCode;
    private String acornName;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
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
	 * @return the areaCode
	 */
	public String getAreaCode() {
		return areaCode;
	}

	/**
	 * @param areaCode
	 *            the areaCode to set
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	/**
	 * @return the code
	 */
	//@Column(name = "code")
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the dn
	 */
	public String getDn() {
		return dn;
	}

	/**
	 * @param dn the dn to set
	 */
	public void setDn(String dn) {
		this.dn = dn;
	}

    public String getAcornAreaCode() {
        return acornAreaCode;
    }

    public void setAcornAreaCode(String acornAreaCode) {
        this.acornAreaCode = acornAreaCode;
    }

    public String getAcornName() {
        return acornName;
    }

    public void setAcornName(String acornName) {
        this.acornName = acornName;
    }
}

/*
 * @(#)CtiLoginDto.java 1.0 2013-9-3下午1:36:10
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.sales.dto;

import java.security.PrivateKey;

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
 * @since 2013-9-3 下午1:36:10
 * 
 */
public class CtiLoginDto {

	private String areaCode;
	private String telephone;
	private String ipaddress;
    private String ctiServerIp;
    private String ctiServerIpBack;
    private String ctiServerPort;
    private String ctiServerPortBack;
    private String agentId;
    private String webapp;
    private Integer phoneType;
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
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * @param telephone
	 *            the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * @return the ipaddress
	 */
	public String getIpaddress() {
		return ipaddress;
	}

	/**
	 * @param ipaddress
	 *            the ipaddress to set
	 */
	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

    public String getCtiServerIp() {
        return ctiServerIp;
    }

    public void setCtiServerIp(String ctiServerIp) {
        this.ctiServerIp = ctiServerIp;
    }

    public String getCtiServerPort() {
        return ctiServerPort;
    }

    public void setCtiServerPort(String ctiServerPort) {
        this.ctiServerPort = ctiServerPort;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getWebapp() {
        return webapp;
    }

    public void setWebapp(String webapp) {
        this.webapp = webapp;
    }

    public String getCtiServerIpBack() {
        return ctiServerIpBack;
    }

    public void setCtiServerIpBack(String ctiServerIpBack) {
        this.ctiServerIpBack = ctiServerIpBack;
    }

    public String getCtiServerPortBack() {
        return ctiServerPortBack;
    }

    public void setCtiServerPortBack(String ctiServerPortBack) {
        this.ctiServerPortBack = ctiServerPortBack;
    }

    public Integer getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(Integer phoneType) {
        this.phoneType = phoneType;
    }
}



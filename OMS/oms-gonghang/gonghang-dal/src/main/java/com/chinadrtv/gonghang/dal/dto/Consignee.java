/*
 * @(#)Consignee.java 1.0 2014-5-13上午10:23:30
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.gonghang.dal.dto;

import java.io.Serializable;

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
 * @since 2014-5-13 上午10:23:30 
 * 
 */
public class Consignee implements Serializable {

	private static final long serialVersionUID = -7027083743987962106L;
	
	private String consigneeName;
	private String consigneeProvince;
	private String consigneeCity;
	private String consigneeDistrict;
	private String consigneeAddress;
	private String consigneeZipcode;
	private String consigneeMobile;
	private String consigneePhone;
	
	public String getConsigneeName() {
		return consigneeName;
	}
	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}
	public String getConsigneeProvince() {
		return consigneeProvince;
	}
	public void setConsigneeProvince(String consigneeProvince) {
		this.consigneeProvince = consigneeProvince;
	}
	public String getConsigneeCity() {
		return consigneeCity;
	}
	public void setConsigneeCity(String consigneeCity) {
		this.consigneeCity = consigneeCity;
	}
	public String getConsigneeDistrict() {
		return consigneeDistrict;
	}
	public void setConsigneeDistrict(String consigneeDistrict) {
		this.consigneeDistrict = consigneeDistrict;
	}
	public String getConsigneeAddress() {
		return consigneeAddress;
	}
	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}
	public String getConsigneeZipcode() {
		return consigneeZipcode;
	}
	public void setConsigneeZipcode(String consigneeZipcode) {
		this.consigneeZipcode = consigneeZipcode;
	}
	public String getConsigneeMobile() {
		return consigneeMobile;
	}
	public void setConsigneeMobile(String consigneeMobile) {
		this.consigneeMobile = consigneeMobile;
	}
	public String getConsigneePhone() {
		return consigneePhone;
	}
	public void setConsigneePhone(String consigneePhone) {
		this.consigneePhone = consigneePhone;
	}
}

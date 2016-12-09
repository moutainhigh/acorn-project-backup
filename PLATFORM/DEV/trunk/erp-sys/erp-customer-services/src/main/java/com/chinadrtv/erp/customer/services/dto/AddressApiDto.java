package com.chinadrtv.erp.customer.services.dto;

import java.io.Serializable;

public class AddressApiDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7780449840851510787L;

	private String provinceId;
	
	private Integer cityId;
	
	private Integer countyId;
	
	private Integer areaId;
	
	private String provinceName;
	
	private String cityName;
	
	private String countyName;
	
	private String areaName;
	
	private Integer spellId;
	
	private String zip;
	
	private Integer auditState;

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getCountyId() {
		return countyId;
	}

	public void setCountyId(Integer countyId) {
		this.countyId = countyId;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Integer getSpellId() {
		return spellId;
	}

	public void setSpellId(Integer spellId) {
		this.spellId = spellId;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public Integer getAuditState() {
		return auditState;
	}

	public void setAuditState(Integer auditState) {
		this.auditState = auditState;
	}
	
}

package com.chinadrtv.erp.uc.dto;

public class CountyDto implements java.io.Serializable {
	private Integer countyId;
	private String countyName;
	private String countyCode ;
    private String areaCode;
	private String value;
	public Integer getCountyId() {
		return countyId;
	}
	public void setCountyId(Integer countyId) {
		this.countyId = countyId;
	}
	public String getCountyName() {
		return countyName;
	}
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
	public String getCountyCode() {
		return countyCode;
	}
	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	
}

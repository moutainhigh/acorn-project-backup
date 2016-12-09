package com.chinadrtv.erp.uc.dto;

/**
 * Created with IntelliJ IDEA.
 * User: gaodejian
 * Date: 13-6-20
 * Time: 上午11:25
 * To change this template use File | Settings | File Templates.
 */
public class ContactAddressDto {

    private String province;
    private Integer cityId;
    private Integer countyId;
    private Integer areaId;
    private String address;
    private String zip;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}

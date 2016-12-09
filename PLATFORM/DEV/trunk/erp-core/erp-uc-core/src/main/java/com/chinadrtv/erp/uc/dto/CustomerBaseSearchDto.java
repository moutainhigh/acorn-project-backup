package com.chinadrtv.erp.uc.dto;

import java.io.Serializable;

/**
 * 查找客户的基础查找条件类
 * Title: CustomerBaseSearchDto
 * Description:
 * User: xieguoqiang
 *
 * @version 1.0
 */
public class CustomerBaseSearchDto implements Serializable {
    private String phone;
    private String name;
    private String provinceId = "";
    private String cityId = "";
    private String countyId = "";
    private String areaId = "";

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCountyId() {
        return countyId;
    }

    public void setCountyId(String countyId) {
        this.countyId = countyId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }
}

package com.chinadrtv.erp.sales.dto;

import com.chinadrtv.erp.tc.core.dto.OrderAuditQueryDto;
import com.chinadrtv.erp.tc.core.dto.OrderQueryDto;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-5-31
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class MyOrderQueryDto extends OrderAuditQueryDto {
    private String provinceId;
    private String cityId;
    private String countyId;
    private String areaId;

    public Integer getCountRows() {
        return countRows;
    }

    public void setCountRows(Integer countRows) {
        this.countRows = countRows;
    }

    private Integer countRows;

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

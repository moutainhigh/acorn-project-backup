package com.chinadrtv.erp.sales.dto;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-5-31
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class MyOrderQueryAuditDto extends MyOrderQueryDto {
    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    private String auditStatus;

    public Integer getCountRows() {
        return countRows;
    }

    public void setCountRows(Integer countRows) {
        this.countRows = countRows;
    }

    private Integer countRows;
}

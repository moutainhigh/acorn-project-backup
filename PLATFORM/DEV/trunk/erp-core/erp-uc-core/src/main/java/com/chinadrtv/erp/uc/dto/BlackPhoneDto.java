/*
 * @(#)AddressDto.java 1.0 2013-5-3下午2:49:48
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.dto;

import com.chinadrtv.erp.model.BlackList;
import com.chinadrtv.erp.model.BlackPhone;

import java.util.Date;
import java.util.List;

public class BlackPhoneDto {
    private Long blackPhoneId;
    private String phoneNum;
    private Integer addTimes;
    private Integer status;
    private String approveManager;
    private Date approveDate;
    private List<BlackList> blackLists;

    public void setBlackPhone(BlackPhone blackPhone) {
        this.blackPhoneId = blackPhone.getId();
        this.phoneNum = blackPhone.getPhoneNum();
        this.addTimes = blackPhone.getAddTimes();
        this.status = blackPhone.getStatus();
        this.approveDate = blackPhone.getApproveDate();
        this.approveManager = blackPhone.getApproveManager();
    }

    public List<BlackList> getBlackLists() {
        return blackLists;
    }

    public void setBlackLists(List<BlackList> blackLists) {
        this.blackLists = blackLists;
    }

    public Long getBlackPhoneId() {
        return blackPhoneId;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public Integer getAddTimes() {
        return addTimes;
    }

    public Integer getStatus() {
        return status;
    }

    public String getApproveManager() {
        return approveManager;
    }

    public void setApproveManager(String approveManager) {
        this.approveManager = approveManager;
    }

    public Date getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }
}

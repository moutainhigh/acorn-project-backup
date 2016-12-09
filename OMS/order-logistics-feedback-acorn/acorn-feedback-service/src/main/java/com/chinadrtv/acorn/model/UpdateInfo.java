package com.chinadrtv.acorn.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-10-30
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class UpdateInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Integer logisticProviderID;
    private String billNo;
    private String operaType;
    private Date operaDate;
    private String station;
    private String operator;
    private String refuseReason;
    private String problemReason;
    private String remark;
    private Date update_time;

    public Integer getLogisticProviderID() {
        return logisticProviderID;
    }

    public void setLogisticProviderID(Integer logisticProviderID) {
        this.logisticProviderID = logisticProviderID;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getOperaType() {
        return operaType;
    }

    public void setOperaType(String operaType) {
        this.operaType = operaType;
    }

    public Date getOperaDate() {
        return operaDate;
    }

    public void setOperaDate(Date operaDate) {
        this.operaDate = operaDate;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }

    public String getProblemReason() {
        return problemReason;
    }

    public void setProblemReason(String problemReason) {
        this.problemReason = problemReason;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

}

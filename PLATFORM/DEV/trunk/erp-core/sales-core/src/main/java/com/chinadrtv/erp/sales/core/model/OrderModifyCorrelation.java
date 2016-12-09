package com.chinadrtv.erp.sales.core.model;

import com.chinadrtv.erp.marketing.core.common.UserBpmTaskType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-9-3
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class OrderModifyCorrelation {

    public OrderModifyCorrelation()
    {
        userBpmTaskType=null;
        orderModifyCorrelationList=new ArrayList<OrderModifyCorrelation>();
    }

    public UserBpmTaskType getUserBpmTaskType() {
        return userBpmTaskType;
    }

    public void setUserBpmTaskType(UserBpmTaskType userBpmTaskType) {
        this.userBpmTaskType = userBpmTaskType;
    }

    public List<OrderModifyCorrelation> getOrderModifyCorrelationList() {
        return orderModifyCorrelationList;
    }

    public void setOrderModifyCorrelationList(List<OrderModifyCorrelation> orderModifyCorrelationList) {
        this.orderModifyCorrelationList = orderModifyCorrelationList;
    }

    public List<String> getProcInstIdList() {
        return procInstIdList;
    }

    public void setProcInstIdList(List<String> procInstIdList) {
        this.procInstIdList = procInstIdList;
    }

    private List<String> procInstIdList;
    private UserBpmTaskType userBpmTaskType;
    private List<OrderModifyCorrelation> orderModifyCorrelationList;
}

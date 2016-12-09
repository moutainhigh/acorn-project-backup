package com.chinadrtv.erp.sales.dto;

import com.chinadrtv.erp.model.agent.CallbackTask;

import java.io.Serializable;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-6-11
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class CallbackTaskHandleDto extends CallbackTask implements Serializable {
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    protected String address;

    public String getMainAddress() {
        return mainAddress;
    }

    public void setMainAddress(String mainAddress) {
        this.mainAddress = mainAddress;
    }

    protected String mainAddress;
}

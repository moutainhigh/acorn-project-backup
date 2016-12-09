package com.chinadrtv.erp.oms.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-7-16
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class NetOrderInfo implements Serializable {
    public String getOps_trade_id() {
        return ops_trade_id;
    }

    public void setOps_trade_id(String ops_trade_id) {
        this.ops_trade_id = ops_trade_id;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    private String ops_trade_id;
    private String process;
    private Date created;

}

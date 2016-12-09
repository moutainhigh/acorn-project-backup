package com.chinadrtv.erp.oms.model;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-29
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class OrderRefuseResult implements java.io.Serializable {
    public OrderRefuseResult()
    {
        this.code=RefuseCode.SUCC.getCode();
        this.dsc="";
    }


    public OrderRefuseResult(int code, String dsc)
    {
        this.code=code;
        this.dsc=dsc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDsc() {
        return dsc;
    }

    public void setDsc(String dsc) {
        this.dsc = dsc;
    }

    private int code;
    private String dsc;
}

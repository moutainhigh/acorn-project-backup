package com.chinadrtv.erp.oms.model;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-29
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public enum RefuseCode {
    SUCC(0,"成功"),
    UNFORMAT(1,"参数错误"),
    REFUND(2,"订单为半签收时，不能进行退包操作"),
    NOEXIST(3,"订单不存在"),
    FINI(4,"订单已完成时，不能进行退包操作");

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

    private RefuseCode(int code,String dsc)
    {
        this.code=code;
        this.dsc=dsc;
    }

}

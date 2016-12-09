package com.chinadrtv.erp.pay.core.icbc.model;

import org.codehaus.jackson.annotate.JsonCreator;

import javax.crypto.Cipher;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-8-6
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public enum ICBCReqType {
    LOGIN("签到", 1),
    LOGOUT("签退", 2),
    PAY("分期付款",12),
    CANCELPAY("分期付款反交易",13),
    RECONCILIATION("分期付款对账",14),
    AUTHENTICATION("客户身份认证",22),
    PAYONCE("带身份认证的消费",23)
    ;

    private String name;
    private int index;
    //TcpInboundGateway tcpInboundGateway;
    ICBCReqType(String name, int index)
    {
        //Cipher cipher=Cipher.getInstance();
        this.name=name;
        this.index=index;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }

    @JsonCreator
    public static ICBCReqType fromIndex(Integer index) {
        if(index != null) {
            for(ICBCReqType icbcReqType : ICBCReqType.values()) {
                if(icbcReqType.getIndex()==index.intValue()) {
                    return icbcReqType;
                }
            }

            throw new IllegalArgumentException(index + " is an invalid index.");
        }

        throw new IllegalArgumentException("A value was not provided.");
    }
}

package com.chinadrtv.erp.pay.core.icbc.model;

/**
 * 建行请求报文基类.
 * User: 徐志凯
 * Date: 13-8-7
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class ICBCReqBaseInfo implements Marshal {
    public final static String delimiter="|";

    protected ICBCReqType reqType;
    protected int traceNum;
    protected String merchantNo;
    protected String prodNo;


    public ICBCReqType getReqType() {
        return reqType;
    }

    public void setReqType(ICBCReqType reqType) {
        this.reqType = reqType;
    }

    public int getTraceNum() {
        return traceNum;
    }

    public void setTraceNum(int traceNum) {
        this.traceNum = traceNum;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getProdNo() {
        return prodNo;
    }

    public void setProdNo(String prodNo) {
        this.prodNo = prodNo;
    }


    public String marshal() {
        StringBuilder stringBuilder=new StringBuilder(String.format("REQ%02d",reqType.getIndex()));
        stringBuilder.append(delimiter);
        stringBuilder.append(traceNum);
        stringBuilder.append(delimiter);
        stringBuilder.append(merchantNo);
        stringBuilder.append(delimiter);
        stringBuilder.append(prodNo);
        stringBuilder.append(delimiter);
        return stringBuilder.toString();
    }

    public String unmarshal(String data) {
        return null;
    }
}

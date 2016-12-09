package com.chinadrtv.erp.pay.core.icbc.model;

/**
 * 建行在线索权响应报文基类.
 * User: 徐志凯
 * Date: 13-8-7
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class ICBCResBaseInfo implements Marshal {
    public final static String delimiter="|";

    public ICBCReqType getReqType() {
        return reqType;
    }

    public void setReqType(ICBCReqType reqType) {
        this.reqType = reqType;
    }

    public boolean isbSucc() {
        return bSucc;
    }

    public void setbSucc(boolean bSucc) {
        this.bSucc = bSucc;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
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

    //protected int length;
    protected ICBCReqType reqType;
    boolean bSucc;
    protected String errorMsg;
    protected int traceNum;
    protected String merchantNo;
    protected String prodNo;

    public String marshal() {
        return null;
    }

    public String unmarshal(String data) {
        String[] datas=data.split("\\"+delimiter,-1);
        if(datas.length>=6)
        {
            int index=Integer.parseInt(datas[0].replace("RES",""));
            reqType=ICBCReqType.fromIndex(index);
            if("0".equals(datas[1]))
            {
                bSucc=true;
            }
            else
            {
                bSucc=false;
            }
            if(datas[2]!=null)
                errorMsg=datas[2].trim();

            traceNum=Integer.parseInt(datas[3]);
            merchantNo=datas[4].trim();
            prodNo=datas[5].trim();

            StringBuilder stringBuilder=new StringBuilder();
            for (int i=6;i<datas.length;i++)
            {
                stringBuilder.append(datas[i]);
                stringBuilder.append(delimiter);
            }
            return stringBuilder.toString();
        }
        return data;
    }
}

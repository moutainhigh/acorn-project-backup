package com.chinadrtv.erp.pay.core.icbc.model;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-10-14
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class ICBCResPayOnce extends ICBCResBaseInfo {
    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Date getTransTime() {
        return transTime;
    }

    public void setTransTime(Date transTime) {
        this.transTime = transTime;
    }

    protected String orderNum;//银行端交易序号
    protected Date transTime;//交易日期 年月日


    @Override
    public String unmarshal(String data) {
        data=super.unmarshal(data);
        String[] datas=data.split("\\"+delimiter,-1);
        if(datas.length>=2)
        {
            orderNum=datas[0].trim();
            try{
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
                transTime=simpleDateFormat.parse(datas[1].trim());
            }catch (Exception exp)
            {

            }
        }
        return "";
    }
}

package com.chinadrtv.erp.sales.dto;

import com.chinadrtv.erp.model.agent.OrderDetail;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-6-18
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class OrderDetailDto extends OrderDetail {
    public String getModifyFlag() {
        return modifyFlag;
    }

    public void setModifyFlag(String modifyFlag) {
        this.modifyFlag = modifyFlag;
    }

    private String modifyFlag;//0-存在 1-增加 2-删除

    public String getProdtypename() {
        return prodtypename;
    }

    public void setProdtypename(String prodtypename) {
        this.prodtypename = prodtypename;
    }

    private String prodtypename;
}

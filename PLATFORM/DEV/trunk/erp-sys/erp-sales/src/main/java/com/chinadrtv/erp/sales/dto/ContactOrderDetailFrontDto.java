package com.chinadrtv.erp.sales.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 联系人历史订单明细信息
 * User: 徐志凯
 * Date: 13-6-24
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class ContactOrderDetailFrontDto implements Serializable {
    private String prodId;
    private String prodName;
    private String typeName;
    private BigDecimal price;
    private Integer quantity;
    private String soldwith;//销售方式

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getSoldwith() {
        return soldwith;
    }

    public void setSoldwith(String soldwith) {
        this.soldwith = soldwith;
    }
}

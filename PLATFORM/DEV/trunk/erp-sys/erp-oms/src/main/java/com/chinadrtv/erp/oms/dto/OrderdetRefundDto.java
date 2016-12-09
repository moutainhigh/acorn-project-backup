package com.chinadrtv.erp.oms.dto;

import java.math.BigDecimal;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-3-27
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class OrderdetRefundDto implements java.io.Serializable {
    public OrderdetRefundDto()
    {
        checkInPayment=BigDecimal.ZERO;
        checkInPrice=BigDecimal.ZERO;
        checkInQuantity=0L;
    }
    private Long id;
    private String prodName;
    private Long prodQuantity;
    private BigDecimal prodPrice;
    private BigDecimal prodPayment;
    private Long checkInQuantity;
    private BigDecimal checkInPrice;
    private BigDecimal checkInPayment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public Long getProdQuantity() {
        return prodQuantity;
    }

    public void setProdQuantity(Long prodQuantity) {
        this.prodQuantity = prodQuantity;
    }

    public BigDecimal getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(BigDecimal prodPrice) {
        this.prodPrice = prodPrice;
    }

    public BigDecimal getProdPayment() {
        return prodPayment;
    }

    public void setProdPayment(BigDecimal prodPayment) {
        this.prodPayment = prodPayment;
    }

    public Long getCheckInQuantity() {
        return checkInQuantity;
    }

    public void setCheckInQuantity(Long checkInQuantity) {
        this.checkInQuantity = checkInQuantity;
    }

    public BigDecimal getCheckInPrice() {
        return checkInPrice;
    }

    public void setCheckInPrice(BigDecimal checkInPrice) {
        this.checkInPrice = checkInPrice;
    }

    public BigDecimal getCheckInPayment() {
        return checkInPayment;
    }

    public void setCheckInPayment(BigDecimal checkInPayment) {
        this.checkInPayment = checkInPayment;
    }
}

package com.chinadrtv.erp.tc.core.constant.model.shipment;

import java.io.Serializable;

/**
 * 运单出库扫描输入参数 (TC).
 * User: 徐志凯
 * Date: 13-2-19
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class RequestScanOutInfo implements Serializable {
    /**
     * 订单号
     */
    private String orderId;
    /**
     * 订单版本号
     */
    private Long revision;
    /**
     * 面单号
     */
    private String mailId;
    /**
     * 当前操作人
     */
    private String user;

    /**
     * 承运商
     * 兼容老版本订单
     */
    private String carrier;

    /**
     *  邮寄类型
     *  兼容老版本订单
     */
    private String mailType;

    /**
     *  商品总价格
     *  兼容老版本订单
     */
    private String prodPrice;

    /**
     * 订单总价格
     * 兼容老版本订单
     */
    private String totalPrice;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getRevision() {
        return revision;
    }

    public void setRevision(Long revision) {
        this.revision = revision;
    }

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }



    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getMailType() {
        return mailType;
    }

    public void setMailType(String mailType) {
        this.mailType = mailType;
    }

    public String getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(String prodPrice) {
        this.prodPrice = prodPrice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}

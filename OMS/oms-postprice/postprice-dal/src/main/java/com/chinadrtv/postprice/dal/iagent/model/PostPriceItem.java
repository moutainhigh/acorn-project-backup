package com.chinadrtv.postprice.dal.iagent.model;

import java.math.BigDecimal;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-21
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class PostPriceItem {
    private Long shipmentHeadId;

    private Long entityId;

    //是否拒收
    //5:正常， 6：退货
    private String accountStatusId;

    //包裹重量
    private BigDecimal weight;

    //订单id ORDER_REF_HIS_ID
    private Long orderRefHisId;

    //订单版本ORDER_REF_REVISION
    private Integer orderRefRevision;

    //orderId
    private String orderId;

    //订单总金额
    private BigDecimal totlePrice;

    //邮费
    private BigDecimal postFee1;
    //成功服务费或拒收服务费
    private BigDecimal postFee2;
    //成功或拒收订单按费率收的费用
    private BigDecimal postFee3;

    public BigDecimal getTotlePrice() {
        return totlePrice;
    }
    public void setTotlePrice(BigDecimal totlePrice) {
        this.totlePrice = totlePrice;
    }
    public Long getShipmentHeadId() {
        return shipmentHeadId;
    }
    public void setShipmentHeadId(Long shipmentHeadId) {
        this.shipmentHeadId = shipmentHeadId;
    }
    public BigDecimal getPostFee1() {
        return postFee1;
    }
    public void setPostFee1(BigDecimal postFee1) {
        this.postFee1 = postFee1;
    }
    public Long getEntityId() {
        return entityId;
    }
    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }
    public String getAccountStatusId() {
        return accountStatusId;
    }
    public void setAccountStatusId(String accountStatusId) {
        this.accountStatusId = accountStatusId;
    }
    public BigDecimal getWeight() {
        return weight;
    }
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }
    public BigDecimal getPostFee2() {
        return postFee2;
    }
    public void setPostFee2(BigDecimal postFee2) {
        this.postFee2 = postFee2;
    }
    public BigDecimal getPostFee3() {
        return postFee3;
    }
    public void setPostFee3(BigDecimal postFee3) {
        this.postFee3 = postFee3;
    }
    public Long getOrderRefHisId() {
        return orderRefHisId;
    }
    public void setOrderRefHisId(Long orderRefHisId) {
        this.orderRefHisId = orderRefHisId;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public Integer getOrderRefRevision() {
        return orderRefRevision;
    }
    public void setOrderRefRevision(Integer orderRefRevision) {
        this.orderRefRevision = orderRefRevision;
    }
}

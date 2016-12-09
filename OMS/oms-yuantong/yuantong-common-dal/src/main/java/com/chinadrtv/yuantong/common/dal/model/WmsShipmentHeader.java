package com.chinadrtv.yuantong.common.dal.model;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 14-2-13
 * Time: 下午3:52
 * To change this template use File | Settings | File Templates.
 * 圆通推送数据
 */
public class WmsShipmentHeader implements java.io.Serializable {
    private String containerId;
    private String shipmentId;
    private String scmShipmentId;
    private String customer;
    private String shipToAddress;
    private String customerPhoneNum;
    private Double weight;
    private String shipToProvinceName;
    private String shipToCityName;
    private Date actualShipDate;
    private String carrier;

    private Double goodsValue;
    private Double totalValue;
    private Integer orderType;

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public Date getActualShipDate() {
        return actualShipDate;
    }

    public void setActualShipDate(Date actualShipDate) {
        this.actualShipDate = actualShipDate;
    }

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getScmShipmentId() {
        return scmShipmentId;
    }

    public void setScmShipmentId(String scmShipmentId) {
        this.scmShipmentId = scmShipmentId;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getShipToAddress() {
        return shipToAddress;
    }

    public void setShipToAddress(String shipToAddress) {
        this.shipToAddress = shipToAddress;
    }

    public String getCustomerPhoneNum() {
        return customerPhoneNum;
    }

    public void setCustomerPhoneNum(String customerPhoneNum) {
        this.customerPhoneNum = customerPhoneNum;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getShipToProvinceName() {
        return shipToProvinceName;
    }

    public void setShipToProvinceName(String shipToProvinceName) {
        this.shipToProvinceName = shipToProvinceName;
    }

    public String getShipToCityName() {
        return shipToCityName;
    }

    public void setShipToCityName(String shipToCityName) {
        this.shipToCityName = shipToCityName;
    }


    public Double getGoodsValue() {
        return goodsValue;
    }

    public void setGoodsValue(Double goodsValue) {
        this.goodsValue = goodsValue;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }
}

package com.chinadrtv.erp.oms.dto;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-7-23
 * Time: 下午3:46
 * To change this template use File | Settings | File Templates.
 * 承运商指派vo
 */
public class LogisticsDto implements java.io.Serializable {
    private Long id;
    private String orderId;
    private String shipmentId;
    private String logisticsStatusId;
    private String entityId;
    private String warehouseId;
    private String address;
    private String status;
    private String prodName;
    private Double totalPrice;
    private String confirmdt;

    public String getConfirmdt() {
        return confirmdt;
    }

    public void setConfirmdt(String confirmdt) {
        this.confirmdt = confirmdt;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getLogisticsStatusId() {
        return logisticsStatusId;
    }

    public void setLogisticsStatusId(String logisticsStatusId) {
        this.logisticsStatusId = logisticsStatusId;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

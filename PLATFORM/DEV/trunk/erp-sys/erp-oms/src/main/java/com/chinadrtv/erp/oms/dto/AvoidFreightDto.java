package com.chinadrtv.erp.oms.dto;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-3-27
 * Time: 上午11:04
 * To change this template use File | Settings | File Templates.
 * 免运费列表
 */
public class AvoidFreightDto {
    private long id;
    private String senddt;
    private String shipmentId;
    private String mailId;
    private String orderType;
    private String totalPrice;
    private String cutFreight;          //减免后的运费
    private String mailPrice;        //原始运费
    private String tel;
    private String addrdesc;

    private String name;    //客户名称
    private String entityId;   //承运商

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSenddt() {
        return senddt;
    }

    public void setSenddt(String senddt) {
        this.senddt = senddt;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCutFreight() {
        return cutFreight;
    }

    public void setCutFreight(String cutFreight) {
        this.cutFreight = cutFreight;
    }

    public String getMailPrice() {
        return mailPrice;
    }

    public void setMailPrice(String mailPrice) {
        this.mailPrice = mailPrice;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddrdesc() {
        return addrdesc;
    }

    public void setAddrdesc(String addrdesc) {
        this.addrdesc = addrdesc;
    }
}

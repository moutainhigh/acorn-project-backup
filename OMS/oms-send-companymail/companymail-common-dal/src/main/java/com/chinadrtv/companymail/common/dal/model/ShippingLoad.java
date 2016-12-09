package com.chinadrtv.companymail.common.dal.model;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-8-9
 * Time: 下午2:53
 * To change this template use File | Settings | File Templates.
 * 中间转换表
 */
public class ShippingLoad implements java.io.Serializable {
    private Integer internalLoadNum;    //装载号
    private String carrier;              //送货公司
    private Double orderMoney;          //订单金额

    public Integer getInternalLoadNum() {
        return internalLoadNum;
    }

    public void setInternalLoadNum(Integer internalLoadNum) {
        this.internalLoadNum = internalLoadNum;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public Double getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(Double orderMoney) {
        this.orderMoney = orderMoney;
    }
}

package com.chinadrtv.expeditingmail.common.dal.model;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-11-21
 * Time: 下午1:15
 * To change this template use File | Settings | File Templates.
 */
public class DeliveryMailDetail implements java.io.Serializable {
    private String orderid;      //订单号
    private String mailid;        //包裹号
    private String rfoutdat;     //交际日期
    private String roadday;       //标准天数
    private String outDay;        //超时效天数
    private String warehouse;    //发货仓库

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getMailid() {
        return mailid;
    }

    public void setMailid(String mailid) {
        this.mailid = mailid;
    }

    public String getRfoutdat() {
        return rfoutdat;
    }

    public void setRfoutdat(String rfoutdat) {
        this.rfoutdat = rfoutdat;
    }

    public String getRoadday() {
        return roadday;
    }

    public void setRoadday(String roadday) {
        this.roadday = roadday;
    }

    public String getOutDay() {
        return outDay;
    }

    public void setOutDay(String outDay) {
        this.outDay = outDay;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }
}

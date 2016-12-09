package com.chinadrtv.erp.model;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: taoyawei
 * Date: 13-1-11
 * Time: 下午1:08
 * 订单送货反馈表
 */
public class OrderDeliverFeed {
    private String orderid;//订单编号
    private String mailid;//邮件编号
    private Date contactdate;//交寄时间
    private String feedbackstatus;//反馈状态
    private String rejectiondesc;//失败原因
    private String nowmoney;//应收金额
    private Double clearfee;//实际结算金额
    private Double mailprice;//投递费
    private Double handlingfee;//手续费

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

    public Date getContactdate() {
        return contactdate;
    }

    public void setContactdate(Date contactdate) {
        this.contactdate = contactdate;
    }

    public String getFeedbackstatus() {
        return feedbackstatus;
    }

    public void setFeedbackstatus(String feedbackstatus) {
        this.feedbackstatus = feedbackstatus;
    }

    public String getRejectiondesc() {
        return rejectiondesc;
    }

    public void setRejectiondesc(String rejectiondesc) {
        this.rejectiondesc = rejectiondesc;
    }

    public String getNowmoney() {
        return nowmoney;
    }

    public void setNowmoney(String nowmoney) {
        this.nowmoney = nowmoney;
    }

    public Double getClearfee() {
        return clearfee;
    }

    public void setClearfee(Double clearfee) {
        this.clearfee = clearfee;
    }

    public Double getMailprice() {
        return mailprice;
    }

    public void setMailprice(Double mailprice) {
        this.mailprice = mailprice;
    }

    public Double getHandlingfee() {
        return handlingfee;
    }

    public void setHandlingfee(Double handlingfee) {
        this.handlingfee = handlingfee;
    }


}

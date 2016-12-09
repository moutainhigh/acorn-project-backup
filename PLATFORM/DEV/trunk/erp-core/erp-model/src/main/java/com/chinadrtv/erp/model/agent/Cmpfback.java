package com.chinadrtv.erp.model.agent;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 13-7-5
 * Time: 下午3:23
 * 投诉处理相关.
 */
@Entity
@Table(name = "CMPFBACK", schema = "IAGENT")
public class Cmpfback {
    private String caseid;  //事件编号
    private String contactid;  //\客户编号
    private String orderid;  //订单编号
    private String productdsc;  //	产品名称
    private Date ordercrdt;  //订购日期
    private String probdsc;  //	投诉内容
    private String yijian;  //	处理意见
    private String crusr;  //	投诉受理人
    private Date CRDT;  //创建日期
    private String chuliqk;  //处理情况
    private String chuliusr;  //处理人
    private String feedbackdsc;  //反馈
    private Date fbdt;  //反馈日期
    private String fbusr;  //反馈人
    private String result;  //结果
    private String reason;  //原因
    private String dnote;  //备注
    private String phone;  //客户电话
    private Date chulidt;  //处理日期
    private String areacode;  //所属区域
    private String yijian2;  //协办业务

    @Id
    @Column(name = "CASEID", unique = true, nullable = false, insertable = true, updatable = true, length = 16)
    public String getCaseid() {
        return caseid;
    }

    public void setCaseid(String caseid) {
        this.caseid = caseid;
    }

    @Column(name = "CONTACTID",length = 16)
    public String getContactid() {
        return contactid;
    }

    public void setContactid(String contactid) {
        this.contactid = contactid;
    }

    @Column(name = "ORDERID",length = 16)
    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    @Column(name = "PRODUCTDSC",length = 1000)
    public String getProductdsc() {
        return productdsc;
    }

    public void setProductdsc(String productdsc) {
        this.productdsc = productdsc;
    }

    @Column(name = "ORDERCRDT",length = 11)
    public Date getOrdercrdt() {
        return ordercrdt;
    }

    public void setOrdercrdt(Date ordercrdt) {
        this.ordercrdt = ordercrdt;
    }
    @Column(name = "PROBDSC",length = 1000)
    public String getProbdsc() {
        return probdsc;
    }

    public void setProbdsc(String probdsc) {
        this.probdsc = probdsc;
    }
    @Column(name = "YIJIAN",length = 1000)
    public String getYijian() {
        return yijian;
    }

    public void setYijian(String yijian) {
        this.yijian = yijian;
    }
    @Column(name = "CRUSR",length = 20)
    public String getCrusr() {
        return crusr;
    }

    public void setCrusr(String crusr) {
        this.crusr = crusr;
    }
    @Column(name = "CRDT",length = 11)
    public Date getCRDT() {
        return CRDT;
    }

    public void setCRDT(Date CRDT) {
        this.CRDT = CRDT;
    }
    @Column(name = "CHULIQK",length = 1000)
    public String getChuliqk() {
        return chuliqk;
    }

    public void setChuliqk(String chuliqk) {
        this.chuliqk = chuliqk;
    }
    @Column(name = "CHULIUSR",length = 20)
    public String getChuliusr() {
        return chuliusr;
    }

    public void setChuliusr(String chuliusr) {
        this.chuliusr = chuliusr;
    }
    @Column(name = "FEEDBACKDSC",length = 1000)
    public String getFeedbackdsc() {
        return feedbackdsc;
    }

    public void setFeedbackdsc(String feedbackdsc) {
        this.feedbackdsc = feedbackdsc;
    }
    @Column(name = "FBDT",length = 11)
    public Date getFbdt() {
        return fbdt;
    }

    public void setFbdt(Date fbdt) {
        this.fbdt = fbdt;
    }
    @Column(name = "FBUSR",length = 20)
    public String getFbusr() {
        return fbusr;
    }

    public void setFbusr(String fbusr) {
        this.fbusr = fbusr;
    }
    @Column(name = "RESULT",length = 1)
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
    @Column(name = "REASON",length = 1000)
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
    @Column(name = "DNOTE",length = 1000)
    public String getDnote() {
        return dnote;
    }

    public void setDnote(String dnote) {
        this.dnote = dnote;
    }
    @Column(name = "PHONE",length = 1000)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    @Column(name = "CHULIDT",length = 11)
    public Date getChulidt() {
        return chulidt;
    }

    public void setChulidt(Date chulidt) {
        this.chulidt = chulidt;
    }
    @Column(name = "AREACODE",length = 6)
    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }
    @Column(name = "YIJIAN2",length = 1000)
    public String getYijian2() {
        return yijian2;
    }

    public void setYijian2(String yijian2) {
        this.yijian2 = yijian2;
    }

}

package com.chinadrtv.erp.model.service;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: cuiming
 * Date: 14-4-22
 * Time: 下午2:47
 * To change this template use File | Settings | File Templates.
 */
@Table(name="REFUNDSMS", schema = "IAGENT")
public class Refundsms {

    private String id;
    private String orderid;
    private String contactid;
    private String bankname;
    private String idcard;
    private String bankcardnum;
    private String crusr;
    private Date crdt;
    private String mdusr;
    private Date mddt;
    private String holdername;
    private String phone;
    private Long money;
    private Long status;
    private String refundtype;
    @Id
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @Column(name = "ORDERID")
    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }
    @Column(name = "CONTACTID")
    public String getContactid() {
        return contactid;
    }

    public void setContactid(String contactid) {
        this.contactid = contactid;
    }
    @Column(name = "BANKNAME")
    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }
    @Column(name = "IDCARD")
    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }
    @Column(name = "BANKCARDNUM")
    public String getBankcardnum() {
        return bankcardnum;
    }

    public void setBankcardnum(String bankcardnum) {
        this.bankcardnum = bankcardnum;
    }
    @Column(name = "CRUSR")
    public String getCrusr() {
        return crusr;
    }

    public void setCrusr(String crusr) {
        this.crusr = crusr;
    }
    @Column(name = "CRDT")
    public Date getCrdt() {
        return crdt;
    }

    public void setCrdt(Date crdt) {
        this.crdt = crdt;
    }
    @Column(name = "MDUSR")
    public String getMdusr() {
        return mdusr;
    }

    public void setMdusr(String mdusr) {
        this.mdusr = mdusr;
    }
    @Column(name = "MDDT")
    public Date getMddt() {
        return mddt;
    }

    public void setMddt(Date mddt) {
        this.mddt = mddt;
    }
    @Column(name = "HOLDERNAME")
    public String getHoldername() {
        return holdername;
    }

    public void setHoldername(String holdername) {
        this.holdername = holdername;
    }
    @Column(name = "PHONE")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    @Column(name = "MONEY")
    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }
    @Column(name = "STATUS")
    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }
    @Column(name = "REFUNDTYPE")
    public String getRefundtype() {
        return refundtype;
    }

    public void setRefundtype(String refundtype) {
        this.refundtype = refundtype;
    }
}

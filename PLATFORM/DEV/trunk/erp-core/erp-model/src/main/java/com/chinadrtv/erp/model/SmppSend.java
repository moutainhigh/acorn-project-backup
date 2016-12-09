package com.chinadrtv.erp.model;

import javax.persistence.*;
import java.util.Date;

/**
 * 订单短信 (TC).
 * User: 徐志凯
 * Date: 13-2-20
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Table(name = "SMPP_SEND", schema = "IAGENT")
@Entity
public class SmppSend {
    private Long id;

    private String type;

    private Date tmrdt;

    private String from;

    private String fromEx;

    private String to;

    private String toEx;

    private String message;

    private Date dt;

    private Integer flag;

    private String customerid;

    private String orderid;

    private String depart;

    private String id1;

    private String id2;

    private String id3;

    private String id4;

    private String id5;

    private Date operDt;

    private String operId;

    private String operName;

    private String errcode;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SMPP_SEND")
    @SequenceGenerator(name = "SMPP_SEND", sequenceName = "IAGENT.SEQSMPP_SEND")
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "TYPE", length = 10)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "TMRDT")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getTmrdt() {
        return tmrdt;
    }

    public void setTmrdt(Date tmrdt) {
        this.tmrdt = tmrdt;
    }

    @Column(name = "FROM_", length = 50)
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @Column(name = "FROM_EX", length = 50)
    public String getFromEx() {
        return fromEx;
    }

    public void setFromEx(String fromEx) {
        this.fromEx = fromEx;
    }

    @Column(name = "TO_", length = 50)
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @Column(name = "TO_EX", length = 50)
    public String getToEx() {
        return toEx;
    }

    public void setToEx(String toEx) {
        this.toEx = toEx;
    }

    @Column(name = "MESSAGE", length = 1000)
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Column(name = "DT")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    @Column(name = "FLAG")
    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    @Column(name = "CUSTOMERID", length = 20)
    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    @Column(name = "ORDERID", length = 20)
    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    @Column(name = "DEPART", length = 50)
    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    @Column(name = "ID1", length = 50)
    public String getId1() {
        return id1;
    }

    public void setId1(String id1) {
        this.id1 = id1;
    }

    @Column(name = "ID2", length = 50)
    public String getId2() {
        return id2;
    }

    public void setId2(String id2) {
        this.id2 = id2;
    }

    @Column(name = "ID3", length = 50)
    public String getId3() {
        return id3;
    }

    public void setId3(String id3) {
        this.id3 = id3;
    }

    @Column(name = "ID4", length = 50)
    public String getId4() {
        return id4;
    }

    public void setId4(String id4) {
        this.id4 = id4;
    }

    @Column(name = "ID5", length = 50)
    public String getId5() {
        return id5;
    }

    public void setId5(String id5) {
        this.id5 = id5;
    }

    @Column(name = "OPER_DT")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getOperDt() {
        return operDt;
    }

    public void setOperDt(Date operDt) {
        this.operDt = operDt;
    }

    @Column(name = "OPER_ID", length = 20)
    public String getOperId() {
        return operId;
    }

    public void setOperId(String operId) {
        this.operId = operId;
    }

    @Column(name = "OPER_NAME", length = 50)
    public String getOperName() {
        return operName;
    }

    public void setOperName(String operName) {
        this.operName = operName;
    }

    @Column(name = "ERRCODE", length = 5)
    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SmppSend smppSend = (SmppSend) o;

        if (customerid != null ? !customerid.equals(smppSend.customerid) : smppSend.customerid != null) return false;
        if (depart != null ? !depart.equals(smppSend.depart) : smppSend.depart != null) return false;
        if (dt != null ? !dt.equals(smppSend.dt) : smppSend.dt != null) return false;
        if (errcode != null ? !errcode.equals(smppSend.errcode) : smppSend.errcode != null) return false;
        if (flag != null ? !flag.equals(smppSend.flag) : smppSend.flag != null) return false;
        if (from != null ? !from.equals(smppSend.from) : smppSend.from != null) return false;
        if (fromEx != null ? !fromEx.equals(smppSend.fromEx) : smppSend.fromEx != null) return false;
        if (id != null ? !id.equals(smppSend.id) : smppSend.id != null) return false;
        if (id1 != null ? !id1.equals(smppSend.id1) : smppSend.id1 != null) return false;
        if (id2 != null ? !id2.equals(smppSend.id2) : smppSend.id2 != null) return false;
        if (id3 != null ? !id3.equals(smppSend.id3) : smppSend.id3 != null) return false;
        if (id4 != null ? !id4.equals(smppSend.id4) : smppSend.id4 != null) return false;
        if (id5 != null ? !id5.equals(smppSend.id5) : smppSend.id5 != null) return false;
        if (message != null ? !message.equals(smppSend.message) : smppSend.message != null) return false;
        if (operDt != null ? !operDt.equals(smppSend.operDt) : smppSend.operDt != null) return false;
        if (operId != null ? !operId.equals(smppSend.operId) : smppSend.operId != null) return false;
        if (operName != null ? !operName.equals(smppSend.operName) : smppSend.operName != null) return false;
        if (orderid != null ? !orderid.equals(smppSend.orderid) : smppSend.orderid != null) return false;
        if (tmrdt != null ? !tmrdt.equals(smppSend.tmrdt) : smppSend.tmrdt != null) return false;
        if (to != null ? !to.equals(smppSend.to) : smppSend.to != null) return false;
        if (toEx != null ? !toEx.equals(smppSend.toEx) : smppSend.toEx != null) return false;
        if (type != null ? !type.equals(smppSend.type) : smppSend.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (tmrdt != null ? tmrdt.hashCode() : 0);
        result = 31 * result + (from != null ? from.hashCode() : 0);
        result = 31 * result + (fromEx != null ? fromEx.hashCode() : 0);
        result = 31 * result + (to != null ? to.hashCode() : 0);
        result = 31 * result + (toEx != null ? toEx.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (dt != null ? dt.hashCode() : 0);
        result = 31 * result + (flag != null ? flag.hashCode() : 0);
        result = 31 * result + (customerid != null ? customerid.hashCode() : 0);
        result = 31 * result + (orderid != null ? orderid.hashCode() : 0);
        result = 31 * result + (depart != null ? depart.hashCode() : 0);
        result = 31 * result + (id1 != null ? id1.hashCode() : 0);
        result = 31 * result + (id2 != null ? id2.hashCode() : 0);
        result = 31 * result + (id3 != null ? id3.hashCode() : 0);
        result = 31 * result + (id4 != null ? id4.hashCode() : 0);
        result = 31 * result + (id5 != null ? id5.hashCode() : 0);
        result = 31 * result + (operDt != null ? operDt.hashCode() : 0);
        result = 31 * result + (operId != null ? operId.hashCode() : 0);
        result = 31 * result + (operName != null ? operName.hashCode() : 0);
        result = 31 * result + (errcode != null ? errcode.hashCode() : 0);
        return result;
    }
}

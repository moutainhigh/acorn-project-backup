package com.chinadrtv.erp.model.agent;

import javax.persistence.*;
import java.util.Date;

/**
 * 短消息信息类
 * User: liyu
 * Date: 13-1-29
 * Time: 下午1:31.
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */

@Entity
@Table(name = "SMPP_SEND", schema = "IAGENT")
public class OrderSms implements java.io.Serializable {

    private String id;
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
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDERSMS_SEQ")
    @SequenceGenerator(name = "ORDERSMS_SEQ", sequenceName = "IAGENT.SEQSMPP_SEND")
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "TYPE", length = 10)
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "TMRDT", length = 7)
    public Date getTmrdt() {
        return this.tmrdt;
    }

    public void setTmrdt(Date tmrdt) {
        this.tmrdt = tmrdt;
    }

    @Column(name = "FROM_", length = 50)
    public String getFrom() {
        return this.from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @Column(name = "FROM_EX", length = 50)
    public String getFromEx() {
        return this.fromEx;
    }

    public void setFromEx(String fromEx) {
        this.fromEx = fromEx;
    }

    @Column(name = "TO_", length = 50)
    public String getTo() {
        return this.to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @Column(name = "TO_EX", length = 50)
    public String getToEx() {
        return this.toEx;
    }

    public void setToEx(String toEx) {
        this.toEx = toEx;
    }

    @Column(name = "MESSAGE", length = 1000)
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DT", length = 7)
    public Date getDt() {
        return this.dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    @Column(name = "FLAG")
    public Integer getFlag() {
        return this.flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    @Column(name = "CUSTOMERID", length = 20)
    public String getCustomerid() {
        return this.customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    @Column(name = "ORDERID", length = 20)
    public String getOrderid() {
        return this.orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    @Column(name = "DEPART", length = 50)
    public String getDepart() {
        return this.depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    @Column(name = "ID1", length = 50)
    public String getId1() {
        return this.id1;
    }

    public void setId1(String id1) {
        this.id1 = id1;
    }

    @Column(name = "ID2", length = 50)
    public String getId2() {
        return this.id2;
    }

    public void setId2(String id2) {
        this.id2 = id2;
    }

    @Column(name = "ID3", length = 50)
    public String getId3() {
        return this.id3;
    }

    public void setId3(String id3) {
        this.id3 = id3;
    }

    @Column(name = "ID4", length = 50)
    public String getId4() {
        return this.id4;
    }

    public void setId4(String id4) {
        this.id4 = id4;
    }

    @Column(name = "ID5", length = 50)
    public String getId5() {
        return this.id5;
    }

    public void setId5(String id5) {
        this.id5 = id5;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "OPER_DT", length = 7)
    public Date getOperDt() {
        return this.operDt;
    }

    public void setOperDt(Date operDt) {
        this.operDt = operDt;
    }

    @Column(name = "OPER_ID", length = 20)
    public String getOperId() {
        return this.operId;
    }

    public void setOperId(String operId) {
        this.operId = operId;
    }

    @Column(name = "OPER_NAME", length = 50)
    public String getOperName() {
        return this.operName;
    }

    public void setOperName(String operName) {
        this.operName = operName;
    }

    @Column(name = "ERRCODE", length = 5)
    public String getErrcode() {
        return this.errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }
}

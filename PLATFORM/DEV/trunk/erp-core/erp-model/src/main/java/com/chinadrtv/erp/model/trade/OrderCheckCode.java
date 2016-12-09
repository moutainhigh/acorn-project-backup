package com.chinadrtv.erp.model.trade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.chinadrtv.erp.model.agent.Order;

/**
 * 订单验证码(TC)
 * User: 徐志凯
 * Date: 13-1-23
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Table(name="ORDERCHECKCODE", schema = "ACOAPP_OMS")
@Entity
public class OrderCheckCode implements java.io.Serializable{
    /**
     * 内部Id
     */
    private Long ruid;

    /**
     * 订单号
     */
    private String orderid;

    /**
     * 验证码
     */
    private String checkcode;

    /**
     * 创建日期
     */
    private Date crdt;

    /**
     * 订单对象
     */
    private Order orderhist;


    @Id
    @Column(name = "RUID", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDERCHECKCODE_SEQ")
    @SequenceGenerator(name = "ORDERCHECKCODE_SEQ", sequenceName = "ACOAPP_OMS.ORDERCHECKCODE_SEQ")
    public Long getRuid() {
        return ruid;
    }

    public void setRuid(Long ruid) {
        this.ruid = ruid;
    }

    @Column(name = "ORDERID", nullable = false, insertable = true, updatable = true, length = 16, precision = 0)
    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    @Column(name = "CHECKCODE", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    public String getCheckcode() {
        return checkcode;
    }

    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }

    @Column(name = "CRDT", nullable = false, insertable = true, updatable = true, length = 7, precision = 0)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCrdt() {
        return crdt;
    }

    public void setCrdt(Date crdt) {
        this.crdt = crdt;
    }

    @OneToOne
    @JoinColumn(name = "ORDER_REF_ID")
    public Order getOrderhist() {
        return orderhist;
    }

    public void setOrderhist(Order orderhist) {
        this.orderhist = orderhist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderCheckCode that = (OrderCheckCode) o;

        if (orderhist!=null&& !orderhist.equals(that.orderhist)) return false;
        if (ruid != that.ruid) return false;
        if (checkcode != null ? !checkcode.equals(that.checkcode) : that.checkcode != null) return false;
        if (crdt != null ? !crdt.equals(that.crdt) : that.crdt != null) return false;
        if (orderid != null ? !orderid.equals(that.orderid) : that.orderid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = orderid != null ? orderid.hashCode() : 0;
        result = 31 * result + (checkcode != null ? checkcode.hashCode() : 0);
        result = 31 * result + (crdt != null ? crdt.hashCode() : 0);
        result = 31 * result + (ruid!=null?ruid.hashCode():0);
        result = 31 * result + (orderhist!=null?orderhist.hashCode():0);
        return result;
    }
}


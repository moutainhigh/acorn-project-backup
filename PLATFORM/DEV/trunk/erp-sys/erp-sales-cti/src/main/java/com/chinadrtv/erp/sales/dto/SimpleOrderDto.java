package com.chinadrtv.erp.sales.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 13-6-6
 * Time: 下午2:32
 * To change this template use File | Settings | File Templates.
 */
public class SimpleOrderDto {
    private String id;
    private String orderid;  //订单ID
    private String crdt;  //订购日期
    private String status;
    private BigDecimal totalprice;  //订单总价
    private String crusr;//
    private String grpid;  //坐席组ID（GRP）
    private boolean  auditState;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getCrdt() {
        return crdt;
    }

    public void setCrdt(String crdt) {
        this.crdt = crdt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(BigDecimal totalprice) {
        this.totalprice = totalprice;
    }

    public String getGrpid() {
        return grpid;
    }

    public void setGrpid(String grpid) {
        this.grpid = grpid;
    }

    public String getCrusr() {
        return crusr;
    }

    public void setCrusr(String crusr) {
        this.crusr = crusr;
    }

    public boolean isAuditState() {
        return auditState;
    }

    public void setAuditState(boolean auditState) {
        this.auditState = auditState;
    }
}

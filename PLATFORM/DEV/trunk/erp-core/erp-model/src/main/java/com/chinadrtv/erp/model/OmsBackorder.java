package com.chinadrtv.erp.model;

import javax.persistence.*;
import java.util.Date;

/**
 * 压单取消导入数据
 * User: gaodejian
 * Date: 13-1-14
 * Time: 下午3:02
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "OMSBACKORDER", schema = "ACOAPP_OMS")
public class OmsBackorder {

    private Long id;
    private String orderId;
    private String prodId;
    private String prodName;
    private String spec;
    private String reason;
    private Date created;
    private Date expired;
    private String status;
    private Date mddt;
    private String mdusr;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OMSBACKORDER_SEQ")
    @SequenceGenerator(name = "OMSBACKORDER_SEQ", sequenceName = "ACOAPP_OMS.OMSBACKORDER_SEQ")
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "ORDERID")
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Column(name = "PRODID")
    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    @Column(name = "PRODNAME")
    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    @Column(name = "SPEC")
    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    @Column(name = "REASON")
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Column(name = "CREATED")
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Column(name = "EXPIRED")
    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "MDDT")
    public Date getMddt() {
        return mddt;
    }

    public void setMddt(Date mddt) {
        this.mddt = mddt;
    }

    @Column(name = "MDUSR")
    public String getMdusr() {
        return mdusr;
    }

    public void setMdusr(String mdusr) {
        this.mdusr = mdusr;
    }
}

package com.chinadrtv.erp.model.trade;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: liuhaidong
 * Date: 12-8-9
 * Time: 下午6:16
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "SHIPMENT_DOWNLOADCONTROL", schema = "ACOAPP_OMS")
public class ShipmentDownControl implements java.io.Serializable {

    private Long id;
    private String shipmentId;
    private String revison;
    private String taskNo;
    private Long isrtn;
    private Long allowRepeat;
    private String orderid;
    private Date  impdat;
    private String logistStatus;
    private String orderStatus;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SHIPMENT_DOWNLOAD_SEQ")
    @SequenceGenerator(name = "SHIPMENT_DOWNLOAD_SEQ", sequenceName = "ACOAPP_OMS.SHIPMENT_DOWNLOAD_CONTROL_SEQ")
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "SHIPMENT_ID", length = 20)
    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    @Column(name = "REVISON", length = 10)
    public String getRevison() {
        return revison;
    }

    public void setRevison(String revison) {
        this.revison = revison;
    }

    @Column(name = "TASKNO", length = 20)
    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    @Column(name = "ISRTN")
    public Long getIsrtn() {
        return isrtn;
    }

    public void setIsrtn(Long isrtn) {
        this.isrtn = isrtn;
    }

    @Column(name = "ALLOW_REPEAT")
    public Long getAllowRepeat() {
        return allowRepeat;
    }

    public void setAllowRepeat(Long allowRepeat) {
        this.allowRepeat = allowRepeat;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "IMPDAT", length = 7)
    public Date getImpdat() {
        return impdat;
    }

    public void setImpdat(Date impdat) {
        this.impdat = impdat;
    }

    @Column(name = "ORDERID", length = 20)
    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    @Column(name = "ORDERSTATUS", length = 10)
    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Column(name = "LOGISTSTATUS", length = 10)
    public String getLogisticsStatus() {
        return logistStatus;
    }

    public void setLogisticsStatus(String logisticsStatus) {
        this.logistStatus = logisticsStatus;
    }
}

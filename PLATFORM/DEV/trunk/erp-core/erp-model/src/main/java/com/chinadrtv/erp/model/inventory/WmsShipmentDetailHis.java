package com.chinadrtv.erp.model.inventory;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-2-26
 * Time: 上午10:19
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="WMS_SHIPMENT_DETAIL1_HIS")
public class WmsShipmentDetailHis implements java.io.Serializable {
    private Long ruid;
    private Integer shipmentLineNum;
    private String item;
    private String itemDesc;
    private Integer totalQty;
    private Double unitPrice;
    private String status;
    private String quantityum;
    private Integer freeFlag;
    private String memo;
    private Integer tasksno;
    private Integer carriage;
    private String batchId;
    private Date batchDate;
    private WmsShipmentHeaderHis shipment;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_WMS_SHIPMENT_DETAIL1")
    @SequenceGenerator(name = "S_WMS_SHIPMENT_DETAIL1", sequenceName = "S_WMS_SHIPMENT_DETAIL1")
    @Column(name = "RUID")
    public Long getRuid() {
        return ruid;
    }

    public void setRuid(Long ruid) {
        this.ruid = ruid;
    }

    @Column(name = "SHIPMENT_LINE_NUM", updatable = false)
    public Integer getShipmentLineNum() {
        return shipmentLineNum;
    }

    public void setShipmentLineNum(Integer shipmentLineNum) {
        this.shipmentLineNum = shipmentLineNum;
    }

    @Column(name = "ITEM", updatable = false)
    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    @Column(name = "ITEM_DESC", updatable = false)
    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    @Column(name = "TOTAL_QTY", updatable = false)
    public Integer getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(Integer totalQty) {
        this.totalQty = totalQty;
    }

    @Column(name = "UNIT_PRICE", updatable = false)
    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Column(name = "STATUS", updatable = false)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "QUANTITYUM", updatable = false)
    public String getQuantityum() {
        return quantityum;
    }

    public void setQuantityum(String quantityum) {
        this.quantityum = quantityum;
    }

    @Column(name = "FREE_FLAG", updatable = false)
    public Integer getFreeFlag() {
        return freeFlag;
    }

    public void setFreeFlag(Integer freeFlag) {
        this.freeFlag = freeFlag;
    }

    @Column(name = "MEMO", updatable = false)
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Column(name = "TASKSNO", updatable = false)
    public Integer getTasksno() {
        return tasksno;
    }

    public void setTasksno(Integer tasksno) {
        this.tasksno = tasksno;
    }

    @Column(name = "CARRIAGE", updatable = false)
    public Integer getCarriage() {
        return carriage;
    }

    public void setCarriage(Integer carriage) {
        this.carriage = carriage;
    }

    @Column(name = "BATCH_ID")
    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    @Column(name = "BATCH_DATE")
    public Date getBatchDate() {
        return batchDate;
    }

    public void setBatchDate(Date batchDate) {
        this.batchDate = batchDate;
    }

    /**
     * 2013-4-16 添加REVISION=REVISON gaodejian
     * @return
     */
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumns({
            @JoinColumn(name = "SHIPMENT_ID", referencedColumnName="SHIPMENT_ID"),
            @JoinColumn(name = "REVISION", referencedColumnName="REVISON")
    })
    public WmsShipmentHeaderHis getShipment() {
        return shipment;
    }

    public void setShipment(WmsShipmentHeaderHis shipment) {
        this.shipment = shipment;
    }
}


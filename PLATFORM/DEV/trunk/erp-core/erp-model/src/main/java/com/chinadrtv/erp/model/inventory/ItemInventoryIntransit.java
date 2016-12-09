package com.chinadrtv.erp.model.inventory;

import javax.persistence.*;
import java.util.Date;

/**
 * 库存在途明细
 * User: gaudi.gao
 * Date: 13-7-31
 * Time: 下午3:42
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "ITEM_INVENTORY_INTRANSIT", schema = "ACOAPP_OMS")
public class ItemInventoryIntransit {

    private Long id;
    private String businessNo;
    private String warehouse;
    private Integer productId;
    private String productCode;
    private Double transitQty;
    private Date arriveDate;
    private Date sendDate;
    private Boolean isFinished;
    private Date finishDate;
    private String finishBy;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ITEM_INVENTORY_INTRANSIT_SEQ")
    @SequenceGenerator(name = "ITEM_INVENTORY_INTRANSIT_SEQ", sequenceName = "ACOAPP_OMS.ITEM_INVENTORY_INTRANSIT_SEQ" , allocationSize = 1)
    @Column(name = "ID")
    public Long getId() {
        return this.id;
    }

    public void setId(Long value) {
        this.id = value;
    }

    @Column(name = "BUSINESS_NO")
    public String getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }

    @Column(name = "WAREHOUSE")
    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    @Column(name = "PRODUCT_ID")
    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    @Column(name = "PRODUCT_CODE")
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    @Column(name = "TRANSIT_QTY")
    public Double getTransitQty() {
        return transitQty;
    }

    public void setTransitQty(Double transitQty) {
        this.transitQty = transitQty;
    }

    @Column(name = "ARRIVE_DATE")
    public Date getArriveDate() {
        return arriveDate;
    }

    public void setArriveDate(Date arriveDate) {
        this.arriveDate = arriveDate;
    }

    @Column(name = "SEND_DATE")
    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    @Column(name = "ISFINISH")
    public Boolean getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(Boolean finished) {
        isFinished = finished;
    }

    @Column(name = "FINISH_DATE")
    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    @Column(name = "FINISH_BY")
    public String getFinishBy() {
        return finishBy;
    }

    public void setFinishBy(String finishBy) {
        this.finishBy = finishBy;
    }
}

package com.chinadrtv.erp.model.inventory;

import javax.persistence.*;
import java.util.Date;

/**
 * User: gaodejian
 * Date: 13-2-5
 * Time: 上午11:42
 * To change this template use File | Settings | File Templates.
 * 收货通知单明细
 */
@Entity
@Table(name="WMS_RECEIPT_DETAIL_HIS")
public class WmsReceiptDetailHis implements java.io.Serializable {

    private Long ruid;


    private Long receiptLlineNum;

    private String item;

    private String itemDesc;

    private Long totalQty;

    private String quantityum;

    private Long freeFlag;

    private String memo;

    private Long tasksNo;

    private Long carriage;

    private Double itemListPrice;

    private WmsReceiptHeaderHis receipt;

    private String batchId;
    private Date batchDate;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_WMS_RECEIPT_DETAIL")
    @SequenceGenerator(name = "S_WMS_RECEIPT_DETAIL", sequenceName = "S_WMS_RECEIPT_DETAIL")
    @Column(name = "RUID")
    public Long getRuid() {
        return ruid;
    }

    public void setRuid(Long ruid) {
        this.ruid = ruid;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RECEIPT_ID", referencedColumnName="RECEIPT_ID", updatable=false)
    public WmsReceiptHeaderHis getReceipt() {
        return receipt;
    }

    public void setReceipt(WmsReceiptHeaderHis receipt) {
        this.receipt = receipt;
    }

    @Column(name = "receipt_line_num", updatable=false)
    public Long getReceiptLlineNum() {
        return receiptLlineNum;
    }

    public void setReceiptLlineNum(Long receiptLlineNum) {
        this.receiptLlineNum = receiptLlineNum;
    }

    @Column(name = "item", updatable=false)
    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    @Column(name = "item_desc", updatable=false)
    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    @Column(name = "total_qty", updatable=false)
    public Long getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(Long totalQty) {
        this.totalQty = totalQty;
    }

    @Column(name = "quantityum", updatable=false)
    public String getQuantityum() {
        return quantityum;
    }

    public void setQuantityum(String quantityum) {
        this.quantityum = quantityum;
    }

    @Column(name = "free_flag", updatable=false)
    public Long getFreeFlag() {
        return freeFlag;
    }

    public void setFreeFlag(Long freeFlag) {
        this.freeFlag = freeFlag;
    }

    @Column(name = "memo", updatable=false)
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Column(name = "tasksno", updatable=false)
    public Long getTasksNo() {
        return tasksNo;
    }

    public void setTasksNo(Long tasksNo) {
        this.tasksNo = tasksNo;
    }

    @Column(name = "carriage", updatable=false)
    public Long getCarriage() {
        return carriage;
    }

    public void setCarriage(Long carriage) {
        this.carriage = carriage;
    }

    @Column(name = "item_list_price", updatable=false)
    public Double getItemListPrice() {
        return itemListPrice;
    }

    public void setItemListPrice(Double itemListPrice) {
        this.itemListPrice = itemListPrice;
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
}


package com.chinadrtv.erp.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 13-1-8
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "PRE_ITEM_INVENTORY", schema = "ACOAPP_OMS")
@Entity
public class PreItemInventory {
    private Long id;

    @Column(name = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRE_ITEM_INVENTORY_SEQ")
    @SequenceGenerator(name = "PRE_ITEM_INVENTORY_SEQ", sequenceName = "ACOAPP_OMS.PRE_ITEM_INVENTORY_SEQ")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Long numIid;
    private String itemId ;

    private String skuId;

    @Column(name = "SKU_ID")
    @Basic
    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    private String outSkuId;

    @Column(name = "OUT_SKU_ID")
    @Basic
    public String getOutSkuId() {
        return outSkuId;
    }

    public void setOutSkuId(String outSkuId) {
        this.outSkuId = outSkuId;
    }

    private String skuTitle;

    @Column(name = "SKU_TITLE")
    @Basic
    public String getSkuTitle() {
        return skuTitle;
    }

    public void setSkuTitle(String skuTitle) {
        this.skuTitle = skuTitle;
    }

    private String skuProps;

    @Column(name = "SKU_PROPS")
    @Basic
    public String getSkuProps() {
        return skuProps;
    }

    public void setSkuProps(String skuProps) {
        this.skuProps = skuProps;
    }

    private String skuStatus;

    @Column(name = "SKU_STATUS")
    @Basic
    public String getSkuStatus() {
        return skuStatus;
    }

    public void setSkuStatus(String skuStatus) {
        this.skuStatus = skuStatus;
    }

    private BigDecimal skuPrice;

    @Column(name = "SKU_PRICE")
    @Basic
    public BigDecimal getSkuPrice() {
        return skuPrice;
    }

    public void setSkuPrice(BigDecimal skuPrice) {
        this.skuPrice = skuPrice;
    }

    private BigDecimal quantity;

    @Column(name = "QUANTITY")
    @Basic
    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    private String status;

    @Column(name = "STATUS")
    @Basic
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String sourceId;

    @Column(name = "SOURCE_ID")
    @Basic
    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    private String customerId;

    @Column(name = "CUSTOMER_ID")
    @Basic
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    private Date onlineTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ONLINE_TIME")
    @Basic
    public Date getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(Date onlineTime) {
        this.onlineTime = onlineTime;
    }

    private Date offlineTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "OFFLINE_TIME")
    @Basic
    public Date getOfflineTime() {
        return offlineTime;
    }

    public void setOfflineTime(Date offlineTime) {
        this.offlineTime = offlineTime;
    }

    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED")
    @Basic
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    private Date modified;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MODIFIED")
    @Basic
    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    private BigDecimal holdQuantity;

    @Column(name = "HOLD_QUANTITY")
    @Basic
    public BigDecimal getHoldQuantity() {
        return holdQuantity;
    }

    public void setHoldQuantity(BigDecimal holdQuantity) {
        this.holdQuantity = holdQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PreItemInventory that = (PreItemInventory) o;

        if (!id.equals(that.id)) return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        if (customerId != null ? !customerId.equals(that.customerId) : that.customerId != null) return false;
        if (holdQuantity != null ? !holdQuantity.equals(that.holdQuantity) : that.holdQuantity != null) return false;
        if (modified != null ? !modified.equals(that.modified) : that.modified != null) return false;
        if (offlineTime != null ? !offlineTime.equals(that.offlineTime) : that.offlineTime != null) return false;
        if (onlineTime != null ? !onlineTime.equals(that.onlineTime) : that.onlineTime != null) return false;
        if (outSkuId != null ? !outSkuId.equals(that.outSkuId) : that.outSkuId != null) return false;
        if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) return false;
        if (skuId != null ? !skuId.equals(that.skuId) : that.skuId != null) return false;
        if (skuPrice != null ? !skuPrice.equals(that.skuPrice) : that.skuPrice != null) return false;
        if (skuProps != null ? !skuProps.equals(that.skuProps) : that.skuProps != null) return false;
        if (skuStatus != null ? !skuStatus.equals(that.skuStatus) : that.skuStatus != null) return false;
        if (skuTitle != null ? !skuTitle.equals(that.skuTitle) : that.skuTitle != null) return false;
        if (sourceId != null ? !sourceId.equals(that.sourceId) : that.sourceId != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (id !=null) ? id.hashCode() : 0;
        result = 31 * result + (skuId != null ? skuId.hashCode() : 0);
        result = 31 * result + (outSkuId != null ? outSkuId.hashCode() : 0);
        result = 31 * result + (skuTitle != null ? skuTitle.hashCode() : 0);
        result = 31 * result + (skuProps != null ? skuProps.hashCode() : 0);
        result = 31 * result + (skuStatus != null ? skuStatus.hashCode() : 0);
        result = 31 * result + (skuPrice != null ? skuPrice.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (sourceId != null ? sourceId.hashCode() : 0);
        result = 31 * result + (customerId != null ? customerId.hashCode() : 0);
        result = 31 * result + (onlineTime != null ? onlineTime.hashCode() : 0);
        result = 31 * result + (offlineTime != null ? offlineTime.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (modified != null ? modified.hashCode() : 0);
        result = 31 * result + (holdQuantity != null ? holdQuantity.hashCode() : 0);
        return result;
    }

    @Column(name = "NUM_IID")
    public Long getNumIid() {
        return numIid;
    }

    public void setNumIid(Long numIid) {
        this.numIid = numIid;
    }

    @Column(name="ITEM_ID")
    public String getItemId(){
        return itemId ;
    }

    public void setItemId(String itemId){
        this.itemId = itemId ;
    }

}

package com.chinadrtv.erp.model.trade;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 发运单明细
 * User: xuzk
 * Date: 13-2-4
 */
@Table(name = "SHIPMENT_DETAIL", schema = "ACOAPP_OMS")
@Entity
public class ShipmentDetail implements java.io.Serializable{
    /**
     *  内部Id
     */
    private Long id;

    /**
     *  发运单对象
     */
    private ShipmentHeader shipmentHeader;

    /**
     *  发运单号
     */
    private String shipmentId;

    /**
     *  发运单行号
     */
    private Long shipmentLineNum;

    /**
     *  商品编码
     */
    private String itemId;

    /**
     * 商品描述
     */
    private String itemDesc;

    /**
     * 订购数量
     */
    private Long totalQty;

    /**
     * 零售单价
     */
    private BigDecimal unitPrice;

    /**
     * 数量
     */
    private String quantity;

    /**
     * 是否赠品
     */
    private Integer freeFlag;

    /**
     * 产品备注
     */
    private String remark;

    /**
     * 指定的出库位
     */
    private String allocationFlag;

    /**
     * 直销运费
     */
    private BigDecimal carrier;



    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SHIPMENT_DETAIL_SEQ")
    @SequenceGenerator(name = "SHIPMENT_DETAIL_SEQ", sequenceName = "ACOAPP_OMS.SHIPMENT_DETAIL_SEQ")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "SHIPMENT_REF_ID")
    public ShipmentHeader getShipmentHeader() {
        return shipmentHeader;
    }

    public void setShipmentHeader(ShipmentHeader shipmentHeader) {
        this.shipmentHeader = shipmentHeader;
    }

    @Column(name = "SHIPMENT_ID",  length = 20)
    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }


    @Column(name = "SHIPMENT_LINE_NUM",  length = 22)
    public Long getShipmentLineNum() {
        return shipmentLineNum;
    }

    public void setShipmentLineNum(Long shipmentLineNum) {
        this.shipmentLineNum = shipmentLineNum;
    }

    @Column(name = "ITEM_ID",  length = 20)
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    @Column(name = "ITEM_DESC",  length = 100)
    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    @Column(name = "TOTAL_QTY",  length = 8)
    public Long getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(Long totalQty) {
        this.totalQty = totalQty;
    }

    @Column(name = "UNIT_PRICE",  length = 15, precision = 4)
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Column(name = "QUANTITY",  length = 25)
    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Column(name = "FREE_FLAG",  length = 1)
    public Integer getFreeFlag() {
        return freeFlag;
    }

    public void setFreeFlag(Integer freeFlag) {
        this.freeFlag = freeFlag;
    }

    @Column(name = "REMARK",  length = 25)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "ALLOCATION_FLAG",  length = 25)
    public String getAllocationFlag() {
        return allocationFlag;
    }

    public void setAllocationFlag(String allocationFlag) {
        this.allocationFlag = allocationFlag;
    }

    @Column(name = "CARRIER",  length = 15, precision = 4)
    public BigDecimal getCarrier() {
        return carrier;
    }

    public void setCarrier(BigDecimal carrier) {
        this.carrier = carrier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShipmentDetail that = (ShipmentDetail) o;

        if (id != that.id) return false;
        if (shipmentLineNum != that.shipmentLineNum) return false;
        if (totalQty != that.totalQty) return false;
        if (allocationFlag != null ? !allocationFlag.equals(that.allocationFlag) : that.allocationFlag != null)
            return false;
        if (carrier != null ? !carrier.equals(that.carrier) : that.carrier != null) return false;
        if (freeFlag != null ? !freeFlag.equals(that.freeFlag) : that.freeFlag != null) return false;
        if (itemDesc != null ? !itemDesc.equals(that.itemDesc) : that.itemDesc != null) return false;
        if (itemId != null ? !itemId.equals(that.itemId) : that.itemId != null) return false;
        if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
        if (shipmentId != null ? !shipmentId.equals(that.shipmentId) : that.shipmentId != null) return false;
        if (unitPrice != null ? !unitPrice.equals(that.unitPrice) : that.unitPrice != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (id !=null ? id.hashCode() : 0);
        result = 31 * result + (shipmentId != null ? shipmentId.hashCode() : 0);
        result = 31 * result + (shipmentLineNum !=null ? shipmentLineNum.hashCode() : 0);
        result = 31 * result + (itemId != null ? itemId.hashCode() : 0);
        result = 31 * result + (itemDesc != null ? itemDesc.hashCode() : 0);
        result = 31 * result + (totalQty != null ? totalQty.hashCode() : 0);
        result = 31 * result + (unitPrice != null ? unitPrice.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (freeFlag != null ? freeFlag.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (allocationFlag != null ? allocationFlag.hashCode() : 0);
        result = 31 * result + (carrier != null ? carrier.hashCode() : 0);
        return result;
    }
}

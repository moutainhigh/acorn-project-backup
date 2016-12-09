package com.chinadrtv.oms.sfexpress.dal.model;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 14-2-17
 * Time: 下午3:29
 * To change this template use File | Settings | File Templates.
 * 商品明细
 */
public class WmsShipmentDetail implements java.io.Serializable {
    private String kits;
    private Integer totalQty;
    private Double unitPrice;

    public String getKits() {
        return kits;
    }

    public void setKits(String kits) {
        this.kits = kits;
    }

    public Integer getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(Integer totalQty) {
        this.totalQty = totalQty;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }
}

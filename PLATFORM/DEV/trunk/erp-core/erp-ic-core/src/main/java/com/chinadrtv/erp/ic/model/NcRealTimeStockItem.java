package com.chinadrtv.erp.ic.model;

/**
 * 商品实时库存(非持久化对象)
 * User: gaodejian
 * Date: 13-5-6
 * Time: 下午3:56
 * To change this template use File | Settings | File Templates.
 */
public class NcRealTimeStockItem implements java.io.Serializable
{
    private String ncCode;               //NC代码
    private String ncName;               //NC名称
    private String spellCode;           //商品简码
    private String spellName;           //商品简称
    private Double onHandQty;           //在手量
    private Double inTransitQty;        //在途量
    private Double availableQty;        //可用量
    private Double groupPrice;           //渠道价格
    private Double listPrice;           //商品原价
    private Double minPrice;            //最低售价
    private Double maxPrice;            //最高价格
    private Integer status;             //是否受控

    public String getNcCode() {
        return ncCode;
    }

    public void setNcCode(String ncCode) {
        this.ncCode = ncCode;
    }

    public String getNcName() {
        return ncName;
    }

    public void setNcName(String ncName) {
        this.ncName = ncName;
    }

    public String getSpellCode() {
        return spellCode;
    }

    public void setSpellCode(String spellCode) {
        this.spellCode = spellCode;
    }

    public String getSpellName() {
        return spellName;
    }

    public void setSpellName(String spellName) {
        this.spellName = spellName;
    }

    public Double getOnHandQty() {
        return onHandQty;
    }

    public void setOnHandQty(Double onHandQty) {
        this.onHandQty = onHandQty;
    }

    public Double getInTransitQty() {
        return inTransitQty;
    }

    public void setInTransitQty(Double inTransitQty) {
        this.inTransitQty = inTransitQty;
    }

    public Double getAvailableQty() {
        return availableQty;
    }

    public void setAvailableQty(Double availableQty) {
        this.availableQty = availableQty;
    }

    public Double getGroupPrice() {
        return groupPrice;
    }

    public void setGroupPrice(Double groupPrice) {
        this.groupPrice = groupPrice;
    }

    public Double getListPrice() {
        return listPrice;
    }

    public void setListPrice(Double listPrice) {
        this.listPrice = listPrice;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

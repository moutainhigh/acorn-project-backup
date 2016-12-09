package com.chinadrtv.erp.ic.model;

/**
 * 库存查看之销售情况(非持久化对象)
 * User: gaodejian
 * Date: 13-5-6
 * Time: 下午3:56
 * To change this template use File | Settings | File Templates.
 */
public class SalesStockItem implements java.io.Serializable
{
    private String ncCode;               //NC代码
    private Long productId;             //产品编码
    private String productCode;         //产品编码
    private String productName;         //产品名称
    private String ncfree;               //NC代码
    private String ncfreeName;          //NC代码
    private String spellCode;           //商品简码
    private String spellName;           //商品简称
    private Double onHandQty;           //在手量
    private Double inTransitQty;       //在途量
    private Double availableQty;       //可用量
    private Double weekAvgSoldQty;       //一周平均销量
    private Double yesterdaySoldQty; //昨天销售量

    public String getNcCode() {
        return ncCode;
    }

    public void setNcCode(String ncCode) {
        this.ncCode = ncCode;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getNcfree() {
        return ncfree;
    }

    public void setNcfree(String ncfree) {
        this.ncfree = ncfree;
    }

    public String getNcfreeName() {
        return ncfreeName;
    }

    public void setNcfreeName(String ncfreeName) {
        this.ncfreeName = ncfreeName;
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

    public Double getAvailableQty() {
        return availableQty;
    }

    public void setAvailableQty(Double availableQty) {
        this.availableQty = availableQty;
    }

    public Double getInTransitQty() {
        return inTransitQty;
    }

    public void setInTransitQty(Double inTransitQty) {
        this.inTransitQty = inTransitQty;
    }

    public Double getWeekAvgSoldQty() {
        return weekAvgSoldQty;
    }

    public void setWeekAvgSoldQty(Double weekAvgSoldQty) {
        this.weekAvgSoldQty = weekAvgSoldQty;
    }

    public Double getYesterdaySoldQty() {
        return yesterdaySoldQty;
    }

    public void setYesterdaySoldQty(Double yesterdaySoldQty) {
        this.yesterdaySoldQty = yesterdaySoldQty;
    }
}

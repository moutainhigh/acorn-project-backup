package com.chinadrtv.erp.ic.model;

/**
 * 商品实时库存(非持久化对象)
 * User: gaodejian
 * Date: 13-5-6
 * Time: 下午3:56
 * To change this template use File | Settings | File Templates.
 */
public class WmsRealTimeStockItem implements java.io.Serializable
{
    private String ncCode;               //NC代码
    private Long productId;             //产品编码
    private String productCode;         //产品编码
    private String productName;         //产品名称
    private String spellCode;           //商品简码
    private String spellName;           //商品简称
    private String warehouse;           //仓库ID
    private String warehouseName;       //仓库简称
    private String ncfree;               //自由项编码
    private String ncfreeName;          //自由项名称
    private Double onHandQty;           //在手量
    private Double inTransitQty;        //在途量
    private Double availableQty;        //可用量
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

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouseId(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
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

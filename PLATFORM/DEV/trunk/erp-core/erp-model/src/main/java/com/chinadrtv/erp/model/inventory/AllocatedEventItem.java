package com.chinadrtv.erp.model.inventory;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-2-1
 * Time: 下午2:15
 * To change this template use File | Settings | File Templates
 * 仓库预分配明细项(非持久化对象).
 */
public class AllocatedEventItem implements java.io.Serializable{
    private Long productId;
    private String productCode;
    private Double quantity;
    private String locationType;
    private boolean allowNegative;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public boolean isAllowNegative() {
        return allowNegative;
    }

    public void setAllowNegative(boolean allowNegative) {
        this.allowNegative = allowNegative;
    }
}

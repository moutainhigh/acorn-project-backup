package com.chinadrtv.model.oms;

import java.io.Serializable;


public class PreTradeDetail implements Serializable{
    
	private static final long serialVersionUID = -9194605603437274700L;

	private Long id;
    private String tradeId;
    private Double shippingFee;
    private Double payment;
    private Double discountFee;
    private Long skuId;
    private String outSkuId;
    private Integer qty;
    private String skuName;
    private Double price;
    private Double upPrice;
    private Long preTradeId;
    private Boolean isActive;
    private Long sourcePreTradeDetailId;
    private String errMsg;
    private Boolean isValid;
    private String outitemid;
    private Long itemid;
    private String outItemId;
    private Long itemId;
    private Long promotionResultId;
    private Integer warehouseType;
    private String itemTradeType;
	private String itemTmsCode;
	private Long oid;
	
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId == null ? null : tradeId.trim();
    }

    public Double getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(Double shippingFee) {
        this.shippingFee = shippingFee;
    }

    public Double getPayment() {
        return payment;
    }

    public void setPayment(Double payment) {
        this.payment = payment;
    }

    public Double getDiscountFee() {
        return discountFee;
    }

    public void setDiscountFee(Double discountFee) {
        this.discountFee = discountFee;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getOutSkuId() {
        return outSkuId;
    }

    public void setOutSkuId(String outSkuId) {
        this.outSkuId = outSkuId == null ? null : outSkuId.trim();
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName == null ? null : skuName.trim();
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getUpPrice() {
        return upPrice;
    }

    public void setUpPrice(Double upPrice) {
        this.upPrice = upPrice;
    }

    public Long getPreTradeId() {
        return preTradeId;
    }

    public void setPreTradeId(Long preTradeId) {
        this.preTradeId = preTradeId;
    }

    public Long getSourcePreTradeDetailId() {
        return sourcePreTradeDetailId;
    }

    public void setSourcePreTradeDetailId(Long sourcePreTradeDetailId) {
        this.sourcePreTradeDetailId = sourcePreTradeDetailId;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg == null ? null : errMsg.trim();
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public String getOutitemid() {
        return outitemid;
    }

    public void setOutitemid(String outitemid) {
        this.outitemid = outitemid == null ? null : outitemid.trim();
    }

    public Long getItemid() {
        return itemid;
    }

    public void setItemid(Long itemid) {
        this.itemid = itemid;
    }

    public String getOutItemId() {
        return outItemId;
    }

    public void setOutItemId(String outItemId) {
        this.outItemId = outItemId == null ? null : outItemId.trim();
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getPromotionResultId() {
        return promotionResultId;
    }

    public void setPromotionResultId(Long promotionResultId) {
        this.promotionResultId = promotionResultId;
    }

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	public Integer getWarehouseType() {
		return warehouseType;
	}

	public void setWarehouseType(Integer warehouseType) {
		this.warehouseType = warehouseType;
	}

	public String getItemTradeType() {
		return itemTradeType;
	}

	public void setItemTradeType(String itemTradeType) {
		this.itemTradeType = itemTradeType;
	}

	public String getItemTmsCode() {
		return itemTmsCode;
	}

	public void setItemTmsCode(String itemTmsCode) {
		this.itemTmsCode = itemTmsCode;
	}

	public Long getOid() {
		return oid;
	}

	public void setOid(Long oid) {
		this.oid = oid;
	}

	/** 
	 * <p>Title: toString</p>
	 * <p>Description: </p>
	 * @return
	 * @see java.lang.Object#toString()
	 */ 
	@Override
	public String toString() {
		return "PreTradeDetail [id=" + id + ", tradeId=" + tradeId + ", shippingFee=" + shippingFee + ", payment="
				+ payment + ", discountFee=" + discountFee + ", skuId=" + skuId + ", outSkuId=" + outSkuId + ", qty="
				+ qty + ", skuName=" + skuName + ", price=" + price + ", upPrice=" + upPrice + ", preTradeId="
				+ preTradeId + ", isActive=" + isActive + ", sourcePreTradeDetailId=" + sourcePreTradeDetailId
				+ ", errMsg=" + errMsg + ", isValid=" + isValid + ", outitemid=" + outitemid + ", itemid=" + itemid
				+ ", outItemId=" + outItemId + ", itemId=" + itemId + ", promotionResultId=" + promotionResultId
				+ ", warehouseType=" + warehouseType + ", itemTradeType=" + itemTradeType + ", itemTmsCode="
				+ itemTmsCode + ", oid=" + oid + "]";
	}

}
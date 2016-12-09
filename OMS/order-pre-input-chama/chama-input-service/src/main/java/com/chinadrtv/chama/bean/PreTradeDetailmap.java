package com.chinadrtv.chama.bean;

import com.chinadrtv.model.oms.PreTrade;


/**   前置订表体
 * User: liuhaidong
 * Date: 12-12-6
 */
public class PreTradeDetailmap implements java.io.Serializable{

    private Long id;

    private PreTrade preTrade;

    private String tradeId;

    private Long skuId;

    private String outSkuId;


    private Integer qty;

    private String skuName;


    private String price;

    private String upPrice;

    private String payment;

    private String shippingFee;

    private String discountFee;

    private boolean isActive;

    private Long sourcePreTradeDetailId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public PreTrade getPreTrade() {
        return preTrade;
    }

    public void setPreTrade(PreTrade preTrade) {
        this.preTrade = preTrade;
    }


    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
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
        this.outSkuId = outSkuId;
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
        this.skuName = skuName;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public String getUpPrice() {
        return upPrice;
    }

    public void setUpPrice(String upPrice) {
        this.upPrice = upPrice;
    }


    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }


    public String getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(String shippingFee) {
        this.shippingFee = shippingFee;
    }


    public String getDiscountFee() {
        return discountFee;
    }

    public void setDiscountFee(String discountFee) {
        this.discountFee = discountFee;
    }


    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }


    public Long getSourcePreTradeDetailId() {
        return sourcePreTradeDetailId;
    }

    public void setSourcePreTradeDetailId(Long sourcePreTradeDetailId) {
        this.sourcePreTradeDetailId = sourcePreTradeDetailId;
    }
}

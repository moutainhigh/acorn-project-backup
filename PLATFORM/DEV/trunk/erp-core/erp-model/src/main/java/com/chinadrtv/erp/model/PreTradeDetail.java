package com.chinadrtv.erp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**   前置订表体
 * User: liuhaidong
 * Date: 12-12-6
 */
@Entity
@Table(name = "PRE_TRADE_DETAIL", schema = "ACOAPP_OMS")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PreTradeDetail implements java.io.Serializable{

    private Long id;

    private PreTrade preTrade;

    private String tradeId;

    private Long skuId;

    private String outSkuId;

    private Long itemId ;

    private String outItemId ;

    @NotNull
    @Min(1)
    private Integer qty;

    private String skuName;

    @NotNull
    private Double price;

    private Double upPrice;

    private Double payment;

    private Double shippingFee;

    private Double discountFee;

    private Boolean isActive =true;

    private Long sourcePreTradeDetailId;

    private Boolean isVaid;
    private String errMsg;

    //promotion result id
    private Long promotionResultId;


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRE_TRADE_DETAIL_SEQ")
    @SequenceGenerator(name = "PRE_TRADE_DETAIL_SEQ", sequenceName = "ACOAPP_OMS.PRE_TRADE_DETAIL_SEQ")
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRE_TRADE_ID")
    public PreTrade getPreTrade() {
        return preTrade;
    }

    public void setPreTrade(PreTrade preTrade) {
        this.preTrade = preTrade;
    }

    @Column(name = "TRADE_ID", length = 100)
    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    @Column(name = "SKU_ID", length = 100)
    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    @Column(name = "OUT_SKU_ID", length = 100)
    public String getOutSkuId() {
        return outSkuId;
    }

    public void setOutSkuId(String outSkuId) {
        this.outSkuId = outSkuId;
    }

    @Column(name = "QTY")
    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    @Column(name = "SKU_NAME", length = 100)
    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    @Column(name = "PRICE")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Column(name = "UP_PRICE")
    public Double getUpPrice() {
        return upPrice;
    }

    public void setUpPrice(Double upPrice) {
        this.upPrice = upPrice;
    }

    @Column(name = "PAYMENT")
    public Double getPayment() {
        return payment;
    }

    public void setPayment(Double payment) {
        this.payment = payment;
    }

    @Column(name = "SHIPPING_FEE")
    public Double getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(Double shippingFee) {
    
        this.shippingFee = shippingFee;
    }

    @Column(name = "DISCOUNT_FEE")
    public Double getDiscountFee() {
        return discountFee;
    }

    public void setDiscountFee(Double discountFee) {
        this.discountFee = discountFee;
    }

    @Column(name = "IS_ACTIVE")
    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    @Column(name = "SOURCE_PRE_TRADE_DETAIL_ID")
    public Long getSourcePreTradeDetailId() {
        return sourcePreTradeDetailId;
    }

    public void setSourcePreTradeDetailId(Long sourcePreTradeDetailId) {
        this.sourcePreTradeDetailId = sourcePreTradeDetailId;
    }

    @Column(name = "IS_VALID")
    public Boolean getIsVaid() {
        return isVaid;
    }

    public void setIsVaid(Boolean vaid) {
        isVaid = vaid;
    }

    @Column(name = "ERR_MSG",length = 300)
    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    @Column(name = "ITEM_ID")
    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    @Column(name = "OUT_ITEM_ID")
    public String getOutItemId() {
        return outItemId;
    }

    public void setOutItemId(String outItemId) {
        this.outItemId = outItemId;
    }

    @Column(name = "PROMOTION_RESULT_ID")
    public Long getPromotionResultId() {
        return promotionResultId;
    }

    public void setPromotionResultId(Long promotionResultId) {
        this.promotionResultId = promotionResultId;
    }
}

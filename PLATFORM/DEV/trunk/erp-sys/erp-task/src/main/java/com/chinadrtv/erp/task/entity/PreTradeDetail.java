package com.chinadrtv.erp.task.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.chinadrtv.erp.task.core.orm.entity.BaseEntity;

/**   前置订表体
 * User: liuhaidong
 * Date: 12-12-6
 */
@Entity
@Table(name = "PRE_TRADE_DETAIL", schema = "ACOAPP_OMS")
public class PreTradeDetail extends BaseEntity{

	private static final long serialVersionUID = 6071292484927791054L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRE_TRADE_DETAIL_SEQ")
    @SequenceGenerator(name = "PRE_TRADE_DETAIL_SEQ", sequenceName = "ACOAPP_OMS.PRE_TRADE_DETAIL_SEQ")
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRE_TRADE_ID")
    private PreTrade preTrade;

    @Column(name = "TRADE_ID", length = 100)
    private String tradeId;

    @Column(name = "SKU_ID", length = 100)
    private Long skuId;

    @Column(name = "OUT_SKU_ID", length = 100)
    private String outSkuId;

    @Column(name = "ITEM_ID")
    private Long itemId ;

    @Column(name = "OUT_ITEM_ID")
    private String outItemId ;

    @NotNull
    @Min(1)
    @Column(name = "QTY")
    private Integer qty;

    @Column(name = "SKU_NAME", length = 100)
    private String skuName;

    @NotNull
    @Column(name = "PRICE")
    private Double price;

    @Column(name = "UP_PRICE")
    private Double upPrice;

    @Column(name = "PAYMENT")
    private Double payment;

    @Column(name = "SHIPPING_FEE")
    private Double shippingFee;

    @Column(name = "DISCOUNT_FEE")
    private Double discountFee;

    @Column(name = "IS_ACTIVE")
    private Boolean isActive =true;

    @Column(name = "SOURCE_PRE_TRADE_DETAIL_ID")
    private Long sourcePreTradeDetailId;

    @Column(name = "IS_VALID")
    private Boolean isVaid;
    
    @Column(name = "ERR_MSG",length = 300)
    private String errMsg;

    @Column(name = "PROMOTION_RESULT_ID")
    private Long promotionResultId;

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

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getOutItemId() {
		return outItemId;
	}

	public void setOutItemId(String outItemId) {
		this.outItemId = outItemId;
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

	public Double getPayment() {
		return payment;
	}

	public void setPayment(Double payment) {
		this.payment = payment;
	}

	public Double getShippingFee() {
		return shippingFee;
	}

	public void setShippingFee(Double shippingFee) {
		this.shippingFee = shippingFee;
	}

	public Double getDiscountFee() {
		return discountFee;
	}

	public void setDiscountFee(Double discountFee) {
		this.discountFee = discountFee;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Long getSourcePreTradeDetailId() {
		return sourcePreTradeDetailId;
	}

	public void setSourcePreTradeDetailId(Long sourcePreTradeDetailId) {
		this.sourcePreTradeDetailId = sourcePreTradeDetailId;
	}

	public Boolean getIsVaid() {
		return isVaid;
	}

	public void setIsVaid(Boolean isVaid) {
		this.isVaid = isVaid;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public Long getPromotionResultId() {
		return promotionResultId;
	}

	public void setPromotionResultId(Long promotionResultId) {
		this.promotionResultId = promotionResultId;
	}

}

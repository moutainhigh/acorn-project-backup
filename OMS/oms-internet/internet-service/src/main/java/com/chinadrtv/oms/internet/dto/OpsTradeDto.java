package com.chinadrtv.oms.internet.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;


public class OpsTradeDto{

	private String ops_trade_id;
	private String alipay_trade_id;
	private String customer_name;
	private String trade_from;
	private String receiver_name;
	private String receiver_mobile;
	private String receiver_phone;
	private String receiver_province;
	private String receiver_city;
	private String receiver_district;
	private String receiver_street;
	private String receiver_address;
	private String receiver_post_code;
	private String tms_code;
	private BigDecimal goodsValue;
	private BigDecimal postfee;
	private String invoiceComment;
	private String invoiceTitle;
	private String buyerMessage;
	private Date created;
	private String tradeType;
	
    private List<SkuDto> skus = new ArrayList<SkuDto>();
	
    public String getOps_trade_id() {
		return ops_trade_id;
	}
	public void setOps_trade_id(String ops_trade_id) {
		this.ops_trade_id = ops_trade_id;
	}
	public String getAlipay_trade_id() {
		return alipay_trade_id;
	}
	public void setAlipay_trade_id(String alipay_trade_id) {
		this.alipay_trade_id = alipay_trade_id;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public String getTrade_from() {
		return trade_from;
	}
	public void setTrade_from(String trade_from) {
		this.trade_from = trade_from;
	}
	public String getReceiver_name() {
		return receiver_name;
	}
	public void setReceiver_name(String receiver_name) {
		this.receiver_name = receiver_name;
	}
	public String getReceiver_mobile() {
		return receiver_mobile;
	}
	public void setReceiver_mobile(String receiver_mobile) {
		this.receiver_mobile = receiver_mobile;
	}
	public String getReceiver_phone() {
		return receiver_phone;
	}
	public void setReceiver_phone(String receiver_phone) {
		this.receiver_phone = receiver_phone;
	}
	public String getReceiver_province() {
		return receiver_province;
	}
	public void setReceiver_province(String receiver_province) {
		this.receiver_province = receiver_province;
	}
	public String getReceiver_city() {
		return receiver_city;
	}
	public void setReceiver_city(String receiver_city) {
		this.receiver_city = receiver_city;
	}
	public String getReceiver_district() {
		return receiver_district;
	}
	public void setReceiver_district(String receiver_district) {
		this.receiver_district = receiver_district;
	}
	public String getReceiver_street() {
		return receiver_street;
	}
	public void setReceiver_street(String receiver_street) {
		this.receiver_street = receiver_street;
	}
	public String getReceiver_address() {
		return receiver_address;
	}
	public void setReceiver_address(String receiver_address) {
		this.receiver_address = receiver_address;
	}
	public String getReceiver_post_code() {
		return receiver_post_code;
	}
	public void setReceiver_post_code(String receiver_post_code) {
		this.receiver_post_code = receiver_post_code;
	}
	public String getTms_code() {
		return tms_code;
	}
	public void setTms_code(String tms_code) {
		this.tms_code = tms_code;
	}
	public String getInvoiceComment() {
		return invoiceComment;
	}
	public void setInvoiceComment(String invoiceComment) {
		this.invoiceComment = invoiceComment;
	}
	public String getInvoiceTitle() {
		return invoiceTitle;
	}
	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}
	public String getBuyerMessage() {
		return buyerMessage;
	}
	public void setBuyerMessage(String buyerMessage) {
		this.buyerMessage = buyerMessage;
	}
	public List<SkuDto> getSkus() {
		return skus;
	}
	public void setSkus(List<SkuDto> skus) {
		this.skus = skus;
	}
	public BigDecimal getGoodsValue() {
		return goodsValue;
	}
	public void setGoodsValue(BigDecimal goodsValue) {
		this.goodsValue = goodsValue;
	}
	public BigDecimal getPostfee() {
		return postfee;
	}
	public void setPostfee(BigDecimal postfee) {
		this.postfee = postfee;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	
	/** 
	 * <p>Title: toString</p>
	 * <p>Description: </p>
	 * @return
	 * @see java.lang.Object#toString()
	 */ 
	@Override
	public String toString() {
		return "OpsTrade [ops_trade_id=" + ops_trade_id + ", alipay_trade_id=" + alipay_trade_id + ", customer_name="
				+ customer_name + ", trade_from=" + trade_from + ", receiver_name=" + receiver_name
				+ ", receiver_mobile=" + receiver_mobile + ", receiver_phone=" + receiver_phone
				+ ", receiver_province=" + receiver_province + ", receiver_city=" + receiver_city
				+ ", receiver_district=" + receiver_district + ", receiver_street=" + receiver_street
				+ ", receiver_address=" + receiver_address + ", receiver_post_code=" + receiver_post_code
				+ ", tms_code=" + tms_code + ", goodsValue=" + goodsValue + ", postfee=" + postfee
				+ ", invoiceComment=" + invoiceComment + ", invoiceTitle=" + invoiceTitle + ", buyerMessage="
				+ buyerMessage + ", created=" + created + ", skus=" + skus + "]";
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
}

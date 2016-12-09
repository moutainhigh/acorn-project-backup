package com.chinadrtv.oms.suning.dto;

import java.math.BigDecimal;

public class SkuDto{
	private String sku_code;
	private Integer number;
	private BigDecimal price;
	
	public String getSku_code() {
		return sku_code;
	}
	public void setSku_code(String sku_code) {
		this.sku_code = sku_code;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	/** 
	 * <p>Title: toString</p>
	 * <p>Description: </p>
	 * @return
	 * @see java.lang.Object#toString()
	 */ 
	@Override
	public String toString() {
		return "Sku [sku_code=" + sku_code + ", number=" + number + ", price=" + price + "]";
	}
}

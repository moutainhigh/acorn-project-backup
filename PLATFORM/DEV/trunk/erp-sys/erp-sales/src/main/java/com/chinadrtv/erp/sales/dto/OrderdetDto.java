/**
 * 
 */
package com.chinadrtv.erp.sales.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author dengqianyong
 *
 */
public class OrderdetDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9041875237324991034L;
	
	private Long id;
	
	private String productId;
	
	private String productType;

	private BigDecimal price;
	
	private Integer num;
	
	private String soldwith;
	
	private Boolean isSuite;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getSoldwith() {
		return soldwith;
	}

	public void setSoldwith(String soldwith) {
		this.soldwith = soldwith;
	}

	public Boolean getIsSuite() {
		return isSuite;
	}

	public void setIsSuite(Boolean isSuite) {
		this.isSuite = isSuite;
	}
	
}

/*
 * @(#)ProductObject.java 1.0 2013-3-8下午3:19:18
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.dto;

/**
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * none</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-3-8 下午3:19:18
 * 
 */
public class ProductObject {

	private String productId;
	private String productName;
	private String isRecommended = "N";

	/**
	 * @return the productId
	 */
	// @Column(name = "productId")
	public String getProductId() {
		return productId;
	}

	/**
	 * @param productId
	 *            the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * @return the productName
	 */
	// @Column(name = "productName")
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName
	 *            the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the isRecommended
	 */
	// @Column(name = "isRecommended")
	public String getIsRecommended() {
		return isRecommended;
	}

	/**
	 * @param isRecommended
	 *            the isRecommended to set
	 */
	public void setIsRecommended(String isRecommended) {
		this.isRecommended = isRecommended;
	}

}

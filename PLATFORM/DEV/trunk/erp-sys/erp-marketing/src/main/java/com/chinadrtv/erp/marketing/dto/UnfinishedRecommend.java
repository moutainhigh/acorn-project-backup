/*
 * @(#)UnfinishedRecommend.java 1.0 2013-3-11下午2:45:37
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-3-11 下午2:45:37 
 * 
 */
public class UnfinishedRecommend {
	
	private String contactid;
	private String businessKey;
	private Date validDate;
	private Date createDate;
	private Date lastUpdateDate;
	private String taskName;
//	private List<ProductObject> products;
	
	private String productId1;
	private String productName1;
	private String isRecommended1 = "N";
	
	private String productId2;
	private String productName2;
	private String isRecommended2 = "N";
	
	private String productId3;
	private String productName3;
	private String isRecommended3 = "N";
	/**
	 * @return the contactid
	 */
	//@Column(name = "contactid")
	public String getContactid() {
		return contactid;
	}
	/**
	 * @param contactid the contactid to set
	 */
	public void setContactid(String contactid) {
		this.contactid = contactid;
	}
	/**
	 * @return the businessKey
	 */
	//@Column(name = "businessKey")
	public String getBusinessKey() {
		return businessKey;
	}
	/**
	 * @param businessKey the businessKey to set
	 */
	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}
	/**
	 * @return the createDate
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return the taskName
	 */
	//@Column(name = "taskName")
	public String getTaskName() {
		return taskName;
	}
	/**
	 * @param taskName the taskName to set
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	/**
	 * @return the lastUpdateDate
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	/**
	 * @param lastUpdateDate the lastUpdateDate to set
	 */
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
//	/**
//	 * @return the products
//	 */
//	//@Column(name = "products")
//	public List<ProductObject> getProducts() {
//		return products;
//	}
//	/**
//	 * @param products the products to set
//	 */
//	public void setProducts(List<ProductObject> products) {
//		this.products = products;
//	}
	/**
	 * @return the validDate
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getValidDate() {
		return validDate;
	}
	/**
	 * @param validDate the validDate to set
	 */
	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}
	/**
	 * @return the productId1
	 */
	//@Column(name = "productId1")
	public String getProductId1() {
		return productId1;
	}
	/**
	 * @param productId1 the productId1 to set
	 */
	public void setProductId1(String productId1) {
		this.productId1 = productId1;
	}
	/**
	 * @return the productName1
	 */
	//@Column(name = "productName1")
	public String getProductName1() {
		return productName1;
	}
	/**
	 * @param productName1 the productName1 to set
	 */
	public void setProductName1(String productName1) {
		this.productName1 = productName1;
	}
	/**
	 * @return the isRecommended1
	 */
	//@Column(name = "isRecommended1")
	public String getIsRecommended1() {
		return isRecommended1;
	}
	/**
	 * @param isRecommended1 the isRecommended1 to set
	 */
	public void setIsRecommended1(String isRecommended1) {
		this.isRecommended1 = isRecommended1;
	}
	/**
	 * @return the productId2
	 */
	//@Column(name = "productId2")
	public String getProductId2() {
		return productId2;
	}
	/**
	 * @param productId2 the productId2 to set
	 */
	public void setProductId2(String productId2) {
		this.productId2 = productId2;
	}
	/**
	 * @return the productName2
	 */
	//@Column(name = "productName2")
	public String getProductName2() {
		return productName2;
	}
	/**
	 * @param productName2 the productName2 to set
	 */
	public void setProductName2(String productName2) {
		this.productName2 = productName2;
	}
	/**
	 * @return the isRecommended2
	 */
	//@Column(name = "isRecommended2")
	public String getIsRecommended2() {
		return isRecommended2;
	}
	/**
	 * @param isRecommended2 the isRecommended2 to set
	 */
	public void setIsRecommended2(String isRecommended2) {
		this.isRecommended2 = isRecommended2;
	}
	/**
	 * @return the productId3
	 */
	//@Column(name = "productId3")
	public String getProductId3() {
		return productId3;
	}
	/**
	 * @param productId3 the productId3 to set
	 */
	public void setProductId3(String productId3) {
		this.productId3 = productId3;
	}
	/**
	 * @return the productName3
	 */
	//@Column(name = "productName3")
	public String getProductName3() {
		return productName3;
	}
	/**
	 * @param productName3 the productName3 to set
	 */
	public void setProductName3(String productName3) {
		this.productName3 = productName3;
	}
	/**
	 * @return the isRecommended3
	 */
	//@Column(name = "isRecommended3")
	public String getIsRecommended3() {
		return isRecommended3;
	}
	/**
	 * @param isRecommended3 the isRecommended3 to set
	 */
	public void setIsRecommended3(String isRecommended3) {
		this.isRecommended3 = isRecommended3;
	}
	
	

}

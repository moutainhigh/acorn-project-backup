/*
 * @(#)ProductSuite.java 1.0 2014-7-17上午11:34:09
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.taobao.common.dal.dto;

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
 * @author andrew
 * @version 1.0
 * @since 2014-7-17 上午11:34:09 
 * 
 */
public class ProductSuiteDto {

	private String prodSuiteScmId;
	private String prodScmId;
	private Long prodNum;
	
	public String getProdSuiteScmId() {
		return prodSuiteScmId;
	}
	public void setProdSuiteScmId(String prodSuiteScmId) {
		this.prodSuiteScmId = prodSuiteScmId;
	}
	public String getProdScmId() {
		return prodScmId;
	}
	public void setProdScmId(String prodScmId) {
		this.prodScmId = prodScmId;
	}
	public Long getProdNum() {
		return prodNum;
	}
	public void setProdNum(Long prodNum) {
		this.prodNum = prodNum;
	}
}

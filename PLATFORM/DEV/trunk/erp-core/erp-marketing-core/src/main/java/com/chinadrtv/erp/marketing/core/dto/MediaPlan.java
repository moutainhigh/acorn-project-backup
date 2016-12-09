/*
 * @(#)MediaPlan.java 1.0 2013-8-27上午10:48:06
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.dto;

import java.io.Serializable;

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
 * @since 2013-8-27 上午10:48:06 
 * 
 */
public class MediaPlan implements Serializable{
	
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	private String mediaName;
	private String productName;
	private String dnis;
	private String tollFreeNum;
	/**
	 * @return the mediaName
	 */
	public String getMediaName() {
		return mediaName;
	}
	/**
	 * @param mediaName the mediaName to set
	 */
	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}
	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/**
	 * @return the dnis
	 */
	public String getDnis() {
		return dnis;
	}
	/**
	 * @param dnis the dnis to set
	 */
	public void setDnis(String dnis) {
		this.dnis = dnis;
	}
	/**
	 * @return the tollFreeNum
	 */
	public String getTollFreeNum() {
		return tollFreeNum;
	}
	/**
	 * @param tollFreeNum the tollFreeNum to set
	 */
	public void setTollFreeNum(String tollFreeNum) {
		this.tollFreeNum = tollFreeNum;
	}

	
}

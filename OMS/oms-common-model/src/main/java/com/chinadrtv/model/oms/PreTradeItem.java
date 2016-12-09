/*
 * @(#)PreTradeItem.java 1.0 2014-6-17上午10:55:13
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.model.oms;

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
 * @author andrew
 * @version 1.0
 * @since 2014-6-17 上午10:55:13 
 * 
 */
public class PreTradeItem implements Serializable {

	private static final long serialVersionUID = -4293571086607895264L;

	private String itemId;
	private Integer warehouseType;
	private String itemDesc;
	private Boolean isActive;
	private String itemTradeType;
	private String itemTmsCode;
	
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public Integer getWarehouseType() {
		return warehouseType;
	}
	public void setWarehouseType(Integer warehouseType) {
		this.warehouseType = warehouseType;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public String getItemTradeType() {
		return itemTradeType;
	}
	public void setItemTradeType(String itemTradeType) {
		this.itemTradeType = itemTradeType;
	}
	public String getItemTmsCode() {
		return itemTmsCode;
	}
	public void setItemTmsCode(String itemTmsCode) {
		this.itemTmsCode = itemTmsCode;
	}
	
	/** 
	 * <p>Title: toString</p>
	 * <p>Description: </p>
	 * @return
	 * @see java.lang.Object#toString()
	 */ 
	@Override
	public String toString() {
		return "PreTradeItem [itemId=" + itemId + ", warehouseType=" + warehouseType + ", itemDesc=" + itemDesc
				+ ", isActive=" + isActive + "]";
	}

}

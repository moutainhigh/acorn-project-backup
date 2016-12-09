/*
 * @(#)ItemStockSyncDto.java 1.0 2014-7-18下午1:34:39
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.taobao.common.dal.dto;

import com.chinadrtv.taobao.common.dal.model.PreTradeInventory;

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
 * @since 2014-7-18 下午1:34:39 
 * 
 */
@SuppressWarnings("serial")
public class ItemStockSyncDto extends PreTradeInventory{

	private Boolean active;
	private Long currentStockQty;

	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public Long getCurrentStockQty() {
		return currentStockQty;
	}
	public void setCurrentStockQty(Long currentStockQty) {
		this.currentStockQty = currentStockQty;
	}
	/** 
	 * <p>Title: toString</p>
	 * <p>Description: </p>
	 * @return
	 * @see java.lang.Object#toString()
	 */ 
	@Override
	public String toString() {
		return "PreTradeInventory [numIid=" + this.getNumIid() + ", cid=" + this.getSkuId() + ", outerId=" + this.getOuterId() 
				+ ", title=" + this.getTitle() + ", nick=" + this.getNick() + ", tradeType=" + this.getTradeType() 
				+ ", listTime=" + this.getListTime() + ", modified=" + this.getModified()
				+ ", createDate=" + this.getCreateDate() + ", updateDate=" + this.getUpdateDate() 
				+ ", updateNum=" + this.getUpdateNum() + ", active=" + active 
				+ ", currentStockQty=" + currentStockQty+ ", syncType=" + this.getSyncType() + "]" ;
	}
	
}

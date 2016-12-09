/*
 * @(#)StockSyncTransferDto.java 1.0 2014-7-21下午4:35:57
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.taobao.model;

import java.util.List;

import com.chinadrtv.taobao.common.dal.dto.ItemStockSyncDto;


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
 * @since 2014-7-21 下午4:35:57 
 * 
 */
public class StockSyncTransferDto {

	private TaobaoOrderConfig config;
	
	private List<ItemStockSyncDto> dataList;

	
	public TaobaoOrderConfig getConfig() {
		return config;
	}

	public void setConfig(TaobaoOrderConfig config) {
		this.config = config;
	}

	public List<ItemStockSyncDto> getDataList() {
		return dataList;
	}

	public void setDataList(List<ItemStockSyncDto> dataList) {
		this.dataList = dataList;
	}
}

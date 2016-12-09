/*
 * @(#)OpsTradeRequest.java 1.0 2014-6-30下午1:24:40
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.oms.internet.dto;

import java.util.ArrayList;
import java.util.List;

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
 * @since 2014-6-30 下午1:24:40 
 * 
 */
public class OpsTradeRequestDto {

	private List<OpsTradeDto> ops_trade = new ArrayList<OpsTradeDto>();

	public List<OpsTradeDto> getOps_trade() {
		return ops_trade;
	}

	public void setOps_trade(List<OpsTradeDto> ops_trade) {
		this.ops_trade = ops_trade;
	}


	/** 
	 * <p>Title: toString</p>
	 * <p>Description: </p>
	 * @return
	 * @see java.lang.Object#toString()
	 */ 
	@Override
	public String toString() {
		return "OpsTradeRequest [ops_trade=" + ops_trade + "]";
	}
}

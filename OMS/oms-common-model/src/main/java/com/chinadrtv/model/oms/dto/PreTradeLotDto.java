/*
 * @(#)PreTradeLotDto.java 1.0 2013年11月1日下午2:44:49
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.model.oms.dto;

import java.util.ArrayList;
import java.util.List;

import com.chinadrtv.model.oms.PreTradeLot;

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
 * @since 2013年11月1日 下午2:44:49 
 * 
 */
public class PreTradeLotDto extends PreTradeLot {

	private List<PreTradeDto> preTrades = new ArrayList<PreTradeDto>();

	public List<PreTradeDto> getPreTrades() {
		return preTrades;
	}
	public void setPreTrades(List<PreTradeDto> preTrades) {
		this.preTrades = preTrades;
	}
	
}

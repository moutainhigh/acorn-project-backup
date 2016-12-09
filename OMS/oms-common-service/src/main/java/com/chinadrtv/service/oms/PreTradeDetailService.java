/*
 * @(#)PreTradeDetailService.java 1.0 2013年11月1日下午2:16:22
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.service.oms;

import java.util.List;

import com.chinadrtv.model.oms.PreTradeDetail;

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
 * @since 2013年11月1日 下午2:16:22 
 * 
 */
public interface PreTradeDetailService {

	List<PreTradeDetail> queryDetailByTradeId(String tradeId);

    void insert(PreTradeDetail preTradeDetail);
}

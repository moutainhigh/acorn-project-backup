/*
 * @(#)StockSyncController.java 1.0 2014-7-16下午4:22:46
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.taobao.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinadrtv.taobao.common.dal.dto.ItemStockSyncDto;
import com.chinadrtv.taobao.model.StockSyncTransferDto;
import com.chinadrtv.taobao.model.TaobaoOrderConfig;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ItemQuantityUpdateRequest;
import com.taobao.api.request.SkusQuantityUpdateRequest;
import com.taobao.api.response.ItemQuantityUpdateResponse;
import com.taobao.api.response.SkusQuantityUpdateResponse;

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
 * @since 2014-7-16 下午4:22:46 
 * 
 */
@Controller
public class StockSyncController {
	
	private static transient final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(StockSyncController.class);

	/**
	 * <p>updating online item stock</p>
	 * @param config
	 * @param itemList
	 * @return
	 */
	@RequestMapping(value = "/syncStockByItem")
	@ResponseBody
	public List<Map<String, Object>> syncStockByItem(@RequestBody StockSyncTransferDto transferDto) {
		
		TaobaoOrderConfig config = transferDto.getConfig();
		List<ItemStockSyncDto> itemList = transferDto.getDataList();
		 
		TaobaoClient client = null;
		
		List<Map<String, Object>> rsList = new ArrayList<Map<String, Object>>();
		
		for(ItemStockSyncDto dto : itemList) {
			client = new DefaultTaobaoClient(config.getUrl(), config.getAppkey(), config.getAppSecret());
			
			//has item properties, synchronize by taobao.skus.quantity.update 
			if (dto.getSyncType().equals("1")) {
				SkusQuantityUpdateRequest req = new SkusQuantityUpdateRequest();
				String param = dto.getOuterId() + ":" + dto.getCurrentStockQty();
				req.setNumIid(Long.parseLong(dto.getNumIid()));
				req.setOuteridQuantities(param);
				SkusQuantityUpdateResponse response = null;
				try {
					response = client.execute(req , config.getSessionKey());
				} catch (ApiException e) {
					logger.error("invoking api error", e);
				}
				
				if(null == response || !response.isSuccess()) {
					logger.error("synchronize failed ", response.getMsg());
					continue;
				}
				
				logger.info(response.getMsg());
				
			}else if (dto.getSyncType().equals("2")) {
				ItemQuantityUpdateRequest req=new ItemQuantityUpdateRequest();
				req.setNumIid(Long.parseLong(dto.getNumIid()));
				req.setQuantity(dto.getCurrentStockQty());
				
				ItemQuantityUpdateResponse response = null;
				try {
					response = client.execute(req , config.getSessionKey());
				} catch (ApiException e) {
					logger.error("invoking api error", e);
				}
				
				if(null == response || !response.isSuccess()) {
					logger.error("synchronized failed ", response.getMsg());
					continue;
				}
				
				logger.info(response.getMsg());
			}
			
			Map<String, Object> rsMap = new HashMap<String, Object>();
			rsMap.put("outerId", dto.getOuterId());
			rsMap.put("success", true);
			rsMap.put("tradeType", dto.getTradeType());
			rsMap.put("updateNum", dto.getCurrentStockQty());
			
			logger.info("stock synchronize success " + rsMap);
			
			rsList.add(rsMap);
		}
		
		return rsList;
	}
}

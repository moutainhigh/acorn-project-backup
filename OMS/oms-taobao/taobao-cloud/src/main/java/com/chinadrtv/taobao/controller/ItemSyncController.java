/*
 * @(#)StockSyncController.java 1.0 2014-7-14上午11:05:41
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.taobao.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinadrtv.taobao.common.dal.model.PreTradeInventory;
import com.chinadrtv.taobao.model.TaobaoOrderConfig;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Item;
import com.taobao.api.domain.Sku;
import com.taobao.api.request.ItemGetRequest;
import com.taobao.api.request.ItemsOnsaleGetRequest;
import com.taobao.api.response.ItemGetResponse;
import com.taobao.api.response.ItemsOnsaleGetResponse;

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
 * @since 2014-7-14 上午11:05:41 
 * 
 */
@Controller
public class ItemSyncController {
	
	private static transient final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ItemSyncController.class);

	@RequestMapping(value = "/syncItemList")
	@ResponseBody
	public List<PreTradeInventory> syncItemList(@RequestBody TaobaoOrderConfig config) {
		
		TaobaoClient client = new DefaultTaobaoClient(config.getUrl(), config.getAppkey(), config.getAppSecret());
		
		ItemsOnsaleGetRequest req = new ItemsOnsaleGetRequest();
		req.setFields("approve_status,num_iid,title,nick,type,cid,pic_url,num,props,valid_thru,list_time,price,has_discount,has_invoice,has_warranty,has_showcase,modified,delist_time,postage_id,seller_cids,outer_id");
		ItemsOnsaleGetResponse response = null;
		try {
			response = client.execute(req , config.getSessionKey());
		} catch (ApiException e) {
			logger.error("invoking api failed", e);
		}
		
		if(null == response || !response.isSuccess()) {
			return new ArrayList<PreTradeInventory>();
		}
		
		List<Item> itemList = response.getItems();
		
		List<PreTradeInventory> inventoryList = new ArrayList<PreTradeInventory>();
		for(Item item : itemList) {
			inventoryList.addAll(this.adapter(item, config));
		}
		
		return inventoryList;
	}

	/**
	 * <p></p>
	 * @param item
	 * @return
	 */
	private List<PreTradeInventory> adapter(Item item, TaobaoOrderConfig config) {
		
		List<PreTradeInventory> rsList = new ArrayList<PreTradeInventory>();
		
		List<PreTradeInventory> pticList = this.queryItemDetail(config, item);
		if (null == pticList || pticList.size() == 0) {
			PreTradeInventory ptic = new PreTradeInventory();
			ptic.setSkuId(null);
			ptic.setNumIid(String.valueOf(item.getNumIid()));
			ptic.setOuterId(item.getOuterId());
			ptic.setTitle(item.getTitle());
			ptic.setNick(item.getNick());
			ptic.setListTime(item.getListTime());
			ptic.setModified(item.getModified());
			ptic.setTradeType(config.getTradeType());
			ptic.setSyncType("2");
			
			rsList.add(ptic);
			
			logger.info("loaded raw data: " + ptic);
		} else {
			rsList.addAll(pticList);
		}
		
		return rsList;
	}

	/**
	 * <p>querying sub item such as properties、 color with item num_iid</p>
	 * @param config
	 * @param item
	 * @return List<PreTradeInventory>
	 */
	private List<PreTradeInventory> queryItemDetail(TaobaoOrderConfig config, Item item) {
		TaobaoClient client = new DefaultTaobaoClient(config.getUrl(), config.getAppkey(), config.getAppSecret());
		
		ItemGetRequest req=new ItemGetRequest();
		req.setFields("detail_url,num_iid,title,nick,type,cid,seller_cids,props,input_pids,input_str,desc,pic_url,num,valid_thru,list_time,delist_time,stuff_status,location,price,post_fee,express_fee,ems_fee,has_discount,freight_payer,has_invoice,has_warranty,has_showcase,modified,increment,approve_status,postage_id,product_id,auction_point,property_alias,item_img,prop_img,sku,video,outer_id,is_virtual");
		req.setNumIid(item.getNumIid());
		
		ItemGetResponse response = null;
		try {
			response = client.execute(req , config.getSessionKey());
		} catch (ApiException e) {
			logger.error("invoking api failed", e);
		}
		
		if(null == response || !response.isSuccess()) {
			return null;
		}
		
		List<Sku> skuList = response.getItem().getSkus();
		if (null == skuList || skuList.size() == 0) {
			return null;
		}
		
		List<PreTradeInventory> rsList = new ArrayList<PreTradeInventory>();
		
		for (Sku sku : skuList) {
			PreTradeInventory ptic = new PreTradeInventory();
			
			ptic.setSkuId(String.valueOf(sku.getSkuId()));
			ptic.setNumIid(String.valueOf(item.getNumIid()));
			ptic.setOuterId(sku.getOuterId());
			ptic.setTitle(item.getTitle());
			ptic.setNick(item.getNick());
			ptic.setListTime(item.getListTime());
			ptic.setModified(item.getModified());
			ptic.setTradeType(config.getTradeType());
			ptic.setSyncType("1");
			
			rsList.add(ptic);
			
			logger.info("loaded raw data: ", ptic);
		}
		return rsList;
	}

}

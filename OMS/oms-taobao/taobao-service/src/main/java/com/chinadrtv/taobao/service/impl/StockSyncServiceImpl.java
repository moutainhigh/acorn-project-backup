/*
 * @(#)StockSyncServiceImpl.java 1.0 2014-7-14上午10:25:46
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.taobao.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.chinadrtv.taobao.common.dal.dao.PreTradeInventoryDao;
import com.chinadrtv.taobao.common.dal.dao.StockSyncDao;
import com.chinadrtv.taobao.common.dal.dto.ItemStockSyncDto;
import com.chinadrtv.taobao.common.dal.dto.ProductSuiteDto;
import com.chinadrtv.taobao.common.dal.model.PreTradeInventory;
import com.chinadrtv.taobao.model.StockSyncTransferDto;
import com.chinadrtv.taobao.model.TaobaoOrderConfig;
import com.chinadrtv.taobao.service.StockSyncService;
import com.chinadrtv.taobao.service.WmsTradeService;

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
 * @since 2014-7-14 上午10:25:46 
 * 
 */
@Service
public class StockSyncServiceImpl implements StockSyncService {
	
	private static transient final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(StockSyncServiceImpl.class);

	@Value("${taobao_cloud_url}")
    private  String cloudUrl;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private PreTradeInventoryDao preTradeInventoryDao;
	
	@Autowired
	private StockSyncDao stockSyncDao;
	
	@Autowired
	private WmsTradeService wmsTradeService;
	
	/** 
	 * <p>Title: syncItemList</p>
	 * <p>Description: </p>
	 * @param configList
	 * @see com.chinadrtv.taobao.service.StockSyncService#syncItemList(java.util.List)
	 */ 
	@Override
	public void syncItemList(List<TaobaoOrderConfig> configList) {

		ResponseEntity<PreTradeInventory[]> response = null;
		
		//Synchronizing by shop, large data may cause error.
		for(TaobaoOrderConfig config : configList) {
			if(null ==config.getSyncStock() || !config.getSyncStock()) {
				continue;
			}
			
			try {
				logger.info("post url: " + cloudUrl + "/syncItemList");
				logger.info("post paramaters: " + configList);
				
				response = restTemplate.postForEntity(cloudUrl + "/syncItemList", config, PreTradeInventory[].class);;
				
			} catch (RestClientException e) {
				logger.error("post url error ", e);
			}
			
			if(null != response && response.getStatusCode().value() == 200) {
				PreTradeInventory[] ptis = response.getBody();
				
				try {
					this.persist(ptis);
				} catch (Exception e) {
					//FIXME mail notification
					logger.error("invalid outerId", e);
				}
			}
		}
	}

	/**
	 * <p></p>
	 * @param pis
	 */
	private void persist(PreTradeInventory[] ptis) {
		for(PreTradeInventory pti : ptis) {
			
			logger.info(pti.toString());
			
			Boolean exist = preTradeInventoryDao.exist(pti);
			
			if (null == pti.getOuterId() || "".equals(pti.getOuterId().trim())) {
				logger.error("outerId is null " + pti);
				continue;
			}
			
			if (null == exist || !exist) {
				preTradeInventoryDao.insertData(pti);
			} else{
				if (null != pti.getOuterId() && !"".equals(pti.getOuterId())) {
					preTradeInventoryDao.updateData(pti);
				}
			}
		}
	}

	/** 
	 * <p>Title: syncStockList</p>
	 * <p>Description: </p>
	 * @param configList
	 * @see com.chinadrtv.taobao.service.StockSyncService#syncStockList(java.util.List)
	 */ 
	@SuppressWarnings("rawtypes")
	@Override
	public void syncStockByShop(List<TaobaoOrderConfig> configList) {

		ResponseEntity<Map[]> response = null;
		
		//Synchronizing by shop, large data may cause error.
		for(TaobaoOrderConfig config : configList) {
			if(null ==config.getSyncStock() || !config.getSyncStock()) {
				continue;
			}
			
			List<ItemStockSyncDto> dataList = this.queryPraparedItemList(config);
			if (null == dataList || dataList.size() <=0 ) {
				continue;
			}
			
			StockSyncTransferDto transferDto = new StockSyncTransferDto();
			transferDto.setConfig(config);
			transferDto.setDataList(dataList);
			
			try {
				logger.info("post url: " + cloudUrl + "/syncStockByItem");
				logger.info("post paramaters: " + configList);
				
				response = restTemplate.postForEntity(cloudUrl + "/syncStockByItem", transferDto, Map[].class);
				
			} catch (RestClientException e) {
				logger.error("post url error ", e);
			}
			
			if(null != response && response.getStatusCode().value() == 200) {
				Map[] rsMap = response.getBody();
				this.logProcess(rsMap);
			}
		}
	}

	/**
	 * <p></p>
	 * @param rsMap
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void logProcess(Map[] rsMap) {
		
		for(Map<String, Object> map : rsMap) {
			Boolean success = null == map.get("success") ? false : Boolean.parseBoolean(map.get("success").toString()); 
			
			if (success) {
				try {
					preTradeInventoryDao.updateSynchronizedStock(map);
				} catch (Exception e) {
					logger.error("rewrite synchronized stock failed ", e);
				}
			}
		}
	}

	/**
	 * <p></p>
	 * @param config
	 * @return
	 * @throws Exception 
	 */
	private List<ItemStockSyncDto> queryPraparedItemList(TaobaoOrderConfig config) {
		
		List<ItemStockSyncDto> dataList =  this.preTradeInventoryDao.querySyncList(config.getTradeType());
		for (Iterator<ItemStockSyncDto> iter = dataList.iterator(); iter.hasNext();) {
			ItemStockSyncDto dto = iter.next();
			if(null != dto.getActive() && dto.getActive()) {
				iter.remove();
			}
		}
		
		//judge item is suite or not
		for (ItemStockSyncDto dto : dataList) {
			if (null == dto.getOuterId() || "".equals(dto.getOuterId())) {
				continue;
			}
			
			//check exists this item, if man mistakes, do nothing then continue 
			Boolean exist = stockSyncDao.checkItemExistance(dto.getOuterId());
			if(null == exist || !exist) {
				continue;
			}
			
			List<ProductSuiteDto> suiteDtoList = stockSyncDao.querySuiteInfoBySuiteId(dto.getOuterId());
			
			Long currentStockQty = null;
			
			//not suite, synchronize directly.
			if (null == suiteDtoList || suiteDtoList.size() == 0) {
				
				currentStockQty = wmsTradeService.calcItemStock(dto.getOuterId());
				//minus unconfirmed item of order.
				currentStockQty -= this.queryUnConfirmedStockByItem(dto.getOuterId());
				
			//suite product, split item get minimum stock No. as stock quantity	
			} else {
				Set<Long> set = new TreeSet<Long>();
				for(ProductSuiteDto suiteDto : suiteDtoList) {
					String itemId = suiteDto.getProdScmId();
					Long prodNum = suiteDto.getProdNum();
					Long tempStock = wmsTradeService.calcItemStock(itemId);
					tempStock = tempStock / prodNum;
					tempStock -= this.queryUnConfirmedStockByItem(itemId);
					
					set.add(tempStock);
				}
				
				Long[] stockArr = new Long[suiteDtoList.size()];
				stockArr = set.toArray(stockArr);
				currentStockQty = stockArr[0];
			}
			
			if (currentStockQty < 0) {
				currentStockQty = 0L;
			}
			
			dto.setCurrentStockQty(currentStockQty);
		}
		
		for(Iterator<ItemStockSyncDto> iter = dataList.iterator(); iter.hasNext();) {
			ItemStockSyncDto dto = iter.next();
			if (null == dto.getCurrentStockQty()) {
				iter.remove();
			}
		}
		
		return dataList;
	}

	/** 
	 * <p>Title: queryUnConfirmedStockByItem</p>
	 * <p>Description: </p>
	 * @param itemId
	 * @return Long
	 * @see com.chinadrtv.taobao.service.StockSyncService#queryUnConfirmedStockByItem(java.lang.String)
	 */ 
	@Override
	public Long queryUnConfirmedStockByItem(String itemId) {
		return stockSyncDao.queryUnConfirmedStockByItem(itemId);
	}

	/** 
	 * <p>Title: querySuiteInfoBySuiteId</p>
	 * <p>Description: </p>
	 * @param suiteId
	 * @return
	 * @see com.chinadrtv.taobao.service.StockSyncService#querySuiteInfoBySuiteId(java.lang.String)
	 */ 
	@Override
	public List<ProductSuiteDto> querySuiteInfoBySuiteId(String suiteId) {
		return stockSyncDao.querySuiteInfoBySuiteId(suiteId);
	}

}

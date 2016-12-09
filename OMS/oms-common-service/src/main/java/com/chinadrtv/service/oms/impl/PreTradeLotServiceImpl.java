/*
 * @(#)PreTradeLotServiceImpl.java 1.0 2013年11月1日下午2:23:39
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.service.oms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.dal.oms.dao.PreTradeCardDao;
import com.chinadrtv.dal.oms.dao.PreTradeDao;
import com.chinadrtv.dal.oms.dao.PreTradeDetailDao;
import com.chinadrtv.dal.oms.dao.PreTradeLotDao;
import com.chinadrtv.model.oms.PreTrade;
import com.chinadrtv.model.oms.PreTradeCard;
import com.chinadrtv.model.oms.PreTradeDetail;
import com.chinadrtv.model.oms.PreTradeLot;
import com.chinadrtv.model.oms.dto.PreTradeDto;
import com.chinadrtv.model.oms.dto.PreTradeLotDto;
import com.chinadrtv.service.oms.PreTradeLotService;
import com.chinadrtv.service.util.PojoUtils;

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
 * @since 2013年11月1日 下午2:23:39 
 * 
 */
@Service("preTradeLotService")
public class PreTradeLotServiceImpl implements PreTradeLotService {
	
	@Autowired
	private PreTradeLotDao preTradeLotDao;
	@Autowired
	private PreTradeDao preTradeDao;
	@Autowired
	private PreTradeDetailDao preTradeDetailDao;
	@Autowired
	private PreTradeCardDao preTradeCardDao;

	/** 
	 * <p>Title: insert</p>
	 * <p>Description: </p>
	 * @param preTradeLotDto
	 * @see com.chinadrtv.service.oms.PreTradeLotService#insert(com.chinadrtv.model.oms.dto.PreTradeLotDto)
	 */ 
	@Override
	public void insert(PreTradeLotDto preTradeLotDto) {
		
		List<PreTradeDto> preTradeDtoList = preTradeLotDto.getPreTrades();
		
		//插入PreTradeLot
		PreTradeLot preTradeLot = (PreTradeLot) PojoUtils.convertDto2Pojo(preTradeLotDto, PreTradeLot.class);
		preTradeLotDao.insert(preTradeLot);
		Long preTradeLotId = preTradeLot.getId();
		
		for(PreTradeDto preTradeDto : preTradeDtoList){
			List<PreTradeCard> preTradeCardList = preTradeDto.getPreTradeCards();
			List<PreTradeDetail> preTradeDetailList = preTradeDto.getPreTradeDetails();
			
			//插入PreTrade
			//PreTrade preTrade = (PreTrade) PojoUtils.convertDto2Pojo(preTradeDto, PreTrade.class);
            PreTrade preTrade = preTradeDto;
			preTrade.setPreTradeLotId(preTradeLotId);
			preTrade.setFeedbackStatus("0");
			
			//如果存在则跳过
			/*PreTrade temp = preTradeDao.findByOpsId(preTrade);
			if(null != temp){
			    continue;
			}*/
			
			preTradeDao.insert(preTrade);
			Long preTradeId = preTrade.getId();
			
			//批量插入 PreTradeDetail
			for(PreTradeDetail ptd : preTradeDetailList) {
				ptd.setPreTradeId(preTradeId);
				ptd.setTradeId(preTrade.getTradeId());
			}
			
			if(null != preTradeDetailList && preTradeDetailList.size() > 0) {
				preTradeDetailDao.insertBatch(preTradeDetailList);	
			}
			
			//批量插入PreTradeCard
			for(PreTradeCard ptc : preTradeCardList){
				ptc.setPreTradeId(preTradeId);
				ptc.setTradeId(preTrade.getTradeId());
			}
			
			if(null != preTradeCardList && preTradeCardList.size() > 0) {
				preTradeCardDao.insertBatch(preTradeCardList);	
			}
		}
	}

}

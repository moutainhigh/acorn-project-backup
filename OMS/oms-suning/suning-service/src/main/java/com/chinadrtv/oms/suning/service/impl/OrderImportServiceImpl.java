/*
 * @(#)OrderImportServiceImpl.java 1.0 2014-11-12上午10:59:07
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.oms.suning.service.impl;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.milyn.Smooks;
import org.milyn.payload.JavaResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.model.oms.PreTradeDetail;
import com.chinadrtv.model.oms.dto.PreTradeDto;
import com.chinadrtv.model.oms.dto.PreTradeLotDto;
import com.chinadrtv.oms.suning.dto.OrderConfig;
import com.chinadrtv.oms.suning.dto.PreTradeDetailDto;
import com.chinadrtv.oms.suning.dto.ResponseHeadDto;
import com.chinadrtv.oms.suning.service.OrderImportService;
import com.chinadrtv.service.oms.PreTradeImportService;
import com.suning.api.DefaultSuningClient;
import com.suning.api.entity.transaction.OrderQueryRequest;
import com.suning.api.entity.transaction.OrderQueryResponse;
import com.suning.api.exception.SuningApiException;

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
 * @since 2014-11-12 上午10:59:07 
 * 
 */
@Service
public class OrderImportServiceImpl implements OrderImportService {
	
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OrderImportServiceImpl.class);
	
	private Smooks smooks;
	
	@Autowired
	private PreTradeImportService preTradeImportService;
	
	@PostConstruct
	public void init() {
		try {
			smooks = new Smooks("/smooks/smooksSuning.xml");
		} catch (Exception e) {
			logger.error("initation smooks error", e);
		}
	}

	/** 
	 * <p>Title: importPreTrade</p>
	 * <p>Description: </p>
	 * @param configList
	 * @param startDate
	 * @param endDate
	 * @see com.chinadrtv.oms.suning.service.OrderImportService#importPreTrade(java.util.List, java.util.Date, java.util.Date)
	 */ 
	@Override
	public void importPreTrade(List<OrderConfig> configList, Date startDate, Date endDate) {
		
		for(OrderConfig oc : configList) {
			try{
				this.importByShop(oc, startDate, endDate);
			}catch(Exception e) {
				logger.error("import error", e);
			}
		}
	}

	/**
	 * <p></p>
	 * @param oc
	 * @param startDate
	 * @param endDate
	 */
	@SuppressWarnings("unchecked")
	private void importByShop(OrderConfig oc, Date startDate, Date endDate) {

		Map<String, Object>  rsMap = this.executeRequest(oc, startDate, endDate);
		if(null == rsMap) {
			return;
		}
		ResponseHeadDto headDto = (ResponseHeadDto) rsMap.get("responseHeadDto");
		List<PreTradeDto> tradeDtolist = (List<PreTradeDto>) rsMap.get("tradeDtolist");
		
		Integer totalPage = headDto.getPageTotal() / oc.getPageSize();
		for(int i=1; i<totalPage; i++) {
			oc.setCurrentPage(i);
			Map<String, Object>  dataMap = this.executeRequest(oc, startDate, endDate);
			if(null == dataMap) {
				continue;
			}
			
			List<PreTradeDto> tempList = (List<PreTradeDto>) dataMap.get("tradeDtolist");
			tradeDtolist.addAll(tempList);
		}
		
		PreTradeLotDto lotDto = new PreTradeLotDto();
		lotDto.setPreTrades(tradeDtolist);
		preTradeImportService.importPretrades(lotDto);
	}

	/**
	 * <p></p>
	 * @param oc
	 * @param startDate
	 * @param endDate
	 */
	private Map<String, Object> executeRequest(OrderConfig oc, Date startDate, Date endDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		OrderQueryRequest request = new OrderQueryRequest(); 
		request.setStartTime(sdf.format(startDate));
		request.setEndTime(sdf.format(endDate));
		request.setOrderStatus("10");
		request.setPageNo(oc.getCurrentPage());
		request.setPageSize(oc.getPageSize());
		//api入参校验逻辑开关，当测试稳定之后建议设置为 false 或者删除该行
		request.setCheckParam(true);
		
		DefaultSuningClient client = new DefaultSuningClient(oc.getUrl(), oc.getAppkey(), oc.getAppSecret(), "xml");
		
		OrderQueryResponse response = null;
		try {
		    response = client.excute(request);
		} catch (SuningApiException e) {
		    logger.error("Call SuNing interface error ", e);
		}
		
		Map<String, Object> rsMap = null;
		if(null != response && null != response.getBody()) {
			rsMap = this.parseResult(oc, response.getBody());
		}
		
		return rsMap;
	}

	/**
	 * <p></p>
	 * @param body
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> parseResult(OrderConfig oc, String body) {
		
		Source source = new StreamSource(new StringReader(body));
		JavaResult javaResult = new JavaResult();
		smooks.filterSource(source, javaResult);

		ResponseHeadDto responseHeadDto = (ResponseHeadDto) javaResult.getBean("responseHeadDto");
		List<PreTradeDto> tradeDtolist = (List<PreTradeDto>) javaResult.getBean("tradeList");
		
		if(null == responseHeadDto || null == tradeDtolist) {
			return null;
		}
		
		for(PreTradeDto ptDto : tradeDtolist) {
			
			Double freight = 0d; 
			ptDto.setTradeFrom("SUNING");
			ptDto.setTradeType(oc.getTradeType());
			ptDto.setSourceId(new Long(oc.getSourceId()));
			ptDto.setTmsCode(oc.getTmsCode());
			ptDto.setCrdt(new Date());
			
			
			if(null != ptDto.getInvoiceType() || null != ptDto.getInvoiceTitle()) {
				ptDto.setInvoiceComment("1");
			}
			
			List<PreTradeDetail> ptdList = new ArrayList<PreTradeDetail>();
			List<?> ptdDtoList = ptDto.getPreTradeDetails();
			for(int i=0; i<ptdDtoList.size(); i++) {
				PreTradeDetailDto ptdDto = (PreTradeDetailDto) ptdDtoList.get(i);
				PreTradeDetail ptd = new PreTradeDetail();
				
				freight += ptdDto.getShippingFee();
				String qty = ptdDto.getQty();
				Double qtyDouble = Double.parseDouble(qty);
				
				ptd.setQty(qtyDouble.intValue());
				ptd.setUpPrice(ptdDto.getUpPrice());
				ptd.setOutSkuId(ptdDto.getOutSkuId());
				ptd.setPayment(ptdDto.getPayAmount());
				ptd.setSkuName(ptdDto.getProductName());
				ptdList.add(ptd);
			}
			
			ptDto.setShippingFee(freight);
			ptDto.setPreTradeDetails(ptdList);
		}
		
		Map<String, Object> rsMap = new HashMap<String, Object>();
		rsMap.put("tradeDtolist", tradeDtolist);
		rsMap.put("responseHeadDto", responseHeadDto);
		
		return rsMap;
	}

}

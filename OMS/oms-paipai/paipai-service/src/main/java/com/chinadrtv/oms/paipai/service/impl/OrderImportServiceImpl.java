/*
 * @(#)OrderImportServiceImpl.java 1.0 2014-11-12上午10:59:07
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.oms.paipai.service.impl;

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

import com.chinadrtv.model.oms.PreTrade;
import com.chinadrtv.model.oms.PreTradeDetail;
import com.chinadrtv.model.oms.dto.PreTradeDto;
import com.chinadrtv.model.oms.dto.PreTradeLotDto;
import com.chinadrtv.oms.paipai.dal.dao.PreTradePaiPaiDao;
import com.chinadrtv.oms.paipai.dto.OrderConfig;
import com.chinadrtv.oms.paipai.dto.PreTradeDetailDto;
import com.chinadrtv.oms.paipai.dto.ResponseHeadDto;
import com.chinadrtv.oms.paipai.oauth.ApiUtil;
import com.chinadrtv.oms.paipai.oauth.PaiPaiOpenApiOauth;
import com.chinadrtv.oms.paipai.service.OrderImportService;
import com.chinadrtv.service.oms.PreTradeImportService;

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
	
	private static final String QUERY_ORDER_LIST_URL = "/deal/sellerSearchDealList.xhtml";
	
	private static final String QUERY_ORDER_DETAIL_URL = "/deal/getDealDetail.xhtml";
	
	private static final String ORDER_STATE = "STATE_WG_COMPLEX_WAIT_SHIP";
	
	private static final String TRADE_FROM = "PAIPAI";
	
	private Smooks smooks;
	
	@Autowired
	private PreTradeImportService preTradeImportService;
	@Autowired
	private PreTradePaiPaiDao preTradePaiPaiDao;
	
	
	@PostConstruct
	public void init() {
		try {
			smooks = new Smooks("/smooks/smooks.xml");
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
	 * @see com.chinadrtv.oms.paipai.service.OrderImportService#importPreTrade(java.util.List, java.util.Date, java.util.Date)
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
		
		List<PreTradeDto> tradeDtolist = new ArrayList<PreTradeDto>();
		for(int i=1; i <= headDto.getPageTotal(); i++) {
			oc.setCurrentPage(i);
			Map<String, Object>  dataMap = this.executeRequest(oc, startDate, endDate);
			if(null == dataMap) {
				continue;
			}
			
			List<PreTradeDto> tempList = (List<PreTradeDto>) dataMap.get("tradeDtolist");
			tradeDtolist.addAll(tempList);
		}
		
		if(null != tradeDtolist && tradeDtolist.size() > 0) {
			PreTradeLotDto lotDto = new PreTradeLotDto();
			lotDto.setPreTrades(tradeDtolist);
			preTradeImportService.importPretrades(lotDto);	
		}
	}

	/**
	 * <p></p>
	 * @param oc
	 * @param startDate
	 * @param endDate
	 */
	private Map<String, Object> executeRequest(OrderConfig oc, Date startDate, Date endDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		PaiPaiOpenApiOauth api = ApiUtil.getAPI(oc, QUERY_ORDER_LIST_URL);
		
		api.addParam("sellerUin", String.valueOf(oc.getUin()));
		api.addParam("timeBegin", sdf.format(startDate));
		api.addParam("timeEnd", sdf.format(endDate));
		api.addParam("dealState", ORDER_STATE);
		api.addParam("pageSize", String.valueOf(oc.getPageSize()));
		api.addParam("pageIndex", String.valueOf(oc.getCurrentPage()));
		api.addParam("listItem", "1");
		
		String responseXml = null;
		try {
			responseXml = PaiPaiOpenApiOauth.invoke(api);
			logger.info("responseXml: " + responseXml);
		} catch (Exception e) {
		    logger.error("Call paipai interface error ", e);
		}
		
		Map<String, Object> rsMap = null;
		if(null != responseXml && !"".equals(responseXml.trim())) {
			rsMap = this.parseResult(oc, responseXml);
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
		List<PreTradeDto> tradeDtolist = (List<PreTradeDto>) javaResult.getBean("tradeDtolist");
		
		if(null == responseHeadDto || null == tradeDtolist) {
			return null;
		}
		
		List<PreTradeDto> tradelist = new ArrayList<PreTradeDto>();
		for(PreTradeDto ptDto : tradeDtolist) {
			PreTradeDto pt = this.parsePreTrade(oc, ptDto);
			tradelist.add(pt);
		}
		
		Map<String, Object> rsMap = new HashMap<String, Object>();
		rsMap.put("tradeDtolist", tradeDtolist);
		rsMap.put("responseHeadDto", responseHeadDto);
		
		return rsMap;
	}
	
	/**
	 * <p>Process the specific column</p>
	 * @param oc
	 * @param ptDto
	 * @return
	 */
	private PreTradeDto parsePreTrade(OrderConfig oc, PreTradeDto ptDto) {
		
		if(null == ptDto || null == ptDto.getTradeId()) {
			return null;
		}
		
		String address = ptDto.getReceiverAddress();
		String[] addrs = address.split(" ");
		
		String province = null;
		String city = null;
		String district = null;
		String shortAddress = null;
		if(addrs.length == 3) {
			province = addrs[0];
			city = addrs[0] + "市";
			district = addrs[1];
			shortAddress = addrs[2];
		}else if(addrs.length == 4) {
			province = addrs[0];
			city = addrs[1];
			district = addrs[2];
			shortAddress = addrs[3];
		}else if(addrs.length > 4) {
			province = addrs[0];
			city = addrs[1];
			district = addrs[2];
			
			StringBuffer sb = new StringBuffer();
			for(int i=3; i<addrs.length; i++ ) {
				sb.append(addrs[i]);
			}
			
			shortAddress = sb.toString();
		}else{
			shortAddress = address;
		}
		
		ptDto.setReceiverProvince(province);
		ptDto.setReceiverCity(city);
		ptDto.setReceiverCounty(district);
		ptDto.setReceiverAddress(shortAddress);
		
		ptDto.setTradeFrom(TRADE_FROM);
		ptDto.setTradeType(oc.getTradeType());
		ptDto.setSourceId(new Long(oc.getSourceId()));
		ptDto.setTmsCode(oc.getTmsCode());
		ptDto.setCrdt(new Date());
		ptDto.setPayment(ptDto.getPayment() / 100);
		ptDto.setShippingFee(ptDto.getShippingFee() / 100);
		//ptDto.setBuyerMessage(ptDto.getBuyerMessage() + this.getPayTypeDesc(ptDto.getRemark()));
		ptDto.setRemark(null);
		
		List<PreTradeDetail> ptdList = new ArrayList<PreTradeDetail>();
		List<?> ptdDtoList = ptDto.getPreTradeDetails();
		for (int i = 0; i < ptdDtoList.size(); i++) {
			PreTradeDetailDto ptdDto = (PreTradeDetailDto) ptdDtoList.get(i);
			PreTradeDetail ptd = new PreTradeDetail();

			String qty = ptdDto.getQty();
			Double qtyDouble = Double.parseDouble(qty);

			ptd.setQty(qtyDouble.intValue());
			ptd.setPrice(ptdDto.getUpPrice() / 100);
			ptd.setUpPrice(ptdDto.getPayAmount() / 100);
			ptd.setOutSkuId(ptdDto.getOutSkuId());
			ptd.setPayment(ptdDto.getPayAmount() / 100);
			ptd.setSkuName(ptdDto.getProductName());
			ptdList.add(ptd);
		}

		ptDto.setPreTradeDetails(ptdList);
		
		return ptDto;
	}

	
	/**
	 * <p></p>
	 * @param remark
	 * @return
	 */
	private String getPayTypeDesc(String remark) {
		if(null == remark || "".equals(remark.trim())) {
			return "";
		}
		
		String desc = "";
		if("UNKNOW".equals(remark)) {
			desc = "未知类型";
		}else if("TENPAY".equals(remark)) {
			desc = "财付通";
		}else if("OFF_LINE".equals(remark)) {
			desc = "线下交易";
		}else if("DEAL_BIZ_FLAG_HDFK".equals(remark)) {
			desc = "货到付款流程";
		}else if("MOBILE_SCORE".equals(remark)) {
			desc = "移动积分";
		}else if("WEIXIN_PAY".equals(remark)) {
			desc = "微信支付";
		}
		
		return desc;
	}

	/** 
	 * <p>Title: updateGroupBuyingPreTrade</p>
	 * <p>Description: </p>
	 * @param configList
	 * @see com.chinadrtv.oms.paipai.service.OrderImportService#updateGroupBuyingPreTrade(java.util.List)
	 */ 
	@Override
	public void updateGroupBuyingPreTrade(List<OrderConfig> configList) {
		for(OrderConfig oc :  configList){
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("tradeType", oc.getTradeType());
			params.put("sourceId", oc.getSourceId());
			
			List<PreTrade> ptList = preTradePaiPaiDao.queryGroupBuyingTrade(params);
			for(PreTrade pt : ptList) {
				this.executeUpdate(oc, pt);
			}
		}
	}

	/**
	 * <p></p>
	 * @param oc
	 * @param pt
	 */
	private void executeUpdate(OrderConfig oc, PreTrade pt) {
		PaiPaiOpenApiOauth api = ApiUtil.getAPI(oc, QUERY_ORDER_DETAIL_URL);
		
		api.addParam("sellerUin", String.valueOf(oc.getUin()));
		api.addParam("dealCode", pt.getTradeId());
		
		String responseXml = null;
		try {
			responseXml = PaiPaiOpenApiOauth.invoke(api);
			logger.info("responseXml: " + responseXml);
		} catch (Exception e) {
		    logger.error("Call paipai interface error ", e);
		}
		
		PreTradeDto ptDto = null;
		if(null != responseXml && !"".equals(responseXml.trim())) {
			ptDto = this.parseSingleResult(oc, responseXml);
		}
		
		if(null != ptDto && !"".equals(ptDto.getTradeId())) {
			pt.setReceiverProvince(ptDto.getReceiverProvince());
			pt.setReceiverCity(ptDto.getReceiverCity());
			pt.setReceiverCounty(ptDto.getReceiverCounty());
			
			preTradePaiPaiDao.updateGroupBuyingTrade(ptDto);
		}
	}

	/**
	 * <p></p>
	 * @param oc
	 * @param responseXml
	 * @return
	 */
	private PreTradeDto parseSingleResult(OrderConfig oc, String responseXml) {
		Source source = new StreamSource(new StringReader(responseXml));
		JavaResult javaResult = new JavaResult();
		smooks.filterSource(source, javaResult);

		PreTradeDto preTradeDto = (PreTradeDto) javaResult.getBean("preTradeDto");
		
		if(null == preTradeDto || null == preTradeDto.getTradeId()) {
			return null;
		}
		
		String address = preTradeDto.getReceiverAddress();
		String[] addrs = address.split(" ");
		
		String province = null;
		String city = null;
		String district = null;
		String shortAddress = null;
		if(addrs.length == 3) {
			province = addrs[0];
			city = addrs[0] + "市";
			district = addrs[1];
			shortAddress = addrs[2];
		}else if(addrs.length == 4) {
			province = addrs[0];
			city = addrs[1];
			district = addrs[2];
			shortAddress = addrs[3];
		}else if(addrs.length > 4) {
			province = addrs[0];
			city = addrs[1];
			district = addrs[2];
			
			StringBuffer sb = new StringBuffer();
			for(int i=3; i<addrs.length; i++ ) {
				sb.append(addrs[i]);
			}
			
			shortAddress = sb.toString();
		}else{
			return null;
		}
		
		preTradeDto.setReceiverProvince(province);
		preTradeDto.setReceiverCity(city);
		preTradeDto.setReceiverCounty(district);
		preTradeDto.setReceiverAddress(shortAddress);
		
		return preTradeDto;
	}

}

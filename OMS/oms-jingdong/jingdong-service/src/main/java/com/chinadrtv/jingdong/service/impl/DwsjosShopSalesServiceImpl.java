/*
 * @(#)DwsjosShopSalesServiceImpl.java 1.0 2014-5-20下午3:13:18
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.jingdong.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.jingdong.common.dal.dao.DwsjosShopSalesDao;
import com.chinadrtv.jingdong.common.dal.model.DwsjosShopSales;
import com.chinadrtv.jingdong.model.JingdongOrderConfig;
import com.chinadrtv.jingdong.service.DwsjosShopSalesService;
import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.request.udp.DataserviceShopsalesbydayGetRequest;
import com.jd.open.api.sdk.response.udp.DataserviceShopsalesbydayGetResponse;
import com.jd.open.api.sdk.response.udp.ShopSalesResultDTO;

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
 * @since 2014-5-20 下午3:13:18 
 * 
 */
@Service
public class DwsjosShopSalesServiceImpl implements DwsjosShopSalesService {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DwsjosShopSalesServiceImpl.class);
	
	@Autowired
	private DwsjosShopSalesDao dwsjosShopSalesDao;
	/** 
	 * <p>Title: importPv</p>
	 * <p>Description: </p>
	 * @param date
	 * @throws JdException 
	 * @see com.chinadrtv.jingdong.service.DwsjosShopSalesService#importPv(java.util.Date)
	 */ 
	@Override
	public void importPv(List<JingdongOrderConfig> configList, Date date) throws JdException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
		Integer dateStamp = Integer.parseInt(sdf1.format(date));
		
		for(JingdongOrderConfig config : configList) {
			DwsjosShopSales queryPv = new DwsjosShopSales();
			queryPv.setOrderType(config.getTradeType());
			queryPv.setDatestamp(dateStamp);
			
			DwsjosShopSales rsPv = dwsjosShopSalesDao.queryDailyByOrderType(queryPv);
			
			if(null == rsPv || null == rsPv.getDwsId()) {
			
				JdClient client = new DefaultJdClient(config.getUrl(), config.getSessionKey(), config.getAppkey(), config.getAppSecret());

				DataserviceShopsalesbydayGetRequest request = new DataserviceShopsalesbydayGetRequest();
				
				request.setTimeId(sdf.format(date));
				DataserviceShopsalesbydayGetResponse response = client.execute(request);
				
				ShopSalesResultDTO resultDto = response.getResultDTO();
				
				if(null == resultDto || null == resultDto.getObj()) {
					logger.error("load pv failed: " + config.toString());
					continue;
				}
				
				rsPv = this.adapter(resultDto, config, dateStamp);
				
				dwsjosShopSalesDao.insertData(rsPv);
			}
		}

		
	}

	/**
	 * <p></p>
	 * @param resultDto
	 * @return
	 */
	private DwsjosShopSales adapter(ShopSalesResultDTO resultDto, JingdongOrderConfig config, Integer dateStamp) {
		DwsjosShopSales pv = new DwsjosShopSales();
		
		pv.setAvgcustordnum(resultDto.getObj().getAvgCustOrdNum());
		pv.setAvgcustprice(resultDto.getObj().getAvgCustPrice());
		pv.setAvgordprice(resultDto.getObj().getAvgOrdPrice());
		pv.setCustrate(resultDto.getObj().getCustRate());
		pv.setDatestamp(dateStamp);
		pv.setOnlpronum(resultDto.getObj().getOnlProNum());
		pv.setOrdamount(resultDto.getObj().getOrdAmount());
		pv.setOrdcustnum(resultDto.getObj().getOrdCustNum());
		pv.setOrderType(config.getTradeType());
		pv.setOrdpronum(resultDto.getObj().getOrdProNum());
		pv.setOrdqtty(resultDto.getObj().getOrdQtty());
		pv.setOrdrate(resultDto.getObj().getOrdRate());
		pv.setPv(resultDto.getObj().getPv());
		pv.setUv(resultDto.getObj().getUv());
		pv.setVisit(resultDto.getObj().getVisit());
		
		return pv;
	}

}




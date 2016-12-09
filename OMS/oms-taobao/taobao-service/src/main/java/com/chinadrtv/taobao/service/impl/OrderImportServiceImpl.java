package com.chinadrtv.taobao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.model.oms.dto.PreTradeDto;
import com.chinadrtv.model.oms.dto.PreTradeLotDto;
import com.chinadrtv.service.oms.PreTradeImportService;
import com.chinadrtv.taobao.model.TaobaoOrderConfig;
import com.chinadrtv.taobao.service.ImportTransformService;
import com.chinadrtv.taobao.service.OrderFetchService;
import com.chinadrtv.taobao.service.OrderImportService;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-10-29
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class OrderImportServiceImpl implements OrderImportService {
	
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OrderImportServiceImpl.class);

    @Autowired
    private OrderFetchService orderFetchService;
    @Autowired
    private ImportTransformService importTransformService;
    @Autowired
    private PreTradeImportService preTradeImportService;

    
    public void importOrders(List<TaobaoOrderConfig> taobaoOrderConfigList,Date startDate, Date endDate) {
        //首先获取订单
        List<PreTradeDto> orderList=new ArrayList<PreTradeDto>();
        for(TaobaoOrderConfig taobaoOrderConfig:taobaoOrderConfigList)
        {
            List<PreTradeDto> preTradeList = this.fetchOrders(taobaoOrderConfig, startDate, endDate);
            if(preTradeList != null){
            	orderList.addAll(preTradeList);
            }
            
            try{
                Thread.sleep(1000 * 10);
            }catch (Exception exp) {
                logger.error("sleep error:",exp);
            }
        }

        //导入到前置订单
		/*if (orderList != null && orderList.size() > 0) {
			PreTradeLotDto preTradeLotDto = new PreTradeLotDto();
			preTradeLotDto.setPreTrades(orderList);
			preTradeImportService.importPretrades(preTradeLotDto);
			// preTradeLotService.insert(preTradeLotDto);
		}*/
        
        //一单一单导入，一单失败不影响其它订单
        for(PreTradeDto preTradeDto : orderList) {
        	this.importSingleOrder(preTradeDto);
        }
    }

	/**
	 * <p></p>
	 * @param preTradeDto
	 */
	private void importSingleOrder(PreTradeDto preTradeDto) {
		try {
			logger.info("import order ["+ preTradeDto.getTradeId() +"]");
			
			PreTradeLotDto preTradeLotDto = new PreTradeLotDto();
			List<PreTradeDto> tempList = new ArrayList<PreTradeDto>();
			tempList.add(preTradeDto);
			preTradeLotDto.setPreTrades(tempList);
			preTradeImportService.importPretrades(preTradeLotDto);

            //数据拆分
            preTradeImportService.splitPreTrades(preTradeDto.getTradeId());
		} catch (Exception e) {
			logger.error("import order [" + preTradeDto.getTradeId() + "] error : ", e);
		}
	}

	private List<PreTradeDto> fetchOrders(TaobaoOrderConfig taobaoOrderConfig, Date startDate, Date endDate) {
		try {
			List<String> list = orderFetchService.getOrders(taobaoOrderConfig, startDate, endDate);
			
			if (list != null && list.size() > 0) {
				return importTransformService.transform(taobaoOrderConfig, list);
			}
			return null;
		} catch (Exception exp) {
			logger.error("order fetch error:(trade type-" + taobaoOrderConfig.getTradeType() + ")", exp);
			return null;
		}
	}
	
}

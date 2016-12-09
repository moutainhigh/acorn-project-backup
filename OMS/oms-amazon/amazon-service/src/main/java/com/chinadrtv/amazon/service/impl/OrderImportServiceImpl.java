package com.chinadrtv.amazon.service.impl;

import com.chinadrtv.model.oms.PreTradeDetail;
import com.chinadrtv.model.oms.dto.PreTradeDto;
import com.chinadrtv.model.oms.dto.PreTradeLotDto;
import com.chinadrtv.service.oms.PreTradeImportService;
import com.chinadrtv.amazon.model.AmazonOrderConfig;
import com.chinadrtv.amazon.service.ImportTransformService;
import com.chinadrtv.amazon.service.OrderFetchService;
import com.chinadrtv.amazon.service.OrderImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private PreTradeImportService preTradeImportService;

    public void importOrders(List<AmazonOrderConfig> amazonOrderConfigList,Date startDate, Date endDate)
    {
        //首先获取订单
        List<PreTradeDto> orderList=new ArrayList<PreTradeDto>();
        for(AmazonOrderConfig amazonOrderConfig:amazonOrderConfigList)
        {
            List<PreTradeDto> preTradeList=this.fetchOrders(amazonOrderConfig,startDate,endDate);
            if(preTradeList!=null)
                orderList.addAll(preTradeList);
            try{
                Thread.sleep(1000 * 10);
            }catch (Exception exp)
            {
                logger.error("sleep error:",exp);
            }
        }

        //导入到前置订单
        if(orderList!=null&&orderList.size()>0)
        {
            PreTradeLotDto preTradeLotDto=new PreTradeLotDto();
            preTradeLotDto.setPreTrades(orderList);
            preTradeLotDto.setErrCount(0L);
            preTradeLotDto.setCrdt(new Date());
            preTradeLotDto.setValidCount(new Long(orderList.size()));
            preTradeLotDto.setTotalCount(preTradeLotDto.getValidCount());
            preTradeLotDto.setStatus(0L);
            preTradeImportService.importPretrades(preTradeLotDto);
        }
    }

    private List<PreTradeDto> fetchOrders(AmazonOrderConfig amazonOrderConfig,Date startDate, Date endDate)
    {
        List<PreTradeDto> preTradeDtoList = orderFetchService.getOrders(amazonOrderConfig,startDate,endDate);
        for(PreTradeDto preTradeDto:preTradeDtoList)
        {
            preTradeDto.setTradeType(amazonOrderConfig.getTradeType());
            preTradeDto.setSourceId(amazonOrderConfig.getSourceId());
            preTradeDto.setCustomerId(amazonOrderConfig.getCustomerId() + amazonOrderConfig.getTradeTypeName());
            preTradeDto.setTmsCode(amazonOrderConfig.getTmsCode());
            preTradeDto.setTradeFrom(amazonOrderConfig.getTradeFrom());
            for(PreTradeDetail tradeDetail : preTradeDto.getPreTradeDetails()){
                tradeDetail.setTradeId(preTradeDto.getTradeId());
            }
        }

        return preTradeDtoList;
    }
}

package com.chinadrtv.taobao.biz.jms;

import com.chinadrtv.runtime.jms.receive.JmsListener;
import com.chinadrtv.taobao.biz.OrderBizHandler;
import com.chinadrtv.taobao.model.TaobaoOrderConfig;
import com.chinadrtv.taobao.service.OrderFetchService;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-10-30
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@SuppressWarnings("rawtypes")
public class OrderImportTopicListener extends JmsListener {
    public OrderImportTopicListener()
    {
        logger.info("OrderImportTopicListener is created.");
    }
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OrderImportTopicListener.class);

    private final int INTERVAL_MINUTES = 240 ;

    @Autowired
    private OrderFetchService orderFetchService;

    @Autowired
    private OrderBizHandler orderImportHandler;

    public List<TaobaoOrderConfig> getTaobaoOrderConfigList() {
        return taobaoOrderConfigList;
    }

    public void setTaobaoOrderConfigList(List<TaobaoOrderConfig> taobaoOrderConfigList) {
        this.taobaoOrderConfigList = taobaoOrderConfigList;
    }

    private List<TaobaoOrderConfig> taobaoOrderConfigList;

    @Override
    public void messageHandler(Object msg) throws Exception {
       try
       {
           logger.info("timing begin import");
           //获取taobao的配置信息
           Date endDate=orderFetchService.getServerTime(taobaoOrderConfigList.get(0));
           Date startDate= DateUtils.addMinutes(endDate, -INTERVAL_MINUTES);
           orderImportHandler.input(taobaoOrderConfigList,startDate,endDate);
       }catch (Exception exp)
       {
           logger.error("timing import taobao order error:", exp);
       }
       finally {
           logger.info("timing end import");
       }
    }
}

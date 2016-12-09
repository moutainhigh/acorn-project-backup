package com.chinadrtv.amazon.biz.jms;

import com.chinadrtv.amazon.biz.OrderBizHandler;
import com.chinadrtv.runtime.jms.receive.JmsListener;
import com.chinadrtv.amazon.model.AmazonOrderConfig;
import com.chinadrtv.amazon.service.OrderFetchService;
import com.chinadrtv.amazon.service.OrderImportService;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-10-30
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@SuppressWarnings("rawtypes")
public class OrderImportTopicListener extends JmsListener {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OrderImportTopicListener.class);

    public OrderImportTopicListener()
    {
        logger.info("OrderImportTopicListener is created.");
    }

    @Autowired
    private OrderBizHandler orderBizHandler;

    public List<AmazonOrderConfig> getAmazonOrderConfigList() {
        return amazonOrderConfigList;
    }

    public void setAmazonOrderConfigList(List<AmazonOrderConfig> amazonOrderConfigList) {
        this.amazonOrderConfigList = amazonOrderConfigList;
    }

    private List<AmazonOrderConfig> amazonOrderConfigList;

    @Override
    public void messageHandler(Object msg) throws Exception {
        logger.info("timing beign import");
        try{
            Date dtNow=new Date();
            orderBizHandler.input(amazonOrderConfigList,DateUtils.addDays(dtNow,-1),DateUtils.addMinutes(dtNow,-5));
        }catch (Exception exp)
        {
            logger.error("timing end with error:",exp);
            throw exp;
        }
        logger.info("timing end with succ");
    }
}

package com.chinadrtv.order.download.biz.jms;

import com.chinadrtv.order.download.biz.OrderDownloadBizHandler;
import com.chinadrtv.runtime.jms.receive.JmsListener;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-21
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class OrderDownloadTopicListener extends JmsListener {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OrderDownloadTopicListener.class);

    @Autowired
    private OrderDownloadBizHandler orderDownloadBizHandler;

    @Override
    public void messageHandler(Object msg) throws Exception {
        logger.info("timing begin order download wms");
        try{
            //获取时间
            orderDownloadBizHandler.orderDownload(new Date());
        }catch (Exception exp)
        {
            logger.error("order download wms error:",exp);
            throw exp;
        }

        logger.info("timing end order download wms");
    }
}

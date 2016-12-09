package com.chinadrtv.amazon.biz.jms;

import com.chinadrtv.amazon.biz.OrderBizHandler;
import com.chinadrtv.amazon.model.TradeResultInfo;
import com.chinadrtv.amazon.model.TradeResultList;
import com.chinadrtv.model.oms.PreTrade;
import com.chinadrtv.runtime.jms.receive.JmsListener;
import com.chinadrtv.amazon.common.dal.model.TradeFeedback;
import com.chinadrtv.amazon.model.AmazonOrderConfig;
import com.chinadrtv.amazon.service.OrderFeedbackFetchService;
import com.chinadrtv.amazon.service.OrderFeedbackService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-5
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class OrderFeedbackTopicListener extends JmsListener {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OrderFeedbackTopicListener.class);

    public OrderFeedbackTopicListener()
    {
        logger.info("OrderFeedbackTopicListener is created");
    }

    public List<AmazonOrderConfig> getAmazonOrderConfigList() {
        return amazonOrderConfigList;
    }

    public void setAmazonOrderConfigList(List<AmazonOrderConfig> amazonOrderConfigList) {
        this.amazonOrderConfigList = amazonOrderConfigList;
    }

    private List<AmazonOrderConfig> amazonOrderConfigList;

    @Autowired
    private OrderBizHandler orderBizHandler;

    @Override
    public void messageHandler(Object msg) throws Exception {
        logger.info("timing begin feedback");
        try
        {
            orderBizHandler.feedback(this.amazonOrderConfigList);
        }catch (Exception exp)
        {
            logger.error("timing end feedback with error:", exp);
            throw exp;
        }

        logger.info("timing end feedback with succ");
    }

}

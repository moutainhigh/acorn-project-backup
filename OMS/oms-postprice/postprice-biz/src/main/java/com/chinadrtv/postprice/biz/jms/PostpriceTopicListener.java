package com.chinadrtv.postprice.biz.jms;

import com.chinadrtv.postprice.biz.PostpriceBizHandler;
import com.chinadrtv.runtime.jms.receive.JmsListener;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-22
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class PostpriceTopicListener extends JmsListener {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(PostpriceTopicListener.class);

    @Autowired
    private PostpriceBizHandler postpriceBizHandler;

    @Override
    public void messageHandler(Object msg) throws Exception {
        logger.info("timing begin post price calc");
        postpriceBizHandler.calcOrderPostprice();
        logger.info("timing end post price calc");
    }
}

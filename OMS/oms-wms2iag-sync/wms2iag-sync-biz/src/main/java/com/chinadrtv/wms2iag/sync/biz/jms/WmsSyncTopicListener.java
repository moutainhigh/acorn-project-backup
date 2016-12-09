package com.chinadrtv.wms2iag.sync.biz.jms;

import com.chinadrtv.runtime.jms.receive.JmsListener;
import com.chinadrtv.wms2iag.sync.biz.TimeLogger;
import com.chinadrtv.wms2iag.sync.biz.WmsSyncBizHandler;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Date;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-21
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class WmsSyncTopicListener extends JmsListener {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(WmsSyncTopicListener.class);

    @Autowired
    private WmsSyncBizHandler wmsSyncBizHandler;

    @Autowired
    @Qualifier("timeLogger")
    private TimeLogger timeLogger;

    @Override
    public void messageHandler(Object msg) throws Exception {
        logger.info("timing begin wms sync");
        try{
            //获取时间
            Date startDate = timeLogger.ReadFromFile();
            if(startDate==null)
                startDate= DateUtils.addDays(new Date(),-1);
            else
                startDate=DateUtils.addMinutes(startDate,-10);

            wmsSyncBizHandler.wmsSync(startDate);
        }catch (Exception exp)
        {
            logger.error("wms sync error:",exp);
            throw exp;
        }

        logger.info("timing end wms sync");
    }
}

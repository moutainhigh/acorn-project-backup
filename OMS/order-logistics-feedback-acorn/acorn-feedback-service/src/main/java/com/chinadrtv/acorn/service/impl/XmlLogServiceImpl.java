package com.chinadrtv.acorn.service.impl;

import com.chinadrtv.acorn.bean.FeedbackInfo;
import com.chinadrtv.acorn.service.XmlLogService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-6
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class XmlLogServiceImpl implements XmlLogService {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(XmlLogServiceImpl.class);
    @Override
    public void logXml(FeedbackInfo feedbackInfo) {
        logger.info(feedbackInfo.getXml());
    }
}

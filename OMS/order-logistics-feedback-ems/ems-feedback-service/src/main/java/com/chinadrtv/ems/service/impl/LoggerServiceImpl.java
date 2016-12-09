package com.chinadrtv.ems.service.impl;

import com.chinadrtv.ems.service.LoggerService;
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
public class LoggerServiceImpl implements LoggerService {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(LoggerServiceImpl.class);
    @Override
    public void logData(String data) {
        logger.info(data);
    }
}

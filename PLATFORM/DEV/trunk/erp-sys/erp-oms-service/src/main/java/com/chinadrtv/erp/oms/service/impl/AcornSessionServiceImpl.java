package com.chinadrtv.erp.oms.service.impl;

import com.chinadrtv.erp.oms.model.AcornSession;
import com.chinadrtv.erp.oms.service.AcornSessionService;
import com.google.code.ssm.api.*;
import org.springframework.stereotype.Service;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-28
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class AcornSessionServiceImpl implements AcornSessionService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AcornSessionServiceImpl.class);
    private static final String CACHE_NS="com.chinadrtv.erp.oms.service.impl.AcornSessionServiceImpl";
    public final static int SESSION_TIMEOUT= 86400;//1 day

    @Override
    @ReadThroughSingleCache(namespace= CACHE_NS, expiration=SESSION_TIMEOUT)
    public AcornSession AddSession(@ParameterValueKeyProvider AcornSession acornSession) {
        logger.info("add session:"+acornSession.getSessionId());
        return acornSession;
    }

    @Override
    @ReadThroughSingleCache(namespace= CACHE_NS, expiration=SESSION_TIMEOUT)
    public AcornSession getSession(@ParameterValueKeyProvider String sessionId) {
        logger.error("session cache not hit:"+sessionId);
        return null;
    }

    @Override
    @InvalidateSingleCache(namespace= CACHE_NS)
    public void invalidSession(@ParameterValueKeyProvider String sessionId) {
        logger.info("invalid session:"+sessionId);
    }

    @Override
    @UpdateSingleCache(namespace= CACHE_NS, expiration=SESSION_TIMEOUT)
    public void refreshSession(@ParameterValueKeyProvider @ParameterDataUpdateContent AcornSession acornSession) {
        logger.info("refresh session:"+acornSession.getSessionId());
    }
}

package com.chinadrtv.erp.oms.service;

import com.chinadrtv.erp.oms.model.AcornSession;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-28
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface AcornSessionService {
    public AcornSession AddSession(AcornSession acornSession);
    public AcornSession getSession(String sessionId);
    public void invalidSession(String sessionId);
    public void refreshSession(AcornSession acornSession);
}

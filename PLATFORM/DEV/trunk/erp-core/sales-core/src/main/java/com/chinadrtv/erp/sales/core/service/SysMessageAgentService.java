package com.chinadrtv.erp.sales.core.service;

import com.chinadrtv.erp.model.SysMessage;
import com.chinadrtv.erp.sales.core.model.MessageCheckStatus;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-8-27
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface SysMessageAgentService {
    public void updateMessageCheck(List<SysMessage> sysMessageList, MessageCheckStatus messageCheckStatus);
    public void handleMessage(SysMessage sysMessage);
}

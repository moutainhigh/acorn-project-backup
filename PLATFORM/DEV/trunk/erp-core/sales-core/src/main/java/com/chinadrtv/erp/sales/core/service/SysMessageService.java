package com.chinadrtv.erp.sales.core.service;

import com.chinadrtv.erp.exception.ErpException;
import com.chinadrtv.erp.model.SysMessage;
import com.chinadrtv.erp.sales.core.constant.MessageType;
import com.chinadrtv.erp.sales.core.model.MessageQueryDto;
import com.chinadrtv.erp.sales.core.model.UsrMessage;

import java.util.Date;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-7-5
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface SysMessageService {
    List<SysMessage> queryMessages(MessageQueryDto messageQueryDto);
    void addMessage(SysMessage sysMessage) throws ErpException;
    void checkMessageByExample(SysMessage sysMessage);
    void checkMessageByIds(List<Long> msgIdList);

    /**
     * 未读消息提醒
     * @param usrMessage
     * @return
     */
    List<SysMessage> queryUsrMessages(UsrMessage usrMessage);

    void handleMessage(MessageType messageType, String content, Date crdt);

    void handleMessageByUsr(MessageType messageType, String content, Date crdt);

    void cancelOrderMessage(String orderId);
}

package com.chinadrtv.erp.sales.core.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.SysMessage;
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
public interface SysMessageDao extends GenericDao<SysMessage,Long> {
    List<SysMessage> queryMessages(MessageQueryDto messageQueryDto);
    void checkMessageByExample(SysMessage sysMessage);
    void checkMessageByIds(List<Long> msgIdList);


    /**
     * 未读消息提醒
     * @param usrMessage
     * @return
     */
    List<SysMessage> queryUsrMessages(UsrMessage usrMessage);
    void invalidUsrMessages(List<String> usrIdList);
    void updateUsrMessage(String usrId, List<SysMessage> sysMessageList);

    List<SysMessage> queryOrderMessage(String orderId, Date dt);
}

package com.chinadrtv.erp.task.service;

import com.chinadrtv.erp.task.entity.OrderUrgentApplication;
import com.chinadrtv.erp.task.entity.SysMessage;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-8-28
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface SysMessageService {
    List<SysMessage> queryUrgentMessage();
    void updateUrgentMessage(List<SysMessage> sysMessageList);
    List<SysMessage> checkUrgentMessage(List<SysMessage> sysMessageList, List<OrderUrgentApplication> orderUrgentApplicationList);
    void refreshUrgentMessage(List<String> usrIdList);
    //void refreshUsrUrgentMessage(String usrId);
    //List<String> getUsrUrgentMessage(String usrId);
}

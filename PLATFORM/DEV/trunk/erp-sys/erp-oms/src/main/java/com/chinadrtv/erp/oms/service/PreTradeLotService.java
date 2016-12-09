package com.chinadrtv.erp.oms.service;
import com.chinadrtv.erp.model.PreTradeLot;
import java.util.*;


/**
 * 
 * 前置订单批次服务 
 * @author haoleitao
 * @date 2013-4-27 上午11:25:49
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface PreTradeLotService{
    PreTradeLot getCompanyById(String preTradeLotId);
    List<PreTradeLot> getAllPreTradeLot();
    List<PreTradeLot> getAllPreTradeLot(int index, int size);
    int getPreTradeLotCount();
    void savePreTradeLot(PreTradeLot preTradeLot);
    void addPreTradeLot(PreTradeLot preTradeLot);
    void removePreTradeLot(PreTradeLot preTradeLot);
}

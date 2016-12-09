package com.chinadrtv.erp.oms.service;
import com.chinadrtv.erp.model.PreTrade;
import com.chinadrtv.erp.model.PreTradeDetail;

import java.util.Date;
import java.util.List;

/**
 * 
 * 前置订单服务
 *  
 * @author haoleitao
 * @date 2012-12-28 下午4:38:12
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface PreTradeService{
    PreTrade getById(Long preTradeId);
    int getCountAllPreTrade(String treadType,Long sourceId,String tradeFrom,String alipayTradeId,Date beginDate,Date endDate, Double min, Double max,String receiverMobile,String buyerMessage,String sellerMessage,int state,int refundStatus,int refundStatusConfirm,String id,String tradeId);
    List<PreTrade> getAllPreTrade(String treadType,Long sourceId,String tradeFrom,String alipayTradeId ,Date beginDate,Date endDate, Double min, Double max,String receiverMobile,String buyerMessage,String sellerMessage,int state,int refundStatus,int refundStatusConfirm,String id,String tradeId,int index, int size);
    int getCountAllIsCombinePreTrade(String treadType,Long sourceId,String tradeFrom,String alipayTradeId,Date beginDate,Date endDate, Double min, Double max,String receiverMobile,String buyerMessage,String sellerMessage,int state,int refundStatus,int refundStatusConfirm,String id,String tradeId);
    List<PreTrade> getAllIsCombinePreTrade(String treadType,Long sourceId,String tradeFrom,String alipayTradeId ,Date beginDate,Date endDate, Double min, Double max,String receiverMobile,String buyerMessage,String sellerMessage,int state,int refundStatus,int refundStatusConfirm,String id,String tradeId,int index, int size);
    List<String> getCombinePreTradeId(PreTrade preTrade,Long sourcdId);
    public void savePreTrade(PreTrade preTrade);
    public PreTrade updatePreTrade(PreTrade preTrade);
    public void delPreTrade(Long id);
    public void savePreTrade(String ids);
    public int batchupdateConfrimState(Long[] ids,int value);
    
    public boolean updateApproval(PreTrade preTrade,String url,List<PreTradeDetail> list);
    List<PreTrade> findPretrades(String tradeId);
   
    }

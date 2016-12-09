package com.chinadrtv.erp.admin.service;
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
 *
 */
public interface PreTradeService{
    PreTrade getById(Long preTradeId);
    int getCountAllPreTrade(Long sourceId,String tradeFrom,String alipayTradeId,Date beginDate,Date endDate, Double min, Double max,String receiverMobile,String buyerMessage,String sellerMessage,int state,int refundStatus,int refundStatusConfirm,String id,String tradeId);
    List<PreTrade> getAllPreTrade(Long sourceId,String tradeFrom,String alipayTradeId ,Date beginDate,Date endDate, Double min, Double max,String receiverMobile,String buyerMessage,String sellerMessage,int state,int refundStatus,int refundStatusConfirm,String id,String tradeId,int index, int size);
    public void savePreTrade(PreTrade preTrade);
    public void delPreTrade(Long id);
    public void savePreTrade(String ids);
    public int batchupdateConfrimState(Long[] ids,int value);
	PreTrade getByTradeId(String tradeId);
   
    }

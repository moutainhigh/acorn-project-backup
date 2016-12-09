package com.chinadrtv.erp.oms.dao;
import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.PreTrade;

import java.util.Date;
import java.util.List;

/**
 * 前置订单Dao
 * PreTradeDao
 * @author haoleitao
 *
 */
public interface PreTradeDao extends GenericDao<PreTrade,Long>{
	List<PreTrade> searchPaginatedPreTradeByAppDate(String treadType,Long sourceId,String tradeFrom,String alipayTradeId,Date beginDate,Date endDate, Double min, Double max,String receiverMobile,String buyerMessage,String sellerMessage,int state,int refundStatus,int refundStatusConfirm,String id,String tradeId,int startIndex, Integer numPerPage);
	
	int searchPaginatedPreTradeByAppDate(String treadType,Long sourceId,String tradeFrom,String alipayTradeId,Date beginDate,Date endDate, Double min, Double max,String receiverMobile,String buyerMessage,String sellerMessage,int state,int refundStatus,int refundStatusConfirm,String id,String tradeId);
    List<PreTrade> searchPaginatedIsCombinePreTradeByAppDate(String treadType,Long sourceId,String tradeFrom,String alipayTradeId,Date beginDate,Date endDate, Double min, Double max,String receiverMobile,String buyerMessage,String sellerMessage,int state,int refundStatus,int refundStatusConfirm,String id,String tradeId,int startIndex, Integer numPerPage);
    
	int searchPaginatedIsCombinePreTradeByAppDate(String treadType,Long sourceId,String tradeFrom,String alipayTradeId,Date beginDate,Date endDate, Double min, Double max,String receiverMobile,String buyerMessage,String sellerMessage,int state,int refundStatus,int refundStatusConfirm,String id,String tradeId);
	List<String> getCombinePreTradeId(PreTrade preTrade,Long sourceId);
	public void savePreTrade(String ids);
	public int batchupdateConfrimState(Long[] ids,int value);
}

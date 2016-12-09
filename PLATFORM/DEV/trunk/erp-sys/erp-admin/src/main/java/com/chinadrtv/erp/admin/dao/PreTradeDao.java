package com.chinadrtv.erp.admin.dao;
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
	List<PreTrade> searchPaginatedPreTradeByAppDate(Long sourceId,String tradeFrom,String alipayTradeId,Date beginDate,Date endDate, Double min, Double max,String receiverMobile,String buyerMessage,String sellerMessage,int state,int refundStatus,int refundStatusConfirm,String id,String tradeId,int startIndex, Integer numPerPage);
	
	int searchPaginatedPreTradeByAppDate(Long sourceId,String tradeFrom,String alipayTradeId,Date beginDate,Date endDate, Double min, Double max,String receiverMobile,String buyerMessage,String sellerMessage,int state,int refundStatus,int refundStatusConfirm,String id,String tradeId);
	public void savePreTrade(String ids);
	public int batchupdateConfrimState(Long[] ids,int value);
	PreTrade getByTradeId(String tradeId);
}

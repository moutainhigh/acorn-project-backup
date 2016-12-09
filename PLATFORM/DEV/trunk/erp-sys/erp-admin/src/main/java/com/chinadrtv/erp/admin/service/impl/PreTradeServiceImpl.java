package com.chinadrtv.erp.admin.service.impl;
import com.chinadrtv.erp.model.PreTrade;
import com.chinadrtv.erp.model.PreTradeDetail;
import com.chinadrtv.erp.admin.service.PreTradeService;
import com.chinadrtv.erp.admin.util.StringUtil;
import com.chinadrtv.erp.admin.dao.PreTradeDao;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service("preTradeService")
public class PreTradeServiceImpl implements PreTradeService{

	private static final Logger log = LoggerFactory
			.getLogger(PreTradeServiceImpl.class);
    @Autowired
    private PreTradeDao preTradeDao;


    public void addPreTrade(PreTrade preTrade) {
        preTradeDao.save(preTrade);
    }



    public void savePreTrade(PreTrade preTrade) {
        preTradeDao.saveOrUpdate(preTrade);
    }

    public void delPreTrade(Long id) {
        preTradeDao.remove(id);
    }



	public PreTrade getById(Long id) {
		// TODO Auto-generated method stub
		return preTradeDao.get(id);
	}




	public List<PreTrade> getAllPreTrade(Long sourceId,String tradeFrom,String alipayTradeId,Date beginDate,
			Date endDate, Double min, Double max,String receiverMobile,String buyerMessage,String sellerMessage,int state,int refundStatus,int refundStatusConfirm,String id,String tradeId,int index, int size) {
		// TODO Auto-generated method stub
		return preTradeDao.searchPaginatedPreTradeByAppDate(sourceId,tradeFrom,alipayTradeId,beginDate, endDate,min, max,receiverMobile,buyerMessage,sellerMessage,state,refundStatus,refundStatusConfirm,id,tradeId,index,size);
	}



	public int getCountAllPreTrade(Long sourceId,String tradeFrom,String alipayTradeId,Date beginDate,
			Date endDate, Double min, Double max, String receiverMobile,String buyerMessage,String sellerMessage,int state,int refundStatus,int refundStatusConfirm,String id, String tradeId) {
		return preTradeDao.searchPaginatedPreTradeByAppDate(sourceId,tradeFrom,alipayTradeId,beginDate, endDate,min, max,receiverMobile,buyerMessage,sellerMessage,state,refundStatus,refundStatusConfirm,id,tradeId);
	}



	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.oms.service.PreTradeService#savePreTrade(java.lang.String)
	 */
	public void savePreTrade(String ids) {
		// TODO Auto-generated method stub
		preTradeDao.savePreTrade(ids);
	}




	
	

	public int batchupdateConfrimState(Long[] ids,int value){
		return preTradeDao.batchupdateConfrimState(ids, value);
	}



	public PreTrade getByTradeId(String tradeId) {
		// TODO Auto-generated method stub
		return preTradeDao.getByTradeId(tradeId);
	}


}

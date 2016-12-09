package com.chinadrtv.erp.task.service;


import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.task.core.service.BaseService;
import com.chinadrtv.erp.task.entity.PreTrade;

/**
 * User: liuhaidong
 * Date: 12-8-10
 */
public interface PreTradeService extends BaseService<PreTrade, Long>{

	List<Object[]> getAllPreTrade(Map<String, Object> parms, Integer state, Integer refundStatus, Integer refundStatusConfirm, Integer pageNo, Integer pageSize);
	public void savePreTrade(PreTrade preTrade);
	
}

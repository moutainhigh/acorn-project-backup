package com.chinadrtv.erp.oms.dao;

import java.util.List;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.PreTradeCompany;

/**
 * 前置订单类型服务
 * @author haoleitao
 *
 */
public interface PreTradeCompanyDao  extends GenericDao<PreTradeCompany,Long>{
	public List<PreTradeCompany> getPreTradeCompanyBySourceid(Long sourceId);
	
}

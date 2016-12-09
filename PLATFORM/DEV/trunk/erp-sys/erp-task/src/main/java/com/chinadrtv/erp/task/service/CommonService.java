package com.chinadrtv.erp.task.service;

import java.io.Serializable;
import java.util.List;

import com.chinadrtv.erp.task.core.orm.entity.BaseEntity;
import com.chinadrtv.erp.task.core.service.BaseService;


public interface CommonService extends BaseService<BaseEntity, Serializable>{
	
	/**
	 * 查询保证额度超标的承运商
	 * @return
	 */
	public List<Object[]> queryMarginExceededCompanyPayment();
	
	/**
	 * 查询漏分拣的订单
	 * @return
	 */
	public List<Object[]> queryLosePickOrder(int pageNo, int pageSize);
	
}

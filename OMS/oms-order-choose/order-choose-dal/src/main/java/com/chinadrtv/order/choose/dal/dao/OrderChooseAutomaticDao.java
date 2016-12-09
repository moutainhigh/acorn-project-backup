package com.chinadrtv.order.choose.dal.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.model.Orderhist;

import java.util.List;
import java.util.Map;

/**
 * User: zhaizhanyi Date: 12-12-29
 */
public interface OrderChooseAutomaticDao extends GenericDao<Orderhist, Long> {
	
	public int automaticChooseOrderForOldData(Map<String, Parameter> param);

	public int automaticChooseOrder(Map<String, Parameter> param);

	public int automaticChooseShipment(Map<String, Parameter> param);
	
	public int updateOderhistById(Map<String, Parameter> param);
	
	public List<Orderhist> queryOrderhist(Map<String, Parameter> param, Integer limit);
}

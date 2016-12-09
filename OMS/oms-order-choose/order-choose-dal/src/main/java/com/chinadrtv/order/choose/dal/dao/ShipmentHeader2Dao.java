package com.chinadrtv.order.choose.dal.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.model.trade.ShipmentHeader;

import java.util.List;
import java.util.Map;

/**
 * User: zhaizhanyi Date: 12-12-29
 */
public interface ShipmentHeader2Dao extends GenericDao<ShipmentHeader, Long> {
	
	public List<ShipmentHeader> queryShipment(Map<String, Parameter> param, Integer limit);
	
	public int updateShipmentHeaderById(Map<String, Parameter> param) ;
	
}

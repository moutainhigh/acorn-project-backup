package com.chinadrtv.erp.oms.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.CardAuthorization;
import com.chinadrtv.erp.model.trade.ShipmentHeader;

import java.util.List;
import java.util.Map;

/**
 * 信用卡索权
 * gdj - 2013-03-12
 */
public interface CardAuthorizationDao extends GenericDao<CardAuthorization, String>{
    Long getCardAuthorizationCount(Map<String, Object> params);
    List<CardAuthorization> getCardAuthorizations(Map<String, Object> params, int index, int size);
    CardAuthorization searchCardAuthorization(String orderId,String cardrightnum);
    int insertCardAuthorization(CardAuthorization c);

}

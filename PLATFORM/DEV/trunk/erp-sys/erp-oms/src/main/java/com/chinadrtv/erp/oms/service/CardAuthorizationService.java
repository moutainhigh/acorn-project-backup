package com.chinadrtv.erp.oms.service;

import com.chinadrtv.erp.model.CardAuthorization;

import java.util.List;
import java.util.Map;

/**
 * 信用卡索权
 * gdj - 2013-03-12
 */
public interface CardAuthorizationService {
    CardAuthorization getCardAuthorization(String orderId,String cardrightnum);
    Long getCardAuthorizationCount(Map<String, Object> params);
    List<CardAuthorization> getCardAuthorizations(Map<String, Object> params, int index, int size);
    void saveCardAuthorization(CardAuthorization auth);
    int insertCardAuthorization(CardAuthorization c);
}

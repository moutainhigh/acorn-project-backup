package com.chinadrtv.erp.oms.service.impl;

import com.chinadrtv.erp.model.CardAuthorization;
import com.chinadrtv.erp.model.trade.ShipmentHeader;
import com.chinadrtv.erp.oms.dao.CardAuthorizationDao;
import com.chinadrtv.erp.oms.service.CardAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 信用卡授权/索权
 * User: gdj
 * Date: 13-3-12
 * Time: 下午3:53
 * To change this template use File | Settings | File Templates.
 */

@Service("cardAuthorizationService")
public class CardAuthorizationServiceImpl implements CardAuthorizationService {

    @Autowired
    private CardAuthorizationDao cardAuthorizationDao;

    public Long getCardAuthorizationCount(Map<String, Object> params) {
        return cardAuthorizationDao.getCardAuthorizationCount(params);
    }

    public List<CardAuthorization> getCardAuthorizations(Map<String, Object> params, int index, int size) {
        return cardAuthorizationDao.getCardAuthorizations(params, index, size);
    }

    public void saveCardAuthorization(CardAuthorization auth){
        cardAuthorizationDao.saveOrUpdate(auth);
    }

    public CardAuthorization getCardAuthorization(String orderId,String cardrightnum){
        try
        {
            return cardAuthorizationDao.searchCardAuthorization(orderId,cardrightnum);
        }catch (Exception ex){
            return null;
        }
    }

    /**
     * 信用卡索权导入
     * @param c
     * @return
     */
    public int insertCardAuthorization(CardAuthorization c) {
        return cardAuthorizationDao.insertCardAuthorization(c);
    }
}

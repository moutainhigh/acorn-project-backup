package com.chinadrtv.erp.tc.core.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.agent.OrderSms;

/**
 * 短信Dao
 * User: liyu
 * Date: 13-1-29
 * Time: 下午1:58
 * 短信操作类
 */
public interface OrderSmsDao extends GenericDao<OrderSms, String>{
    void saveOrUpdate(OrderSms orderSms);
}
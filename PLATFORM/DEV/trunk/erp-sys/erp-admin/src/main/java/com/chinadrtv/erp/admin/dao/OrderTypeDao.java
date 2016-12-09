package com.chinadrtv.erp.admin.dao;


import com.chinadrtv.erp.admin.model.OrderType;
import com.chinadrtv.erp.core.dao.GenericDao;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gaodejian
 * Date: 12-8-10
 * Time: 上午10:12
 * To change this template use File | Settings | File Templates.
 */
public interface OrderTypeDao extends GenericDao<OrderType, String> {
    List<OrderType> getAllOrderTypes();
}

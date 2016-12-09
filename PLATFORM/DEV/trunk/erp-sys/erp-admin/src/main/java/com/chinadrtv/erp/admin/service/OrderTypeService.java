package com.chinadrtv.erp.admin.service;


import com.chinadrtv.erp.admin.model.OrderType;

import java.util.List;

/**
 * User: taoyawei
 * Date: 12-8-10
 */
public interface OrderTypeService {
    OrderType findById(String id);
    List<OrderType> getAllOrderTypes();
}

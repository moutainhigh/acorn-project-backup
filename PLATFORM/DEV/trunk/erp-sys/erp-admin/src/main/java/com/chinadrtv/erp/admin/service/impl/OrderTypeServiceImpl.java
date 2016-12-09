package com.chinadrtv.erp.admin.service.impl;

import com.chinadrtv.erp.admin.service.OrderTypeService;
import com.chinadrtv.erp.admin.dao.OrderTypeDao;
import com.chinadrtv.erp.admin.model.OrderType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: gaodejian
 * Date: 12-8-10
 */
@Service("OrderTypeService")
public class OrderTypeServiceImpl implements OrderTypeService {
    @Autowired
    private OrderTypeDao dao;
    public OrderType findById(String id){
        return dao.get(id);
    }
    /*
    TODO:提供订单类型ComboBox绑定数据
    */
    public List<OrderType> getAllOrderTypes() {
        return dao.getAllOrderTypes();
    }


}

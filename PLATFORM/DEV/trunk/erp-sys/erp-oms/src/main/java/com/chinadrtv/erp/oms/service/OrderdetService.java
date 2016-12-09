package com.chinadrtv.erp.oms.service;
import com.chinadrtv.erp.model.Orderdet;
import java.util.*;

/**
 * 
 *  订单详细服务
 * @author haoleitao
 * @date 2013-4-27 上午11:27:05
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface OrderdetService{
    Orderdet getOrderdetById(String orderdetId);
    List<Orderdet> getAllOrderdet();
    List<Orderdet> getAllOrderdet(String orderid);
    List<Orderdet> getAllOrderdet(int index, int size);
    int getOrderdetCount();
    void saveOrderdet(Orderdet orderdet);
    void addOrderdet(Orderdet orderdet);
    void removeOrderdet(Orderdet orderdet);
}

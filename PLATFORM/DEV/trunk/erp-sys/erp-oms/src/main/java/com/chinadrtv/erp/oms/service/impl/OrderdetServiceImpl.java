package com.chinadrtv.erp.oms.service.impl;
import com.chinadrtv.erp.model.Orderdet;
import com.chinadrtv.erp.oms.service.OrderdetService;
import com.chinadrtv.erp.oms.dao.OrderdetDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * 订单详细服务
 *  
 * @author haoleitao
 * @date 2013-4-27 上午11:21:00
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service("orderdetService")
public class OrderdetServiceImpl implements OrderdetService{

    @Autowired
    private OrderdetDao orderdetDao;


    public void addOrderdet(Orderdet orderdet) {
        orderdetDao.save(orderdet);
    }



    public void saveOrderdet(Orderdet orderdet) {
        orderdetDao.saveOrUpdate(orderdet);
    }

    public void delOrderdet(Long id) {
        //orderdetDao.remove(id);
    }
	
	public Orderdet getOrderdetById(String orderdetId){
		return orderdetDao.get(orderdetId);
	}



	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.oms.service.OrderdetService#getAllOrderdet()
	 */
	public List<Orderdet> getAllOrderdet() {
		// TODO Auto-generated method stub
		return null;
	}



	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.oms.service.OrderdetService#getAllOrderdet(int, int)
	 */
	public List<Orderdet> getAllOrderdet(int index, int size) {
		// TODO Auto-generated method stub
		return null;
	}



	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.oms.service.OrderdetService#getOrderdetCount()
	 */
	public int getOrderdetCount() {
		// TODO Auto-generated method stub
		return 0;
	}



	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.oms.service.OrderdetService#removeOrderdet(com.chinadrtv.erp.model.Orderdet)
	 */
	public void removeOrderdet(Orderdet orderdet) {
		// TODO Auto-generated method stub
		
	}



	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.oms.service.OrderdetService#getAllOrderdet(java.lang.String)
	 */
	public List<Orderdet> getAllOrderdet(String orderid) {
		// TODO Auto-generated method stub
		return orderdetDao.getAllOrderdet(orderid);
	}
}

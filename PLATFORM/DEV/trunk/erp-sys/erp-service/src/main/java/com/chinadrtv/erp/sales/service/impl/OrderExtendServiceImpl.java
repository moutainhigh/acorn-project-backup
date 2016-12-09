package com.chinadrtv.erp.sales.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.sales.dao.OrderExtendDao;
import com.chinadrtv.erp.sales.service.OrderExtendService;

@Service
public class OrderExtendServiceImpl implements OrderExtendService {

	@Autowired
	private OrderExtendDao orderExtendDao;
	
	@Override
	public List<String> listOrderIds(String contactId) {
		return orderExtendDao.listOrderIds(contactId);
	}

}

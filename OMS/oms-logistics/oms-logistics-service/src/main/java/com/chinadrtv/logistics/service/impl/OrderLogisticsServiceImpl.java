package com.chinadrtv.logistics.service.impl;

import com.chinadrtv.logistics.dal.bak.dao.OrderLogisticsDao;
import com.chinadrtv.logistics.dal.model.OrderLogistics;
import com.chinadrtv.logistics.service.OrderLogisticsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderLogisticsServiceImpl
        implements OrderLogisticsService
{

    @Autowired
    private OrderLogisticsDao orderLogisticsDao;

    public List<OrderLogistics> queryLogistics(String orderId)
    {
        return this.orderLogisticsDao.findLogistics(orderId);
    }
}
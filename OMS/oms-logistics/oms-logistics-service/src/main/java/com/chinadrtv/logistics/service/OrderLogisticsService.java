package com.chinadrtv.logistics.service;

import com.chinadrtv.logistics.dal.model.OrderLogistics;
import java.util.List;

public interface OrderLogisticsService
{
    public List<OrderLogistics> queryLogistics(String paramString);
}
package com.chinadrtv.logistics.dal.bak.dao;

import com.chinadrtv.logistics.dal.model.OrderLogistics;
import java.util.List;

public interface OrderLogisticsDao
{
    public List<OrderLogistics> findLogistics(String paramString);
}
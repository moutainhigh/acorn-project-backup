package com.chinadrtv.erp.tc.core.service;

import java.util.Map;

import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.agent.Order;

public interface DisHuifangMesService extends GenericService<Order, String>
{
    public boolean upOrderHistByfive(Map<String, Object> mapval) throws Exception;

	public void couponReviseproc(Map<String, Object> mapval) throws Exception;
            
}
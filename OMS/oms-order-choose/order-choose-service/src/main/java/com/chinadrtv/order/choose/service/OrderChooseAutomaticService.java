package com.chinadrtv.order.choose.service;

import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.tc.core.constant.model.OrderhistAssignInfo;
import com.chinadrtv.order.choose.dal.model.OrderChooseQueryParm;

import java.util.List;
import java.util.Map;

public interface OrderChooseAutomaticService {

    public int automaticChooseOrderForOldData(Map<String, Parameter> param);
    public int automaticChooseOrder(Map<String, Parameter> param);
    public int automaticChooseShipment(Map<String, Parameter> param);

    public void automaticOrderForOldData(Map<String, Parameter> param, Integer limit);
    public void automaticShipmentHeader(Map<String, Parameter> param, Integer limit) ;

    public List<OrderhistAssignInfo> automaticAssignOrder(OrderChooseQueryParm queryParm);

    public void assignOrderCompany(OrderhistAssignInfo orderhistAssignInfo);

    public void saveErrorOrderAndLog(Long orderId, String orderNum, String assignFlag, String errorMsg);
}


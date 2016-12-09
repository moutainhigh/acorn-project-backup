package com.chinadrtv.erp.uc.service;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.service.Cusnote;
import com.chinadrtv.erp.model.service.CusnoteId;
import com.chinadrtv.erp.uc.dto.CusnoteDto;

import java.util.Map;

public interface CusnoteService extends GenericService<Cusnote, CusnoteId> {
    /**
     * 根据业务订单号获取订单的客服通知
     *
     * @param orderId
     * @return Orderhist
     */
    Map<String, Object> getCusnoteByOrderid(String orderId);

    /**
     * 根据业务订单号获取订单的客服通知条数
     *
     * @param orderId
     * @return int
     */
    int getCusnoteCountByOrderid(String orderId);

    /**
     * 根据查询条件获取订单的客服通知
     */
    Map<String, Object> queryCusnote(CusnoteDto cusnoteDto, DataGridModel dataGrid);

    Cusnote queryCusnoteById(CusnoteId cusnoteId);

    boolean haveNoRepalyNotice(String userId, String customerId);

    int queryCountNoRepalyNotice(String userId);
}
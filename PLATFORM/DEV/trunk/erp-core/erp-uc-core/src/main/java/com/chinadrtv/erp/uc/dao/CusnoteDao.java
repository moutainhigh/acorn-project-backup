package com.chinadrtv.erp.uc.dao;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.service.Cusnote;
import com.chinadrtv.erp.model.service.CusnoteId;
import com.chinadrtv.erp.uc.dto.CusnoteDto;

import java.util.List;

public interface CusnoteDao extends GenericDao<Cusnote, CusnoteId> {
    /**
     * 根据业务订单号获取订单的客服通知的条数
     *
     * @param orderId
     * @return Orderhist
     * @throws
     * @Description:
     */
    int getOrderCusnoteCountByOrderid(String orderId);

    /**
     * 根据业务订单号获取订单的客服通知
     *
     * @param orderId
     * @return Orderhist
     * @throws
     * @Description:
     */
    List<Cusnote> getOrderCusnoteByOrderid(String orderId);

    List<Cusnote> queryCusnote(CusnoteDto cusnoteDto, DataGridModel dataGrid);

    int queryCusnoteCount(CusnoteDto cusnoteDto);

    boolean haveNoRepalyNotice(String userId, String customerId);

    int queryCountNoRepalyNotice(String userId);
}

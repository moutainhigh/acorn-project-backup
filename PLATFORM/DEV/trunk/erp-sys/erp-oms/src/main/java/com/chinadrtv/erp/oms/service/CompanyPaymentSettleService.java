package com.chinadrtv.erp.oms.service;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.CompanyPayment;
import com.chinadrtv.erp.model.CompanyPaymentSettle;
import com.chinadrtv.erp.model.trade.ShipmentSettlement;

import java.util.List;
import java.util.Map;

/**
 * 结算单拍平-服务类
 * User: gaodejian
 * Date: 13-3-7
 * Time: 下午1:17
 * To change this template use File | Settings | File Templates.
 */
public interface CompanyPaymentSettleService extends GenericService<CompanyPaymentSettle, Long> {
    /**
     * 结算单数量
     * @param params
     * @return
     */
    Long getCompanyPaymentSettleCount(Map<String, Object> params);
    /**
     * 结算单列表(可分页)
     * @param params
     * @param index
     * @param size
     * @return
     */
    List<CompanyPaymentSettle> getCompanyPaymentSettles(Map<String, Object> params, int index, int size);

    /**
     * 水单拍平
     * @param payments
     * @param settlements
     */
    void makeBalance(List<CompanyPayment> payments, List<ShipmentSettlement>[] settlements);


}

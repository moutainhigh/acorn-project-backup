package com.chinadrtv.erp.oms.service.impl;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.CompanyPayment;
import com.chinadrtv.erp.model.CompanyPaymentSettle;
import com.chinadrtv.erp.model.trade.ShipmentSettlement;
import com.chinadrtv.erp.oms.dao.CompanyPaymentSettleDao;
import com.chinadrtv.erp.oms.dao.ShipmentSettlementDao;
import com.chinadrtv.erp.oms.service.CompanyPaymentSettleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 流水单拍平
 * User: gdj
 * Date: 13-4-9
 * Time: 下午1:04
 * To change this template use File | Settings | File Templates.
 */
@Service("companyPaymentSettleService")
public class CompanyPaymentSettleServiceImpl extends GenericServiceImpl<CompanyPaymentSettle, Long> implements CompanyPaymentSettleService {

    @Autowired
    private CompanyPaymentSettleDao companyPaymentSettleDao;

    @Override
    protected GenericDao<CompanyPaymentSettle, Long> getGenericDao() {
        return companyPaymentSettleDao;
    }

    public Long getCompanyPaymentSettleCount(Map<String, Object> params) {
        return companyPaymentSettleDao.getCompanyPaymentSettleCount(params);
    }

    public List<CompanyPaymentSettle> getCompanyPaymentSettles(Map<String, Object> params, int index, int size) {
        return companyPaymentSettleDao.getCompanyPaymentSettles(params, index, size);
    }

    public  void makeBalance(List<CompanyPayment> payments, List<ShipmentSettlement>[] settlements){

    }
}

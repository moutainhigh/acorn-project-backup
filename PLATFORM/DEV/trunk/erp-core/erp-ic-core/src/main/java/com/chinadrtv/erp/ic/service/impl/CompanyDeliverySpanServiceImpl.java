package com.chinadrtv.erp.ic.service.impl;

import com.chinadrtv.erp.ic.dao.CompanyDeliverySpanDao;
import com.chinadrtv.erp.ic.model.CompanyDeliverySpan;
import com.chinadrtv.erp.ic.service.CompanyDeliverySpanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-5-8
 * Time: 下午4:24
 * To change this template use File | Settings | File Templates.
 */
@Service("companyDeliveryScopeService")
public class CompanyDeliverySpanServiceImpl implements CompanyDeliverySpanService {

    @Autowired
    private CompanyDeliverySpanDao companyDeliverySpanDao;

    public CompanyDeliverySpan getDeliverySpan(String companyId,
                                               Long orderTypeId,
                                               Long payTypeId,
                                               Long provinceId,
                                               Long cityId,
                                               Long countyId,
                                               Long areaId) {
        return companyDeliverySpanDao.getDeliverySpan(companyId, orderTypeId, payTypeId, provinceId, cityId, countyId, areaId);
    }
}

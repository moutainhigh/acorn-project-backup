package com.chinadrtv.erp.ic.service;

import com.chinadrtv.erp.ic.model.CompanyDeliverySpan;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-5-8
 * Time: 下午4:23
 * To change this template use File | Settings | File Templates.
 */
public interface CompanyDeliverySpanService {
    CompanyDeliverySpan getDeliverySpan(
            String companyId,
            Long orderTypeId,
            Long payTypeId,
            Long provinceId,
            Long cityId,
            Long countyId,
            Long areaId);
}

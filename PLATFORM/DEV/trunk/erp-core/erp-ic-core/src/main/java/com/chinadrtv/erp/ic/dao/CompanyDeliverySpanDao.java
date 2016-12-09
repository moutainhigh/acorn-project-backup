package com.chinadrtv.erp.ic.dao;

import com.chinadrtv.erp.ic.model.CompanyDeliverySpan;

/**
 * 承运商配送范围与配送时效
 * User: gaodejian
 * Date: 13-5-8
 * Time: 上午11:23
 * To change this template use File | Settings | File Templates.
 */
public interface CompanyDeliverySpanDao {
    CompanyDeliverySpan getDeliverySpan(
            String companyId,
            Long orderTypeId,
            Long payTypeId,
            Long provinceId,
            Long cityId,
            Long countyId,
            Long areaId);
}

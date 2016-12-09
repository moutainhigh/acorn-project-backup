package com.chinadrtv.erp.sales.core.service;

import com.chinadrtv.erp.model.Amortisation;

import java.util.List;

/**
 * 信用卡分期服务
 * User: gaodejian
 * Date: 13-6-9
 * Time: 下午1:53
 * To change this template use File | Settings | File Templates.
 */
public interface AmortisationService {
    List<Amortisation> queryCardTypeAmortisations(String cardTypeId);
}

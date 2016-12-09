package com.chinadrtv.erp.sales.core.service.impl;

import com.chinadrtv.erp.model.Amortisation;
import com.chinadrtv.erp.sales.core.dao.AmortisationDao;
import com.chinadrtv.erp.sales.core.service.AmortisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 信用卡分期服务
 * User: gaodejian
 * Date: 13-6-9
 * Time: 下午1:53
 * To change this template use File | Settings | File Templates.
 */
@Service("amortisationService")
public class AmortisationServiceImpl implements AmortisationService {

    @Autowired
    private AmortisationDao amortisationDao;

    public List<Amortisation> queryCardTypeAmortisations(String cardTypeId){
        return amortisationDao.queryCardTypeAmortisations(cardTypeId);
    }
}

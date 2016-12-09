package com.chinadrtv.erp.oms.service.impl;

import com.chinadrtv.erp.model.LogisticsWeightRuleDetail;
import com.chinadrtv.erp.model.agent.Province;
import com.chinadrtv.erp.oms.dao.LogisticsWeightRuleDetailDao;
import com.chinadrtv.erp.oms.dao.ProvinceDao;
import com.chinadrtv.erp.oms.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: gaodejian
 * Date: 12-8-10
 */
@Service("provinceService")
public class ProvinceServiceImpl implements ProvinceService {
    @Autowired
    private ProvinceDao dao;

    public Province findById(String provinceId) {
        return dao.get(provinceId);
    }

    public List<Province> getAllProvinces() {
        return dao.getAllProvinces();
    }
}

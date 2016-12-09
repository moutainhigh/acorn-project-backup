package com.chinadrtv.erp.oms.service;


import com.chinadrtv.erp.model.agent.Province;

import java.util.List;

/**
 * User: gaodejian
 * Date: 12-8-10
 */
public interface ProvinceService {
    Province findById(String provinceId);
    List<Province> getAllProvinces();
}

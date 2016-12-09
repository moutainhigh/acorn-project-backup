package com.chinadrtv.erp.admin.service;


import com.chinadrtv.erp.admin.model.*;

import java.util.List;

/**
 * User: gaodejian
 * Date: 12-8-10
 */
public interface ProvinceService {
    Province findById(String provinceId);
    List<Province> getAllProvinces();
}

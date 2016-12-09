package com.chinadrtv.erp.admin.service.impl;

import com.chinadrtv.erp.admin.dao.*;
import com.chinadrtv.erp.admin.model.*;
import com.chinadrtv.erp.admin.service.*;
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

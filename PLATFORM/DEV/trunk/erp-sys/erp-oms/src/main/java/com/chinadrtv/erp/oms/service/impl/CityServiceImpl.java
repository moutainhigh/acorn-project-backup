package com.chinadrtv.erp.oms.service.impl;

import com.chinadrtv.erp.model.agent.CityAll;
import com.chinadrtv.erp.oms.dao.CityDao;
import com.chinadrtv.erp.oms.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: gaodejian
 * Date: 12-8-10
 */
@Service("cityService")
public class CityServiceImpl implements CityService {
    @Autowired
    private CityDao dao;

    public CityAll findById(Long cityId) {
        return dao.get(cityId);
    }

    public List<CityAll> getCities(String provinceId){
        return dao.getCities(provinceId);
    }

    public List<CityAll> getAllCities(){
        return dao.getAllCities();
    }
}

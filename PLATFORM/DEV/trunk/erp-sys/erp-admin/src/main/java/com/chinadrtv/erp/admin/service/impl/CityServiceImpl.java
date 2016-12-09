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
@Service("cityService")
public class CityServiceImpl implements CityService {
    @Autowired
    private CityDao dao;

    public City findById(Long cityId) {
        return dao.get(cityId);
    }

    public List<City> getCities(String provinceId){
        return dao.getCities(provinceId);
    }

    public List<City> getAllCities(){
        return dao.getAllCities();
    }
}

package com.chinadrtv.erp.admin.service;


import com.chinadrtv.erp.admin.model.*;

import java.util.List;

/**
 * User: gaodejian
 * Date: 12-8-10
 */
public interface CityService {
    City findById(Long id);
    List<City> getAllCities();
    List<City> getCities(String provinceId);
}

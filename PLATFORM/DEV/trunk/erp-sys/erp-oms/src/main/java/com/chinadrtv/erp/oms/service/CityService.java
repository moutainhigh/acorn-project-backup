package com.chinadrtv.erp.oms.service;


import com.chinadrtv.erp.model.agent.CityAll;
import java.util.List;

/**
 * User: gaodejian
 * Date: 12-8-10
 */
public interface CityService {
    CityAll findById(Long id);
    List<CityAll> getAllCities();
    List<CityAll> getCities(String provinceId);
}

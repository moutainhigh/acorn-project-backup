package com.chinadrtv.erp.oms.service;


import com.chinadrtv.erp.model.agent.CountyAll;

import java.util.List;

/**
 * User: gaodejian
 * Date: 12-8-10
 */
public interface CountyService {
    CountyAll findById(Long id);
    List<CountyAll> getAllCounties();
    List<CountyAll> getCounties(Long cityId);
}

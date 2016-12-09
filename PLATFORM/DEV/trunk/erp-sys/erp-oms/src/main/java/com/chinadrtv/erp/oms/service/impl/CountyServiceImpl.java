package com.chinadrtv.erp.oms.service.impl;

import com.chinadrtv.erp.model.agent.CountyAll;
import com.chinadrtv.erp.oms.dao.CountyDao;
import com.chinadrtv.erp.oms.service.CountyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: gaodejian
 * Date: 12-8-10
 */
@Service("countyService")
public class CountyServiceImpl implements CountyService {
    @Autowired
    private CountyDao dao;

    public CountyAll findById(Long CountyId) {
        return dao.get(CountyId);
    }

    public List<CountyAll> getCounties(Long cityId){
        return dao.getCounties(cityId);
    }

    public List<CountyAll> getAllCounties(){
        return dao.getAllCounties();
    }
}

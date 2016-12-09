package com.chinadrtv.service.oms.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.dal.oms.dao.CountyMappingDao;
import com.chinadrtv.model.oms.CountyMapping;
import com.chinadrtv.service.oms.CountyMappingService;

/**
 * User: zhaizhanyi
 * Date: 12-12-29
 */
@Service("countyMappingService")
public class CountyMappingServiceImpl implements CountyMappingService {

    @Autowired
    private CountyMappingDao countyMappingDao;


	public CountyMapping findById(int countryId) {
		return countyMappingDao.findById(countryId);
	}

}

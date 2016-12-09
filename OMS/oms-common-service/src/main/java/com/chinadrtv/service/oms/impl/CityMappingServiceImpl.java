package com.chinadrtv.service.oms.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.dal.oms.dao.CityMappingDao;
import com.chinadrtv.model.oms.CityMapping;
import com.chinadrtv.service.oms.CityMappingService;

/**
 * User: zhaizhanyi
 * Date: 12-12-29
 */
@Service("cityMappingService")
public class CityMappingServiceImpl implements CityMappingService {

    @Autowired
    private CityMappingDao cityMappingDao;

	public CityMapping findById(int cityId) {
		return cityMappingDao.findById(cityId);
	}

}

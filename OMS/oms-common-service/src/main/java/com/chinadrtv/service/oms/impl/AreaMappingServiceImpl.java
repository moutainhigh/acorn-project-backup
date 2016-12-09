package com.chinadrtv.service.oms.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.dal.oms.dao.AreaMappingDao;
import com.chinadrtv.model.oms.AreaMapping;
import com.chinadrtv.service.oms.AreaMappingService;

/**
 * User: zhaizhanyi
 * Date: 12-12-29
 */
@Service("areaMappingService")
public class AreaMappingServiceImpl implements AreaMappingService {

    @Autowired
    private AreaMappingDao areaMappingDao;

	public AreaMapping findById(int areaId) {
		return areaMappingDao.findById(areaId);
	}

}

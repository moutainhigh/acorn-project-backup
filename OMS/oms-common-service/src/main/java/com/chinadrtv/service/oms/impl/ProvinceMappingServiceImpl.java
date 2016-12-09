package com.chinadrtv.service.oms.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.dal.oms.dao.ProvinceMappingDao;
import com.chinadrtv.model.oms.ProvinceMapping;
import com.chinadrtv.service.oms.ProvinceMappingService;

/**
 * User: zhaizhanyi
 * Date: 12-12-29
 */
@Service("provinceMappingService")
public class ProvinceMappingServiceImpl implements ProvinceMappingService {

    @Autowired
    private ProvinceMappingDao provinceMappingDao;

	public ProvinceMapping findById(int provinceId) {
		return provinceMappingDao.findById(provinceId);
	}
}

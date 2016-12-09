package com.chinadrtv.erp.report.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.report.dao.ResourceDao;
import com.chinadrtv.erp.report.entity.Resource;
import com.chinadrtv.erp.report.service.ResourceService;

/**
 * @author zhangguosheng
 *
 */
@Service
public class ResourceServiceImpl implements ResourceService{

	@Autowired
	private ResourceDao resourceDao;

	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.report.service.ResourceService#get(java.lang.Long)
	 */
	@Override
	public Resource get(Long id) {
		return resourceDao.get(Resource.class, id);
	}

	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.report.service.ResourceService#findAll()
	 */
	@Override
	public Iterable<Resource> findAll() {
		return resourceDao.findAll();
	}
	
}

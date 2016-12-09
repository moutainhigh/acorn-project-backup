package com.chinadrtv.erp.report.service;

import com.chinadrtv.erp.report.entity.Resource;


/**
 * @author zhangguosheng
 *
 */
public interface ResourceService{
	
	/**
	 * @param id
	 * @return
	 */
	public Resource get(Long id);

	/**
	 * @return
	 */
	public Iterable<Resource> findAll();
	
}

package com.chinadrtv.erp.report.service;

import com.chinadrtv.erp.report.entity.Document;


/**
 * @author zhangguosheng
 *
 */
public interface DocumentService{
	
	/**
	 * 通过主键查询
	 * @param id
	 * @return
	 */
	public Document get(Long id);
	
}

package com.chinadrtv.erp.report.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.erp.report.dao.DocumentDao;
import com.chinadrtv.erp.report.entity.Document;
import com.chinadrtv.erp.report.service.DocumentService;

//@Service
/**
 * @author zhangguosheng
 *
 */
public class DocumentServiceImpl implements DocumentService{

	@Autowired
	private DocumentDao documentDao;

	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.report.service.DocumentService#get(java.lang.Long)
	 */
	@Override
	public Document get(Long id) {
		return documentDao.get(Document.class, id);
	}
	
}

/*
 * @(#)LeadTypeParamsServiceImpl.java 1.0 2013-6-4上午10:17:07
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.service.impl;

import java.util.List;

import javax.xml.rpc.ServiceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.exception.ExceptionConstant;
import com.chinadrtv.erp.marketing.core.dao.LeadTypeParamsDao;
import com.chinadrtv.erp.marketing.core.service.LeadTypeParamsService;
import com.chinadrtv.erp.model.marketing.LeadTypeParams;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-6-4 上午10:17:07
 * 
 */
@Service("leadTypeParamsService")
public class LeadTypeParamsServiceImpl implements LeadTypeParamsService {
	@Autowired
	private LeadTypeParamsDao leadTypeParamsDao;

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: getParamsList
	 * </p>
	 * <p>
	 * Description:根据leadtypeid 查询LeadTypeParams
	 * </p>
	 * 
	 * @param leadTypeId
	 * 
	 * @return
	 * @throws ServiceException
	 * 
	 * @see com.chinadrtv.erp.marketing.core.service.LeadTypeParamsService#getParamsList
	 *      (java.lang.Long)
	 */
	public List<LeadTypeParams> getParamsList(Long leadTypeId)
			throws ServiceException {
		// TODO Auto-generated method stub
		if (leadTypeId == null) {
			throw new ServiceException(
					ExceptionConstant.SERVICE_PARAMETER_EXCEPTION);
		}
		return leadTypeParamsDao.getParamsList(leadTypeId);
	}

}

/*
 * @(#)CouponServiceImpl.java 1.0 2013-8-23下午3:37:17
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.exception.ExceptionConstant;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.model.marketing.CouponConfig;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;
import com.chinadrtv.erp.smsapi.dao.CouponConfigDao;
import com.chinadrtv.erp.smsapi.service.CouponConfigService;
import com.chinadrtv.erp.smsapi.util.StringUtil;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-8-23 下午3:37:17
 * 
 */
@Service("couponConfigService")
public class CouponConfigServiceImpl implements CouponConfigService {

	@Autowired
	private CouponConfigDao couponConfigDao;
	private static final Logger logger = LoggerFactory
			.getLogger(CouponConfigServiceImpl.class);

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: getListForPage
	 * </p>
	 * <p>
	 * Description: 分页查询
	 * </p>
	 * 
	 * @param coupon
	 * 
	 * @param dataModel
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.smsapi.service.CouponConfigService#getListForPage(com
	 *      .chinadrtv.erp.model.marketing.CouponConfig,
	 *      com.chinadrtv.erp.smsapi.constant.DataGridModel)
	 */
	public Map<String, Object> getListForPage(CouponConfig couponConfig,
			DataGridModel dataModel) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<String, Object>();
		List<CouponConfig> list = couponConfigDao
				.query(couponConfig, dataModel);
		Integer total = couponConfigDao.queryCount(couponConfig);
		result.put("rows", list);
		result.put("total", total);

		return result;
	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: getById
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param id
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.smsapi.service.CouponConfigService#getById(java.lang
	 *      .Long)
	 */
	public CouponConfig getById(Long id) {
		// TODO Auto-generated method stub
		return couponConfigDao.get(id);
	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: saveCouponConfig
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param couponConfig
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.smsapi.service.CouponConfigService#saveCouponConfig
	 *      (com.chinadrtv.erp.model.marketing.CouponConfig)
	 */
	public Boolean saveCouponConfig(CouponConfig couponConfig) {
		// TODO Auto-generated method stub
		Boolean result = false;
		try {
			couponConfigDao.saveOrUpdate(couponConfig);
			result = true;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("保存couponConfig 失败" + e.getStackTrace());
		}
		return result;
	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: deleteCouponConfig
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param ids
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.smsapi.service.CouponConfigService#deleteCouponConfig
	 *      (java.lang.String)
	 */
	public Boolean deleteCouponConfig(String ids) {
		// TODO Auto-generated method stub
		String[] ida = ids.split(",");
		Boolean flag = false;
		try {
			for (int i = 0; i < ida.length; i++) {
				couponConfigDao.updateStatus(Long.valueOf(ida[i]));
				flag = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("删除失败" + e.getMessage());
		}
		return flag;
	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: queryList
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param department
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.smsapi.service.CouponConfigService#queryList(java.lang
	 *      .String)
	 */

	public List<CouponConfig> queryList(String department)
			throws ServiceException {
		// TODO Auto-generated method stub
		if (StringUtil.isNullOrBank(department)) {
			throw new ServiceException(
					ExceptionConstant.SERVICE_PARAMETER_EXCEPTION,
					"department is null");
		}
		return couponConfigDao.queryList(department);
	}
}

/*
 * @(#)OrderTypeTemplateRelationDao.java 1.0 2013-5-24下午3:23:50
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.smsapi.model.OrderTypeTemplateRelation;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-5-24 下午3:23:50
 * 
 */
public interface OrderTypeTemplateRelationDao extends
		GenericDao<OrderTypeTemplateRelation, java.lang.Long> {
	/**
	 * 
	 * @Description: 根据订单类型查询模板
	 * @param orderType
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	public OrderTypeTemplateRelation getTemplateByOrderType(String orderType);

	/**
	 * 
	 * @Description: 生成验证码
	 * @return
	 * @return String
	 * @throws
	 */
	public Long getchechCode();

	/**
	 * 插入 ordercheckcode 表
	 * 
	 * @Description: TODO
	 * @param checkCode
	 * @param orderId
	 * @return
	 * @return Boolean
	 * @throws
	 */
	public Boolean insertCode(String checkCode, String orderId);

}

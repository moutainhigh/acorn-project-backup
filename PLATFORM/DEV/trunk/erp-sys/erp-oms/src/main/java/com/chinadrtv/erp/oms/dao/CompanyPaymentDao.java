/*
 * @(#)CompanyPaymentDao.java 1.0 2013-4-8下午2:35:20
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.oms.dao;

import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.CompanyPayment;
import com.chinadrtv.erp.oms.common.DataGridModel;
import com.chinadrtv.erp.oms.dto.CompanyPaymentDto;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2013-4-8 下午2:35:20 
 * 
 */
public interface CompanyPaymentDao extends GenericDao<CompanyPayment, Long> {

	/**
	* @Description: 
	* @param cpDto
	* @return int
	* @throws
	*/ 
	int queryPageCount(CompanyPaymentDto cpDto);

	/**
	* @Description: 
	* @param cpDto
	* @param dataModel
	* @return List<CompanyPayment>
	* @throws
	*/ 
	List<CompanyPayment> queryPageList(CompanyPaymentDto cpDto, DataGridModel dataModel);

	/**
	 * 导出查询
	* @Description: 
	* @param cpDto
	* @return
	* @return List<CompanyPayment>
	* @throws
	*/ 
	List<CompanyPayment> queryList(CompanyPaymentDto cpDto);

    /**
     * 水单数量
     * @param params
     * @return
     */
    Long getPaymentCount(Map<String, Object> params);
    /**
     * 水单列表(可分页)
     * @param params
     * @param index
     * @param size
     * @return
     */
    List<CompanyPayment> getPayments(Map<String, Object> params, int index, int size);

	/**
	 * 根据paymentCode 查询
	* @Description: 
	* @param cp
	* @return
	* @return CompanyPayment
	* @throws
	*/ 
	Boolean checkPaymentCode(CompanyPayment cp);
	/**
     * 获取指定id的水单列表
     * @param paymentIds
     * @return
     */
    List<CompanyPayment> getPayments(Long[] paymentIds);

}

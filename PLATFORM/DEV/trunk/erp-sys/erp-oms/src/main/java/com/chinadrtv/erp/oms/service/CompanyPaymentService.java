/*
 * @(#)CompanyPaymentService.java 1.0 2013-4-8下午2:37:27
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.oms.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.chinadrtv.erp.core.service.GenericService;
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
 * @since 2013-4-8 下午2:37:27 
 * 
 */
public interface CompanyPaymentService extends GenericService<CompanyPayment, Long> {

	/**
	 * 列表查询
	* @Description: 
	* @param cpDto
	* @return
	* @return Map<String,Object>
	* @throws
	*/ 
	Map<String, Object> queryPaymentList(CompanyPaymentDto cpDto, DataGridModel dataModel);
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
     * 获取水单
     * @param paymentIds
     * @return
     */
    List<CompanyPayment> getPayments(Long[] paymentIds);
	/**
	 * 删除
	* @Description: 
	* @param id
	* @return void
	* @throws
	*/ 
	void remove(Long id);

	/**
	 * 导出
	* @Description: 
	* @param cpDto
	* @return
	* @return HSSFWorkbook
	* @throws
	*/ 
	HSSFWorkbook export(CompanyPaymentDto cpDto);
	
	
	/**
	 * <p>保存</p>
	 * @param cp
	 */
	void persist(CompanyPayment cp);

}

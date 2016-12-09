/*
 * @(#)ObCustomerService.java 1.0 2013-5-9下午3:21:19
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.service;

import java.util.Map;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.uc.dto.ObCustomerDto;

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
 * @since 2013-5-9 下午3:21:19 
 * 
 */
public interface ObCustomerService {

    
    /**
     * <p>API-UC-33.查询我的客户（OUTBOND版本）</p>
     * @param gataGridModel
     * @param customerDto
     * @return Map<String, Object>
     */
    Map<String, Object> queryObCustomer(DataGridModel dataGridModel, ObCustomerDto obCustomerDto);
}

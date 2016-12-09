/*
 * @(#)CustomerController.java 1.0 2013-5-24上午9:54:27
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.uc.dto.ObCustomerDto;
import com.chinadrtv.erp.uc.service.ObCustomerService;

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
 * @since 2013-5-24 上午9:54:27 
 * 
 */
@Controller
@RequestMapping(value="/customer")
public class CustomerController {
	@Autowired
	private ObCustomerService obCustomerService;

	@ResponseBody
	@RequestMapping(value="/queryAllCustomer")
	public Map<String, Object> queryAllCustomer(@RequestBody Map<String, Object> params){
		DataGridModel dataModel = (DataGridModel) params.get("dataModel");
		ObCustomerDto obCustomerDto = (ObCustomerDto) params.get("obCustomerDto");
		Map<String, Object> pageMap = obCustomerService.queryObCustomer(dataModel, obCustomerDto);
		return pageMap;
	}
}

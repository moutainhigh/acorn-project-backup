/*
 * @(#)CompanyPaymentTest.java 1.0 2013-4-18上午9:31:19
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.oms.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.erp.model.CompanyPayment;
import com.chinadrtv.erp.oms.common.DataGridModel;
import com.chinadrtv.erp.oms.dto.CompanyPaymentDto;
import com.chinadrtv.erp.test.SpringTest;

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
 * @since 2013-4-18 上午9:31:19 
 * 
 */
public class CompanyPaymentServiceTest extends SpringTest{
	
	@Autowired
	private CompanyPaymentService companyPaymentService;
	
	@Test
	public void testInit(){
		Assert.assertNotNull(companyPaymentService);
	}
	
	@Test
	public void testPageList(){
		CompanyPaymentDto cpDto = new CompanyPaymentDto();
		DataGridModel dataGridModel = new DataGridModel();
		dataGridModel.setPage(1);
		Map<String, Object>  pageMap = companyPaymentService.queryPaymentList(cpDto, dataGridModel);
		
		Assert.assertNotNull(pageMap);
		Integer totlaCount = (Integer) pageMap.get("total");
		List<CompanyPayment> cpList = (List<CompanyPayment>) pageMap.get("rows");
		Assert.assertTrue(totlaCount >= 0);
		Assert.assertTrue(cpList.size() >= 0);
	}
	
	//@Test
	public void testPersistence(){
		CompanyPayment cp = new CompanyPayment();
		cp.setAmount(new BigDecimal(100));
		cp.setIsSettled("0");
		cp.setPaymentCode("11");
		cp.setSettledAmount(new BigDecimal(0));
		cp.setCompanyAccountCode("11");
		cp.setCpompanyAccountName("11");
		cp.setPaymentDate(new Date());
		
		companyPaymentService.saveOrUpdate(cp);
		
		companyPaymentService.remove(cp.getId());
	}
	
	@Test
	public void testExport(){
		CompanyPaymentDto cpDto = new CompanyPaymentDto();
		HSSFWorkbook workbook = companyPaymentService.export(cpDto);
		Assert.assertNotNull(workbook);
	}
}

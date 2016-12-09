/*
 * @(#)CompanyPaymentController.java 1.0 2013-4-8下午2:46:33
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.oms.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chinadrtv.erp.model.CompanyAccount;
import com.chinadrtv.erp.model.CompanyContract;
import com.chinadrtv.erp.model.CompanyPayment;
import com.chinadrtv.erp.oms.common.DataGridModel;
import com.chinadrtv.erp.oms.dto.CompanyPaymentDto;
import com.chinadrtv.erp.oms.service.CompanyAccountService;
import com.chinadrtv.erp.oms.service.CompanyContractService;
import com.chinadrtv.erp.oms.service.CompanyPaymentService;

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
 * @since 2013-4-8 下午2:46:33 
 * 
 */
@Controller
@RequestMapping(value = "/company")
public class CompanyPaymentController extends BaseController{
	
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CompanyPaymentController.class);
	
	@Autowired
	private CompanyPaymentService companyPaymentService;
	@Autowired
	private CompanyContractService companyContractService;
	@Autowired
	private CompanyAccountService companyAccountService;
	
    @InitBinder
    public void InitBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
    }
	
    /**
     * 加载
    * @Description: 
    * @return ModelAndView
    * @throws
     */
	@RequestMapping(value = "companyPayment")
	public ModelAndView companyPayment(){
		
		List<CompanyContract> companyContractList = companyContractService.getAllContracts();
		
		ModelAndView mav = new ModelAndView("company/companyPayment");
		mav.addObject("companyContractList", companyContractList);
		
		return mav;
	}

	/**
	 * 分页查询
	* @Description: 
	* @param cpDto
	* @param dataModel
	* @return Map<String,Object>
	* @throws
	 */
	@RequestMapping(value = "/queryPaymentList")
	@ResponseBody
	public Map<String, Object> queryPaymentList(CompanyPaymentDto cpDto, DataGridModel dataModel){
		return companyPaymentService.queryPaymentList(cpDto, dataModel);
	}
	
	/**
	 * 级联查询账户
	* @Description: 
	* @param id
	* @return Map<String,Object>
	* @throws
	 */
	@RequestMapping(value = "/queryAccountListByContract")
	@ResponseBody
	public List<CompanyAccount> queryAccountListByContract(Long id){
		List<CompanyAccount> accountList = companyAccountService.queryAccountListByContract(id);
		return accountList;
	}
	
	/**
	 * 保存
	* @Description: 
	* @param cp
	* @return Map<String,Object>
	* @throws
	 */
	@RequestMapping(value = "/persist")
	@ResponseBody
	public Map<String, Object> persist(CompanyPayment cp){
		Map<String, Object> rsMap = new HashMap<String, Object>();
		boolean result = false;
		String message = "";
		try {
			companyPaymentService.persist(cp);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			message = "保存失败：" + e.getMessage();
			logger.error("保存水单失败", e);
		}
		
		rsMap.put("success", result);
		rsMap.put("message", message);
		
		return rsMap;
	}
	
	
	/**
	 * 删除
	* @Description: 
	* @param id
	* @return Map<String,Object>
	* @throws
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Map<String, Object> remove(Long id){
		Map<String, Object> rsMap = new HashMap<String, Object>();
		boolean success = false;
		String message = "";
		try {
			companyPaymentService.remove(id);
			success = true;
		} catch (Exception e) {
			message = e.getMessage();
			logger.error("删除审核单失败", e);
			e.printStackTrace();
		}
		
		rsMap.put("success", success);
		rsMap.put("message", message);
		return rsMap;
	}
	
	/**
	 * 导出
	* @Description: 
	* @return void
	* @throws IOException
	 */
	@RequestMapping(value = "/export")
	public void export(CompanyPaymentDto cpDto, HttpServletResponse response) throws IOException {
		HSSFWorkbook wb = null;
		try {
			wb = companyPaymentService.export(cpDto);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (wb != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String fileName = "Report" +sdf.format(new Date()) + ".xls";
			response.setContentType("application/ms-excel");
			response.setHeader("Content-Disposition", "attachment;filename="+ fileName);

			wb.write(response.getOutputStream());
			response.getOutputStream().flush();
		}
	}
	
}

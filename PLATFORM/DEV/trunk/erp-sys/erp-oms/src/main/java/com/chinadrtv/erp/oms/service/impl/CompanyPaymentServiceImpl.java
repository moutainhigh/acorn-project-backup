/*
 * @(#)CompanyPaymentServiceImpl.java 1.0 2013-4-8下午2:38:03
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.oms.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.exception.BizException;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.CompanyPayment;
import com.chinadrtv.erp.oms.common.DataGridModel;
import com.chinadrtv.erp.oms.constants.CompanyConstants;
import com.chinadrtv.erp.oms.dao.CompanyPaymentDao;
import com.chinadrtv.erp.oms.dto.CompanyPaymentDto;
import com.chinadrtv.erp.oms.service.CompanyPaymentService;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.util.SecurityHelper;

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
 * @since 2013-4-8 下午2:38:03 
 * 
 */
@Service
public class CompanyPaymentServiceImpl extends GenericServiceImpl<CompanyPayment, Long> implements
		CompanyPaymentService {

	@Autowired
	private CompanyPaymentDao companyPaymentDao;
	
	@Override
	protected GenericDao<CompanyPayment, Long> getGenericDao() {
		return companyPaymentDao;
	}

	/*
	 * 列表查询
	* <p>Title: queryPaymentList</p>
	* <p>Description: </p>
	* @param cpDto
	* @return
	* @see com.chinadrtv.erp.oms.service.CompanyPaymentService#queryPaymentList(com.chinadrtv.erp.oms.dto.CompanyPaymentDto)
	*/ 
	public Map<String, Object> queryPaymentList(CompanyPaymentDto cpDto, DataGridModel dataModel) {
		Map<String, Object> pageMap = new HashMap<String, Object>();
		
		if(!cpDto.isInitLoad()){
			pageMap.put("total", 0);
			pageMap.put("rows", new ArrayList<CompanyPayment>());
			return pageMap;
		}
		
		int count = companyPaymentDao.queryPageCount(cpDto);
		List<CompanyPayment> pageList = companyPaymentDao.queryPageList(cpDto, dataModel);
		
		pageMap.put("total", count);
		pageMap.put("rows", pageList);
		
		return pageMap;
	}

	/**
	 * <p>保存或更新</p>
	 * @param model
	 */
	public void persist(CompanyPayment model) {
		AgentUser agentUser = SecurityHelper.getLoginUser();
		
		boolean isDuplicate = companyPaymentDao.checkPaymentCode(model);
		if (isDuplicate) {
			throw new BizException("已存在水单号为[" + model.getPaymentCode() + "]的记录");
		}
		
		if(null == model.getId()){
			model.setCreateDate(new Date());
			model.setCreateUser(agentUser.getUserId());
			model.setIsSettled(CompanyConstants.COMPANY_PAYMENT_UNAUDITED);
			Date paymnetDate = model.getPaymentDate();
			
			//获取凌晨时间
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, 24);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			Date midnight = calendar.getTime();
			if(paymnetDate.getTime() > midnight.getTime()){
				throw new BizException("打款时间不能大于今天24点");
			}
		}else{
			Date paymnetDate = model.getPaymentDate();
			//获取凌晨时间
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(model.getCreateDate());
			calendar.set(Calendar.HOUR_OF_DAY, 24);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			Date midnight = calendar.getTime();
			if(paymnetDate.getTime() > midnight.getTime()){
				throw new BizException("打款时间不能大于创建当天24点");
			}
			model.setUpdateDate(new Date());
			model.setUpdateUser(agentUser.getUserId());
		}
		super.saveOrUpdate(model);
	}

	/* (none Javadoc)
	* <p>Title: remove</p>
	* <p>Description: </p>
	* @param id
	* @see com.chinadrtv.erp.oms.service.CompanyPaymentService#remove(java.lang.Long)
	*/ 
	public void remove(Long id) {
		CompanyPayment cp = companyPaymentDao.get(id);
		if(!cp.getIsSettled().equals(CompanyConstants.COMPANY_PAYMENT_UNAUDITED)){
			throw new BizException("已审核或审核完的核销单不能删除");
		}
		companyPaymentDao.remove(id);
	}

	/* 
	 * 导出
	* <p>Title: export</p>
	* <p>Description: </p>
	* @param cpDto
	* @return
	* @see com.chinadrtv.erp.oms.service.CompanyPaymentService#export(com.chinadrtv.erp.oms.dto.CompanyPaymentDto)
	*/ 
	public HSSFWorkbook export(CompanyPaymentDto cpDto) {
		List<CompanyPayment> pageList = companyPaymentDao.queryList(cpDto);
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
		HSSFRow title = sheet.createRow(0);
		title.createCell(0).setCellValue("水单号");
		title.createCell(1).setCellValue("承运商");
		title.createCell(2).setCellValue("水单金额");
		title.createCell(3).setCellValue("打款时间");
		title.createCell(4).setCellValue("状态");
		title.createCell(5).setCellValue("打款账号");
		title.createCell(6).setCellValue("户名");
		title.createCell(7).setCellValue("录入时间");
		title.createCell(8).setCellValue("录入人员");
		
		for(int i=0; i<pageList.size(); i++){
			HSSFRow row = sheet.createRow(i+1);
			CompanyPayment cp = pageList.get(i);
			HSSFCell cell0 = row.createCell(0);
			cell0.setCellValue(cp.getPaymentCode());
			
			HSSFCell cell1 = row.createCell(1);
			cell1.setCellValue(cp.getCompanyContract().getName());
			
			HSSFCell cell2 = row.createCell(2);
			cell2.setCellValue(cp.getAmount().doubleValue());
			
			if(null!=cp.getPaymentDate()){
				HSSFCell cell3 = row.createCell(3);
				cell3.setCellValue(sdf.format(cp.getPaymentDate()));
			}
			
			HSSFCell cell4 = row.createCell(4);
			cell4.setCellValue(cp.getIsSettled());
			
			HSSFCell cell5 = row.createCell(5);
			cell5.setCellValue(cp.getCompanyAccountCode());
			
			HSSFCell cell6 = row.createCell(6);
			cell6.setCellValue(cp.getCpompanyAccountName());
			
			HSSFCell cell7 = row.createCell(7);
			cell7.setCellValue(cp.getCreateUser());
			
			if(null != cp.getCreateDate()){
				HSSFCell cell8 = row.createCell(8);
				cell8.setCellValue(sdf.format(cp.getCreateDate()));
			}
			
		}
		
		return wb;
	}

	 /**
     * 水单数量
     * @param params
     * @return
     */
    public Long getPaymentCount(Map<String, Object> params){
        return companyPaymentDao.getPaymentCount(params);
    }
    /**
     * 水单列表(可分页)
     * @param params
     * @param index
     * @param size
     * @return
     */
    public List<CompanyPayment> getPayments(Map<String, Object> params, int index, int size){
        return companyPaymentDao.getPayments(params, index, size);
    }

    /**
     * 获取指定的水单
     * @param paymentIds
     * @return
     */
    public List<CompanyPayment> getPayments(Long[] paymentIds){
        return companyPaymentDao.getPayments(paymentIds);
    }
}

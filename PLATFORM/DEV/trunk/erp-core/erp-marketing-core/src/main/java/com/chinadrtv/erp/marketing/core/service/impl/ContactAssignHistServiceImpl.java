/*
 * @(#)ContactAssignHistServiceImpl.java 1.0 2013年8月29日上午10:08:43
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.service.impl;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.marketing.core.dto.SearchContactAssignHistDto;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.marketing.core.dao.ContactAssignHistDao;
import com.chinadrtv.erp.marketing.core.service.ContactAssignHistService;
import com.chinadrtv.erp.model.marketing.ContactAssignHist;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * @since 2013年8月29日 上午10:08:43 
 * 
 */
@Service
public class ContactAssignHistServiceImpl extends GenericServiceImpl<ContactAssignHist, Long> implements
		ContactAssignHistService {

	@Autowired
	private ContactAssignHistDao contactAssignHistDao;
	
	@Override
	protected GenericDao<ContactAssignHist, Long> getGenericDao() {
		return contactAssignHistDao;
	}

    @Override
    public Map<String, Object> findPageList(SearchContactAssignHistDto searchContactAssignHistDto, DataGridModel dataGridModel) {
        Map<String, Object> pageMap = new HashMap<String, Object>();
        pageMap.put("total", contactAssignHistDao.findCount(searchContactAssignHistDto));
        pageMap.put("rows", contactAssignHistDao.findPageList(searchContactAssignHistDto, dataGridModel.getStartRow(), dataGridModel.getRows()));
        return pageMap;
    }

    @Override
    public HSSFWorkbook generateExportFile(SearchContactAssignHistDto histDto) {
        //创建新的Excel工作蒲
        HSSFWorkbook workbook = new HSSFWorkbook();
        //Excel名称
        HSSFSheet sheet = workbook.createSheet("任务分配历史");
        //创建第一行
        HSSFRow row = sheet.createRow(0);
        //创建第一列
        HSSFCell cell = row.createCell((short)0);
        //定义单元格为字符串类型
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        //在单元格中输入一些内容
        cell.setCellValue("id");
        //创建第二列
        cell = row.createCell((short)1);
        //定义单元格为字符串类型
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        //在单元格中输入一些内容
        cell.setCellValue("营销计划");
        List<ContactAssignHist> list = contactAssignHistDao.find(histDto);
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i+1);
            cell = row.createCell((short)0);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            cell.setCellValue(list.get(i).getId());
            cell = row.createCell((short)1);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            cell.setCellValue(list.get(i).getCampaignId());
        }
        return workbook;
    }
}

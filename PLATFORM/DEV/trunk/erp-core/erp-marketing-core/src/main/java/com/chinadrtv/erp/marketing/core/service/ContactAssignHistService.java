/*
 * @(#)ContactAssignHistService.java 1.0 2013年8月29日上午10:07:50
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.service;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.marketing.core.dto.SearchContactAssignHistDto;
import com.chinadrtv.erp.model.marketing.ContactAssignHist;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

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
 * @since 2013年8月29日 上午10:07:50 
 * 
 */
public interface ContactAssignHistService extends GenericService<ContactAssignHist, Long> {

    /**
     * 根据条件分页输出符合条件的记录
     * @param searchContactAssignHistDto
     * @param dataGridModel
     * @return
     */
    Map<String, Object> findPageList(SearchContactAssignHistDto searchContactAssignHistDto, DataGridModel dataGridModel);

    /**
     * 生成导出的excel文件
     * @param histDto
     * @return
     */
    HSSFWorkbook generateExportFile(SearchContactAssignHistDto histDto);
}

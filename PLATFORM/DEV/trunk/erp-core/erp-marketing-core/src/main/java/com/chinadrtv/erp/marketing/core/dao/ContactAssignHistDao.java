/*
 * @(#)ContactAssignHistDao.java 1.0 2013年8月29日上午10:03:23
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.marketing.core.dto.SearchContactAssignHistDto;
import com.chinadrtv.erp.model.marketing.ContactAssignHist;

import java.util.List;

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
 * @since 2013年8月29日 上午10:03:23 
 * 
 */
public interface ContactAssignHistDao extends GenericDao<ContactAssignHist, Long> {
    /**
     * 根据条件分页输出
     * @param searchContactAssignHistDto
     * @param startRow
     * @param rows
     * @return
     */
    List<ContactAssignHist> findPageList(SearchContactAssignHistDto searchContactAssignHistDto, int startRow, int rows);

    /**
     * 根据条件查出所有符合条件的对象
     * @param searchContactAssignHistDto
     * @return
     */
    List<ContactAssignHist> find(SearchContactAssignHistDto searchContactAssignHistDto);

    /**
     * 查出所有符合条件的对象个数
     * @param searchContactAssignHistDto
     * @return
     */
    int findCount(SearchContactAssignHistDto searchContactAssignHistDto);
}

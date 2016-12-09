package com.chinadrtv.erp.uc.dao;

import java.util.List;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.marketing.Lead;
/**
 *  API-UC-30.	查询用户交互历史
 *  
 * @author haoleitao
 * @date 2013-5-14 下午1:36:27
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface LeadDao extends GenericDao<Lead, Long> {
    List<Lead> getAllLeadByContactId(String contactId,int index, int size);
    int getAllLeadByContactIdCount(String contactId);
}

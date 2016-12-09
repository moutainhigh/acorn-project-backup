package com.chinadrtv.erp.uc.dao;
import java.util.List;

import com.chinadrtv.erp.model.agent.Cases;
import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.agent.Cmpfback;

/**
 *  
 *  API-UC-32.	查询服务历史
 *  
 * @author haoleitao
 * @date 2013-5-13 下午3:41:19
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface CasesDao extends GenericDao<Cases,java.lang.String>{
    List<Cases> getAllCasesByContactId(String contactId,int index, int size);
    int getAllCasesByContactIdCount(String contactId);

    Cmpfback getCmpfbackByCaseId(String caseId);
}

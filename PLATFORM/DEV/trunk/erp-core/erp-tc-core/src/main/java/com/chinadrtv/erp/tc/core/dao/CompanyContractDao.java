package com.chinadrtv.erp.tc.core.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.CompanyContract;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-4-21
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface CompanyContractDao extends GenericDao<CompanyContract,Integer> {
    List<CompanyContract> findCompanyContracts(List<Integer> companyIdList);
}

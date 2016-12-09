package com.chinadrtv.erp.tc.core.service;

import com.chinadrtv.erp.tc.core.model.CompanyAssignQuantity;

import java.math.BigDecimal;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-4-22
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface CompanyAssignQuantityService {
    void updateCompanyAssignQuantity(Long companyId, Long addQuantity, BigDecimal addPrice);
    CompanyAssignQuantity getCompanyAssignQuantity(Long companyId);
    void resetCompanyAssignQuantity();
}

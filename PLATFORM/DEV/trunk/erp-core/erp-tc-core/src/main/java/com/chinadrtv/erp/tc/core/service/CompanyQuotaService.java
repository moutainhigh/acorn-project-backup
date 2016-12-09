package com.chinadrtv.erp.tc.core.service;


import com.chinadrtv.erp.tc.core.model.CompanyAllocation;

import java.util.List;

/**
 * 承运商配额服务
 * 根据承运商的配送能力和配比来分配承运商
 * User: 徐志凯
 * Date: 13-4-21
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface CompanyQuotaService {
    List<CompanyAllocation> readjustCompanysAllocation(List<CompanyAllocation> companyAllocationList);
}

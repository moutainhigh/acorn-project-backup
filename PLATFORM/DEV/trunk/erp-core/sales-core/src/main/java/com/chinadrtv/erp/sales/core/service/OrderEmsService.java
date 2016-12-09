package com.chinadrtv.erp.sales.core.service;

import com.chinadrtv.erp.model.AddressExt;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-9-5
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface OrderEmsService {
    String getEmsCompanyId(AddressExt addressExt);
    List<String> getAllEmsCompanyIds();
}

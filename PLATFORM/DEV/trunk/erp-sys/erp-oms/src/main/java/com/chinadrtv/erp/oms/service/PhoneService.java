package com.chinadrtv.erp.oms.service;

import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.Phone;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-3-28
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface PhoneService extends GenericService<Phone, Long> {
    List<Phone> findPhoneFromPhn(String phoneNumber,String phoneType);
    String findMainPhoneFromContactId(String contactId);
}

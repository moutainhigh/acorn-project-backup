package com.chinadrtv.erp.oms.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.Phone;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-3-28
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface PhoneDao extends GenericDao<Phone,Long> {
    List<Phone> findPhonesFromPHN(String phn, String phoneType);
    List<Phone> findPhonesFromContactId(String contactId);
}

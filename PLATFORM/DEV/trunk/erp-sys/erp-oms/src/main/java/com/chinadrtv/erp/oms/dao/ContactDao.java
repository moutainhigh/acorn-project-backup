package com.chinadrtv.erp.oms.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.Contact;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-5-6
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface ContactDao extends GenericDao<Contact,String> {
    List<Contact> getContactFromName(String name);
}

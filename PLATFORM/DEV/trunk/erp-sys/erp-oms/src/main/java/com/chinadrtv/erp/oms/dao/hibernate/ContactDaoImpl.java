package com.chinadrtv.erp.oms.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.Contact;
import com.chinadrtv.erp.oms.dao.ContactDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-5-6
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Repository
public class ContactDaoImpl extends GenericDaoHibernate<Contact,String> implements ContactDao {
    public ContactDaoImpl()
    {
        super(Contact.class);
    }

    public List<Contact> getContactFromName(String name)
    {
        return this.findList("from Contact where name=:name", new ParameterString("name", name));
    }
}

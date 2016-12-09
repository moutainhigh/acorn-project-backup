package com.chinadrtv.erp.tc.core.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.Contact;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liyu
 * Date: 13-1-4
 * Time: 下午3:07
 * To change this template use File | Settings | File Templates.
 */
public interface ContactDao extends GenericDao<Contact, String>  {
    Contact findByContactId(String ContactId);
    List<Contact> findByContactName(String name);
    String GetContactId();
    List<Contact> getContactFromIds(List<String> contactIdList);
}


/*
 * @(#)ContactChangeDaoImpl.java 1.0 2013-5-20下午6:10:22
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.dao.hibernate;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.ContactChange;
import com.chinadrtv.erp.uc.dao.ContactChangeDao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2013-5-20 下午6:10:22 
 * 
 */
@Repository
public class ContactChangeDaoImpl extends GenericDaoHibernate<ContactChange, Long> implements ContactChangeDao {

	public ContactChangeDaoImpl() {
		super(ContactChange.class);
	}

	/** 
	 * <p>Title: queryByTaskId</p>
	 * <p>Description: 根据流程编号查询</p>
	 * @param contactId
	 * @param taskId
	 * @return ContactChange
	 * @see com.chinadrtv.erp.uc.dao.ContactChangeDao#queryByTaskId(java.lang.String, java.lang.String)
	 */ 
	public ContactChange queryByTaskId(String contactId, String taskId) {
		String hql = "select cc from ContactChange cc where cc.contactid=:contactId and cc.procInstId=:taskId";
		return this.find(hql, new ParameterString("contactId", contactId), new ParameterString("taskId", taskId));
	}


    public List<String> findProcInstFromContactId(String contactId,Date beginDate)
    {
        Query query=this.getSession().createQuery("select procInstId from ContactChange where contactid=:contactid and createDate>:uptime");
        query.setParameter("contactid",contactId);
        query.setParameter("uptime",beginDate);

        List<String> procInstList= new ArrayList<String>();
        List list=query.list();
        for(Object str:list)
        {
            procInstList.add((String)str);
        }
        return procInstList;
    }

    @Override
    public ContactChange queryByBpmInstID(String bpmInstID) {
        String hql = "select cc from ContactChange cc where cc.procInstId=:bpmInstID";
        return this.find(hql, new ParameterString("bpmInstID", bpmInstID));
    }

}

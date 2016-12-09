/*
 * @(#)AddressExtChangeDaoImpl.java 1.0 2013-5-20下午5:41:26
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.dao.hibernate;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.AddressExtChange;
import com.chinadrtv.erp.uc.dao.AddressExtChangeDao;

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
 * @since 2013-5-20 下午5:41:26 
 * 
 */
@Repository
public class AddressExtChangeDaoImpl extends GenericDaoHibernate<AddressExtChange, Long> implements AddressExtChangeDao {

	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param persistentClass
	 */ 
	public AddressExtChangeDaoImpl() {
		super(AddressExtChange.class);
	}

	/** 
	 * <p>Title: queryByInstId</p>
	 * <p>Description: </p>
	 * @param addressId
	 * @param instId
	 * @return AddressExtChange
	 * @see com.chinadrtv.erp.uc.dao.AddressExtChangeDao#queryByInstId(java.lang.String, java.lang.String)
	 */ 
	public AddressExtChange queryByInstId(String addressId, String instId) {
		String hql = "select ac from AddressExtChange ac where ac.addressId=:addressId and ac.procInstId=:instId";
		return this.find(hql, new ParameterString("addressId", addressId), new ParameterString("instId", instId));
	}


    public List<String> findProcInstFromAddressId(String addressId,Date beginDate)
    {
        Query query=this.getSession().createQuery("select procInstId from AddressExtChange where addressId=:addressid and createDate>:uptime");
        query.setParameter("addressid",addressId);
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
    public AddressExtChange queryByBpmInstID(String bpmInstID) {
        String hql = "select ac from AddressExtChange ac where ac.procInstId=:bpmInstID";
        return this.find(hql, new ParameterString("bpmInstID", bpmInstID));
    }

}

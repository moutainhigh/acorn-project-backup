/*
 * @(#)AddressChangeDaoImpl.java 1.0 2013-5-20下午5:38:28
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.AddressChange;
import com.chinadrtv.erp.uc.dao.AddressChangeDao;

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
 * @since 2013-5-20 下午5:38:28 
 * 
 */
@Repository
public class AddressChangeDaoImpl extends GenericDaoHibernate<AddressChange, Long> implements AddressChangeDao {

	public AddressChangeDaoImpl() {
		super(AddressChange.class);
	}

	/** 
	 * <p>Title: queryByInstId</p>
	 * <p>Description: </p>
	 * @param addressId
	 * @param InstId
	 * @return AddressChange
	 * @see com.chinadrtv.erp.uc.dao.AddressChangeDao#queryByInstId(java.lang.String, java.lang.String)
	 */ 
	public AddressChange queryByInstId(String addressId, String instId) {
		String hql = "select ac from AddressChange ac where ac.addressid=:addressId and ac.procInstId=:instId";
		return this.find(hql, new ParameterString("addressId", addressId), new ParameterString("instId", instId));
	}

    @Override
    public AddressChange queryByBpmInstID(String bpmInstID) {
        String hql = "select ac from AddressChange ac where ac.procInstId=:bpmInstID";
        return this.find(hql, new ParameterString("bpmInstID", bpmInstID));
    }

}

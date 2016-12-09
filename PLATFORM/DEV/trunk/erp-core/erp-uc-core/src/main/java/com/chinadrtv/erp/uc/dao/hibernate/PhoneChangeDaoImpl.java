/*
 * @(#)PhoneChangeDaoImpl.java 1.0 2013-6-3下午1:51:35
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.ParameterLong;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.PhoneChange;
import com.chinadrtv.erp.uc.dao.PhoneChangeDao;

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
 * @since 2013-6-3 下午1:51:35 
 * 
 */
@Repository
public class PhoneChangeDaoImpl extends GenericDaoHibernate<PhoneChange, Long> implements PhoneChangeDao {

	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param persistentClass
	 */ 
	public PhoneChangeDaoImpl() {
		super(PhoneChange.class);
	}

	/** 
	 * <p>Title: queryByTaskId</p>
	 * <p>Description: </p>
	 * @param phoneId
	 * @param instId
	 * @return PhoneChange
	 * @see com.chinadrtv.erp.uc.dao.PhoneChangeDao#queryByTaskId(java.lang.Long, java.lang.String)
	 */ 
	public PhoneChange queryByTaskId(Long phoneId, String instId) {
		String hql = "select pc from PhoneChange pc where pc.phoneid=:phoneId and pc.procInstId=:instId";
		return this.find(hql, new ParameterLong("phoneId", phoneId), new ParameterString("instId", instId));
	}

    @Override
    public PhoneChange queryByBpmInstID(String bpmInstID) {
        String hql = "select pc from PhoneChange pc where pc.procInstId=:bpmInstID";
        return this.find(hql, new ParameterString("bpmInstID", bpmInstID));
    }

}

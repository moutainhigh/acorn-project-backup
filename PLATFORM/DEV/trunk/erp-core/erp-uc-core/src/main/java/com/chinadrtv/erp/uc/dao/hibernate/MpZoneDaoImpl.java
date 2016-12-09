/*
 * @(#)MpZoneDaoImpl.java 1.0 2013-6-5上午9:48:09
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.agent.MpZone;
import com.chinadrtv.erp.uc.dao.MpZoneDao;

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
 * @since 2013-6-5 上午9:48:09 
 * 
 */
@Repository
public class MpZoneDaoImpl extends GenericDaoHibernate<MpZone, String> implements MpZoneDao {
 
	public MpZoneDaoImpl() {
		super(MpZone.class);
	}

}

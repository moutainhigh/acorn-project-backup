package com.chinadrtv.erp.sales.core.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.agent.Telephone;

/**
 * Created with (TC).
 * User: zhaizhanyi
 * Date: 13-5-22
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface TelephoneDao extends GenericDao<Telephone,String> {
	
	public Telephone query(String ip);
}

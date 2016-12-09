package com.chinadrtv.erp.core.dao;
import com.chinadrtv.erp.model.Ems;
import com.chinadrtv.erp.core.dao.GenericDao;

/**
 * EmsDao
 *  
 * @author haoleitao
 * @date 2013-3-4 下午2:52:24
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface EmsDao extends GenericDao<Ems,java.lang.Integer>{
	String getCityNameById(String cityId);

    Ems getEmsBySpellid(Integer spellid);
}

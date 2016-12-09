package com.chinadrtv.erp.oms.dao;
import java.util.List;

import com.chinadrtv.erp.model.Cardtype;
import com.chinadrtv.erp.core.dao.GenericDao;


public interface CardtypeDao extends GenericDao<Cardtype,java.lang.String>{
	public List getAllCardtype();
    List<String> getCreditCardNames();
}

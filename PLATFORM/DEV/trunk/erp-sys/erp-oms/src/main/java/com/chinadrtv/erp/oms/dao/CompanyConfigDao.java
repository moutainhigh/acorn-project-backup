package com.chinadrtv.erp.oms.dao;
import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.Company;
import com.chinadrtv.erp.model.CompanyConfig;


public interface CompanyConfigDao extends GenericDao<CompanyConfig,java.lang.String>{
	public CompanyConfig getCompanyConfigByID(String id);
}

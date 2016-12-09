package com.chinadrtv.erp.oms.dao;
import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.model.Company;
import com.chinadrtv.erp.core.dao.GenericDao;

/**
 * CompanyDao
 *  
 * @author haoleitao
 * @date 2013-3-4 下午2:52:11
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface CompanyDao extends GenericDao<Company,java.lang.String>{
	public String getCompanyNameByID(String id);
	public Map<String,String> getAllCompanytoMap();
    public List<Company> getWarehouseCompanies(Long warehouseId);
	public Company getCompanyById(String companyId);
	public List<Company> getAllCompanies();
	public List<Company> getAllCompanies(int index, int size);
	public List<Company> getAllCompanies(String companyName, int index, int size);
	public List<Company> getAllCompanies(String companyName, String companyType, String mailType, int index, int size);
	public int getCompanyCount();
	public int getCompanyCount(String companyName, String companyType, String mailType);
	public void saveOrUpdate(Company company);
	public List<Company> getAllCompaniesForManual();
}

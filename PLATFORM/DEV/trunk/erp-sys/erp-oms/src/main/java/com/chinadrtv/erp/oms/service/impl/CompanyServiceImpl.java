package com.chinadrtv.erp.oms.service.impl;
import com.chinadrtv.erp.model.Company;
import com.chinadrtv.erp.oms.service.CompanyService;
import com.chinadrtv.erp.oms.dao.CompanyDao;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughSingleCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * 送货公司服务
 *  
 * @author haoleitao
 * @date 2013-3-4 下午2:46:57
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */

@Service("company")
public class CompanyServiceImpl implements CompanyService{

    @Autowired
    private CompanyDao companyDao;


    public Company getCompanyById(String companyId) {
        return companyDao.getCompanyById(companyId);
    }

    public List<Company> getAllCompanies() {
        return companyDao.getAllCompanies();
    }

    public List<Company> getAllCompanies(int index, int size) {
        return companyDao.getAllCompanies(index, size);
    }
    public List<Company> getAllCompanies(String companyName, int index, int size)
    {
        return companyDao.getAllCompanies(companyName, index, size);
    }

    public List<Company> getAllCompanies(String companyName, String companyType, String mailType, int index, int size)
    {
        return companyDao.getAllCompanies(companyName, companyType, mailType, index, size);
    }

    public List<Company> getWarehouseCompanies(Long warehouseId){
        return companyDao.getWarehouseCompanies(warehouseId);
    }

    public int getCompanyCount()
    {
        return companyDao.getCompanyCount();
    }

    public int getCompanyCount(String companyName, String companyType, String mailType)
    {
        return companyDao.getCompanyCount(companyName, companyType, mailType);
    }

    public void saveCompany(Company company)
    {
        companyDao.saveOrUpdate(company);
    }

    public void addCompany(Company company)
    {
        companyDao.save(company);
    }

    public void removeCompany(String company)
    {
        companyDao.remove(company);
    }



	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.oms.service.CompanyService#getCompanyByID(java.lang.String)
	 */
	@ReadThroughSingleCache(namespace = "com.chinadrtv.erp.oms.model.Company",expiration=3600)
	public String getCompanyNameByID(@ParameterValueKeyProvider String id){
		// TODO Auto-generated method stub
		return companyDao.getCompanyNameByID(id);
	}
	
	public Map<String,String> getAllCompanytoMap(){
		return companyDao.getAllCompanytoMap();
	}

	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.core.service.CompanyService#getCompanyByID(java.lang.String)
	 */
	public Company getCompanyByID(String companyId) {
		// TODO Auto-generated method stub
		return companyDao.getCompanyById(companyId);
	}

	/**
	* <p>Title: getAllCompaniesForManual</p>
	* <p>Description: </p>
	* @return
	* @see com.chinadrtv.erp.oms.service.CompanyService#getAllCompaniesForManual()
	*/ 
	public List<Company> getAllCompaniesForManual() {
		return companyDao.getAllCompaniesForManual();
	}


}

package com.chinadrtv.erp.oms.service;
import com.chinadrtv.erp.model.Company;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughSingleCache;

import java.util.*;
/**
 * 送货公司服务
 *  
 * @author haoleitao
 * @date 2013-3-4 下午2:45:48
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface CompanyService{
	String getCompanyNameByID(String id);
	Company getCompanyByID(String companyId);
    public Map<String,String> getAllCompanytoMap();
    List<Company> getAllCompanies();
    List<Company> getAllCompanies(int index, int size);
    List<Company> getAllCompanies(String companyName, int index, int size);
    List<Company> getAllCompanies(String companyName, String companyType, String mailType, int index, int size);
    List<Company> getWarehouseCompanies(Long warehouseId);
    int getCompanyCount();
    int getCompanyCount(String companyName, String companyType, String mailType);
    void saveCompany(Company company);
    void addCompany(Company company);
    void removeCompany(String company);
    public List<Company> getAllCompaniesForManual();
}

package com.chinadrtv.erp.admin.service;


import com.chinadrtv.erp.admin.model.Company;

import java.util.List;

/**
 * User: gaodejian
 * Date: 12-8-10
 */
public interface CompanyService {
    Company getCompanyById(String companyId);
    List<Company> getAllCompanies();
    List<Company> getAllCompanies(int index, int size);
    List<Company> getAllCompanies(String companyName, int index, int size);
    List<Company> getAllCompanies(String companyName, String companyType, String mailType, int index, int size);
    int getCompanyCount();
    int getCompanyCount(String companyName, String companyType, String mailType);
    void saveCompany(Company company);
    void addCompany(Company company);
    void removeCompany(String company);
}

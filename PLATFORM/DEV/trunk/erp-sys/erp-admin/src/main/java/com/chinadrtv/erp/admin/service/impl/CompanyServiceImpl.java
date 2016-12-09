package com.chinadrtv.erp.admin.service.impl;

import com.chinadrtv.erp.admin.dao.*;
import com.chinadrtv.erp.admin.model.*;
import com.chinadrtv.erp.admin.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: gaodejian
 * Date: 12-8-10
 */
@Service("companyService")
public class CompanyServiceImpl implements CompanyService {
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
}

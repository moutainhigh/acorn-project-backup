package com.chinadrtv.erp.admin.dao;


import com.chinadrtv.erp.admin.model.*;
import com.chinadrtv.erp.core.dao.GenericDao;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gaodejian
 * Date: 12-8-10
 * Time: 上午10:12
 * To change this template use File | Settings | File Templates.
 */
public interface CompanyDao extends GenericDao<Company, String> {
    Company getCompanyById(String companyId);
    List<Company> getAllCompanies();
    List<Company> getAllCompanies(int index, int size);
    List<Company> getAllCompanies(String companyName, int index, int size);
    List<Company> getAllCompanies(String companyName, String companyType, String mailType, int index, int size);
    int getCompanyCount();
    int getCompanyCount(String companyName, String companyType, String mailType);
    void saveOrUpdate(Company company);
}

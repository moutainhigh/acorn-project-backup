package com.chinadrtv.erp.tc.core.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.Company;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 12-12-26
 * To change this template use File | Settings | File Templates.
 */
public interface CompanyDao extends GenericDao<Company, String> {
    //Company getCompany(String companyId);

    List<Company> getAllCompany();

    Company getCompany(String companyId);

    List<Company> getCompanys(List<String> companyIdList);
}

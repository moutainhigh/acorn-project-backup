package com.chinadrtv.erp.tc.core.service;

import java.util.List;

import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.Company;


/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 13-1-10
 * To change this template use File | Settings | File Templates.
 */
public interface CompanyService extends GenericService<Company, String> {
    Company findCompany(String companyId);
    
    List<Company> getAllCompany();
}

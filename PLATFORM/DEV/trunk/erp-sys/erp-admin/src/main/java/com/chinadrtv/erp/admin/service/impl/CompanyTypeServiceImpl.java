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
@Service("companyTypeService")
public class CompanyTypeServiceImpl implements CompanyTypeService {
    @Autowired
    private CompanyTypeDao dao;

    public CompanyType findById(String id){
        return dao.getNativeById(id);
    }

    public List<CompanyType> getAllCompanyTypes() {
        return dao.getAllCompanyTypes();
    }


}

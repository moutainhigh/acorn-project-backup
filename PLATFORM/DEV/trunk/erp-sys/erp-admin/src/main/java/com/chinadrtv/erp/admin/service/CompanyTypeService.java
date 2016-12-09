package com.chinadrtv.erp.admin.service;


import com.chinadrtv.erp.admin.model.*;
import java.util.*;

/**
 * User: gaodejian
 * Date: 12-8-10
 */
public interface CompanyTypeService {
    CompanyType findById(String id);
    List<CompanyType> getAllCompanyTypes();
}

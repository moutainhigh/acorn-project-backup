package com.chinadrtv.erp.tc.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.Company;
import com.chinadrtv.erp.tc.core.dao.CompanyDao;
import com.chinadrtv.erp.tc.core.service.CompanyService;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 13-1-10
 * To change this template use File | Settings | File Templates.
 */
@Service
public class CompanyServiceImpl extends GenericServiceImpl<Company,String> implements CompanyService {
    @Autowired
    private CompanyDao companyDao;

    @Override
    protected GenericDao<Company,String> getGenericDao(){
        return companyDao;
    }

    public Company findCompany(String companyId)
    {
        if(companyId==null||"".equals(companyId))
            return null;

        return companyDao.getCompany(companyId);
        /*List<Company> companyList=companyDao.getAllCompany();
        if(companyList==null)
        {
            return null;
        }

        for(Company company:companyList)
        {
            if(companyId.equals(company.getCompanyid()))
                return company;
        }
        return null;*/
    }

	/* (none Javadoc)
	* <p>Title: getAllCompany</p>
	* <p>Description: </p>
	* @return
	* @see com.chinadrtv.erp.shipment.service.CompanyService#getAllCompany()
	*/ 
	public List<Company> getAllCompany() {
		return companyDao.getAllCompany();
	}
}

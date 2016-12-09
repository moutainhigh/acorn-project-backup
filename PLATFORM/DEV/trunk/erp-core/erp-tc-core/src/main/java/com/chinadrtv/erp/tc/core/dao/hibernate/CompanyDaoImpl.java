package com.chinadrtv.erp.tc.core.dao.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughSingleCache;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.Company;
import com.chinadrtv.erp.tc.core.constant.cache.CacheNames;
import com.chinadrtv.erp.tc.core.dao.CompanyDao;
import com.chinadrtv.erp.tc.core.utils.BulkListSplitter;
import com.google.code.ssm.api.ReadThroughAssignCache;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 12-12-26
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class CompanyDaoImpl extends GenericDaoHibernateBase<Company,String> implements CompanyDao {
    public CompanyDaoImpl()
    {
        super(Company.class);
    }

    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }


    @ReadThroughAssignCache(namespace= CacheNames.CACHE_COMPANY, assignedKey = CacheNames.All)
    public List<Company> getAllCompany()
    {
        return this.getAll();
    }

    @ReadThroughSingleCache(namespace = CacheNames.CACHE_COMPANY, expiration=3000)
    public Company getCompany(@ParameterValueKeyProvider String companyId)
    {
        return this.find("from Company where companyid=:companyid", new ParameterString("companyid",companyId));
        //return this.get(companyId);
    }

    public List<Company> getCompanys(List<String> companyIdList)
    {
        BulkListSplitter<String> listSplitter=new BulkListSplitter<String>(50,"Company","companyid");
        List<List<String>> listList=listSplitter.splitList(companyIdList);

        List<Company> companyList=new ArrayList<Company>();

        for(List<String> companyIds:listList)
        {
            Map<String,String> parmsValue=new HashMap<String,String>();
            String hql=listSplitter.getHql(companyIds,parmsValue);

            Map<String,Parameter> parms= new HashMap<String,Parameter>();
            for(Map.Entry<String,String> entry : parmsValue.entrySet())
            {
                parms.put(entry.getKey(),new ParameterString(entry.getKey(),entry.getValue()));
            }
            companyList.addAll(this.findList(hql,parms));
        }
        return companyList;
    }

    /*private List<Company> getCompanyList(List<String> companyIdList)
    {
        StringBuilder hql=new StringBuilder("from Company where ");
        Map<String,Parameter> parms=new HashMap<String,Parameter>();

        for(int i=0;i<companyIdList.size();i++)
        {
            if(i>0)
            {
                hql.append(" or ");
            }
            ParameterString parm=new ParameterString("id"+i,companyIdList.get(i));
            parms.put(parm.getName(),parm);
            hql.append("companyid=:"+parm.getName());
        }

        return this.findList(hql.toString() , parms);
        //return null;
    } */
}

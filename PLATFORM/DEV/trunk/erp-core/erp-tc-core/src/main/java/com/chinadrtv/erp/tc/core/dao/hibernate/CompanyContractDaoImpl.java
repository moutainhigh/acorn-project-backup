package com.chinadrtv.erp.tc.core.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.model.CompanyContract;
import com.chinadrtv.erp.model.OrderChannel;
import com.chinadrtv.erp.model.Warehouse;
import com.chinadrtv.erp.tc.core.constant.cache.CacheNames;
import com.chinadrtv.erp.tc.core.dao.CompanyContractDao;
import com.chinadrtv.erp.tc.core.utils.BulkListSplitter;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughMultiCache;
import com.google.code.ssm.api.ReadThroughMultiCacheOption;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-4-21
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Repository
public class CompanyContractDaoImpl extends GenericDaoHibernateBase<CompanyContract,Integer> implements CompanyContractDao {
    public CompanyContractDaoImpl()
    {
        super(CompanyContract.class);
    }

    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }

    @ReadThroughMultiCache(namespace = CacheNames.CACHE_COMPANY_CONTRACT, expiration =3000, option=@ReadThroughMultiCacheOption(generateKeysFromResult=true))
    public List<CompanyContract> findCompanyContracts(@ParameterValueKeyProvider List<Integer> companyIdList)
    {
        if(companyIdList==null || companyIdList.size() == 0)
        {
            return new ArrayList<CompanyContract>();
        }

        List<CompanyContract> companyContractList = new ArrayList<CompanyContract>();
        BulkListSplitter<Integer> bulkListSplitter=new BulkListSplitter<Integer>(50,"CompanyContract","ID");
        List<List<Integer>> bulkIdList = bulkListSplitter.splitList(companyIdList);
        for(List<Integer> idList : bulkIdList)
        {
            Map<String,Integer> mapParms =new HashMap<String, Integer>();
            String hql = bulkListSplitter.getHql(idList, mapParms);

            companyContractList.addAll(getCompanyContracts(hql,mapParms));
        }

        List<CompanyContract> returnCompanyContractList=new ArrayList<CompanyContract>();
        if(companyContractList!=null)
        {
            for(CompanyContract companyContract:companyContractList)
            {
                CompanyContract companyContract1=new CompanyContract();
                BeanUtils.copyProperties(companyContract,companyContract1);
                if(companyContract.getWarehouse()!=null)
                {
                    companyContract1.setWarehouse(new Warehouse());
                    companyContract1.getWarehouse().setWarehouseId(companyContract.getWarehouse().getWarehouseId());
                }
                if(companyContract.getChannel()!=null)
                {
                    companyContract1.setChannel(new OrderChannel());
                    companyContract1.getChannel().setId(companyContract.getChannel().getId());
                }

                returnCompanyContractList.add(companyContract1);
            }
        }
        return returnCompanyContractList;
    }

    private List<CompanyContract> getCompanyContracts(String hql, Map<String,Integer> mapParms)
    {
        Query query = this.getSession().createQuery(hql);
        for(Map.Entry<String, Integer> entry : mapParms.entrySet())
        {
            query.setParameter(entry.getKey(),entry.getValue());
        }

        return query.list();
    }

}

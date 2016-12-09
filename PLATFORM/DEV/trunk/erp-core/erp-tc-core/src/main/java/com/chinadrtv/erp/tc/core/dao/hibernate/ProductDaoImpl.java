package com.chinadrtv.erp.tc.core.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.model.CompanyContract;
import com.chinadrtv.erp.model.agent.Product;
import com.chinadrtv.erp.tc.core.constant.cache.CacheNames;
import com.chinadrtv.erp.tc.core.dao.ProductDao;
import com.chinadrtv.erp.tc.core.utils.BulkListSplitter;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughMultiCache;
import com.google.code.ssm.api.ReadThroughMultiCacheOption;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
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
 * Date: 13-5-3
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Repository("com.chinadrtv.erp.tc.core.dao.hibernate.ProductDaoImpl")
public class ProductDaoImpl extends GenericDaoHibernateBase<Product, String> implements ProductDao {
    public ProductDaoImpl()
    {
        super(Product.class);
    }

    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }

    @ReadThroughMultiCache(namespace = CacheNames.CACHE_PRODUCT, expiration =3000, option=@ReadThroughMultiCacheOption(generateKeysFromResult=true))
    public List<Product> getProductsFromProdIds(@ParameterValueKeyProvider List<String> prodIdList)
    {
        if(prodIdList==null || prodIdList.size() == 0)
        {
            return new ArrayList<Product>();
        }

        List<Product> productList = new ArrayList<Product>();
        BulkListSplitter<String> bulkListSplitter=new BulkListSplitter<String>(50,"Product","prodid");
        List<List<String>> bulkIdList = bulkListSplitter.splitList(prodIdList);
        for(List<String> idList : bulkIdList)
        {
            Map<String,String> mapParms =new HashMap<String, String>();
            String hql = bulkListSplitter.getHql(idList, mapParms);

            productList.addAll(getProducts(hql, mapParms));
        }

        return productList;
    }

    private List<Product> getProducts(String hql, Map<String,String> mapParms)
    {
        Query query = this.getSession().createQuery(hql);
        for(Map.Entry<String, String> entry : mapParms.entrySet())
        {
            query.setParameter(entry.getKey(),entry.getValue());
        }

        return query.list();
    }
}

package com.chinadrtv.erp.oms.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.agent.CityAll;
import com.chinadrtv.erp.oms.dao.CityDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 城市数据访问
 * User: gaodejian
 * Date: 12-8-10
 * Time: 上午10:19
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class CityDaoImpl extends GenericDaoHibernate<CityAll, Long> implements CityDao {

    public CityDaoImpl() {
        super(CityAll.class);
    }

    public List<CityAll> getAllCities()
    {
        Session session = getSession();
        Query query = session.createQuery("from CityAll");
        return query.list();
    }

    public  List<CityAll> getCities(String provinceId)
    {
        Session session = getSession();
        Query query = session.createQuery("from CityAll c where c.provid = '"+provinceId+"'");
        return query.list();
    }

}

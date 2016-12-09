package com.chinadrtv.erp.admin.dao.hibernate;

import com.chinadrtv.erp.admin.dao.*;
import com.chinadrtv.erp.admin.model.*;
import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gaodejian
 * Date: 12-8-10
 * Time: 上午10:19
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class CityDaoImpl extends GenericDaoHibernate<City, Long> implements CityDao {

    public CityDaoImpl() {
        super(City.class);
    }

    public List<City> getAllCities()
    {
        Session session = getSession();
        Query query = session.createQuery("from City");
        return query.list();
    }

    public  List<City> getCities(String provinceId)
    {
        Session session = getSession();
        Query query = session.createQuery("from City c where c.province.id = '"+provinceId+"'");
        return query.list();
    }

}
